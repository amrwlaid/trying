package net.idk.modattempt.Items;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.idk.modattempt.ModAttempt;
import net.idk.modattempt.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup modattemptGroup = Registry.register(Registries.ITEM_GROUP,
            new Identifier(ModAttempt.MOD_ID, "modattempt"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.modattempt"))
                    .icon(() -> new ItemStack(ModItems.something)).entries((displayContext,
                                                                            entries) -> {


                        entries.add(ModItems.RUBY);
                        entries.add(ModItems.something);
                        entries.add(ModItems.nein);

                        entries.add(ModBlocks.something_block);
                        entries.add(ModBlocks.NEIN_block);
                        //entries.add(ModBlocks.deepslate_something_ore);
                        entries.add(ModBlocks.something_ore);
                        //entries.add(ModItems.something_pickaxe);
                        entries.add(ModBlocks.minerBlock);

                        entries.add(ModItems.udjustable_mine_area_pickaxe);





                    }).build());


    public static void registerItemGroups(){
        ModAttempt.LOGGER.info("registering item groups for modattempt");
    }
}
