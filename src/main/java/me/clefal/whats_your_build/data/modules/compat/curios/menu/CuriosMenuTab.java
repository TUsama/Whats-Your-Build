//? if neoforge || forge {
package me.clefal.whats_your_build.data.modules.compat.curios.menu;

import me.clefal.whats_your_build.client.screen.BaseBuildScreen;
import me.clefal.whats_your_build.client.screen.buildscreen.BuildMenuTab;
import me.clefal.whats_your_build.client.screen.buildscreen.PlayerBuildScreen;
import me.clefal.whats_your_build.client.screen.buildscreen.RenderContext;
import me.clefal.whats_your_build.data.modules.compat.curios.CuriosComponent;
import net.minecraft.network.chat.Component;

import java.util.function.Supplier;

public class CuriosMenuTab extends BuildMenuTab<CuriosComponent, CuriosMenu> {

    public CuriosMenuTab(CuriosComponent component, BaseBuildScreen screen) {
        super(Component.translatable("wyb.compat.screen.tab.curios"), component, screen);
    }

    @Override
    public Supplier<CuriosMenu> getMenu() {
        return () -> new CuriosMenu(component, screen.generateRenderContext());
    }
}
//?}