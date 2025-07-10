package me.clefal.whats_your_build.client.screen.loadoutscreen;

import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import me.clefal.whats_your_build.client.screen.component.BuildPresentContainer;
import me.clefal.whats_your_build.client.screen.buildscreen.BuildMenu;
import me.clefal.whats_your_build.client.screen.buildscreen.BuildMenuTab;
import me.clefal.whats_your_build.data.buildobject.Build;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

public class LoadoutViewContainer extends BuildPresentContainer {

    public final Player targetPlayer;
    public Build currentBuild;
    public List<BuildMenuTab<?, ?>> tabs;
    @Nullable
    private BuildMenu<?> currentMenu;

    public LoadoutViewContainer(Player targetPlayer, Build build) {
        super(targetPlayer, build);
        this.targetPlayer = targetPlayer;
        this.tabs = List.of();
    }

    public void setCurrentBuild(Build build) {
        this.currentBuild = build;
        this.currentMenu = null;
        if (!tabs.isEmpty()) {
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
