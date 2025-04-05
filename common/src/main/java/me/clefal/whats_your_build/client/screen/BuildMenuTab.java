package me.clefal.whats_your_build.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import me.clefal.whats_your_build.data.handler.IBuildComponent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public abstract class BuildMenuTab<E extends IBuildComponent<?>, T extends BuildMenu<E>> extends ImageButton {
    public static int TAB_WIDTH = 14;
    public static int TAB_HEIGHT = 8;
    protected E component;
    protected PlayerBuildScreen screen;


    public BuildMenuTab(Component message, E component, PlayerBuildScreen screen) {
        super(0, 0, TAB_WIDTH, TAB_HEIGHT, 0, 0, 32, component.getRenderIcon(), 32, 64, button -> {
        }, message);
        this.component = component;
        this.setTooltip(Tooltip.create(message));
        this.screen = screen;
    }


    @Override
    public void renderTexture(GuiGraphics guiGraphics, ResourceLocation texture, int x, int y, int uOffset, int vOffset, int textureDifference, int width, int height, int textureWidth, int textureHeight) {
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        pose.translate(0, 0, 10);
        if (!this.isHoveredOrFocused()) {
            guiGraphics.setColor(1, 1, 1, 0.7f);
            guiGraphics.blit(resourceLocation, (int) (getX() + (getWidth() / 2.0f) - 4), getY(), getHeight(), getHeight(), 0, 0, 32, 32, 32, 64);
            guiGraphics.setColor(1, 1, 1, 1);
        } else {
            guiGraphics.blit(resourceLocation, (int) (getX() + (getWidth() / 2.0f) - 5), getY() - 1, getHeight() + 1, getHeight() + 1, 0, 32, 32, 32, 32, 64);
        }
        pose.popPose();
    }

    public abstract Function<PlayerBuildScreen, T> getMenu();

    @Override
    public final void onPress() {
        screen.setNewMenu(getMenu().apply(screen));
    }


}
