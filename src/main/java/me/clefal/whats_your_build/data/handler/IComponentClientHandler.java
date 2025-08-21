package me.clefal.whats_your_build.data.handler;

import me.clefal.whats_your_build.client.screen.IBuildMenuContainer;
import me.clefal.whats_your_build.client.screen.IBuildMenuContainerHolder;
import me.clefal.whats_your_build.client.screen.buildscreen.BuildMenuTab;
import me.clefal.whats_your_build.world.IRewritableMenu;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface IComponentClientHandler {

    public enum Type{
        IMMUTABLE,
        WRITABLE
    }

    byte getIndex();

    BiFunction<IBuildMenuContainerHolder<?>, IRewritableMenu, BuildMenuTab<?>> getBuildMenuTabFunction(IBuildComponent<?> component);

}
