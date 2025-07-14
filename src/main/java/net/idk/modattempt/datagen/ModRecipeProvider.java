package net.idk.modattempt.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.idk.modattempt.Items.ModItems;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.MinecartItem;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.udjustable_mine_area_pickaxe, 1)
                .pattern("###")
                .pattern("@I@")
                .pattern("@I@")
                .input('#', ModItems.something)
                .input('@', Items.AIR)
                .input('I', Items.STICK)
                .criterion(hasItem(ModItems.something), conditionsFromItem(ModItems.something))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.udjustable_mine_area_pickaxe)));
    }
}
