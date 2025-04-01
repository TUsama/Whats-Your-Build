package me.clefal.whats_your_build.client.screen;

import me.clefal.whats_your_build.handler.IBuildComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public abstract class BuildMenu<T extends IBuildComponent<?>> extends AbstractWidget implements Renderable {
    protected final List<? extends GuiEventListener> children = new ArrayList<>();
    protected final Minecraft minecraft = Minecraft.getInstance();

    protected final T component;
    protected final PlayerBuildScreen screen;

    public BuildMenu(T component, PlayerBuildScreen screen) {
        super(0, 0, 0, 0, Component.literal(""));
        this.component = component;
        this.screen = screen;
    }


    public void setSize(int width, int height){
        this.width = width;
        this.height = height;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
