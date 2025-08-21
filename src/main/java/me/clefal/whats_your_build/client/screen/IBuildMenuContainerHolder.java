package me.clefal.whats_your_build.client.screen;

public interface IBuildMenuContainerHolder<T extends IBuildMenuContainer> {

    RenderContext generateRenderContext();

    T getContainer();
}
