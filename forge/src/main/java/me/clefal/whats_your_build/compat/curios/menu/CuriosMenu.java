package me.clefal.whats_your_build.compat.curios.menu;

import me.clefal.whats_your_build.client.screen.BuildMenu;
import me.clefal.whats_your_build.client.screen.PlayerBuildScreen;
import me.clefal.whats_your_build.compat.curios.CuriosComponent;
import net.minecraft.client.gui.GuiGraphics;

public class CuriosMenu extends BuildMenu<CuriosComponent> {


    public CuriosMenu(CuriosComponent component, PlayerBuildScreen screen) {
        super(component, screen);
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int i1, float v) {

    }
}
