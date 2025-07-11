// ===== FILE: net/idk/modattempt/mixin/mine_vein.java =====
package net.idk.modattempt.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.idk.modattempt.util.ModNetworking;
import net.idk.modattempt.util.VeinMinePacket;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.idk.modattempt.util.KeyInputHandler;


import java.util.*;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerInteractionManager.class)
public class mine_vein {

    private static final int MAX_BLOCKS = 100;
    private final Set<BlockPos> collectedBlocks = new HashSet<>();
    private BlockState lastScannedState = null;

    @Inject(method = "updateBlockBreakingProgress", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/BlockState;calcBlockBreakingDelta(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)F"
    ))
    private void scanMatchingBlocks(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (!KeyInputHandler.veinMineEnabled) return;


        ClientWorld world = MinecraftClient.getInstance().world;
        if (world == null) return;

        BlockState centerState = world.getBlockState(pos);
        if (centerState.isAir()) return;

        collectedBlocks.clear();
        collectedBlocks.add(pos);
        lastScannedState = centerState;

        getPos_x(world, pos, centerState, collectedBlocks);
        if (collectedBlocks.size() >= MAX_BLOCKS) return;

        getPos_y(world, pos, centerState, collectedBlocks);
        if (collectedBlocks.size() >= MAX_BLOCKS) return;

        getPos_z(world, pos, centerState, collectedBlocks);
    }

    @Inject(method = "updateBlockBreakingProgress", at = @At("RETURN"))
    private void breakScannedBlocksWhenBroken(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        ClientWorld world = MinecraftClient.getInstance().world;
        if (world == null || lastScannedState == null) return;

        if (world.getBlockState(pos).isAir()) {
            List<BlockPos> toSend = new ArrayList<>(collectedBlocks);
            PacketByteBuf buf = new PacketByteBuf(io.netty.buffer.Unpooled.buffer());
            VeinMinePacket.write(buf, toSend);

            ClientPlayNetworking.send(ModNetworking.VEIN_MINE, buf);

            collectedBlocks.clear();
            lastScannedState = null;
        }
    }

    private void getPos_x(ClientWorld world, BlockPos origin, BlockState centerState, Set<BlockPos> sharedSet) {
        Queue<BlockPos> toCheck = new LinkedList<>();
        toCheck.add(origin);

        while (!toCheck.isEmpty() && sharedSet.size() < MAX_BLOCKS) {
            BlockPos current = toCheck.poll();

            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    for (int dx : new int[]{-1, 1}) {
                        BlockPos checkPos = current.add(dx, dy, dz);
                        if (!sharedSet.contains(checkPos)
                                && world.getBlockState(checkPos).isOf(centerState.getBlock())) {
                            sharedSet.add(checkPos);
                            toCheck.add(checkPos);
                            if (sharedSet.size() >= MAX_BLOCKS) return;
                        }
                    }
                }
            }
        }
    }

    private void getPos_y(ClientWorld world, BlockPos origin, BlockState centerState, Set<BlockPos> sharedSet) {
        Queue<BlockPos> toCheck = new LinkedList<>();
        toCheck.add(origin);

        while (!toCheck.isEmpty() && sharedSet.size() < MAX_BLOCKS) {
            BlockPos current = toCheck.poll();

            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    for (int dy : new int[]{-1, 1}) {
                        BlockPos checkPos = current.add(dx, dy, dz);
                        if (!sharedSet.contains(checkPos)
                                && world.getBlockState(checkPos).isOf(centerState.getBlock())) {
                            sharedSet.add(checkPos);
                            toCheck.add(checkPos);
                            if (sharedSet.size() >= MAX_BLOCKS) return;
                        }
                    }
                }
            }
        }
    }

    private void getPos_z(ClientWorld world, BlockPos origin, BlockState centerState, Set<BlockPos> sharedSet) {
        Queue<BlockPos> toCheck = new LinkedList<>();
        toCheck.add(origin);

        while (!toCheck.isEmpty() && sharedSet.size() < MAX_BLOCKS) {
            BlockPos current = toCheck.poll();

            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dz : new int[]{-1, 1}) {
                        BlockPos checkPos = current.add(dx, dy, dz);
                        if (!sharedSet.contains(checkPos)
                                && world.getBlockState(checkPos).isOf(centerState.getBlock())) {
                            sharedSet.add(checkPos);
                            toCheck.add(checkPos);
                            if (sharedSet.size() >= MAX_BLOCKS) return;
                        }
                    }
                }
            }
        }
    }
}
