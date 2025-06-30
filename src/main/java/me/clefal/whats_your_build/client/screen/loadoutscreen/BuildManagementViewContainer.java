package me.clefal.whats_your_build.client.screen.loadoutscreen;

import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import me.clefal.whats_your_build.client.screen.IBuildMenuContainer;
import me.clefal.whats_your_build.client.screen.buildscreen.BuildMenu;
import me.clefal.whats_your_build.client.screen.buildscreen.BuildMenuTab;
import me.clefal.whats_your_build.client.screen.buildscreen.RenderContext;
import me.clefal.whats_your_build.data.buildobject.Build;
import me.clefal.whats_your_build.data.handler.HandlerManager;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

public class BuildManagementViewContainer implements IBuildMenuContainer {

    public final Player targetPlayer;
    public Build currentBuild;
    public List<BuildMenuTab<?, ?>> tabs;
    @Nullable
    private BuildMenu<?> currentMenu;

    public BuildManagementViewContainer(Player targetPlayer) {
        this.targetPlayer = targetPlayer;
        this.tabs = List.of();
    }

    public void setCurrentBuild(Build build){
        this.currentBuild = build;
        this.tabs = HandlerManager.getInstance().getBuildMenuTabFunction(build).map(x -> x.apply(this));
        this.currentMenu = null;
        if (!tabs.isEmpty()){
            this.currentMenu = tabs.headOption().get().getMenu().get();
        }
    }


    @Override
    public List<BuildMenuTab<?, ?>> getTabs() {
        return tabs;
    }

    @Override
    public void setNewMenu(BuildMenu<?> menu) {
        this.currentMenu = menu;
    }

}
