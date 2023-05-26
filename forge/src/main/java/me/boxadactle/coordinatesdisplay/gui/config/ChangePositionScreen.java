package me.boxadactle.coordinatesdisplay.gui.config;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3d;
import me.boxadactle.coordinatesdisplay.CoordinatesDisplay;
<<<<<<< Updated upstream
import me.boxadactle.coordinatesdisplay.gui.widget.InvisibleButtonWidget;
import me.boxadactle.coordinatesdisplay.util.ModUtils;
=======
import me.boxadactle.coordinatesdisplay.util.ModUtil;
>>>>>>> Stashed changes
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.ChunkPos;
<<<<<<< Updated upstream
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
=======
import net.minecraft.world.phys.Vec3;
>>>>>>> Stashed changes

public class ChangePositionScreen extends Screen {

    private final Minecraft client = Minecraft.getInstance();

    Screen parent;

    Vector3d pos;
    ChunkPos chunkPos;
    float cameraYaw;

    boolean lockHudPos = false;

    int x;
    int y;
    float scale;

    int hudOffsetX;
    int hudOffsetY;

    boolean clickDelta = false;
    boolean scaleDelta = false;
    boolean moveDelta = false;

    int delay = 10;

    public ChangePositionScreen(Screen parent) {
        super(Component.translatable("screen.coordinatesdisplay.config.position"));
        this.parent = parent;

<<<<<<< Updated upstream
        this.pos = new Vector3d(Math.random() * 1000, Math.random() * 5, Math.random() * 1000);
        this.chunkPos = new ChunkPos(new BlockPos(pos.x, pos.y, pos.z));
        this.cameraYaw = ModUtils.randomYaw();
=======
        this.pos = new Vec3(Math.random() * 1000, Math.random() * 5, Math.random() * 1000);
        this.chunkPos = new ChunkPos((int)Math.round(this.pos.x), (int)Math.round(this.pos.z));
        this.cameraYaw  = (float) Math.random() * 180;
        this.cameraPitch  = (float) Math.random() * 180;
>>>>>>> Stashed changes

        x = CoordinatesDisplay.CONFIG.get().hudX;
        y = CoordinatesDisplay.CONFIG.get().hudY;
        scale = CoordinatesDisplay.CONFIG.get().hudScale;

        if (Minecraft.getInstance().level != null) {
            CoordinatesDisplay.shouldRenderOnHud = false;
        }

<<<<<<< Updated upstream
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, new ResourceLocation("coordinatesdisplay", "textures/background/change_position_bg.png"));

        double a = 1.4;
        int b = (int) (this.width * a);

        blit(matrices, 0, 0, 0.0F, 0.0F, b, ModUtils.aspectRatio(16, 9, b), b, ModUtils.aspectRatio(16, 9, this.width));
=======
>>>>>>> Stashed changes
    }

    @Override
    public void render(PoseStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);

        if (ModUtil.isMousePressed() && delay == 0) {
            if (CoordinatesDisplay.OVERLAY.isScaleButtonHovered(mouseX, mouseY) && !scaleDelta && !moveDelta) scaleDelta = true;

            if (!scaleDelta) {
                if (!clickDelta) {
                    clickDelta = true;

<<<<<<< Updated upstream
        fill(matrices, 0,0, (int) (this.width / 0.8), 35, ModUtils.TRANSPARENT_GRAY);

        drawCenteredString(matrices, this.font, Component.translatable("screen.coordinatesdisplay.config.position").getString(), (int) ((this.width / 2) / 0.8), 5, ModUtils.WHITE);
        drawCenteredString(matrices, this.font, Component.translatable("description.coordinatesdisplay.changeposition").getString(), (int) ((this.width / 2) / 0.9), 20, ModUtils.GRAY);
=======
                    int[] distance = ModUtil.getDistance(Math.round(mouseX / scale), Math.round(mouseY / scale), CoordinatesDisplay.OVERLAY.getX(), CoordinatesDisplay.OVERLAY.getY());
                    hudOffsetX = distance[0];
                    hudOffsetY = distance[1];
                }

                x = Math.round(ModUtil.clampToZero(Math.round(mouseX / scale) - hudOffsetX));
                y = Math.round(ModUtil.clampToZero(Math.round(mouseY / scale) - hudOffsetY));
>>>>>>> Stashed changes

                if (!moveDelta) moveDelta = true;

<<<<<<< Updated upstream
        fill(matrices, 0, this.height - 20, this.width, this.height, ModUtils.TRANSPARENT_GRAY);

        drawCenteredString(matrices, this.font, Component.translatable("description.coordinatesdisplay.changeposition2").getString(), this.width / 2, this.height - 15, ModUtils.WHITE);
=======
            } else {
                if (!clickDelta) {
                    clickDelta = true;

                    hudOffsetX = Math.round(CoordinatesDisplay.OVERLAY.getX() * scale);
                    hudOffsetY = Math.round(CoordinatesDisplay.OVERLAY.getY() * scale);
                }
>>>>>>> Stashed changes

                scale = ModUtil.calculateMouseScale(
                        Math.round(CoordinatesDisplay.OVERLAY.getX() * scale),
                        Math.round(CoordinatesDisplay.OVERLAY.getY() * scale),
                        CoordinatesDisplay.OVERLAY.getWidth(),
                        CoordinatesDisplay.OVERLAY.getHeight(),
                        mouseX, mouseY
                );

                x = Math.round(hudOffsetX / scale);
                y = Math.round(hudOffsetY / scale);
            }
        } else {
            if (delay != 0) delay -= 1;
        }

<<<<<<< Updated upstream
        CoordinatesDisplay.OVERLAY.render(matrices, pos, chunkPos, cameraYaw, null, x, y);
=======
        if (!ModUtil.isMousePressed() && clickDelta) {
            clickDelta = false;
            scaleDelta = false;
            moveDelta = false;
        }

        CoordinatesDisplay.OVERLAY.render(matrices, pos, chunkPos, cameraYaw, cameraPitch, null, x , y, CoordinatesDisplay.CONFIG.get().minMode, CoordinatesDisplay.OVERLAY.isHovered(mouseX, mouseY), scale);
>>>>>>> Stashed changes

    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void onClose() {
        CoordinatesDisplay.CONFIG.get().hudX = x;
        CoordinatesDisplay.CONFIG.get().hudY = y;
        CoordinatesDisplay.CONFIG.get().hudScale = scale;

        if (Minecraft.getInstance().level != null) {
            CoordinatesDisplay.shouldRenderOnHud = true;
            CoordinatesDisplay.CONFIG.save();
        }

        this.client.setScreen(parent);
    }
}
