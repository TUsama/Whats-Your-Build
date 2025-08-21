package me.clefal.whats_your_build.client.screen.buildscreen;

import me.clefal.whats_your_build.client.screen.RenderContext;
import me.clefal.whats_your_build.data.handler.IBuildComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractBuildMenu<T extends IBuildComponent<?>> extends AbstractContainerMenu {
    protected final Minecraft minecraft = Minecraft.getInstance();

    protected final T component;
    protected final RenderContext context;

    public AbstractBuildMenu(@Nullable MenuType<?> menuType, int containerId, T component, RenderContext context) {
        super(menuType, containerId);
        this.component = component;
        this.context = context;
    }


}
