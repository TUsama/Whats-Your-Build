package me.clefal.whats_your_build.client.screen.vanilla;

import me.clefal.whats_your_build.client.screen.BuildMenuTab;
import me.clefal.whats_your_build.client.screen.PlayerBuildScreen;
import me.clefal.whats_your_build.data.modules.armor.VanillaArmorComponent;
import net.minecraft.network.chat.Component;

import java.util.function.Function;

public class VanillaArmorMenuTab extends BuildMenuTab<VanillaArmorComponent, VanillaArmorMenu> {


    public VanillaArmorMenuTab(VanillaArmorComponent component, PlayerBuildScreen screen) {
        super(Component.translatable("wyb.screen.tab.armor"), component, screen);
    }

    @Override
    public Function<PlayerBuildScreen, VanillaArmorMenu> getMenu() {
        return (screen) -> new VanillaArmorMenu(component, screen);
    }

}
