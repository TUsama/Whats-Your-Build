package me.clefal.whats_your_build.client.screen.vanilla;

import me.clefal.whats_your_build.client.screen.BuildMenuTab;
import me.clefal.whats_your_build.client.screen.PlayerBuildScreen;
import me.clefal.whats_your_build.handler.IBuildComponent;
import me.clefal.whats_your_build.modules.armor.VanillaArmorComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.function.Supplier;

public class VanillaArmorMenuTab extends BuildMenuTab<VanillaArmorComponent, VanillaArmorMenu> {

    public VanillaArmorMenuTab(int x, int y, int width, int height, VanillaArmorComponent component, PlayerBuildScreen screen) {
        super(x, y, width, height, Component.translatable("wyb.screen.tab.armor"), () -> new VanillaArmorMenu(component), component, screen);
    }


}
