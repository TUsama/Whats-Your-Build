package me.clefal.whats_your_build.world.loadout.load;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class ItemLoadDescriber extends LoadDescriber<List<ItemStack>>{

    public ItemLoadDescriber(List<ItemStack> target) {
        super(target);
    }

    protected static @NotNull List<ItemStack> getArmory(Container armory) {
        List<ItemStack> itemStacks = new ArrayList<>();
        for (int i = 0; i < armory.getContainerSize(); i++) {
            ItemStack item = armory.getItem(i);
            itemStacks.add(item);
        }
        return itemStacks;
    }
}
