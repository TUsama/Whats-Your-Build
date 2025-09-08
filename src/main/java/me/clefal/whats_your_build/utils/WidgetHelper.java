package me.clefal.whats_your_build.utils;

import lombok.experimental.UtilityClass;
import me.clefal.whats_your_build.mixinhelper.IAbstractWidgetHelper;
import net.minecraft.client.gui.components.AbstractWidget;

@UtilityClass
public class WidgetHelper {
    public void setSize(AbstractWidget widget, int width, int height){
        ((IAbstractWidgetHelper) widget).WYB$setSize(width, height);
    }

    public void setPosition(AbstractWidget widget, int x, int y){
        ((IAbstractWidgetHelper) widget).WYB$setPosition(x, y);
    }
}
