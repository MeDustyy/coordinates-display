package dev.boxadactle.coordinatesdisplay;

import com.mojang.blaze3d.platform.InputConstants;
import dev.boxadactle.boxlib.config.BConfigClass;
import dev.boxadactle.boxlib.config.BConfigHandler;
import dev.boxadactle.boxlib.util.ClientUtils;
import dev.boxadactle.boxlib.util.ModLogger;
import dev.boxadactle.boxlib.util.WorldUtils;
import dev.boxadactle.coordinatesdisplay.config.screen.ConfigScreen;
import dev.boxadactle.coordinatesdisplay.config.screen.HudPositionScreen;
import dev.boxadactle.coordinatesdisplay.hud.CoordinatesHuds;
import dev.boxadactle.coordinatesdisplay.hud.Hud;
import dev.boxadactle.coordinatesdisplay.config.ModConfig;
import dev.boxadactle.coordinatesdisplay.hud.renderer.*;
import dev.boxadactle.coordinatesdisplay.hud.visibility.AlwaysVisibility;
import dev.boxadactle.coordinatesdisplay.hud.visibility.HoldCompassVisibility;
import dev.boxadactle.coordinatesdisplay.hud.visibility.NeverVisibility;
import dev.boxadactle.coordinatesdisplay.hud.visibility.OwnCompassVisibility;
import dev.boxadactle.coordinatesdisplay.position.Position;

public class CoordinatesDisplay {

	public static final String MOD_NAME = "CoordinatesDisplay";

	public static final String MOD_ID = "coordinatesdisplay";

	public static final String VERSION = "10.0.0";

	public static final String VERSION_STRING = MOD_NAME + " v" + VERSION;


	// wiki pages
	public static final String WIKI = "https://boxadactle.dev/wiki/coordinates-display/";
	public static String WIKI_VISUAL = WIKI + "#visual";
	public static String WIKI_RENDER = WIKI + "#rendering";
	public static String WIKI_COLOR = WIKI + "#color";
	public static String WIKI_DEATHPOS = WIKI + "#deathpos";
	public static String WIKI_TEXTS = WIKI + "#text";


	public static final ModLogger LOGGER = new ModLogger(MOD_NAME);

	public static boolean shouldConfigGuiOpen = false;
	public static boolean shouldCoordinatesGuiOpen = false;
	public static boolean shouldHudPositionGuiOpen = false;

	public static boolean shouldHudRender = true;

	public static BConfigClass<ModConfig> CONFIG;

	public static Hud HUD;

	public static void init() {
		LOGGER.info("Initializing " + MOD_NAME + " v" + VERSION);

		LOGGER.info("Loading config file");
		CONFIG = BConfigHandler.registerConfig(ModConfig.class);

		LOGGER.info("Registering hud renderers");
		CoordinatesHuds.register(DefaultRenderer.class);
		CoordinatesHuds.register(MinRenderer.class);
		CoordinatesHuds.register(MaxRenderer.class);
		CoordinatesHuds.register(LineRenderer.class);
		CoordinatesHuds.register(NetherOverworldRenderer.class);
		CoordinatesHuds.register(HotbarRenderer.class);
		CoordinatesHuds.register(SpawnpointRenderer.class);
		CoordinatesHuds.register(DirectionRenderer.class);

		LOGGER.info("Registering hud visibility filters");
		CoordinatesHuds.registerVisibilityFilter(AlwaysVisibility.class);
		CoordinatesHuds.registerVisibilityFilter(NeverVisibility.class);
		CoordinatesHuds.registerVisibilityFilter(HoldCompassVisibility.class);
		CoordinatesHuds.registerVisibilityFilter(OwnCompassVisibility.class);

		LOGGER.info("Initializing hud");
		HUD = new Hud();
	}

	public static ModConfig getConfig() {
		return CONFIG.get();
	}

	public static void tick() {
		if (shouldConfigGuiOpen) {
			ClientUtils.setScreen(new ConfigScreen(null));
			shouldConfigGuiOpen = false;
		}

		if (shouldCoordinatesGuiOpen) {
			Position pos = Position.of(WorldUtils.getPlayer());

			ClientUtils.setScreen(new CoordinatesScreen(pos));

			shouldCoordinatesGuiOpen = false;
		}

		if (shouldHudPositionGuiOpen) {
			ClientUtils.setScreen(new HudPositionScreen(null));
			shouldHudPositionGuiOpen = false;
		}
	}

	public static class BiomeColors {

		public static int getBiomeColor(String name, int defaultColor) {

			return switch (name) {
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

		}

		public static int getDimensionColor(String name, int defaultColor) {
			return switch (name) {
				case "Overworld" -> 0x00ff00;
				case "Nether" -> 0xff0000;
				case "End" -> 0x0000ff;
				default -> {
					if (name.contains("The ")) {
						yield getDimensionColor(name.substring(4), defaultColor);
					} else {
						yield defaultColor;
					}
				}
			};
		}

	}

	public static class Bindings {

		public static void coordinatesGui() {
			shouldCoordinatesGuiOpen = true;
		}

		public static void copyLocation(Position pos) {
			ClientUtils.getKeyboard().setClipboard(ModUtil.parseText(CONFIG.get().copyPosMessage, pos));
			LOGGER.player.info("Copied to clipboard!");
			LOGGER.info("Copied location to clipboard");
		}

		public static void sendLocation(Position pos) {
			LOGGER.player.info(ModUtil.parseText(CONFIG.get().posChatMessage, pos));
			LOGGER.info("Sent position as chat message");
		}

		public static void copyTeleportCommand(Position pos) {
			ClientUtils.getKeyboard().setClipboard(getConfig().teleportMode.toCommand(pos));

			LOGGER.player.info("Copied position as teleport command!");
		}

		public static void openHudPositionGui() {
			shouldHudPositionGuiOpen = true;
		}

		public static void cycleDisplayMode() {
			CoordinatesHuds.RegisteredRenderer renderer;

			if (!InputConstants.isKeyDown(ClientUtils.getWindow(), 340)) renderer = CoordinatesHuds.nextRenderer(getConfig().renderMode);
			else renderer = CoordinatesHuds.previousRenderer(getConfig().renderMode);

			getConfig().renderMode = renderer.getId();
			CONFIG.save();
		}
	}

}
