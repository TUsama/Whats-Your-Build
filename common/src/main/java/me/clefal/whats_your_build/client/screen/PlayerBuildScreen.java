package me.clefal.whats_your_build.client.screen;

import com.clefal.nirvana_lib.client.render.batch.TextureBufferInfo;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import com.google.common.escape.Escaper;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.client.screen.vanilla.VanillaArmorMenuTab;
import me.clefal.whats_your_build.config.WYBClientConfig;
import me.clefal.whats_your_build.modules.armor.VanillaArmorComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;


import javax.annotation.Nullable;
import java.util.function.Function;

public class PlayerBuildScreen extends Screen {

    public final static ResourceLocation COMPONENT = CommonClass.id("textures/gui/component.png");
    protected static int BACKGROUND_WIDTH = 128;
    protected static int BACKGROUND_HEIGHT = 128;
    private float topLeftX;
    private float topLeftY;

    private final List<BuildMenuTab<?, ?>> tabs;
    private float tabOriginalX;
    private float tabOriginalY;
    @Nullable
    private BuildMenu<?> currentMenu;
    public float scale;


    public PlayerBuildScreen(List<Function<PlayerBuildScreen, BuildMenuTab<?, ?>>> tabs) {
        super(Component.literal(""));
        this.tabs = tabs.map(x -> x.apply(this));

    }

    public float getTopLeftX() {
        return topLeftX;
    }

    public float getTopLeftY() {
        return topLeftY;
    }

    public float getTabOriginalX() {
        return tabOriginalX;
    }

    public float getTabOriginalY() {
        return tabOriginalY;
    }

    public void setNewMenu(BuildMenu<?> menu) {
        this.currentMenu = menu;
    }




    @Override
    protected void init() {
        super.init();

        scale = WYBClientConfig.config.global_scale;

        BACKGROUND_WIDTH = (int) (128 * scale);
        BACKGROUND_HEIGHT = (int) (128 * scale);

        topLeftX = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2.0f - BACKGROUND_WIDTH / 2.0f;
        topLeftY = Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2.0f - BACKGROUND_HEIGHT / 2.0f;
/*
        topLeftX *= 1 / scale;
        topLeftY *= 1 / scale;*/

        BuildMenuTab.TAB_WIDTH = (int) (14 * scale);
        BuildMenuTab.TAB_HEIGHT = (int) (8 * scale);

        this.tabOriginalX = topLeftX + BACKGROUND_WIDTH / 5.0f;
        this.tabOriginalY = topLeftY + BACKGROUND_HEIGHT / 5.0f;
        int i = (int) tabOriginalX;
        for (BuildMenuTab<?, ?> tab : List.of(new VanillaArmorMenuTab(new VanillaArmorComponent(java.util.List.of()), this))) {
            tab.setPosition(i, (int) tabOriginalY);
            addRenderableWidget(tab);
            i += tab.getWidth();
        }

        this.currentMenu = null;
        if (!tabs.isEmpty()) {
            if (!this.tabs.lastOption().isEmpty()) {
                this.currentMenu = this.tabs.lastOption().get().getMenu().apply(this);
                setInitialFocus(this.tabs.lastOption().get());
            }
        }


    }


    @Override
    public void setFocused(@Nullable GuiEventListener listener) {
        if (!(listener instanceof BuildMenuTab<?, ?> tab)) return;
        super.setFocused(tab);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        PoseStack pose = guiGraphics.pose();
        RenderSystem.enableDepthTest();

        pose.pushPose();
        //pose.scale(scale, scale, 1.0f);
        //already multiply the scale in init(), but here we use the scale again. so we need to multiply it with (1 / scale)
        //otherwise it will become BACKGROUND_WIDTH * 1.3 * 1.3

        super.render(guiGraphics, mouseX, mouseY, partialTick);
        {
            pose.pushPose();
            float portion = 8.0f;
            float interval = BACKGROUND_WIDTH * (1 / portion);
            pose.translate(topLeftX + interval, tabOriginalY + BuildMenuTab.TAB_HEIGHT, 0);
            guiGraphics.blit(COMPONENT ,0, 0, (int)(BACKGROUND_WIDTH * ((portion - 2) / portion)), 1, 128, 0, 128, 1, 256, 256);
            pose.popPose();
        }
        {
            pose.pushPose();

            {

                pose.translate(0, 0, -10);
                renderBackground(guiGraphics);
                pose.translate(topLeftX, topLeftY, 1);
                guiGraphics.blitNineSliced(COMPONENT, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT, (int) (6 * scale), 8, 128, 256, 0, 0);

            }

            {
                pose.pushPose();
                float scale = 0.8f * this.scale;
                float scaleReciprocal = 1.0f / scale;
                pose.scale(scale, scale, 1.0f);
                MutableComponent translatable = Component.translatable("wyb.screen.head.player_build");
                float mx = BACKGROUND_WIDTH / 2.0f - Minecraft.getInstance().font.width(translatable.getString()) * scale / 2.0f;
                float my = BACKGROUND_HEIGHT / (2.0f * 4.0f) - Minecraft.getInstance().font.lineHeight * scale / 2.0f;
                pose.translate(mx * scaleReciprocal, my * scaleReciprocal, 0);
                guiGraphics.drawString(Minecraft.getInstance().font, translatable, 0, 0, ChatFormatting.BLACK.getColor(), false);

                pose.popPose();
            }

            pose.popPose();
        }


        {
            pose.pushPose();
            pose.translate(tabOriginalX, tabOriginalY, 0);

            if (currentMenu != null) {

                pose.pushPose();
                //for the Minecraft.getInstance().font.lineHeight + 1 above
                pose.translate(0, 1, 0);
                currentMenu.render(guiGraphics, mouseX, mouseY, partialTick);
                pose.popPose();
            }
            pose.popPose();
        }

        pose.popPose();
    }
}
