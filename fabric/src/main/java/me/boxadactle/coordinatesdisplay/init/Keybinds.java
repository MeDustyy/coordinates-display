package me.boxadactle.coordinatesdisplay.init;

import me.boxadactle.coordinatesdisplay.CoordinatesDisplay;
import me.boxadactle.coordinatesdisplay.gui.CoordinatesScreen;
<<<<<<< Updated upstream
import me.boxadactle.coordinatesdisplay.util.ModUtils;
=======
import me.boxadactle.coordinatesdisplay.gui.config.HudPositionScreen;
import me.boxadactle.coordinatesdisplay.util.ModUtil;
>>>>>>> Stashed changes
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
<<<<<<< Updated upstream
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
=======
>>>>>>> Stashed changes
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class Keybinds {
    static KeyBinding visibleKeybind = new KeyBinding("key.coordinatesdisplay.visible", GLFW.GLFW_KEY_O, "category.coordinatesdisplay");
    static KeyBinding coordinatesGUIKeybind = new KeyBinding("key.coordinatesdisplay.coordinatesgui", GLFW.GLFW_KEY_C, "category.coordinatesdisplay");

    static KeyBinding openConfigFileKeybind = new KeyBinding("key.coordinatesdisplay.openfile", GLFW.GLFW_KEY_F6, "category.coordinatesdisplay");
    static KeyBinding reloadConfigKeybind = new KeyBinding("key.coordinatesdisplay.configreload", GLFW.GLFW_KEY_F7, "category.coordinatesdisplay");

    static KeyBinding copyLocation = new KeyBinding("key.coordinatesdisplay.copypos", GLFW.GLFW_KEY_B, "category.coordinatesdisplay");
    static KeyBinding sendLocation = new KeyBinding("key.coordinatesdisplay.sendpos", GLFW.GLFW_KEY_X, "category.coordinatesdisplay");
    static KeyBinding copyPosTp = new KeyBinding("key.coordinatesdisplay.copypostp", GLFW.GLFW_KEY_N, "category.coordinatesdisplay");
    static KeyBinding changeHudPos = new KeyBinding("key.coordinatesdisplay.changeHudPos", GLFW.GLFW_KEY_F9, "category.coordinatesdisplay");

    public static void register() {
        KeyBindingHelper.registerKeyBinding(visibleKeybind);
        KeyBindingHelper.registerKeyBinding(coordinatesGUIKeybind);
        KeyBindingHelper.registerKeyBinding(reloadConfigKeybind);
        KeyBindingHelper.registerKeyBinding(openConfigFileKeybind);
        KeyBindingHelper.registerKeyBinding(copyLocation);
        KeyBindingHelper.registerKeyBinding(sendLocation);
        KeyBindingHelper.registerKeyBinding(copyPosTp);
        KeyBindingHelper.registerKeyBinding(changeHudPos);
    }

    public static void checkBindings(int x, int y, int z) {

        if (visibleKeybind.wasPressed()) {
            CoordinatesDisplay.CONFIG.get().visible = !CoordinatesDisplay.CONFIG.get().visible;
            CoordinatesDisplay.CONFIG.save();
            CoordinatesDisplay.LOGGER.info("Updated visible property in config file");
        }

        if (coordinatesGUIKeybind.wasPressed()) {
            MinecraftClient.getInstance().setScreen(new CoordinatesScreen(x, y, z));
        }

        if (openConfigFileKeybind.wasPressed()) {
            if (ModUtils.openConfigFile()) {
                CoordinatesDisplay.LOGGER.info("Opened file in native explorer!");
            } else {
                CoordinatesDisplay.LOGGER.chatError("Sorry I could not open the file. It is saved at: " + FabricLoader.getInstance().getConfigDir().toFile().getAbsolutePath());
            }
        }

        if (reloadConfigKeybind.wasPressed()) {
            CoordinatesDisplay.resetConfig();
            CoordinatesDisplay.LOGGER.player.info("Config reloaded!");
            CoordinatesDisplay.LOGGER.info("Reloaded all config");
        }

        if (copyLocation.wasPressed()) {
<<<<<<< Updated upstream
            MinecraftClient.getInstance().keyboard.setClipboard(ModUtils.parseText(CoordinatesDisplay.CONFIG.copyPosMessage));
=======
            MinecraftClient.getInstance().keyboard.setClipboard(ModUtil.parseText(CoordinatesDisplay.CONFIG.get().copyPosMessage));
>>>>>>> Stashed changes
            CoordinatesDisplay.LOGGER.player.info("Copied to clipboard!");
            CoordinatesDisplay.LOGGER.player.info("Copied location to clipboard!");
        }

        if (sendLocation.wasPressed()) {
<<<<<<< Updated upstream
            CoordinatesDisplay.LOGGER.player.publicChat(ModUtils.parseText(CoordinatesDisplay.CONFIG.posChatMessage));
=======
            CoordinatesDisplay.LOGGER.player.publicChat(ModUtil.parseText(CoordinatesDisplay.CONFIG.get().posChatMessage));
>>>>>>> Stashed changes
            CoordinatesDisplay.LOGGER.info("Sent position as chat message");
        }

        if (copyPosTp.wasPressed()) {
            RegistryKey<World> registry = MinecraftClient.getInstance().player.world.getRegistryKey();

            MinecraftClient.getInstance().keyboard.setClipboard(ModUtils.asTpCommand(x, y, z, (registry != null ? registry.getValue().toString() : null)));

            CoordinatesDisplay.LOGGER.player.info("Copied position as command");
        }

        if (changeHudPos.wasPressed()) {

            MinecraftClient.getInstance().setScreen(new HudPositionScreen(null));

        }
    }
}
