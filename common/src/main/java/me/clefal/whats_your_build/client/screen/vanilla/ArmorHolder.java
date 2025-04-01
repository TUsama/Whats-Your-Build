package me.clefal.whats_your_build.client.screen.vanilla;

import com.clefal.nirvana_lib.utils.DevUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import me.clefal.whats_your_build.client.screen.PlayerBuildScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

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
        RenderSystem.enableBlend();
        guiGraphics.setColor(1.0f, 1.0f, 1.0f, 0.5f);
        guiGraphics.blit(PlayerBuildScreen.COMPONENT, getX(), getY(), width, height, 128, 41, 17, 17, 256, 256);
        guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);

        if (itemStack != null) {
            pose.pushPose();
            //original radius 16
            //lossy
            float scale = getWidth() / 16.0f;
            pose.translate(getX(), getY(), 0);
            pose.scale(scale, scale, 1);
            guiGraphics.renderItem(itemStack, 0, 0);
            pose.popPose();
            if (isHovered && !itemStack.getItem().equals(Items.AIR)) {
                guiGraphics.renderTooltip(Minecraft.getInstance().font, itemStack, mouseX, mouseY);
            }
        } else {
            DevUtils.runWhenOnDev(() -> {
                pose.pushPose();
                float scale = getWidth() / 16.0f;
                pose.translate(getX(), getY(), 0);
                pose.scale(scale, scale, 1);
                ItemStack defaultInstance = BuiltInRegistries.ITEM.get(new ResourceLocation("minecraft:stick")).getDefaultInstance();
                guiGraphics.renderItem(defaultInstance, 0, 0);
                pose.popPose();
                if (isHovered) {
                    guiGraphics.renderTooltip(Minecraft.getInstance().font, defaultInstance, mouseX, mouseY);
                }
            });
        }
        pose.popPose();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

}
