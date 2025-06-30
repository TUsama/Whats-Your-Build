package me.clefal.whats_your_build.client.screen.buildscreen.vanilla;

import me.clefal.whats_your_build.client.screen.IBuildMenuContainer;
import me.clefal.whats_your_build.client.screen.IBuildMenuContainerHolder;
import me.clefal.whats_your_build.client.screen.buildscreen.BuildMenuTab;
import me.clefal.whats_your_build.client.screen.buildscreen.RenderContext;
import me.clefal.whats_your_build.data.modules.armor.VanillaArmorComponent;
import net.minecraft.network.chat.Component;

import java.util.function.Supplier;

public class VanillaArmorMenuTab extends BuildMenuTab<VanillaArmorComponent, VanillaArmorMenu> {


    public VanillaArmorMenuTab(VanillaArmorComponent component, IBuildMenuContainerHolder<?> holder) {
        super(Component.translatable("wyb.screen.tab.armor"), component, holder);
    }

    @Override
    public Supplier<VanillaArmorMenu> getMenu() {
        return () -> new VanillaArmorMenu(component, holder.generateRenderContext());
    }

}
