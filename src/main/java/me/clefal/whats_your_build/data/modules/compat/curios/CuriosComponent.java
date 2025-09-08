//? if neoforge || forge {
package me.clefal.whats_your_build.data.modules.compat.curios;

import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.data.handler.ComponentType;
import me.clefal.whats_your_build.data.handler.IBuildComponent;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;

public record CuriosComponent(List<ItemStack> curios) implements IBuildComponent<CuriosComponent> {

    public static final String ID = "ring";
    public static final MapCodec<CuriosComponent> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(ItemStack.CODEC.listOf().fieldOf("curios").forGetter(x -> x.curios().asJava())).apply(i, x -> new CuriosComponent(List.ofAll(x))));

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

        return new CuriosComponent(curios.map(ItemStack::copy));
    }

    @Override
    public CuriosComponent makeCleanCopy() {
        return new CuriosComponent(Util.make(() -> {
            List<ItemStack> objects = List.empty();
            for (int i = 0; i < this.curios.size(); i++) {
                objects = objects.prepend(ItemStack.EMPTY);
            }
            return objects;

        }));
    }

    @Override
    public Container asContainer() {
        return new SimpleContainer(this.curios.toJavaArray(ItemStack[]::new));
    }

    @Override
    public CuriosComponent getFromContainer(Container container) {
        return new CuriosComponent(Util.make(() -> {
            List<ItemStack> objects = List.empty();
            for (int i = 0; i < this.curios.size(); i++) {
                objects = objects.prepend(container.getItem(i));
            }
            return objects;

        }));
    }


}
//?}