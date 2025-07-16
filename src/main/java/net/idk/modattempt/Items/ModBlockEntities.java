package net.idk.modattempt.Items;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.idk.modattempt.Items.custom.MinerBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.idk.modattempt.block.ModBlocks;

public class ModBlockEntities {
    public static  BlockEntityType<MinerBlockEntity> MINER_BLOCK_ENTITY;

    public static void register() {
        MINER_BLOCK_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                new Identifier("modattempt", "minerblockentity"),
                FabricBlockEntityTypeBuilder.create(MinerBlockEntity::new, ModBlocks.minerBlock).build(null)
        );
    }
}
