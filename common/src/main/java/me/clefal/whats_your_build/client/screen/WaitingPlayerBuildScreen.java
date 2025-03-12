package me.clefal.whats_your_build.client.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector2f;

import java.util.Map;

public class WaitingPlayerBuildScreen extends Screen {
    protected final int WIDTH = 128;
    protected final int HEIGHT = 256;

    protected WaitingPlayerBuildScreen() {
        super(Component.literal(""));
    }



    @Override
    protected void init() {
        super.init();
        this.width = WIDTH;
        this.height = HEIGHT;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderBackground(guiGraphics);
    }
}
