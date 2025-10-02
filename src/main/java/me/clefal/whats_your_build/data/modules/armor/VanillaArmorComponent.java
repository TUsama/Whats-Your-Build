package me.clefal.whats_your_build.data.modules.armor;

import com.clefal.nirvana_lib.relocated.io.vavr.Tuple;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.clefal.whats_your_build.data.handler.ComponentType;
import me.clefal.whats_your_build.data.handler.IBuildComponent;
import me.clefal.whats_your_build.data.handler.IItemBuildComponent;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class VanillaArmorComponent implements IItemBuildComponent<VanillaArmorComponent> {

    public static final String ID = "vanilla_armor";
    public static final MapCodec<VanillaArmorComponent> CODEC = RecordCodecBuilder.mapCodec(i ->
            i.group(
                    Codec.unboundedMap(Codec.STRING.xmap(EquipmentSlot::byName, EquipmentSlot::getName), ItemStack.CODEC).fieldOf("armors").forGetter(x -> {
                        HashMap<EquipmentSlot, ItemStack> equipmentSlotItemStackHashMap = new HashMap<>(x.armors);
                        x.armors.entrySet().stream().filter(entry -> entry.getValue().isEmpty()).map(Map.Entry::getKey).forEach(equipmentSlotItemStackHashMap::remove);
                        return equipmentSlotItemStackHashMap;
                    })
            ).apply(i, VanillaArmorComponent::new)
    );
    public Map<EquipmentSlot, ItemStack> armors;

    public VanillaArmorComponent(Map<EquipmentSlot, ItemStack> armors) {
        this.armors = Stream.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND)
                .map(x -> Tuple.of(x, armors.getOrDefault(x, ItemStack.EMPTY)))
                .collect(HashMap::new, (equipmentSlotItemStackHashMap, equipmentSlotItemStackTuple2) -> equipmentSlotItemStackHashMap.put(equipmentSlotItemStackTuple2._1, equipmentSlotItemStackTuple2._2), HashMap::putAll);
    }

    private VanillaArmorComponent(List<ItemStack> itemStacks){
        if (itemStacks.size() != 6) throw new RuntimeException("illegally invoke this constructor!");
        List<EquipmentSlot> head = List.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND);

        this.armors = IntStream.rangeClosed(0, 5)
                .boxed()
                .map(x -> Tuple.of(head.get(x), itemStacks.get(x)))
                .collect(HashMap::new,
                        (equipmentSlotItemStackHashMap, equipmentSlotItemStackTuple2) -> equipmentSlotItemStackHashMap.put(equipmentSlotItemStackTuple2._1, equipmentSlotItemStackTuple2._2),
                        HashMap::putAll);
    }

    @Override
    public byte getHandlerIndex() {
        return ComponentType.VANILLA_ARMOR;
    }


    @Override
    public String getIdentifier() {
        return ID;
    }



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
        return new VanillaArmorComponent(objectObjectHashMap);
    }

    @Override
    public Container asContainer() {
        List<EquipmentSlot> list = List.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND);

        SimpleContainer simpleContainer = new SimpleContainer(list.size());
        for (int i = 0; i < simpleContainer.getContainerSize(); i++) {
            simpleContainer.setItem(i, this.armors.getOrDefault(list.get(i), ItemStack.EMPTY));
        }
        return simpleContainer;
    }

    @Override
    public VanillaArmorComponent getFromContainer(Container container) {
        ArrayList<ItemStack> itemStacks = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            itemStacks.add(container.getItem(i));
        }

        return new VanillaArmorComponent(itemStacks);
    }


}
