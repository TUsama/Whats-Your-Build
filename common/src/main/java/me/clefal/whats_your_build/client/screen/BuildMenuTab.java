package me.clefal.whats_your_build.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.handler.IBuildComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;


import java.util.ResourceBundle;
import java.util.function.Supplier;

public abstract class BuildMenuTab<E extends IBuildComponent<?> , T extends BuildMenu<E>> extends PlainTextButton {
    protected Supplier<T> menu;
    protected E component;
    protected PlayerBuildScreen screen;


    public BuildMenuTab(int x, int y, int width, int height, Component message, Supplier<T> menu, E component, PlayerBuildScreen screen) {
        super(x, y, width, height, message, new OnPress() {
            @Override
            public void onPress(Button button) {
                screen.setNewMenu(menu.get());
            }
        }, Minecraft.getInstance().font);
        this.menu = menu;
        this.component = component;
        this.screen = screen;
        this.setTooltip(Tooltip.create(message));
        this.height = Minecraft.getInstance().font.lineHeight;
        this.width = Minecraft.getInstance().font.width(message);
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        if (this.isFocused()){
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
