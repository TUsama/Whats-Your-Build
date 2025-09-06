package me.clefal.whats_your_build.client.components;

import me.clefal.whats_your_build.CommonClass;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.resources.ResourceLocation;

public class WYBImageButton extends ImageButton {

    public WYBImageButton(int x, int y, int width, int height, OnPress onPress, String allInOneName) {
        //? 1.20.1
        /*super(x, y, width, height, 0, 0, 32, allInOne, 32, 64, onPress);*/
        //? >1.20.1
        super(x, y, width, height, new WidgetSprites(CommonClass.id("textures/gui/" + allInOneName + "-enabled.png"), CommonClass.id("textures/gui/" + allInOneName + "-disabled.png"), CommonClass.id("textures/gui/sprite/" + allInOneName + "-enabled-focused.png")), onPress);
    }

    public OnPress getOnPress(){
        return onPress;
    }
}
