package me.clefal.whats_your_build.client.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector2f;

import java.util.Map;

public class PlayerBuildScreen extends Screen {

    private Map<Vector2f, ItemStack> positionAndItem;

    protected PlayerBuildScreen() {
        super(Component.literal(""));
    }

    public static void refresh() {
        if (Minecraft.getInstance().screen instanceof PlayerBuildScreen) {
            Minecraft.getInstance().screen = new PlayerBuildScreen();
        }
    }

    public void update(Map<Vector2f, ItemStack> positionAndItem) {
        this.positionAndItem = positionAndItem;
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
