package me.clefal.whats_your_build.modules.armor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.clefal.whats_your_build.handler.ComponentType;
import me.clefal.whats_your_build.handler.IBuildComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public record VanillaArmorComponent(List<ItemStack> armors) implements IBuildComponent<VanillaArmorComponent> {

    public static final Codec<VanillaArmorComponent> CODEC = RecordCodecBuilder.create(i -> i.group(ItemStack.CODEC.listOf().fieldOf("armors").forGetter(VanillaArmorComponent::armors)).apply(i, VanillaArmorComponent::new));

    @Override
    public byte getHandlerType() {
        return ComponentType.VANILLA_ARMOR;
    }

    @Override
    public Codec<VanillaArmorComponent> getCodeC() {
        return CODEC;
    }


    @Override
    public ResourceLocation getLocation() {
        return null;
    }



}
