package me.boxadactle.coordinatesdisplay.gui.config;

import me.boxadactle.coordinatesdisplay.gui.ConfigScreen;
import me.boxadactle.coordinatesdisplay.CoordinatesDisplay;
import me.boxadactle.coordinatesdisplay.util.ModUtil;
import me.boxadactle.coordinatesdisplay.util.ModVersion;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ConfirmLinkScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class ColorScreen extends ConfigScreen {

    public ColorScreen(Screen parent) {
        super(parent);
        this.parent = parent;

        super.generatePositionData();
        super.setTitle(Text.translatable("screen.coordinatesdisplay.config.color", CoordinatesDisplay.MOD_NAME, ModVersion.getVersion()));

        CoordinatesDisplay.shouldRenderOnHud = false;

    }

    private void renderColorPicker(DrawContext drawContext) {
        float s = 1.3F;
        MatrixStack matrices = drawContext.getMatrices();
        matrices.push();
        matrices.scale(s, s, s);

        int a = (int) (this.width / s);
        int b = (int) (this.height / s) + 75;
        int c = (int) (a / 2 + (5 / s));
        int d = (int) (b / 2.3) - 4;
        int e = (int) (872 / 1.8) / this.nonZeroGuiScale() / 4;
        int f = (int) (586 / 1.8) / this.nonZeroGuiScale() /4;

        drawContext.drawTexture(new Identifier("coordinatesdisplay", "textures/color_picker.png"), c, d, 0.0F, 0.0F, e, f, e, f);

        matrices.pop();
    }

    public void render(DrawContext drawContext, int mouseX, int mouseY, float delta) {
        this.renderBackground(drawContext);

        super.drawTitle(drawContext);

        int y = (int) (this.height / 2.3);

        drawContext.drawTextWithShadow(this.textRenderer, Text.translatable("button.coordinatesidisplay.colors.definition"), this.width / 2 - smallButtonW, start + 8, ModUtil.WHITE);
        drawContext.drawTextWithShadow(this.textRenderer, Text.translatable("button.coordinatesidisplay.colors.data"), this.width / 2, start + 8, ModUtil.WHITE);
        drawContext.drawTextWithShadow(this.textRenderer, Text.translatable("button.coordinatesidisplay.colors.deathpos"), this.width / 2 - smallButtonW - p, start + 8 + (buttonHeight + p) * 2, ModUtil.WHITE);
        drawContext.drawTextWithShadow(this.textRenderer, Text.translatable("button.coordinatesidisplay.colors.background"), this.width / 2, start + 8 + (buttonHeight + p) * 2, ModUtil.WHITE);

        CoordinatesDisplay.OVERLAY.render(drawContext, pos, chunkPos, cameraYaw, cameraPitch, null, this.width / 2 - CoordinatesDisplay.OVERLAY.getWidth() - 5, y + 40, CoordinatesDisplay.CONFIG.get().minMode, false);

        Text posT = Texts.bracketed(Text.translatable("message.coordinatesdisplay.deathlocation", deathx, deathy, deathz, dimension)).styled(style -> style.withColor(CoordinatesDisplay.CONFIG.get().deathPosColor));
        Text deathPos = Text.translatable("message.coordinatesdisplay.deathpos", posT);
        drawContext.drawCenteredTextWithShadow(this.textRenderer, deathPos, this.width / 2, y - (CoordinatesDisplay.OVERLAY.getHeight() / 4) + 40, ModUtil.WHITE);

        this.renderColorPicker(drawContext);

        super.render(drawContext, mouseX,  mouseY, delta);
    }

    protected void init() {
        super.init();

        this.addDrawableChild(new ButtonWidget.Builder(Text.translatable("button.coordinatesdisplay.back"), (button) -> this.close()).dimensions(this.width / 2 - largeButtonW / 2, this.height - buttonHeight - p, largeButtonW, buttonHeight).build());

        // open wiki
        this.addDrawableChild(new ButtonWidget.Builder(Text.translatable("button.coordinatesdisplay.help"), (button) -> this.client.setScreen(new ConfirmLinkScreen((yes) -> {
            this.client.setScreen(this);
            if (yes) {
                Util.getOperatingSystem().open(ModUtil.CONFIG_WIKI_COLOR);
                CoordinatesDisplay.LOGGER.info("Opened link");
            }
        }, ModUtil.CONFIG_WIKI_COLOR, false))).dimensions(5, 5, tinyButtonW, buttonHeight).build());

        this.initConfigScreen();
    }

    public void initConfigScreen() {
        TextFieldWidget definitionColor = new TextFieldWidget(this.textRenderer, this.width / 2 - smallButtonW - p, start + (buttonHeight + p), smallButtonW, buttonHeight, Text.literal(Integer.toHexString(CoordinatesDisplay.CONFIG.get().definitionColor)));
        TextFieldWidget dataColor = new TextFieldWidget(this.textRenderer, this.width / 2 + p, start + (buttonHeight + p), smallButtonW, buttonHeight, Text.literal(Integer.toHexString(CoordinatesDisplay.CONFIG.get().definitionColor)));
        TextFieldWidget deathposColor = new TextFieldWidget(this.textRenderer, this.width / 2 - smallButtonW - p, start + (buttonHeight + p) * 3, smallButtonW, buttonHeight, Text.literal(Integer.toHexString(CoordinatesDisplay.CONFIG.get().definitionColor)));
        TextFieldWidget backgroundColor = new TextFieldWidget(this.textRenderer, this.width / 2 + p, start + (buttonHeight + p) * 3, smallButtonW, buttonHeight, Text.literal(Integer.toHexString(CoordinatesDisplay.CONFIG.get().definitionColor)));

        definitionColor.setMaxLength(6);
        dataColor.setMaxLength(6);
        deathposColor.setMaxLength(6);

        definitionColor.setText(Integer.toHexString(CoordinatesDisplay.CONFIG.get().definitionColor));
        dataColor.setText(Integer.toHexString(CoordinatesDisplay.CONFIG.get().dataColor));
        deathposColor.setText(Integer.toHexString(CoordinatesDisplay.CONFIG.get().deathPosColor));
        backgroundColor.setText(Integer.toHexString(CoordinatesDisplay.CONFIG.get().backgroundColor));

        definitionColor.setChangedListener((txt) -> {
            if (txt.isEmpty()) return;
            try {
                CoordinatesDisplay.CONFIG.get().definitionColor = Integer.valueOf(txt.replaceAll("#", ""), 16);
            } catch (NumberFormatException e) {
                CoordinatesDisplay.LOGGER.error("Why you put invalid hex code?");
                CoordinatesDisplay.LOGGER.printStackTrace(e);
            }
        });

        dataColor.setChangedListener((txt) -> {
            if (txt.isEmpty()) return;
            try {
                CoordinatesDisplay.CONFIG.get().dataColor = Integer.valueOf(txt.replaceAll("#", ""), 16);
            } catch (NumberFormatException e) {
                CoordinatesDisplay.LOGGER.error("Why you put invalid hex code?");
                CoordinatesDisplay.LOGGER.printStackTrace(e);
            }
        });

        deathposColor.setChangedListener((txt) -> {
            if (txt.isEmpty()) return;
            try {
                CoordinatesDisplay.CONFIG.get().deathPosColor = Integer.valueOf(txt.replaceAll("#", ""), 16);
            } catch (NumberFormatException e) {
                CoordinatesDisplay.LOGGER.error("Why you put invalid hex code?");
                CoordinatesDisplay.LOGGER.printStackTrace(e);
            }
        });

        backgroundColor.setChangedListener((txt) -> {
            if (txt.isEmpty()) return;
            try {
                // Need to use parseUnsignedInt in order to get ARGB values over 0x7fffffff to work
                CoordinatesDisplay.CONFIG.get().backgroundColor = Integer.parseUnsignedInt(txt.replaceAll("#", ""), 16);
            } catch (NumberFormatException e) {
                CoordinatesDisplay.LOGGER.error("Why you put invalid hex code?");
                CoordinatesDisplay.LOGGER.printStackTrace(e);
            }
        });

        this.addDrawableChild(definitionColor);
        this.addDrawableChild(dataColor);
        this.addDrawableChild(deathposColor);

        this.addDrawableChild(new ButtonWidget.Builder(Text.literal("Color Picker..."), (button -> {
            Util.getOperatingSystem().open("https://htmlcolorcodes.com/color-picker/");
        })).dimensions(this.width / 2 + p1, start + (buttonHeight + p) * 3, smallButtonW, buttonHeight).build());

    }

    @Override
    public void close() {
        this.client.setScreen(parent);
        CoordinatesDisplay.shouldRenderOnHud = true;
    }
}
