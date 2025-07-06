package net.idk.modattempt;

import net.fabricmc.api.ModInitializer;

import net.idk.modattempt.modItems.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModAttempt implements ModInitializer {
	public static final String MOD_ID = "modattempt";


	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModItems.registerModItems();
		LOGGER.info("Hello Fabric world!");
	}
}