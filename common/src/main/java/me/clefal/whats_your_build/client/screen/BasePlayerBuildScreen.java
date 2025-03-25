package me.clefal.whats_your_build.client.screen;

import com.clefal.nirvana_lib.client.render.batch.VertexContainer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import me.clefal.whats_your_build.CommonClass;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public abstract class BasePlayerBuildScreen extends Screen {
    public final static ResourceLocation COMPONENT = CommonClass.id("textures/gui/component.png");
    public final static VertexContainer container = new VertexContainer();
    protected static final int BACKGROUND_WIDTH = 128;
    protected static final int BACKGROUND_HEIGHT = 128;
    public float topLeftX = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2.0f - BACKGROUND_WIDTH / 2.0f;
    public float topLeftY = Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2.0f - BACKGROUND_HEIGHT / 2.0f;

    protected BasePlayerBuildScreen() {
        super(Component.literal(""));
        //super.init(Minecraft.getInstance(),WIDTH, HEIGHT);
    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        RenderSystem.enableDepthTest();
        pose.translate(0, 0, -10);
        renderBackground(guiGraphics);
        pose.translate(topLeftX, topLeftY, 1);
        guiGraphics.blitNineSliced(COMPONENT, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT, 6, 8, 128, 256, 0, 0);
        MutableComponent translatable = Component.translatable("wyb.screen.head.player_build");

        //pose.translate(mx, my, 1);
        pose.pushPose();

        float scale = 0.8f;
        float scaleReciprocal = 1.0f / scale;
        pose.scale(scale, scale, 1.0f);
        float mx = BACKGROUND_WIDTH / 2.0f - Minecraft.getInstance().font.width(translatable.getString()) * scale/ 2.0f;
        float my = BACKGROUND_HEIGHT / (2.0f * 4.0f) - Minecraft.getInstance().font.lineHeight * scale/ 2.0f;
        pose.translate(mx * scaleReciprocal, my * scaleReciprocal, 0);
        guiGraphics.drawString(Minecraft.getInstance().font, translatable, 0, 0, ChatFormatting.BLACK.getColor(), false);

        pose.popPose();

        pose.popPose();

    }
}
