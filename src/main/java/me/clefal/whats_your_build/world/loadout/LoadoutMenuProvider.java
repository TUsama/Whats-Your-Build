package me.clefal.whats_your_build.world.loadout;

import me.clefal.whats_your_build.data.buildobject.Build;
import me.clefal.whats_your_build.loaders.WYBRegistrate;
import me.clefal.whats_your_build.world.block.entity.LoadoutChestEntity;
import me.clefal.whats_your_build.world.player_build.PlayerBuildMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.Nullable;

public class LoadoutMenuProvider implements MenuProvider {
    private Build targetBuild;
    private Container armory;
    private BlockPos pos;

    public LoadoutMenuProvider(Build targetBuild, LoadoutChestEntity entity) {
        this.targetBuild = targetBuild;
        this.armory = entity;
        this.pos = entity.getBlockPos();
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new LoadoutMenu(WYBRegistrate.loadoutMenu.get(), containerId, playerInventory, armory, targetBuild, pos);
    }
}
