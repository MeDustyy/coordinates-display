package dev.boxadactle.coordinatesdisplay.gui.config;

import dev.boxadactle.coordinatesdisplay.CoordinatesDisplay;
import dev.boxadactle.boxlib.gui.BConfigScreen;
import dev.boxadactle.boxlib.gui.widget.BBooleanButton;
import dev.boxadactle.coordinatesdisplay.gui.HudHelper;
import dev.boxadactle.coordinatesdisplay.util.ModUtil;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class DeathPosScreen extends BConfigScreen implements HudHelper {

    public DeathPosScreen(Screen parent) {
        super(parent);
    }

    @Override
    protected Component getName() {
        return Component.translatable("screen.coordinatesdisplay.deathpos", CoordinatesDisplay.getModConstants().getString());
    }

    @Override
    protected void initFooter(int startX, int startY) {
        this.setSaveButton(createBackButton(startX, startY, parent));

        this.setWiki(Component.translatable("button.coordinatesdisplay.wiki"), ModUtil.CONFIG_WIKI_DEATH);
    }

    @Override
    protected void initConfigButtons() {

        // display on death screen
        this.addConfigOption(new BBooleanButton(
                "button.coordinatesdisplay.displayondeathscreen",
                config().displayPosOnDeathScreen,
                newVal -> config().displayPosOnDeathScreen = newVal
        ));

        // display in chat
        this.addConfigOption(new BBooleanButton(
                "button.coordinatesdisplay.sendinchat",
                config().showDeathPosInChat,
                newVal -> config().showDeathPosInChat = newVal
        ));

    }
}
