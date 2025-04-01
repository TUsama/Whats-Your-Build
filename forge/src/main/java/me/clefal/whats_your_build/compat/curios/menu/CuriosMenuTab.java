package me.clefal.whats_your_build.compat.curios.menu;

import me.clefal.whats_your_build.client.screen.BuildMenuTab;
import me.clefal.whats_your_build.client.screen.PlayerBuildScreen;
import me.clefal.whats_your_build.compat.curios.CuriosComponent;
import net.minecraft.network.chat.Component;

import java.util.function.Function;

public class CuriosMenuTab extends BuildMenuTab<CuriosComponent, CuriosMenu> {

    public CuriosMenuTab(CuriosComponent component, PlayerBuildScreen screen) {
        super(Component.translatable("wyb.compat.screen.tab.curios"), component, screen);
    }

    @Override
    public Function<PlayerBuildScreen, CuriosMenu> getMenu() {
        return screen1 -> new CuriosMenu(component, screen1);
    }
}
