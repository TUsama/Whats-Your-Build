package me.clefal.whats_your_build.client.screen.buildscreen.vanilla;

import me.clefal.whats_your_build.client.screen.WYBScreen;
import me.clefal.whats_your_build.client.screen.buildscreen.BuildMenuTab;
import me.clefal.whats_your_build.data.modules.armor.VanillaArmorComponent;
import me.clefal.whats_your_build.world.IRewritableMenu;
import net.minecraft.network.chat.Component;

public class VanillaArmorMenuTab extends BuildMenuTab<VanillaArmorComponent> {


    public VanillaArmorMenuTab(VanillaArmorComponent component, WYBScreen<?> holder, IRewritableMenu menu) {
        super(Component.translatable("wyb.screen.tab.armor"), component, holder, menu);
    }


}
