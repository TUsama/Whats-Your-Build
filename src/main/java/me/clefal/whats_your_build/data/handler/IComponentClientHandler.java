package me.clefal.whats_your_build.data.handler;

import me.clefal.whats_your_build.client.screen.WYBScreen;
import me.clefal.whats_your_build.client.screen.buildscreen.BuildMenuTab;
import me.clefal.whats_your_build.world.IRewritable;

import java.util.function.BiFunction;

public interface IComponentClientHandler {

    public enum Type{
        IMMUTABLE,
        WRITABLE
    }

    byte getIndex();

    BiFunction<WYBScreen<?>, IRewritable, BuildMenuTab<?>> getBuildMenuTabFunction(IBuildComponent<?> component);

}
