package me.clefal.whats_your_build.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import me.clefal.whats_your_build.handler.IBuildComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;

import java.util.function.Supplier;

public abstract class BuildMenuTab<E extends IBuildComponent<?>, T extends BuildMenu<E>> extends PlainTextButton {
    protected E component;
    protected PlayerBuildScreen screen;


    public BuildMenuTab(Component message, E component, PlayerBuildScreen screen) {
        super(0, 0, 0, 0, message, button -> {
        }, Minecraft.getInstance().font);
        this.component = component;
        this.screen = screen;
        this.setTooltip(Tooltip.create(message));
        this.height = Minecraft.getInstance().font.lineHeight;
        this.width = Minecraft.getInstance().font.width(message);
    }

    public abstract Supplier<T> getMenu();

    @Override
    public final void onPress() {
        screen.setNewMenu(getMenu().get());
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        if (this.isFocused()) {
            guiGraphics.blitNineSliced(BasePlayerBuildScreen.COMPONENT, this.getX(), this.getY(), this.getWidth(), this.getHeight(), 2, 2, 128, 20, 128, 10);
        } else {
            guiGraphics.fillGradient(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), 1, FastColor.ARGB32.color(0, 0, 0, 0), FastColor.ARGB32.color(255, 0, 0, 0));
            guiGraphics.blitNineSliced(BasePlayerBuildScreen.COMPONENT, this.getX(), this.getY(), this.getWidth(), this.getHeight(), 2, 2, 12, 8, 0, 0);
        }

    }

    @Override
    public void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

}
