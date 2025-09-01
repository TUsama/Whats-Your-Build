package me.clefal.whats_your_build.world.player_build;

import me.clefal.whats_your_build.data.buildobject.Build;
import me.clefal.whats_your_build.loaders.WYBRegistrate;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BucketItem;
import org.jetbrains.annotations.Nullable;

public class PlayerBuildMenuProvider implements MenuProvider {
    private Build targetBuild;

    public PlayerBuildMenuProvider(Build targetBuild) {
        this.targetBuild = targetBuild;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new PlayerBuildMenu(WYBRegistrate.playerBuildMenu.get(), containerId, targetBuild);
    }
}
