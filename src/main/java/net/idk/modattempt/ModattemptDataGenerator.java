package net.idk.modattempt;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.idk.modattempt.datagen.ModBlockTagProvider;
import net.idk.modattempt.datagen.ModLootTableProvider;
import net.idk.modattempt.datagen.ModModelProvider;
import net.idk.modattempt.datagen.ModRecipeProvider;

public class ModattemptDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModBlockTagProvider::new);
		pack.addProvider(ModRecipeProvider::new);
		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModLootTableProvider::new);

	}
}
