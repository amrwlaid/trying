package net.idk.modattempt.Items.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.idk.modattempt.Items.ModBlockEntities;

public class MinerBlockEntity extends BlockEntity {

    private int fuel = 0;

    public MinerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MINER_BLOCK_ENTITY, pos, state);
    }

    public void addFuel(int amount) {
        fuel += amount;
        markDirty();
    }

    public boolean consumeFuel() {
        if (fuel > 0) {
            fuel--;
            markDirty();
            return true;
        }
        return false;
    }

    public int getFuel() {
        return fuel;
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (!world.isClient && world.getTime() % 20 == 0) {
            consumeFuel();
        }
    }
}

