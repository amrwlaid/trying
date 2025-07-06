package net.idk.modattempt.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.idk.modattempt.ModAttempt;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
        public static final PillarBlock something_block = registerPillarBlock("something_block",
                new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG).hardness(Blocks.IRON_BLOCK.getHardness())));

    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(ModAttempt.MOD_ID, name), block);
    }


    private static PillarBlock  registerPillarBlock(String name, Block block){
        registerBlockItem(name, block);
        return (PillarBlock) Registry.register(Registries.BLOCK, new Identifier(ModAttempt.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block){
        return Registry.register(Registries.ITEM, new Identifier(ModAttempt.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks(){
        ModAttempt.LOGGER.info("registering ModBlocks from" + ModAttempt.MOD_ID);
    }
}
