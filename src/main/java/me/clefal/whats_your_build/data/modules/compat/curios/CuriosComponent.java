//? curios {
package me.clefal.whats_your_build.data.modules.compat.curios;

import com.clefal.nirvana_lib.relocated.io.vavr.Tuple;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.*;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.clefal.whats_your_build.data.handler.ComponentType;
import me.clefal.whats_your_build.data.handler.IBuildComponent;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

public class CuriosComponent implements IBuildComponent<CuriosComponent> {

    public Integer max;
    public Map<Integer, ItemStack> curios;
    public static final String ID = "curios";
    public static final MapCodec<CuriosComponent> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codec.INT.fieldOf("max").forGetter(x -> x.max),
            Codec.unboundedMap(Codec.STRING.xmap(Integer::valueOf, String::valueOf), ItemStack.CODEC).xmap(x -> (Map<Integer, ItemStack>) TreeMap.ofAll(x), x -> x.reject(tuple2 -> tuple2._2.isEmpty()).toJavaMap()).fieldOf("map").forGetter(x -> x.curios.reject(tuple2 -> tuple2._2.isEmpty()))
            ).apply(i, CuriosComponent::new));

    private CuriosComponent(Integer max, Map<Integer, ItemStack> curios) {
        this.max = max;
        for (int i = 0; i < max; i++) {
            if (curios.containsKey(i)) continue;
            curios = curios.put(i, ItemStack.EMPTY);
        }
        if (curios.isOrdered()){
            this.curios = curios;
        } else {
            this.curios = TreeMap.ofEntries(curios);
        }

    }

    public CuriosComponent(ItemStack... stacks) {
        this(stacks.length, TreeMap.ofEntries(Stream.of(stacks).zipWithIndex((itemStack, integer) -> Tuple.of(integer, itemStack))));
    }

    @Override
    public byte getHandlerIndex() {
        return ComponentType.CURIOS;
    }


    @Override
    public String getIdentifier() {
        return ID;
    }


    @Override
    public MapCodec<CuriosComponent> getCodec() {
        return CODEC;
    }

    @Override
    public CuriosComponent copy() {

        return new CuriosComponent(max, curios.mapValues(ItemStack::copy));
    }

    @Override
    public CuriosComponent makeCleanCopy() {
        return new CuriosComponent(max, curios.mapValues(x -> ItemStack.EMPTY));
    }

    @Override
    public Container asContainer() {
        return new SimpleContainer(this.curios.values().toJavaArray(ItemStack[]::new));
    }

    @Override
    public CuriosComponent getFromContainer(Container container) {
        List<ItemStack> objects = List.empty();
        for (int i = 0; i < this.curios.size(); i++) {
            objects = objects.prepend(container.getItem(i));
        }
        return new CuriosComponent(objects.toJavaArray(ItemStack[]::new));
    }


}
//?}