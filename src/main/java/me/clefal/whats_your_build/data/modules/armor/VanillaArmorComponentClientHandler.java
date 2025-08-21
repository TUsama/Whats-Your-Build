package me.clefal.whats_your_build.data.modules.armor;

import me.clefal.whats_your_build.client.screen.IBuildMenuContainer;
import me.clefal.whats_your_build.client.screen.IBuildMenuContainerHolder;
import me.clefal.whats_your_build.client.screen.buildscreen.BuildMenuTab;
import me.clefal.whats_your_build.client.screen.buildscreen.vanilla.VanillaArmorMenuTab;
import me.clefal.whats_your_build.client.screen.buildscreen.vanilla.WritableVanillaArmorMenuTab;
import me.clefal.whats_your_build.data.handler.ComponentType;
import me.clefal.whats_your_build.data.handler.IBuildComponent;
import me.clefal.whats_your_build.data.handler.IComponentClientHandler;
import me.clefal.whats_your_build.world.IRewritableMenu;

import java.rmi.UnexpectedException;
import java.util.function.BiFunction;
import java.util.function.Function;

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
    public BiFunction<IBuildMenuContainerHolder<?>, IRewritableMenu, BuildMenuTab<?>> getBuildMenuTabFunction(IBuildComponent<?> component) {
        return (x, y) -> new VanillaArmorMenuTab((VanillaArmorComponent) component, x, y);
    }


}
