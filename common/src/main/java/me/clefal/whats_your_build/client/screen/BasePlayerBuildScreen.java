package me.clefal.whats_your_build.client.screen;

import com.clefal.nirvana_lib.client.render.batch.TextureBufferInfo;
import com.clefal.nirvana_lib.client.render.batch.VertexContainer;
import com.clefal.nirvana_lib.client.render.rendertype.RenderTypeCreator;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;
import me.clefal.whats_your_build.CommonClass;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

import java.util.function.Function;

public class BasePlayerBuildScreen extends Screen {
    protected static final int WIDTH = 128;
    protected static final int HEIGHT = 256;
    public final static ResourceLocation COMPONENT = CommonClass.id("textures/gui/component.png");
    public final static VertexContainer container = new VertexContainer();
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
