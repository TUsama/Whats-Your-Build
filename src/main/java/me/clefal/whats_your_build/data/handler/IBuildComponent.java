package me.clefal.whats_your_build.data.handler;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;

public interface IBuildComponent<SELF> {
    byte getHandlerIndex();
    Codec<SELF> getCodeC();
    ResourceLocation getRenderIcon();
    SELF self();

}
