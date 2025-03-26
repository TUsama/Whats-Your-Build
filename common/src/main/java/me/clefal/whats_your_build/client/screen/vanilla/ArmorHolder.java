package me.clefal.whats_your_build.client.screen.vanilla;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import me.clefal.whats_your_build.client.screen.PlayerBuildScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class ArmorHolder extends AbstractWidget {
    @Nullable
    private final ItemStack itemStack;

    public ArmorHolder(int radius, @Nullable ItemStack itemStack) {
        super(0, 0, radius, radius, Component.literal(""));
        this.itemStack = itemStack;
    }


    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        guiGraphics.setColor(1.0f,1.0f,1.0f,0.5f);
        RenderSystem.enableBlend();
        guiGraphics.blit(PlayerBuildScreen.COMPONENT, 0, 0, width, height, 128, 41, 17, 17, 256, 256);
        guiGraphics.setColor(1.0f,1.0f,1.0f,1.0f);

        if (itemStack != null) {
            pose.pushPose();

            guiGraphics.renderItem(itemStack, 0, 0);
            pose.popPose();
            if (isHovered){
                guiGraphics.renderTooltip(Minecraft.getInstance().font, itemStack, mouseX, mouseY);
            }
        }
        pose.popPose();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

}
