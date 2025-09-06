package me.clefal.whats_your_build.data.modules.armor;

import me.clefal.whats_your_build.client.screen.WYBScreen;
import me.clefal.whats_your_build.client.screen.buildscreen.BuildMenuTab;
import me.clefal.whats_your_build.client.screen.buildscreen.vanilla.VanillaArmorMenuTab;
import me.clefal.whats_your_build.data.handler.ComponentType;
import me.clefal.whats_your_build.data.handler.IBuildComponent;
import me.clefal.whats_your_build.data.handler.IComponentClientHandler;
import me.clefal.whats_your_build.world.IRewritable;

import java.util.function.BiFunction;

public class VanillaArmorComponentClientHandler implements IComponentClientHandler {
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
    public BiFunction<WYBScreen<?>, IRewritable, BuildMenuTab<?>> getBuildMenuTabFunction(IBuildComponent<?> component) {
        return (x, y) -> new VanillaArmorMenuTab((VanillaArmorComponent) component, x, y);
    }


}
