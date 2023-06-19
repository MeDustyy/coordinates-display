package me.boxadactle.coordinatesdisplay.gui;

import me.boxadactle.coordinatesdisplay.util.ModUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public abstract class ConfigScreen extends Screen {

    protected int p = 2;
    protected int p1 = p / 2;
    protected int th;
    protected int tp = 4;

    protected int largeButtonW = 300;
    protected int smallButtonW = 150 - p;
    protected int tinyButtonW = 75;
    protected int buttonHeight = 20;

    protected int start = 20;

    protected Screen parent;

    protected Vec3 pos;
    protected ChunkPos chunkPos;
    protected float cameraYaw;
    protected float cameraPitch;

    protected int deathx;
    protected int deathy;
    protected int deathz;

    protected String dimension;

    protected Minecraft client = Minecraft.getInstance();

    private Component title;

    protected ConfigScreen(Screen parent) {
        super(null);

        this.parent = parent;
    }

    protected void setTitle(Component title) {
        this.title = title;
    }

    @Override
    public @NotNull Component getNarrationMessage() {
        return this.title;
    }

    protected void drawTitle(GuiGraphics guiGraphics) {
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 5, ModUtil.WHITE);
    }

    protected int nonZeroGuiScale() {
        int scale = Minecraft.getInstance().options.guiScale().get();
        if (scale == 0) {
            // This formula copied from the Minecraft wiki
            return (int) Math.max(1, Math.min(Math.floor(this.width / 320), Math.floor(this.height / 240)));
        } else {
            return scale;
        }
    }

    protected void generatePositionData() {
        this.pos = new Vec3(Math.random() * 1000, Math.random() * 5, Math.random() * 1000);
        this.chunkPos = new ChunkPos(new BlockPos(ModUtil.doubleVecToIntVec(this.pos)));
        this.cameraYaw = (float) Math.random() * 180;
        this.cameraPitch  = (float) Math.random() * 180;

        this.deathx = (int) Math.round(Math.random() * 1000);
        this.deathy = (int) Math.round(Math.random() * 100);
        this.deathz = (int) Math.round(Math.random() * 1000);

        this.dimension = (String) ModUtil.selectRandom("minecraft:overworld", "minecraft:the_nether", "minecraft:the_end");
    }

    @FunctionalInterface
    public interface Redirector<T extends Screen> {

        T create(Screen parent);

    }
}
