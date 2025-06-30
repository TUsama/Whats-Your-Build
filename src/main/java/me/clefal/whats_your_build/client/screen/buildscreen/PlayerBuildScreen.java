package me.clefal.whats_your_build.client.screen.buildscreen;

import com.clefal.nirvana_lib.client.render.batch.DrawStringBufferInfo;
import com.clefal.nirvana_lib.client.render.batch.TextureBufferInfo;
import com.clefal.nirvana_lib.client.render.batch.VertexContainer;
import com.clefal.nirvana_lib.client.render.rendertype.RenderTypeCreator;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import lombok.Getter;
import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.client.screen.IBuildMenuContainerHolder;
import me.clefal.whats_your_build.client.screen.loadoutscreen.BuildPresentContainer;
import me.clefal.whats_your_build.config.WYBClientConfig;
import me.clefal.whats_your_build.data.buildobject.Build;
import me.clefal.whats_your_build.utils.IBufferSourceProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

public class PlayerBuildScreen extends Screen implements IBuildMenuContainerHolder<BuildPresentContainer> {

    public final static ResourceLocation COMPONENT = CommonClass.id("textures/gui/component.png");
    public static VertexContainer vertexContainer = new VertexContainer();
    protected static int BACKGROUND_WIDTH = 128;
    protected static int BACKGROUND_HEIGHT = 128;
    private final BuildPresentContainer buildViewContainer;
    public float scale;
    @Getter
    private float topLeftX;
    @Getter
    private float topLeftY;
    @Getter
    private float tabOriginalX;
    @Getter
    private float tabOriginalY;
    @Nullable
    private BuildMenu<?> currentMenu;


    public PlayerBuildScreen(Build build, Player target) {
        super(Component.literal(""));
        this.buildViewContainer = new BuildPresentContainer(target, build);
    }

    @Override
    public void tick() {
        super.tick();
        if (!Minecraft.getInstance().player.level().players().contains(buildViewContainer.targetPlayer)) {
            Minecraft.getInstance().setScreen(null);
        }
    }


    @Override
    protected void init() {
        super.init();
        vertexContainer = new VertexContainer();
        scale = WYBClientConfig.config.globalScale;

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
        List<BuildMenuTab<?, ?>> tabs = buildViewContainer.tabs;
        for (BuildMenuTab<?, ?> tab : tabs) {
            tab.setPosition(i, (int) tabOriginalY);
            addRenderableWidget(tab);
            i += tab.getWidth();
        }

        this.currentMenu = null;
        if (!tabs.isEmpty()) {
            if (!tabs.headOption().isEmpty()) {
                this.currentMenu = tabs.headOption().get().getMenu().get();
                setInitialFocus(tabs.headOption().get());
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
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        float portion = 8.0f;
        float interval = BACKGROUND_WIDTH * (1 / portion);
        float lineStartX = topLeftX + interval;
        float lineStartY = tabOriginalY + BuildMenuTab.TAB_HEIGHT;
        {
            //tab line
            pose.pushPose();
            pose.translate(lineStartX, lineStartY, 10);
            vertexContainer.putBliz(COMPONENT, TextureBufferInfo.of(0, 0, (int) (BACKGROUND_WIDTH * ((portion - 2) / portion)), 1, 128, 0, 128, 1, 256, 256, pose.last().pose()));
            //guiGraphics.blit(COMPONENT ,0, 0, (int)(BACKGROUND_WIDTH * ((portion - 2) / portion)), 1, 128, 0, 128, 1, 256, 256);
            pose.popPose();
        }
        {
            pose.pushPose();

            {
                //background
                pose.translate(0, 0, -1);
                pose.translate(topLeftX, topLeftY, 1);
                vertexContainer.putBlitNineSliced(COMPONENT, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT, (int) (6 * scale), 8, 128, 256, 0, 0, pose.last().pose());
                //guiGraphics.blitNineSliced(COMPONENT, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT, (int) (6 * scale), 8, 128, 256, 0, 0);
            }

            {
                //title
                pose.pushPose();
                float scale = 0.8f * this.scale;
                float scaleReciprocal = 1.0f / scale;
                pose.scale(scale, scale, 1.0f);
                MutableComponent translatable = Component.translatable("wyb.screen.head.player_build");
                float mx = BACKGROUND_WIDTH / 2.0f - Minecraft.getInstance().font.width(translatable.getString()) * scale / 2.0f;
                float my = BACKGROUND_HEIGHT / (2.0f * 4.0f) - Minecraft.getInstance().font.lineHeight * scale / 2.0f;
                pose.translate(mx * scaleReciprocal, my * scaleReciprocal, 0);
                //guiGraphics.drawString(Minecraft.getInstance().font, translatable, 0, 0, ChatFormatting.BLACK.getColor(), false);
                vertexContainer.putString(DrawStringBufferInfo.of(translatable.getString(), 0, 0, ChatFormatting.BLACK.getColor(), pose.last().pose()));


                pose.popPose();
            }

            pose.popPose();
        }


        {
            //menu
            pose.pushPose();

            if (currentMenu != null) {

                pose.pushPose();
                float menuOffsetY = 5 * scale;
                currentMenu.setSize((int) (BACKGROUND_WIDTH - 2 * interval), (int) (topLeftY + BACKGROUND_HEIGHT - lineStartY));
                currentMenu.setPosition((int) lineStartX, (int) (lineStartY + menuOffsetY));
                currentMenu.render(guiGraphics, mouseX, mouseY, partialTick);
                pose.popPose();
            }
            pose.popPose();
        }

        pose.popPose();
        vertexContainer.draw(((IBufferSourceProvider) guiGraphics).whats_Your_Build$getBufferSource(), RenderTypeCreator.gui);
    }


    public RenderContext generateRenderContext() {
        return new RenderContext(((int) this.getTabOriginalX()), ((int) this.getTabOriginalY()), this.scale, buildViewContainer.targetPlayer);
    }

    @Override
    public BuildPresentContainer getContainer() {
        return buildViewContainer;
    }
}
