package net.idk.modattempt.util;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler implements ClientModInitializer {
    public static boolean veinMineEnabled = false;

    private static KeyBinding toggleVeinMineKey;

    @Override
    public void onInitializeClient() {
        toggleVeinMineKey = new KeyBinding(
                "key.modattempt.toggle_veinmine",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                "key.categories.modattempt"
        );

        net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper.registerKeyBinding(toggleVeinMineKey);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleVeinMineKey.wasPressed()) {
                veinMineEnabled = !veinMineEnabled;
                client.player.sendMessage(
                        net.minecraft.text.Text.literal("Vein Mine: " + (veinMineEnabled ? "Enabled" : "Disabled")),
                        true
                );
            }
        });
    }
}
