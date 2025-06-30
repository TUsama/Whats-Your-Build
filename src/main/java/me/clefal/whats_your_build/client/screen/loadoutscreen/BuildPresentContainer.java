package me.clefal.whats_your_build.client.screen.loadoutscreen;

import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import me.clefal.whats_your_build.client.screen.IBuildMenuContainer;
import me.clefal.whats_your_build.client.screen.buildscreen.BuildMenu;
import me.clefal.whats_your_build.client.screen.buildscreen.BuildMenuTab;
import me.clefal.whats_your_build.data.buildobject.Build;
import me.clefal.whats_your_build.data.handler.HandlerManager;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

public abstract class BuildPresentContainer extends AbstractContainerEventHandler implements IBuildMenuContainer, Renderable {

    public final Player targetPlayer;
    public final List<BuildMenuTab<?, ?>> tabs;
    @Nullable
    private BuildMenu<?> currentMenu;

    public BuildPresentContainer(Player targetPlayer, Build build) {
        super();
        this.targetPlayer = targetPlayer;
        this.tabs = HandlerManager.getInstance().getBuildMenuTabFunction(build).map(x -> x.apply(this));
    }


    @Override
    public List<BuildMenuTab<?, ?>> getTabs() {
        return tabs;
    }


    @Override
    public void setNewMenu(BuildMenu<?> menu) {
        this.currentMenu = menu;
    }


    @Override
    public java.util.List<? extends GuiEventListener> children() {
        return tabs.asJava();
    }

}
