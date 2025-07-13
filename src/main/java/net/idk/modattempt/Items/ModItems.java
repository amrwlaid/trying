package net.idk.modattempt.Items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.idk.modattempt.Items.custom.Custom_Item;
import net.idk.modattempt.Items.custom.custom_PickaxeItem;
import net.idk.modattempt.ModAttempt;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item RUBY = registerItem("ruby", new Item(new FabricItemSettings()));
    public static final Item something = registerItem("something", new Item(new FabricItemSettings()));
    public static final Item nein = registerItem("nein", new Item(new FabricItemSettings()));
    public static final custom_PickaxeItem udjustable_mine_area_pickaxe = registerCustom_PickaxeItem("udjustable_mine_area_pickaxe",
            new custom_PickaxeItem(ToolMaterials.DIAMOND, 10,
            -0.4f ,new  FabricItemSettings(), true));

    /*public static final Custom_Item something_pickaxe = registerCustom_Item("something_pickaxe",
            new Custom_Item(new FabricItemSettings().maxCount(1).maxDamage(10)));*/

    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries){
        entries.add(RUBY);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(ModAttempt.MOD_ID, name), item);
    }

    private static custom_PickaxeItem registerCustom_PickaxeItem(String name, custom_PickaxeItem item) {
        return Registry.register(Registries.ITEM, new Identifier(ModAttempt.MOD_ID, name), item);
    }

    private static Custom_Item registerCustom_Item(String name, Custom_Item item) {
        return Registry.register(Registries.ITEM, new Identifier(ModAttempt.MOD_ID, name), item);
    }






    public static void registerModItems(){
        ModAttempt.LOGGER.info("Registering Mod Items for" + ModAttempt.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);

    }

}
