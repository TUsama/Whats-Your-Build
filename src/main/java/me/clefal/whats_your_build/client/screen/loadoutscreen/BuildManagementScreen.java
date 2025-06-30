//? neoforge {
package me.clefal.whats_your_build.client.screen.loadoutscreen;

import me.clefal.whats_your_build.world.loadout.BuildManagementMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class BuildManagementScreen extends AbstractContainerScreen<BuildManagementMenu> {
    public final BuildSelectionList buildList;


    public BuildManagementScreen(BuildManagementMenu menu, Inventory playerInventory) {
        super(menu, playerInventory, Component.literal(""));
        this.buildList = new BuildSelectionList(100, 80, 80, 160);
    }

    @Override
    protected void init() {
        super.init();

    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {

    }
}
//?}