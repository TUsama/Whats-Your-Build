package me.clefal.whats_your_build.client.screen;

import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.Nullable;

public class PlayerBuildScreen extends WaitingPlayerBuildScreen {

    private final List<BuildMenuTab<?>> tabs;
    @Nullable
    private BuildMenu currentMenu;


    public PlayerBuildScreen(List<BuildMenuTab<?>> tabs) {
        this.tabs = tabs;
        this.currentMenu = tabs.lastOption().isEmpty() ? null : tabs.lastOption().get().menu.get();
    }



    @Override
    protected void init() {
        super.init();

    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }
}
