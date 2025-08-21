package me.clefal.whats_your_build.data.modules.armor;

import com.clefal.nirvana_lib.relocated.io.vavr.API;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.HashMap;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.data.handler.ComponentType;
import me.clefal.whats_your_build.data.handler.IBuildComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;


public record VanillaArmorComponent(EnumMap<EquipmentSlot, ItemStack> armors) implements IBuildComponent<VanillaArmorComponent> {
    public static final MapCodec<VanillaArmorComponent> CODEC = RecordCodecBuilder.mapCodec(i ->
            i.group(
                    Codec.pair(EquipmentSlot.CODEC, ItemStack.CODEC).listOf().xmap(x -> {
                        java.util.HashMap<EquipmentSlot, ItemStack> equipmentSlotItemStackHashMap = new java.util.HashMap<>();
                        for (Pair<EquipmentSlot, ItemStack> equipmentSlotItemStackPair : x) {
                            equipmentSlotItemStackHashMap.put(equipmentSlotItemStackPair.getFirst(), equipmentSlotItemStackPair.getSecond());
                        }

                        return new EnumMap<>(equipmentSlotItemStackHashMap);
                    }, x -> {
                        ArrayList<Pair<EquipmentSlot, ItemStack>> pairs = new ArrayList<>();
                        for (Map.Entry<EquipmentSlot, ItemStack> equipmentSlotItemStackEntry : x.entrySet()) {
                            pairs.add(Pair.of(equipmentSlotItemStackEntry.getKey(), equipmentSlotItemStackEntry.getValue()));
                        }
                        return pairs;
                    }).fieldOf("armors").forGetter(x -> x.armors)

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
        java.util.HashMap<EquipmentSlot, ItemStack> equipmentSlotItemStackHashMap = new java.util.HashMap<>();
        for (Map.Entry<EquipmentSlot, ItemStack> equipmentSlotItemStackEntry : armors.entrySet()) {
            equipmentSlotItemStackHashMap.put(equipmentSlotItemStackEntry.getKey(), equipmentSlotItemStackEntry.getValue().copy());
        }

        return new VanillaArmorComponent(new EnumMap<>(equipmentSlotItemStackHashMap));
    }


}
