package dev.boxadactle.coordinatesdisplay.config.screen;

import dev.boxadactle.boxlib.gui.config.BOptionScreen;
import dev.boxadactle.boxlib.gui.config.widget.button.BBooleanButton;
import dev.boxadactle.boxlib.gui.config.widget.button.BEnumButton;
import dev.boxadactle.boxlib.gui.config.widget.field.BStringField;
import dev.boxadactle.boxlib.gui.config.widget.label.BLabel;
import dev.boxadactle.boxlib.util.GuiUtils;
import dev.boxadactle.coordinatesdisplay.CoordinatesDisplay;
import dev.boxadactle.coordinatesdisplay.config.HudHelper;
import dev.boxadactle.coordinatesdisplay.config.ModConfig;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class TextScreen extends BOptionScreen implements HudHelper {

    public TextScreen(Screen parent) {
        super(parent);
    }

    @Override
    protected Component getName() {
        return Component.translatable("screen.coordinatesdisplay.text", CoordinatesDisplay.VERSION_STRING);
    }

    @Override
    protected void initFooter(int startX, int startY) {
        this.setSaveButton(createBackButton(startX, startY, parent));

        this.setWiki(Component.translatable("button.coordinatesdisplay.wiki"), CoordinatesDisplay.WIKI_TEXTS);
    }

    @Override
    protected void initConfigButtons() {

        // coordinates chat message
        this.addConfigLine(new BLabel(Component.translatable("label.coordinatesdisplay.posChatMessage")));
        this.addConfigLine(new BStringField(
                config().posChatMessage,
                newVal -> config().posChatMessage = newVal
        ));

        // copy pos message
        this.addConfigLine(new BLabel(Component.translatable("label.coordinatesdisplay.copyPosMessage")));
        this.addConfigLine(new BStringField(
                config().copyPosMessage,
                newVal -> config().copyPosMessage = newVal
        ));

        // round when copying
        this.addConfigLine(new BBooleanButton(
                "button.coordinatesdisplay.roundwhencopying",
                config().shouldRoundWhenCopying,
                newVal -> config().shouldRoundWhenCopying = newVal
        ));

        // teleport mode
        this.addConfigLine(new BEnumButton<>(
                "button.coordinatesdisplay.tpmode",
                config().teleportMode,
                ModConfig.TeleportMode.class,
                newVal -> config().teleportMode = newVal,
                GuiUtils.AQUA
        ));

    }
}
