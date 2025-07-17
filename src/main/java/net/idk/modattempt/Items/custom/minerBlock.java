package net.idk.modattempt.Items.custom;


import net.idk.modattempt.Items.ModBlockEntities;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class minerBlock extends BlockWithEntity {


    public minerBlock(Settings settings) {
        super(settings);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MinerBlockEntity(pos, state);
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        BlockEntity be = builder.getOptional(LootContextParameters.BLOCK_ENTITY);

        ItemStack drop = new ItemStack(this);
        MinerBlockEntity miner = (MinerBlockEntity) be;
        if (be instanceof MinerBlockEntity) {
            NbtCompound tag = new NbtCompound();
            tag.putInt("fuel", miner.getFuel());
            drop.setSubNbt("BlockEntityTag", tag);
            Inventories.writeNbt(tag, miner.getItems());
        }

        return List.of(drop);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        NbtCompound nbt = stack.getSubNbt("BlockEntityTag");
        int fuel = 0;
        if (nbt != null && nbt.contains("fuel"))
            fuel = nbt.getInt("fuel");

        tooltip.add(Text.translatable("tooltip.modattempt.autominer.fueltooltip", fuel));

        super.appendTooltip(stack, world, tooltip, options);


    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,
                              Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof MinerBlockEntity) {
                player.openHandledScreen((MinerBlockEntity) be);
            }
        }
        return ActionResult.SUCCESS;
    }


    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            World world,
            BlockState state,
            BlockEntityType<T> type
    ) {
        return world.isClient ? null : checkType(
                type,
                ModBlockEntities.MINER_BLOCK_ENTITY,
                (w, pos, s, be) -> ((MinerBlockEntity) be).tick(w, pos, s)
        );
    }



    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }






}
