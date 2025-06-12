package me.clefal.whats_your_build.data.modules.compat.curios;

import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.data.handler.ComponentType;
import me.clefal.whats_your_build.data.handler.IBuildComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public record CuriosComponent(List<ItemStack> curios) implements IBuildComponent<CuriosComponent> {

    public static final Codec<CuriosComponent> CODEC = RecordCodecBuilder.create(i -> i.group(ItemStack.CODEC.listOf().fieldOf("curios").forGetter(x -> x.curios().asJava())).apply(i, x -> new CuriosComponent(List.ofAll(x))));

    @Override
    public byte getHandlerIndex() {
        return ComponentType.CURIOS;
    }

    @Override
    public ResourceLocation getRenderIcon() {
        return CommonClass.id("textures/gui/ring.png");
    }

    @Override
    public Codec<CuriosComponent> getCodec() {
        return CODEC;
    }

}
