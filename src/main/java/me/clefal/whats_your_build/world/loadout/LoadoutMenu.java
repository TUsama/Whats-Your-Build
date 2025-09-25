package me.clefal.whats_your_build.world.loadout;

import lombok.Getter;
import me.clefal.whats_your_build.data.buildobject.Build;
import me.clefal.whats_your_build.world.BuildMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class LoadoutMenu extends BuildMenu {
    public static final int armoryRows = 9;
    public static final int armoryColumns = 3;
    public static final int size = armoryRows * armoryColumns;
    private final Container armory;
    public int startX;
    public int startY;
    private Build selfBuild;
    @Getter
    private BlockPos pos;

    public LoadoutMenu(MenuType<?> menuType, int containerId, Inventory playerInventory, FriendlyByteBuf buf) {
        this(menuType, containerId, playerInventory, new SimpleContainer(size), buf.readJsonWithCodec(Build.CODEC), buf.readBlockPos());
    }

    public LoadoutMenu(MenuType<?> menuType, int containerId, Inventory playerInventory, Container armory, Build selfBuild, BlockPos pos) {
        super(menuType, containerId);
        this.armory = armory;
        this.selfBuild = selfBuild;
        this.pos = pos;
        int armoryStartX = 8 + 9 * 18 + 14;
        int armoryStartY = 12;
        startX = armoryStartX;
        startY = armoryStartY;

        for (int row = 0; row < armoryRows; row++) {
            for (int col = 0; col < armoryColumns; col++) {
                int index = row * armoryColumns + col;
                int x = armoryStartX + col * 18;
                int y = armoryStartY + row * 18;
                this.addSlot(new Slot(armory, index, x, y));
            }
        }

        int i1;
        int j1;
        for (i1 = 0; i1 < 3; ++i1) {
            for (j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerInventory, j1 + (i1 + 1) * 9, 8 + j1 * 18, 84 + i1 * 18));
            }
        }

        for (i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 142));
        }

    }

    public Build getSelfBuild() {
        return selfBuild.copy();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(i);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (i < size) {
                if (!this.moveItemStackTo(itemstack1, armoryColumns * 9, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, armoryColumns * 9, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.armory.stillValid(player);
    }

    @Override
    public void handleBuild(Build build) {

    }
}
