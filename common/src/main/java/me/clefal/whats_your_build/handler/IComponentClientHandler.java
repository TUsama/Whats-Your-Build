package me.clefal.whats_your_build.handler;

import com.clefal.nirvana_lib.relocated.io.vavr.Tuple;
import com.mojang.serialization.Codec;
import me.clefal.whats_your_build.client.screen.BuildMenuTab;
import me.clefal.whats_your_build.client.screen.PlayerBuildScreen;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Function;

public interface IComponentClientHandler<T extends IBuildComponent<T>> {
    default IBuildComponent<T> readBuf(FriendlyByteBuf buf){
        return buf.readJsonWithCodec(getCodeC());
    }

    byte getIndex();

    //why java's generic is so dumb...
    Function<PlayerBuildScreen, BuildMenuTab<?, ?>> getBuildMenuTabFunction(Object component);

    Codec<T> getCodeC();
}
