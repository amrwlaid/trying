package net.idk.modattempt.util;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class ModNetworking {
    public static final Identifier VEIN_MINE = new Identifier("modattempt", "vein_mine");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(VEIN_MINE, (server, player, handler, buf, responseSender) -> {
            List<BlockPos> positions = VeinMinePacket.read(buf);

            server.execute(() -> {
                for (BlockPos pos : positions) {
                    if (player.getServerWorld().getBlockState(pos).isAir()) continue;
                    player.getServerWorld().breakBlock(pos, true, player);
                }
            });
        });
    }
}
