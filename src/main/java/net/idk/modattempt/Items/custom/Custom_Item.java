package net.idk.modattempt.Items.custom;

import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

public class Custom_Item extends PickaxeItem {
    TagKey<Block> effectiveBlocks;

    public Custom_Item(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings, TagKey<Block> effectiveBlocks){
        super(material,attackDamage,attackSpeed,settings);
        this.effectiveBlocks = effectiveBlocks;


    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        return 2.0F;
    }

    //@Override
    public int getMiningLevel(){
        return this.getMaterial().getMiningLevel();
    }

    @Override
    public boolean isSuitableFor(BlockState state) {
        int i = this.getMiningLevel();
        if (i < MiningLevels.DIAMOND && state.isIn(BlockTags.NEEDS_DIAMOND_TOOL)) {
            return false;
        } else if (i < MiningLevels.IRON && state.isIn(BlockTags.NEEDS_IRON_TOOL)) {
            return false;
        } else {
            return i < MiningLevels.STONE && state.isIn(BlockTags.NEEDS_STONE_TOOL) ? false : state.isIn(this.effectiveBlocks);
        }
    }


}





