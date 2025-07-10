//? neoforge {
package me.clefal.whats_your_build.client.screen.loadoutscreen;

import com.clefal.nirvana_lib.client.render.batch.DrawStringBufferInfo;
import com.mojang.blaze3d.vertex.PoseStack;
import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.data.buildobject.Build;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.List;

public class LoadoutSelectionList extends AbstractSelectionList<LoadoutSelectionList.BuildEntry> {

    static final int WIDTH = 244;
    static final int HEIGHT = 12;
    private int rowWidth = 200;
    public static final int buttonXInterval = 24;
    public static final int buttonYInterval = 8;
    private static final ResourceLocation TEXTURE = CommonClass.id("textures/gui/screen_background.png");

    public LoadoutSelectionList(int width, int height, int y0, int y1) {
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
    public void addBuildsOnInit(List<Build> builds){
        for (Build build : builds) {
            addEntry(new BuildEntry(build));
        }
        int targetAmount = 10;
        if (children().size() < targetAmount){
            int i = targetAmount - children().size();
            while (i > 0){
                addEntry(new BuildEntry(null));
                i--;
            }
        }
    }


    @Nullable
    public Build getCurrentBuild(){
        if (getFocused() != null) return getFocused().storageBuild;
        return null;
    }

    @Override
    public int getRowWidth() {
        return width;
    }

    @Override
    protected boolean scrollbarVisible() {
        return false;
    }

    public class BuildEntry extends AbstractSelectionList.Entry<BuildEntry>{
        @Nullable
        public Build storageBuild;
        @Nullable
        public Build editingBuild;


        public BuildEntry(@Nullable Build storageBuild) {
            this.storageBuild = storageBuild;
        }

        @Override
        public void render(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean hovering, float partialTick) {
            PoseStack pose = guiGraphics.pose();
            pose.pushPose();
            if (storageBuild != null){
                LoadoutScreen.vertexContainer.putString(DrawStringBufferInfo.of(index + " ", left + 24, top, ChatFormatting.BLACK.getColor(), pose.last().pose()));

                LoadoutScreen.vertexContainer.putString(DrawStringBufferInfo.of(storageBuild.getName(), left + 48, top, ChatFormatting.BLACK.getColor(), pose.last().pose()));
            } else {
                if (hovering){
                    guiGraphics.drawString(Minecraft.getInstance().font, "222", left, top, ChatFormatting.GOLD.getColor());
                } else {
                    guiGraphics.drawString(Minecraft.getInstance().font, "111", left, top, ChatFormatting.GOLD.getColor());
                }

            }

            pose.popPose();
        }


        @Override
        public void renderBack(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTick) {
            //guiGraphics.drawString(Minecraft.getInstance().font, "11111", left, top, ChatFormatting.WHITE.getColor());
            /*BuildManagementScreen.vertexContainer.putString(DrawStringBufferInfo.of("111", top, left, ChatFormatting.WHITE.getColor(), guiGraphics.pose().last().pose()));*/
        }
    }
}
//?}
