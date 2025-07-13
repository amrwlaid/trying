package net.idk.modattempt.Items.custom;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;

import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;

import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;


public class custom_PickaxeItem extends MiningToolItem {
    boolean extra_function;
    int width_of_area_toBeBroken = 3;
    int hight_of_area_toBeBroken = 3;



    public custom_PickaxeItem(ToolMaterial material, int attackDamage, float attackSpeed, Item.Settings settings, boolean extra_function) {
        super(attackDamage, attackSpeed, material, BlockTags.PICKAXE_MINEABLE, settings);
        this.extra_function = extra_function;
    }

    public void toggle_extra_function(){
        this.extra_function = !this.extra_function;
    }

    public void increment_x(){
        this.width_of_area_toBeBroken+=2;
    }

    public void increment_y(){
        this.hight_of_area_toBeBroken+=2;
    }





    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (!world.isClient && state.getHardness(world, pos) != 0.0F) {
            stack.damage(1, miner, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        }
        int x = this.width_of_area_toBeBroken;
        int y = this.hight_of_area_toBeBroken;


        if (this.extra_function && !(x == 0) && !(y == 0)){
            break_XbyY_Area(state, pos, world, stack, miner, x, y);
        }

        return true;
    }

    public void break_XbyY_Area(BlockState state, BlockPos pos,  World world, ItemStack stack, LivingEntity miner, int x, int y){
        Direction facing_Dir = Direction.getEntityFacingOrder(miner)[0];
        Block referenceBlock = state.getBlock();
        int dx = 0;
        int dy = 0;
        int dz = 0;
        int max_dx = 0;
        int max_dy = 0;
        int max_dz = 0;
        System.out.println("inside break_XbyY_Area");



        switch (facing_Dir){
            case UP:
            case DOWN: dx = -(x/2);
            dz = -(y/2);
            max_dx = x/2;
            max_dz = y/2;
            break;
            case EAST:
            case WEST: dz = -(x/2);
            dy = -(y/2);
            max_dz = x/2;
            max_dy = y/2;
            break;
            case NORTH:
            case SOUTH: dx = -(x/2);
            dy = -(y/2);
            max_dx = x/2;
            max_dy = y/2;
            break;
        }

        for (int i = dx; i <= max_dx; i++){
            for (int j = dy; j <= max_dy; j++){
                for (int k = dz; k <= max_dz; k++){


                    if (!world.isClient && world instanceof ServerWorld serverWorld) {
                        BlockPos targetPos = pos.add(i, j, k);
                        BlockState targetState = world.getBlockState(targetPos);

                        if (targetState.isOf(referenceBlock)) {
                            BlockEntity blockEntity = world.getBlockEntity(targetPos);

                            // LootContextParameterSet.Builder is what getDroppedStacks expects
                            LootContextParameterSet.Builder lootContextBuilder = new LootContextParameterSet.Builder(serverWorld)
                                    .add(LootContextParameters.ORIGIN, Vec3d.ofCenter(targetPos))
                                    .add(LootContextParameters.TOOL, stack)
                                    .addOptional(LootContextParameters.BLOCK_ENTITY, blockEntity)
                                    .addOptional(LootContextParameters.THIS_ENTITY, miner)
                                    .add(LootContextParameters.BLOCK_STATE, targetState);

                            // ✅ This is the correct call in 1.20.1
                            List<ItemStack> drops = targetState.getDroppedStacks(lootContextBuilder);

                            for (ItemStack drop : drops) {
                                Block.dropStack(world, targetPos, drop);
                            }

                            if (miner instanceof PlayerEntity player) {
                                targetState.getBlock().onBreak(world, targetPos, targetState, player);
                            }

                            world.breakBlock(targetPos, false); // false = don't drop again (we handled it)
                        }
                    }











                }
            }
        }
    }









}