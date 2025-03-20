package me.clefal.whats_your_build.handler;

import com.clefal.nirvana_lib.relocated.io.vavr.Tuple;
import com.clefal.nirvana_lib.relocated.io.vavr.Tuple2;
import com.mojang.serialization.Codec;
import me.clefal.whats_your_build.client.screen.BuildMenuTab;
import me.clefal.whats_your_build.client.screen.PlayerBuildScreen;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Function;

public interface IComponentClientHandler<T extends IBuildComponent<T>> {
    default Tuple2<IBuildComponent<T>,  Function<PlayerBuildScreen, BuildMenuTab<?, ?>>> readBuf(FriendlyByteBuf buf){
        T t = buf.readJsonWithCodec(getCodeC());
        return Tuple.of(t, getBuildMenuTab(t));
    }

    byte getIndex();

    Function<PlayerBuildScreen, BuildMenuTab<?, ?>> getBuildMenuTab(T component);

    Codec<T> getCodeC();
}
