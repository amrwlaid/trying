package net.idk.modattempt.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.idk.modattempt.ModAttempt;
import net.idk.modattempt.block.ModBlocks;
import net.idk.modattempt.util.Modtags;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        //this one creates a json file for a set of blocks with a specific tag
        //getOrCreateTagBuilder(Modtags.Blocks."put thing name here").add(other thing) || .forceAddTag()
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.something_block)
                .add(ModBlocks.something_ore)
                .add(ModBlocks.NEIN_block);



    }
}
