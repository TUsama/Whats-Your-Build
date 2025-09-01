//? if neoforge || forge {
package me.clefal.whats_your_build.data.modules.compat.curios.menu;

import me.clefal.whats_your_build.client.screen.WYBScreen;
import me.clefal.whats_your_build.client.screen.buildscreen.BuildMenuTab;
import me.clefal.whats_your_build.data.modules.compat.curios.CuriosComponent;
import me.clefal.whats_your_build.world.IRewritableMenu;
import net.minecraft.network.chat.Component;

public class CuriosMenuTab extends BuildMenuTab<CuriosComponent> {

    public CuriosMenuTab(CuriosComponent component, WYBScreen<?> holder, IRewritableMenu menu) {
        super(Component.translatable("wyb.compat.screen.tab.curios"), component, holder, menu);
    }

}
//?}