package me.clefal.whats_your_build.client.screen.component;

import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import com.mojang.blaze3d.vertex.PoseStack;
import me.clefal.whats_your_build.client.screen.buildscreen.BuildMenu;
import me.clefal.whats_your_build.client.screen.buildscreen.RenderContext;
import me.clefal.whats_your_build.client.screen.buildscreen.vanilla.ArmorHolder;
import me.clefal.whats_your_build.data.handler.IBuildComponent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;


public abstract class NormalArmorHolder<T extends IBuildComponent<?>> extends BuildMenu<T> {

    protected List<ArmorHolder> holders;
    protected int holderRadius = 16;

    public NormalArmorHolder(T component, RenderContext context) {
        super(component, context);
        holderRadius = (int) (holderRadius * context.scale());
        initHolders();
    }

    public abstract void initHolders();

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        {
            PoseStack pose = guiGraphics.pose();
            pose.pushPose();
            int i = this.context.tabOriginalX();
            int j = this.context.tabOriginalY();
            pose.pushPose();
            // x and y, two magic numbers
            //4, 1.5 and (32 * (1 + screen.scale / 4.0f)) are all magic numbers

            //? 1.20.1 {
            /*InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, (int) (getX() + getWidth() / 4f), (int) (getY() + getHeight() / 1.3f), (int) (32 + (35f * Math.pow(context.scale() - 1, 1.0d))), (float) (i + 51) - mouseX, (float) (j + 75 - 50) - mouseY, context.player());
             *///?} else {
            InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, i - 46, j + 28, i + 95, j + 98, 35, 0.0625F, mouseX, mouseY, context.player());

            //?}


            pose.popPose();

            pose.pushPose();
            int holderOffsetX = (int) (getX() + getWidth() / 1.8f);
            int holderOffsetY = (int) (getY() + getHeight() / 12f);
            int time = 0;
            for (var holder : holders) {
                pose.pushPose();
                holder.setPosition(holderOffsetX, holderOffsetY + ((int) ((holderRadius + holderRadius * (1 / 5.0f)) * time)));
                holder.render(guiGraphics, mouseX, mouseY, partialTick);
                time++;
                if (time == 4) {
                    time = 0;
                    float xChange = holderRadius * (1 / 5.0f);
                    holderOffsetX = holderOffsetX + (int) (holderRadius + xChange);
                    pose.translate(xChange, 0, 0);
                }
                pose.popPose();
            }
            pose.popPose();

            pose.popPose();
        }
    }
}
