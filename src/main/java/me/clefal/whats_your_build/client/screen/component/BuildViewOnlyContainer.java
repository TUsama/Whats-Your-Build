package me.clefal.whats_your_build.client.screen.component;

import me.clefal.whats_your_build.data.buildobject.Build;
import net.minecraft.world.entity.player.Player;

public class BuildViewOnlyContainer extends BuildPresentContainer {

    public BuildViewOnlyContainer(Player targetPlayer, Build build) {
        super(targetPlayer, build);
    }


}
