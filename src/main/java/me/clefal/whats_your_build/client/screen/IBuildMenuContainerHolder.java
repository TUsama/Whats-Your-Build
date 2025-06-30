package me.clefal.whats_your_build.client.screen;

import me.clefal.whats_your_build.client.screen.buildscreen.RenderContext;

public interface IBuildMenuContainerHolder<T extends IBuildMenuContainer> {

    RenderContext generateRenderContext();
    T getContainer();
}
