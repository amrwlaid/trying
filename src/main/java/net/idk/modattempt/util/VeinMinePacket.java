package net.idk.modattempt.util;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class VeinMinePacket {
    public static void write(PacketByteBuf buf, List<BlockPos> positions) {
        buf.writeInt(positions.size());
        for (BlockPos pos : positions) {
            buf.writeBlockPos(pos);
        }
    }

    public static List<BlockPos> read(PacketByteBuf buf) {
        int size = buf.readInt();
        List<BlockPos> positions = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            positions.add(buf.readBlockPos());
        }
        return positions;
    }
}
