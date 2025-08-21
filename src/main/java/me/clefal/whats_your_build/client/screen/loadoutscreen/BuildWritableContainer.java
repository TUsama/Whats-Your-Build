package me.clefal.whats_your_build.client.screen.loadoutscreen;

import me.clefal.whats_your_build.client.screen.IBuildMenuContainerHolder;
import me.clefal.whats_your_build.client.screen.component.BuildPresentContainer;
import me.clefal.whats_your_build.data.buildobject.Build;
import me.clefal.whats_your_build.data.handler.HandlerManager;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

public class BuildWritableContainer extends BuildPresentContainer {

    @Nullable
    public Build editingBuild;


    public BuildWritableContainer(Player targetPlayer, Build build) {
        super(targetPlayer, build);
    }

    public Build showBuild() {
        return editingBuild == null ? build : editingBuild;
    }

    public void refreshEdit() {
        editingBuild = null;
    }

    public void saveEdit() {
        build = editingBuild;
        refreshEdit();
    }

    public Build getEditingBuild() {
        if (editingBuild == null) {
            editingBuild = build.copy();
        }
        return editingBuild;
    }

    @Override
    public void initTabs(IBuildMenuContainerHolder<?> holder) {
        this.tabs = HandlerManager.getInstance().getWritableBuildMenuTabFunction(build).map(x -> x.apply(holder));
        tabs.headOption()
                .forEach(x -> {
                    this.currentMenu = x.getNewSlots().get();
                    setFocused(x);
                });
    }
}
