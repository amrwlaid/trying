package net.idk.modattempt.Items.custom;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class custom_ToolMaterial implements ToolMaterial {
    int durability;
    float miningSpeedMultiplier;
    float attackDamage;
    int miningLevel;
    int enchantability;
    Ingredient RepairIngredient;

    public custom_ToolMaterial(){

    }


    @Override
    public int getDurability() {
        return 10000;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 100;
    }

    @Override
    public float getAttackDamage() {
        return 5;
    }

    @Override
    public int getMiningLevel() {
        return 4;
    }

    @Override
    public int getEnchantability() {
        return 100;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return null;
    }
}
