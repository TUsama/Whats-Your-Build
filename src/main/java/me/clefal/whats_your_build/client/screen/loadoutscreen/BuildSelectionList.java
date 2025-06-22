package me.clefal.whats_your_build.client.screen.loadoutscreen;

import com.clefal.nirvana_lib.client.render.batch.VertexContainer;
import com.mojang.blaze3d.systems.RenderSystem;
import me.clefal.whats_your_build.CommonClass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.resources.ResourceLocation;

public class BuildSelectionList extends AbstractSelectionList<BuildSelectionList.BuildEntry> {

    static final int WIDTH = 244;
    static final int HEIGHT = 24;
    private int rowWidth = 200;
    public static final int buttonXInterval = 24;
    public static final int buttonYInterval = 8;
    private static final ResourceLocation TEXTURE = CommonClass.id("textures/gui/screen_background.png");
    public static VertexContainer vertexContainer = new VertexContainer();

    public BuildSelectionList(int width, int height, int y0, int y1) {
        //? 1.20.1
        /*super(Minecraft.getInstance(), width, height, y0, y1, HEIGHT);*/
        //? >1.20.1
        super(Minecraft.getInstance(), width, height, y1 - y0, HEIGHT);
        //setLeftPos(-10);

        //? !=1.21.4
        this.setRenderHeader(false, 0);
        //? 1.20.1 {
        /*this.setRenderBackground(false);
        this.setRenderTopAndBottom(false);
        this.setRenderSelection(false);
        *///?}

    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }




    //not WIDTH!!
    @Override
    public int getRowWidth() {
        return rowWidth;
    }



    public abstract class BuildEntry extends AbstractSelectionList.Entry<BuildEntry>{

        @Override
        public void renderBack(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTick) {
            RenderSystem.enableDepthTest();
            vertexContainer.putBlitNineSliced(TEXTURE, left, top, rowWidth, HEIGHT, 5, 5, 5, 5, WIDTH, HEIGHT, 0, 166, guiGraphics.pose().last().pose());
        }
    }
}
