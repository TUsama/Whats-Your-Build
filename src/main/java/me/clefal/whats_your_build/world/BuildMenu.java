package me.clefal.whats_your_build.world;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public abstract class BuildMenu extends AbstractContainerMenu implements IBuildHandler {



    protected BuildMenu(@Nullable MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }


    @FunctionalInterface
    public interface SlotPlacer {
        void place();

    }

}
