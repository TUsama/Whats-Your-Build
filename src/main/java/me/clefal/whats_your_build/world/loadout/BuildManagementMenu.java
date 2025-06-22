package me.clefal.whats_your_build.world.loadout;

import me.clefal.whats_your_build.register.WYBMenuType;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class BuildManagementMenu extends AbstractContainerMenu {
    public static final int listWidth = 80;
    public static final int previewWidth = 120;

    public BuildManagementMenu(int containerId, Inventory playerInventory) {
        this(WYBMenuType.buildManagementMenuType.get(), containerId, playerInventory, new SimpleContainer(9 * 6));
    }

    public BuildManagementMenu(MenuType<BuildManagementMenu> menuType, int containerId, Inventory playerInventory, Container armory) {
        super(menuType, containerId);
        int i1;
        int j1;
        for(i1 = 0; i1 < 3; ++i1) {
            for(j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerInventory, j1 + (i1 + 1) * 9, 8 + j1 * 18, 84 + i1 * 18));
            }
        }

        for(i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 142));
        }

        int armoryStartX = 8 + 9 * 18 + 8; // 物品栏右边（9列宽 + 一点间隔）
        int armoryStartY = 8;              // 对齐头盔槽
        int armoryColumns = 6;
        int armoryRows = 9;

        for (int row = 0; row < armoryRows; row++) {
            for (int col = 0; col < armoryColumns; col++) {
                int index = row * armoryColumns + col;
                int x = armoryStartX + col * 18;
                int y = armoryStartY + row * 18;
                this.addSlot(new Slot(armory, index, x, y));
            }
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
