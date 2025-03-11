package me.clefal.whats_your_build.handler;

import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;

public interface IComponentClientHandler<T extends IBuildComponent<T>> {
    default IBuildComponent<T> readBuf(FriendlyByteBuf buf){
        return buf.readJsonWithCodec(getCodeC());
    }

    byte getIndex();

    Codec<T> getCodeC();
}
