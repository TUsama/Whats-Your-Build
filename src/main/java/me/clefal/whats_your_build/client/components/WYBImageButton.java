package me.clefal.whats_your_build.client.components;

import com.clefal.nirvana_lib.client.render.batch.TextureBufferInfo;
import com.clefal.nirvana_lib.client.render.batch.VertexContainer;
import me.clefal.whats_your_build.CommonClass;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
//? >1.20.1
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.resources.ResourceLocation;

public class WYBImageButton extends ImageButton {

    protected VertexContainer container;

    public WYBImageButton(int x, int y, int width, int height, OnPress onPress,
                          //? >1.20.1
                          WidgetSprites sprites,
                          //? 1.20.1
                          //String allInOne,
                          VertexContainer container) {
        //? 1.20.1
        //super(x, y, width, height, 0, 0, 32, CommonClass.gui(allInOne), 32, 64, onPress);
        //? >1.20.1
        super(x, y, width, height, sprites, onPress);
        this.container = container;
    }

    public ResourceLocation getRenderResourceLocation(){
        //? >1.20.1
        return this.sprites.get(this.isActive(), this.isHoveredOrFocused());
        //? 1.20.1
        //return this.resourceLocation;
    }


}
