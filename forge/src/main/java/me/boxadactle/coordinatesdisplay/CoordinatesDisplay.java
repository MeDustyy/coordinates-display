package me.boxadactle.coordinatesdisplay;

import me.boxadactle.coordinatesdisplay.gui.ConfigScreenMain;
import me.boxadactle.coordinatesdisplay.util.*;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

import java.io.File;

@Mod("coordinatesdisplay")
public class CoordinatesDisplay {

    public static final ModLogger LOGGER = new ModLogger();

    public static final String MOD_NAME = "CoordinatesDisplay";
    public static final String MOD_ID = "coordinatesdisplay";

    public static boolean shouldRenderOnHud = true;

    public static final File configDir = new File(Minecraft.getInstance().gameDirectory.getAbsolutePath() + "\\config");

    public static ConfigHolder<ModConfig> CONFIG;

    public static HudRenderer OVERLAY;

    public CoordinatesDisplay() {

        LOGGER.info("Loading " + ModVersion.getString());

        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(ModConfig.class);

        // what a pain
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () ->
                new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> new ConfigScreenMain(screen)));

        LOGGER.info("Creating instance of HUD overlay");
        OVERLAY = new HudRenderer();

        MinecraftForge.EVENT_BUS.register(this);
    }

    public static class BiomeColors {

        public static int getBiomeColor(String name, int defaultColor) {

            int returning = switch (name) {
                case "Eroded Badlands", "Badlands" -> 0xb55a26;
                case "Bamboo Jungle" -> 0x2be625;
                case "Snowy Beach", "Snowy Plains", "Snowy Slopes", "Snowy Taiga", "Basalt Deltas" -> 0xadadad;
                case "Beach" -> 0xc5c93a;
                case "Birch Forest", "Old Growth Birch Forest" -> 0xdecc7a;
                case "Cold Ocean" -> 0x738ae6;
                case "Crimson Forest", "Nether Wastes" -> 0xad201d;
                case "Dark Forest" -> 0x452309;
                case "River", "Ocean", "Deep Cold Ocean" -> 0x161d78;
                case "Deep Dark" -> 0x03273d;
                case "Deep Frozen Ocean" -> 0x1e4054;
                case "Deep Lukewarm Ocean" -> 0x235b63;
                case "Deep Ocean" -> 0x15115c;
                case "End Barrens", "End Highlands", "End Midlands", "Small End Islands", "Desert" -> 0xb3ac30;
                case "Dripstone Caves" -> 0x665f50;
                case "Flower Forest", "Forest", "Lush Caves", "Meadow" -> 0x32701c;
                case "Frozen Ocean", "Frozen Peaks", "Frozen River", "Ice Spikes" -> 0x34c4c9;
                case "Grove", "Jagged Peaks" -> 0xacb0a7;
                case "Jungle" -> 0x85c41f;
                case "Lukewarm Ocean" -> 0x3d9ba8;
                case "Mushroom Fields" -> 0x4c4654;
                case "Old Growth Pine Taiga", "Old Growth Spruce Forest" -> 0x3b230d;
                case "Plains", "Sunflower Plains" -> 0x4dd115;
                case "Savanna", "Savanna Plateau" -> 0x5c701c;
                case "Cherry Grove" -> 0xd863e0;
                default -> defaultColor;
            };

            return returning;

        }

    }
}
