package me.clefal.whats_your_build.data.modules.armor;

import me.clefal.whats_your_build.client.screen.IBuildMenuContainer;
import me.clefal.whats_your_build.client.screen.IBuildMenuContainerHolder;
import me.clefal.whats_your_build.client.screen.buildscreen.BuildMenuTab;
import me.clefal.whats_your_build.client.screen.buildscreen.vanilla.VanillaArmorMenuTab;
import me.clefal.whats_your_build.data.handler.ComponentType;
import me.clefal.whats_your_build.data.handler.IComponentClientHandler;

import java.util.function.Function;

public class VanillaArmorComponentClientHandler implements IComponentClientHandler<VanillaArmorComponent> {
    private static VanillaArmorComponentClientHandler INSTANCE;

    public static VanillaArmorComponentClientHandler getInstance() {
        if (INSTANCE == null){
            INSTANCE = new VanillaArmorComponentClientHandler();
        }
        return INSTANCE;
    }

    @Override
    public byte getIndex() {
        return ComponentType.VANILLA_ARMOR;
    }

    @Override
    public Function<IBuildMenuContainerHolder<?>, BuildMenuTab<?, ?>> getBuildMenuTabFunction(Object component) {
        return holder -> new VanillaArmorMenuTab(((VanillaArmorComponent) component), holder);
    }



}
