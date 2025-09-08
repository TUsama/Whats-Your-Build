package me.clefal.whats_your_build.mixin;

import me.clefal.whats_your_build.mixinhelper.IAbstractWidgetHelper;
import net.minecraft.client.gui.components.AbstractWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

//? =1.20.1 || fabric {
/*@Mixin(value = AbstractWidget.class)
*///?} else {
@Mixin(value = AbstractWidget.class, remap = false)
 //?}

public class AbstractWidgetMixin implements IAbstractWidgetHelper {
    @Shadow protected int width;

    @Shadow protected int height;

    @Shadow private int x;

    @Shadow private int y;

    @Override
    public void WYB$setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void WYB$setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
