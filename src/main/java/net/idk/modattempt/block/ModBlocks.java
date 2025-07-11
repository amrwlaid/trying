package net.idk.modattempt.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.idk.modattempt.ModAttempt;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;


public class ModBlocks {
        public static final PillarBlock something_block = registerPillarBlock("something_block",
                new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG).hardness(Blocks.IRON_BLOCK.getHardness())));


        public static final Block NEIN_block = registerBlock("nein_block",
                new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)));

        public static final Block something_ore = registerBlock("something_ore",
                new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(0),
                        UniformIntProvider.create(10, 30)));
        /*public static final Block deepslate_something_ore = registerBlock("something_ore",
                new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.DEEPSLATE).strength(4f),
                        UniformIntProvider.create(10, 30)));*/

        /*public static final ChestBlock custom_chest = registerChestBlock("custom_chest",
                new ChestBlock(FabricBlockSettings.copyOf(Blocks.CHEST)));*/

    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(ModAttempt.MOD_ID, name), block);
    }


    private static ChestBlock registerChestBlock(String name, ChestBlock chest_block){
            registerBlockItem(name, chest_block);
            return Registry.register(Registries.BLOCK, new Identifier(ModAttempt.MOD_ID, name), chest_block);
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
