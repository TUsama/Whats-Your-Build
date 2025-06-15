package me.clefal.whats_your_build.data.handler;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;

public interface IBuildComponent<SELF> {
    byte getHandlerIndex();
    //? 1.20.1
    /*ResourceLocation getRenderIcon();*/
    //? > 1.20.1
    String getIdentifier();
    Codec<SELF> getCodec();
}
