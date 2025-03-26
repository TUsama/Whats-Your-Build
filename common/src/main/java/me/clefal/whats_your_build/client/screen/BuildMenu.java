package me.clefal.whats_your_build.client.screen;

import me.clefal.whats_your_build.handler.IBuildComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;

import java.util.ArrayList;
import java.util.List;

public abstract class BuildMenu<T extends IBuildComponent<?>> extends AbstractContainerEventHandler implements Renderable {
    protected final List<? extends GuiEventListener> children = new ArrayList<>();
    protected final Minecraft minecraft = Minecraft.getInstance();
    protected final T component;
    protected final PlayerBuildScreen screen;

    public BuildMenu(T component, PlayerBuildScreen screen) {
        this.component = component;
        this.screen = screen;
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return children;
    }
}
