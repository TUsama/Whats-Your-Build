package me.clefal.whats_your_build.client.screen;

import com.clefal.nirvana_lib.client.render.batch.TextureBufferInfo;
import com.clefal.nirvana_lib.client.render.batch.VertexContainer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import me.clefal.whats_your_build.CommonClass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class BasePlayerBuildScreen extends Screen {
    public final static ResourceLocation COMPONENT = CommonClass.id("textures/gui/component.png");
    public final static VertexContainer container = new VertexContainer();
    protected static final int WIDTH = 128;
    protected static final int HEIGHT = 256;
    public float topLeftX = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2.0f - WIDTH / 2.0f;
    public float topLeftY = Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2.0f - HEIGHT / 2.0f;

    protected BasePlayerBuildScreen() {
        super(Component.literal(""));
    }


    @Override
    protected void init() {
        super.init();
        this.width = WIDTH;
        this.height = HEIGHT;


    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        RenderSystem.enableDepthTest();
        pose.translate(0, 0, -10);
        renderBackground(guiGraphics);
        pose.translate(topLeftX, topLeftY, -10);
        container.putBliz(COMPONENT, new TextureBufferInfo(0, 64, 0, 128, 0, 0, 128, 0, 256, guiGraphics.pose().last().pose(), TextureBufferInfo.RenderInfo.ofOpacity(1.0f)));
        pose.popPose();

    }
}
