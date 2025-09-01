package me.clefal.whats_your_build.data.modules.armor;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.clefal.whats_your_build.data.handler.ComponentType;
import me.clefal.whats_your_build.data.handler.IBuildComponent;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.util.*;


public record VanillaArmorComponent(Map<EquipmentSlot, ItemStack> armors) implements IBuildComponent<VanillaArmorComponent> {
    public static final String ID = "armor";
    public static final MapCodec<VanillaArmorComponent> CODEC = RecordCodecBuilder.mapCodec(i ->
            i.group(
                    Codec.unboundedMap(EquipmentSlot.CODEC, ItemStack.CODEC).fieldOf("armors").forGetter(x -> x.armors)
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
        return ID;
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

    @Override
    public VanillaArmorComponent makeCleanCopy() {
        HashMap<EquipmentSlot, ItemStack> objectObjectHashMap = new HashMap<>();
        this.armors.forEach((x, y) -> {
            objectObjectHashMap.put(x, ItemStack.EMPTY);
        });
        return null;
    }

    @Override
    public Container asContainer() {
        List<EquipmentSlot> list = List.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND);

        SimpleContainer simpleContainer = new SimpleContainer(list.size());
        for (int i = 0; i < simpleContainer.getContainerSize(); i++) {
            simpleContainer.setItem(i, this.armors().getOrDefault(list.get(i), ItemStack.EMPTY));
        }
        return simpleContainer;
    }


}
