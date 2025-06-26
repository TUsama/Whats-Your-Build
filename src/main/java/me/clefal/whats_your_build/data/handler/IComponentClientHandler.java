package me.clefal.whats_your_build.data.handler;

import com.mojang.serialization.Codec;
import me.clefal.whats_your_build.client.screen.BaseBuildScreen;
import me.clefal.whats_your_build.client.screen.buildscreen.BuildMenuTab;
import me.clefal.whats_your_build.client.screen.buildscreen.PlayerBuildScreen;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Function;

public interface IComponentClientHandler<T extends IBuildComponent<T>> {


    byte getIndex();

    //why java's generic is so dumb...
    Function<BaseBuildScreen, BuildMenuTab<?, ?>> getBuildMenuTabFunction(Object component);
}
