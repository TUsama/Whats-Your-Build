package me.clefal.whats_your_build.data;

import com.mojang.serialization.Codec;

public interface IPersistedObject<SELF>
{
    Codec<SELF> getCodec();
}
