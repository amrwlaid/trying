package net.idk.modattempt.util;

import net.idk.modattempt.ModAttempt;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;


public class Modtags {
    public static class Blocks{



        private static TagKey<Block> creatTag(String name){
            return TagKey.of(RegistryKeys.BLOCK, new Identifier(ModAttempt.MOD_ID, name));
        }
    }



    public static class Items{
        private static TagKey<Item> creatTag(String name){
            return TagKey.of(RegistryKeys.ITEM, new Identifier(ModAttempt.MOD_ID, name));
        }
    }



}
