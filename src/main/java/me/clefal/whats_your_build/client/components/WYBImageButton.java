package me.clefal.whats_your_build.client.components;

import com.clefal.nirvana_lib.client.render.batch.TextureBufferInfo;
import com.clefal.nirvana_lib.client.render.batch.VertexContainer;
import me.clefal.whats_your_build.CommonClass;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.resources.ResourceLocation;

public class WYBImageButton extends ImageButton {

    protected VertexContainer container;
    public WYBImageButton(int x, int y, int width, int height, OnPress onPress, WidgetSprites sprites, VertexContainer container) {
        //? 1.20.1
        /*super(x, y, width, height, 0, 0, 32, allInOne, 32, 64, onPress);*/
        //? >1.20.1
        super(x, y, width, height, sprites, onPress);
        this.container = container;
    }


    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        ResourceLocation resourcelocation = this.sprites.get(this.isActive(), this.isHoveredOrFocused());
        container.putBliz(resourcelocation, TextureBufferInfo.of(getX(), getY(), 0, 0, getWidth(), getHeight(), guiGraphics.pose().last().pose()));
    }
}
