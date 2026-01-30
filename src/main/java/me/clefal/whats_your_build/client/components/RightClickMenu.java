package me.clefal.whats_your_build.client.components;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
//? > 1.20.1
import net.minecraft.client.gui.components.AbstractContainerWidget;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class RightClickMenu extends
        //? >1.20.1
        AbstractContainerWidget
        //? 1.20.1
        //AbstractWidget implements GuiEventListener 
{
    public List<WYBImageButton> buttons;

    public RightClickMenu(int x, int y, int width, int height, Component message, WYBImageButton... buttons) {
        super(x, y, width, height, message);
        this.buttons = new ArrayList<>(List.of(buttons));
    }


    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        //400.0F from renderTooltips() in GuiGraphics
        pose.translate(0, 0, 400.0F);
        for (int i = 0; i < buttons.size(); i++) {
            WYBImageButton wybImageButton = buttons.get(i);
            wybImageButton.setPosition(getX(), getY() + i * wybImageButton.getHeight());
            wybImageButton.render(guiGraphics, mouseX, mouseY, partialTick);
        }
        pose.popPose();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }


    //? >1.20.1 {
    @Override
    public List<? extends GuiEventListener> children() {
        return buttons;
    }
    //?} else {
    /*@Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean flag = false;
        for (WYBImageButton wybImageButton : this.buttons) {
            flag = flag || wybImageButton.mouseClicked(mouseX, mouseY, button);
        }
        return flag;
    }
    *///?}
}
