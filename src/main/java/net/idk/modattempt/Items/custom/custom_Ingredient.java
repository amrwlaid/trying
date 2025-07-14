package net.idk.modattempt.Items.custom;

import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;

import java.util.List;

public class custom_Ingredient implements CustomIngredient {


    @Override
    public boolean test(ItemStack itemStack) {
        return false;
    }

    @Override
    public List<ItemStack> getMatchingStacks() {
        return List.of();
    }

    @Override
    public boolean requiresTesting() {
        return false;
    }

    @Override
    public CustomIngredientSerializer<?> getSerializer() {
        return null;
    }
}
