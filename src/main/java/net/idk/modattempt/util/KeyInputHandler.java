package net.idk.modattempt.util;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.idk.modattempt.Items.custom.custom_PickaxeItem;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler implements ClientModInitializer {
    public static boolean veinMineEnabled = false;

    private static KeyBinding toggleVeinMineKey;
    private static KeyBinding toggleExtraFunctionKey;
    private static KeyBinding toggleIncrementKey;
    private static KeyBinding changeXKey;
    private static KeyBinding changeYKey;

    @Override
    public void onInitializeClient() {
        toggleVeinMineKey = new KeyBinding(
                "key.modattempt.toggle_veinmine",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                "key.categories.modattempt"
        );

        toggleExtraFunctionKey = new KeyBinding(
                "key.modattempt.toggle_extra",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                "key.categories.modattempt"
        );

        toggleIncrementKey = new KeyBinding(
                "key.modattempt.toggle_increment",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_I,
                "key.categories.modattempt"
        );

        changeXKey = new KeyBinding(
                "key.modattempt.change_x",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_K,
                "key.categories.modattempt"
        );

        changeYKey = new KeyBinding(
                "key.modattempt.change_y",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_L,
                "key.categories.modattempt"
        );

        // Register all keybindings
        KeyBindingHelper.registerKeyBinding(toggleVeinMineKey);
        KeyBindingHelper.registerKeyBinding(toggleExtraFunctionKey);
        KeyBindingHelper.registerKeyBinding(toggleIncrementKey);
        KeyBindingHelper.registerKeyBinding(changeXKey);
        KeyBindingHelper.registerKeyBinding(changeYKey);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            while (toggleVeinMineKey.wasPressed()) {
                veinMineEnabled = !veinMineEnabled;
                client.player.sendMessage(
                        Text.literal("Vein Mine: " + (veinMineEnabled ? "Enabled" : "Disabled")),
                        true
                );
            }

            if (client.player.getMainHandStack().getItem() instanceof custom_PickaxeItem pickaxe) {
                int x = pickaxe.getX();
                int y = pickaxe.getY();
                String increment;
                String extraFunction;
                String xChanged;
                String yChanged;
                while (toggleExtraFunctionKey.wasPressed()){ pickaxe.toggle_extra_function();
                    if (pickaxe.getExtraFunction())
                        extraFunction = "this tool's extra function is on";
                    else extraFunction = "this tool's extra function is off";
                    client.player.sendMessage(Text.literal(extraFunction),true);
                }
                while (toggleIncrementKey.wasPressed()){ pickaxe.toggleIncrement();
                    if (pickaxe.getIncrement())
                        increment = "now increasing the dimensions";
                    else increment = "now decreasing the dimensions";
                    client.player.sendMessage(Text.literal(increment),true);
                }
                while (changeXKey.wasPressed()){ pickaxe.change_x();

                    xChanged = pickaxe.getX() +" x "+ pickaxe.getY();

                    if (x == pickaxe.getMaxWidth() && pickaxe.getIncrement())
                        xChanged = "maximum width has been reached";

                    if (x == 1 && !pickaxe.getIncrement()) xChanged = "the width is 0";

                    client.player.sendMessage(Text.literal(xChanged),true);
                }
                while (changeYKey.wasPressed()){ pickaxe.change_y();

                    yChanged = pickaxe.getX() +" x "+ pickaxe.getY();

                    if (y == pickaxe.getMaxHeight() && pickaxe.getIncrement())
                        yChanged = "maximum height has been reached";

                    if (y == 1 && !pickaxe.getIncrement()) yChanged = "the height is 0";

                    client.player.sendMessage(Text.literal(yChanged),true);
                }
            }
        });
    }
}

