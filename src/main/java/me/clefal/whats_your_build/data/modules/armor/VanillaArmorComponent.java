package me.clefal.whats_your_build.data.modules.armor;

import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.data.handler.ComponentType;
import me.clefal.whats_your_build.data.handler.IBuildComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;


public record VanillaArmorComponent(List<ItemStack> armors) implements IBuildComponent<VanillaArmorComponent> {
    public static final MapCodec<VanillaArmorComponent> CODEC = RecordCodecBuilder.mapCodec(i ->
            i.group(
                    ItemStack.CODEC.listOf().fieldOf("armors").forGetter(x -> x.armors.toJavaList())
            ).apply(i, x -> new VanillaArmorComponent(List.ofAll(x)))
    );
    @Override
    public byte getHandlerIndex() {
        return ComponentType.VANILLA_ARMOR;
    }

    //? 1.20.1 {
    /*@Override
    public ResourceLocation getRenderIcon() {
        return CommonClass.id("textures/gui/armor-icon.png");
    }
    *///?} else {

    @Override
    public String getIdentifier() {
        return "armor";
    }

    //?}



    @Override
    public MapCodec<VanillaArmorComponent> getCodec() {
        return CODEC;
    }

    @Override
    public VanillaArmorComponent copy() {
        return new VanillaArmorComponent(armors.map(ItemStack::copy));
    }


}
