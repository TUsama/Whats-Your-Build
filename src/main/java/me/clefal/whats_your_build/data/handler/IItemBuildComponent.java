package me.clefal.whats_your_build.data.handler;

import net.minecraft.world.Container;

public interface IItemBuildComponent<T extends IBuildComponent<?>> extends IBuildComponent<T>{

    T copy();
    T makeCleanCopy();

    Container asContainer();
    T getFromContainer(Container container);
}
