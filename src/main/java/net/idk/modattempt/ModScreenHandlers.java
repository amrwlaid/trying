package net.idk.modattempt;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.idk.modattempt.Items.custom.minerBlockScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static ScreenHandlerType<minerBlockScreenHandler> miner_block;

    public static void registerAllScreenHandlers() {
        miner_block = ScreenHandlerRegistry.registerExtended(
                new Identifier(ModAttempt.MOD_ID, "minerblock_screen"),
                minerBlockScreenHandler::new
        );
    }
}

