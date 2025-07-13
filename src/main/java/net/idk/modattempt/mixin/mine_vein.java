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
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.idk.modattempt.util.KeyInputHandler;
import net.idk.modattempt.util.BlockDir_for_mine_vein;


import java.util.*;



@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerInteractionManager.class)

public class mine_vein {
    @Unique
    private static final int MAX_BLOCKS = 900;
    @Unique
    private static final int MAX_BLOCKS_x = MAX_BLOCKS/3;
    @Unique
    private static final int MAX_BLOCKS_y = MAX_BLOCKS/3;
    @Unique
    private static final int MAX_BLOCKS_z = MAX_BLOCKS/3;
    @Unique
    private final Set<BlockPos> collectedBlocks = new HashSet<>();
    @Unique
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
        Map<BlockPos, BlockDir_for_mine_vein> directionsx = new HashMap<>();
        Map<BlockPos, BlockDir_for_mine_vein> directionsy = new HashMap<>();
        Map<BlockPos, BlockDir_for_mine_vein> directionsz = new HashMap<>();

        getPos_x(world, pos, centerState, collectedBlocks, directionsx);
        getPos_y(world, pos, centerState, collectedBlocks, directionsy);
        getPos_z(world, pos, centerState, collectedBlocks, directionsz);
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

    @Unique
    BlockDir_for_mine_vein blockDir_origin = new BlockDir_for_mine_vein(false, false);
    @Unique
    private void getPos_x(ClientWorld world, BlockPos origin, BlockState centerState, Set<BlockPos> sharedSet,Map<BlockPos, BlockDir_for_mine_vein> directions) {
        int count = 0;
        Queue<BlockPos> toCheck = new LinkedList<>();

        BlockPos posBeingChecked = origin;
        toCheck.add(posBeingChecked);
        directions.put(posBeingChecked, blockDir_origin);
        boolean dir1; //directions.get(posBeingChecked).dir1;
        boolean dir2; //directions.get(posBeingChecked).dir2;

        int[] x;
        int[] if_checking_rightside_only = {1};
        int[] if_checking_leftside_only = {-1};
        int[] if_checking_both_sides = {-1,1};




        while (!toCheck.isEmpty()) {
            posBeingChecked = toCheck.poll();
            dir1 = directions.get(posBeingChecked).dir1;
            dir2 = directions.get(posBeingChecked).dir2;
            if (dir1 && !dir2)
                x = if_checking_rightside_only;
            else if (!dir1 && dir2)
                x = if_checking_leftside_only;
            else
                x = if_checking_both_sides;

            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {

                    for (int dx : x) {
                        BlockPos checkPos = posBeingChecked.add(dx, dy, dz);
                        if (!sharedSet.contains(checkPos)
                                && world.getBlockState(checkPos).isOf(centerState.getBlock())) {
                            sharedSet.add(checkPos);
                            count++;
                            toCheck.add(checkPos);
                            if (dx == 1)
                                directions.put(checkPos, new BlockDir_for_mine_vein(true, false));  // moving right
                            else if (dx == -1)
                                directions.put(checkPos, new BlockDir_for_mine_vein(false, true));  // moving left


                            if (count >= MAX_BLOCKS_x) return;
                        }
                    }
                }
            }
        }
    }


    @Unique
    private void getPos_y(ClientWorld world, BlockPos origin, BlockState centerState, Set<BlockPos> sharedSet, Map<BlockPos, BlockDir_for_mine_vein> directions) {
        int count = 0;
        Queue<BlockPos> toCheck = new LinkedList<>();

        BlockPos posBeingChecked = origin;
        toCheck.add(posBeingChecked);
        directions.put(posBeingChecked, blockDir_origin);
        boolean dir1;//directions.get(posBeingChecked).dir1;
        boolean dir2;//directions.get(posBeingChecked).dir2;

        int[] y;
        int[] if_checking_rightside_only = {1};
        int[] if_checking_leftside_only = {-1};
        int[] if_checking_both_sides = {-1,1};


        while (!toCheck.isEmpty()) {
            posBeingChecked = toCheck.poll();
            dir1 = directions.get(posBeingChecked).dir1;
            dir2 = directions.get(posBeingChecked).dir2;
            if (dir1 && !dir2)
                y = if_checking_rightside_only;
            else if (!dir1 && dir2)
                y = if_checking_leftside_only;
            else
                y = if_checking_both_sides;

            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    for (int dy : y) {
                        BlockPos checkPos = posBeingChecked.add(dx, dy, dz);
                        if (!sharedSet.contains(checkPos)
                                && world.getBlockState(checkPos).isOf(centerState.getBlock())) {
                            sharedSet.add(checkPos);
                            count++;
                            toCheck.add(checkPos);
                            if (dy == 1)
                                directions.put(checkPos, new BlockDir_for_mine_vein(true, false));  // moving right
                            else if (dy == -1)
                                directions.put(checkPos, new BlockDir_for_mine_vein(false, true));  // moving left


                            if (count >= MAX_BLOCKS_y) return;
                        }
                    }
                }
            }
        }
    }
    @Unique
    private void getPos_z(ClientWorld world, BlockPos origin, BlockState centerState, Set<BlockPos> sharedSet, Map<BlockPos, BlockDir_for_mine_vein> directions) {
        int count = 0;
        Queue<BlockPos> toCheck = new LinkedList<>();


        BlockPos posBeingChecked = origin;
        toCheck.add(posBeingChecked);
        directions.put(posBeingChecked, blockDir_origin);
        boolean dir1; //directions.get(origin).dir1;
        boolean dir2; //directions.get(origin).dir2;

        int[] z;
        int[] if_checking_rightside_only = {1};
        int[] if_checking_leftside_only = {-1};
        int[] if_checking_both_sides = {-1,1};


        while (!toCheck.isEmpty()) {
            posBeingChecked = toCheck.poll();
            dir1 = directions.get(posBeingChecked).dir1;
            dir2 = directions.get(posBeingChecked).dir2;
            if (dir1 && !dir2)
                z = if_checking_rightside_only;
            else if (!dir1 && dir2)
                z = if_checking_leftside_only;
            else
                z = if_checking_both_sides;

            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dz : z) {
                        BlockPos checkPos = posBeingChecked.add(dx, dy, dz);
                        if (!sharedSet.contains(checkPos)
                                && world.getBlockState(checkPos).isOf(centerState.getBlock())) {
                            sharedSet.add(checkPos);
                            count++;
                            toCheck.add(checkPos);
                            if (dz == 1)
                                directions.put(checkPos, new BlockDir_for_mine_vein(true, false));  // moving right
                            else if (dz == -1)
                                directions.put(checkPos, new BlockDir_for_mine_vein(false, true));  // moving left


                            if (count >= MAX_BLOCKS_z) return;
                        }
                    }
                }
            }
        }
    }



}
