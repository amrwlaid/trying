package net.idk.modattempt;

import net.fabricmc.api.ModInitializer;

import net.idk.modattempt.Items.ModItemGroups;
import net.idk.modattempt.Items.ModItems;
import net.idk.modattempt.block.ModBlocks;
import net.idk.modattempt.util.ModNetworking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModAttempt implements ModInitializer {
	public static final String MOD_ID = "modattempt";


	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModNetworking.registerC2SPackets();

		ModItemGroups.registerItemGroups();

		ModBlocks.registerModBlocks();

		ModItems.registerModItems();

		LOGGER.info("Hello Fabric world!");
	}
}