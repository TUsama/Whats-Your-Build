package me.clefal.whats_your_build.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.handler.IBuildComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;

import java.util.function.Supplier;

public abstract class BuildMenuTab<E extends IBuildComponent<?>, T extends BuildMenu<E>> extends PlainTextButton {
    protected E component;
    protected PlayerBuildScreen screen;
    public final static ResourceLocation TAB = CommonClass.id("textures/gui/menu-tab.png");


    public BuildMenuTab(Component message, E component, PlayerBuildScreen screen) {
        super(0, 0, 0, 0, message, button -> {
        }, Minecraft.getInstance().font);
        this.component = component;
        this.screen = screen;
        this.height = 8;
        this.width = 16;
    }

    public abstract Supplier<T> getMenu();

    @Override
    public final void onPress() {
        screen.setNewMenu(getMenu().get());
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        var scale = 0.7f;
        var i = 1.0f / scale;
        pose.scale(scale, scale, 1);
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
        pose.popPose();
        RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();

        //pose.translate(0, 0, 10);
        if (this.isHoveredOrFocused()) {
            guiGraphics.blit(TAB, this.getX(), this.getY(), this.getWidth(), this.getHeight(), 0, 0, 32, 16, 32, 32);
        } else {
            guiGraphics.blit(TAB, this.getX(), this.getY(), this.getWidth(), this.getHeight(), 0, 16, 32, 16, 32, 32);
            //pose.translate(0, 0, 1);
            //guiGraphics.fillGradient(RenderType.guiOverlay(), this.getX(), this.getY() + this.getHeight() / 2, this.getX() + this.getWidth(), this.getY() + this.getHeight(), 0, -16777216, 0);

        }
        pose.popPose();

    }

    @Override
    public void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

}
