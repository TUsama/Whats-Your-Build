package me.clefal.whats_your_build.world;

import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import net.minecraft.world.inventory.Slot;

public interface IRewritableMenu {

    void rewriteSlots(List<Slot> slots);
}
