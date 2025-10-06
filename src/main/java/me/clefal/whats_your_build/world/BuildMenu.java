package me.clefal.whats_your_build.world;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;

public abstract class BuildMenu extends AbstractContainerMenu{



    protected BuildMenu(@Nullable MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }


    @FunctionalInterface
    public interface ComponentHandler {
        void handle();

    }


}
