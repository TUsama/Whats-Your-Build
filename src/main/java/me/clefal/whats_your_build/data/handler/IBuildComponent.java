package me.clefal.whats_your_build.data.handler;

import com.clefal.nirvana_lib.relocated.io.vavr.collection.LinkedHashMap;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.Map;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import me.clefal.whats_your_build.data.modules.armor.VanillaArmorComponent;
//? forge || neoforge
import me.clefal.whats_your_build.data.modules.compat.curios.CuriosComponent;
import net.minecraft.world.Container;

public interface IBuildComponent<SELF extends IBuildComponent<?>> {
    Map<Byte, MapCodec<? extends IBuildComponent<?>>> COMPONENT_CODECS = LinkedHashMap.of(
            ComponentType.VANILLA_ARMOR, VanillaArmorComponent.CODEC
            //? forge || neoforge
            ,ComponentType.CURIOS, CuriosComponent.CODEC
    );

    Codec<IBuildComponent<?>> COMPONENT_CODEC = Codec.BYTE.dispatch(
            IBuildComponent::getHandlerIndex,
            index -> COMPONENT_CODECS.get(index).getOrElseThrow(() -> new IllegalArgumentException("can't find a Codec with index: " + index))
    //? 1.20.1
                    /*.codec()*/
    );

    byte getHandlerIndex();
    //? 1.20.1
    /*ResourceLocation getRenderIcon();*/
    //? > 1.20.1
    String getIdentifier();
    MapCodec<SELF> getCodec();
    SELF copy();
    SELF makeCleanCopy();

    Container asContainer();
    SELF getFromContainer(Container container);
}
