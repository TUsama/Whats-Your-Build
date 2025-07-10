package me.clefal.whats_your_build.data.modules.armor;

import com.clefal.nirvana_lib.relocated.io.vavr.API;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.HashMap;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.Map;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.data.handler.ComponentType;
import me.clefal.whats_your_build.data.handler.IBuildComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;


public record VanillaArmorComponent(Map<String, ItemStack> armors) implements IBuildComponent<VanillaArmorComponent> {
    public static final MapCodec<VanillaArmorComponent> CODEC = RecordCodecBuilder.mapCodec(i ->
            i.group(
                    Codec.unboundedMap(Codec.STRING, ItemStack.CODEC).xmap(x -> Map.narrow(HashMap.ofAll(x)), Map::toJavaMap).fieldOf("map").forGetter(x -> x.armors)
            ).apply(i, VanillaArmorComponent::new)
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
        return new VanillaArmorComponent(HashMap.ofEntries(armors.map(x -> API.Tuple(x._1, x._2.copy()))));
    }


}
