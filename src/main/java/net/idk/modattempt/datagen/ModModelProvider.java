package net.idk.modattempt.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.idk.modattempt.Items.ModItems;
import net.idk.modattempt.ModAttempt;
import net.idk.modattempt.block.ModBlocks;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NEIN_block);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.something_ore);

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.udjustable_mine_area_pickaxe, Models.HANDHELD);
        itemModelGenerator.register(ModItems.nein, Models.GENERATED);
        itemModelGenerator.register(ModItems.something, Models.GENERATED);
    }
}
