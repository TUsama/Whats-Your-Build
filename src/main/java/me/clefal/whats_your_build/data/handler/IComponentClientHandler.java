package me.clefal.whats_your_build.data.handler;

import me.clefal.whats_your_build.client.screen.IBuildMenuContainer;
import me.clefal.whats_your_build.client.screen.IBuildMenuContainerHolder;
import me.clefal.whats_your_build.client.screen.buildscreen.BuildMenuTab;

import java.util.function.Function;

public interface IComponentClientHandler<T extends IBuildComponent<T>> {


    byte getIndex();

    //why java's generic is so dumb...
    Function<IBuildMenuContainerHolder<?>, BuildMenuTab<?, ?>> getBuildMenuTabFunction(Object component);
}
