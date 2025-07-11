package net.idk.modattempt.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.idk.modattempt.Items.ModItems;
import net.idk.modattempt.block.ModBlocks;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    protected ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.NEIN_block);
        addDrop(ModBlocks.something_block);


        addDrop(ModBlocks.something_ore, oreDrops(ModBlocks.something_ore, ModItems.something));
    }
}
