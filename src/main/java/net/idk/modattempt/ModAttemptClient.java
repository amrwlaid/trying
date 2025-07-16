package net.idk.modattempt;

import net.fabricmc.api.ClientModInitializer;
import net.idk.modattempt.Items.ModItems;
import net.idk.modattempt.Items.custom.minerBlockScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class ModAttemptClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ModelPredicateProviderRegistry.register(
                ModItems.udjustable_mine_area_pickaxe,
                new Identifier("modattempt", "variant"),
                (stack, world, entity, seed) -> {
                    int dim = stack.getOrCreateNbt().getInt("Dimension");
                    return switch (dim) {
                        case 3 -> 0.3f;
                        case 5 -> 0.5f;
                        case 7 -> 0.7f;
                        case 9 -> 0.9f;
                        default -> 0.1f;
                    };
                }
        );
        HandledScreens.register(ModScreenHandlers.miner_block, minerBlockScreen::new);
    }


}
