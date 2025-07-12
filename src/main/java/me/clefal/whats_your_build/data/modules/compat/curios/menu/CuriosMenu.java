//? if neoforge || forge {
package me.clefal.whats_your_build.data.modules.compat.curios.menu;

import me.clefal.whats_your_build.client.screen.buildscreen.RenderContext;
import me.clefal.whats_your_build.client.screen.buildscreen.vanilla.ArmorHolder;
import me.clefal.whats_your_build.client.screen.component.NormalArmorHolder;
import me.clefal.whats_your_build.data.modules.compat.curios.CuriosComponent;


public class CuriosMenu extends NormalArmorHolder<CuriosComponent> {


    public CuriosMenu(CuriosComponent component, RenderContext context) {
        super(component, context);

    }

    @Override
    public void initHolders() {
        holders = component.curios()
                .map(x -> new ArmorHolder(holderRadius, x));
    }

}
//?}