package me.clefal.whats_your_build.handler;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;

public interface IBuildComponent<SELF> {
    byte getHandlerType();
    Codec<SELF> getCodeC();
    ResourceLocation getLocation();

}
