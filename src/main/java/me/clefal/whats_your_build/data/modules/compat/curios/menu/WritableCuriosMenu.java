package me.clefal.whats_your_build.data.modules.compat.curios.menu;

import me.clefal.whats_your_build.client.screen.RenderContext;
import me.clefal.whats_your_build.client.screen.loadoutscreen.InMenuMutableArmorHolder;
import me.clefal.whats_your_build.data.modules.compat.curios.CuriosComponent;

public class WritableCuriosMenu extends CuriosMenu {

    public WritableCuriosMenu(CuriosComponent component, RenderContext context) {
        super(component, context);
    }

    @Override
    public void initHolders() {
        holders = component.curios()
                .map(x -> new InMenuMutableArmorHolder(holderRadius, x));
    }
}
