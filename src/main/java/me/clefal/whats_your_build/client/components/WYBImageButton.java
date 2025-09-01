package me.clefal.whats_your_build.client.components;

import me.clefal.whats_your_build.CommonClass;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.resources.ResourceLocation;

public class WYBImageButton extends ImageButton {

    public WYBImageButton(int x, int y, int width, int height, OnPress onPress, ResourceLocation allInOne) {
        //? 1.20.1
        /*super(x, y, width, height, 0, 0, 32, allInOne, 32, 64, onPress);*/
        //? >1.20.1
        super(x, y, width, height, new WidgetSprites(CommonClass.id("textures/gui/" + allInOne + "-enabled.png"), CommonClass.id("textures/gui/" + allInOne + "-disabled.png"), CommonClass.id("textures/gui/sprite/" + allInOne + "-enabled-focused.png")), onPress);
    }
}
