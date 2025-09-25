package me.clefal.whats_your_build.world.loadout.load;

import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Consumer;

public abstract class ItemLoadDescriber extends LoadDescriber<List<ItemStack>>{

    public ItemLoadDescriber(List<ItemStack> target) {
        super(target);
    }
}
