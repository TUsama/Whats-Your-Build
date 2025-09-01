package me.clefal.whats_your_build.client.screen;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public abstract class WYBScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> implements IWYBScreen{

    public WYBScreen(T menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }
}
