package me.clefal.whats_your_build.client.screen.buildscreen;

import com.clefal.nirvana_lib.client.render.batch.TextureBufferInfo;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.client.screen.IBuildMenuContainerHolder;
import me.clefal.whats_your_build.data.handler.IBuildComponent;
import me.clefal.whats_your_build.world.IRewritableMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
//? >1.20.1
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;


public abstract class BuildMenuTab<E extends IBuildComponent<?>> extends ImageButton {
    public static int TAB_WIDTH = 14;
    public static int TAB_HEIGHT = 8;
    protected E component;
    protected IBuildMenuContainerHolder<?> holder;
    private IRewritableMenu menu;


    public BuildMenuTab(Component message, E component, IBuildMenuContainerHolder<?> holder, IRewritableMenu menu) {
        //? 1.20.1
        /*super(0, 0, TAB_WIDTH, TAB_HEIGHT, 0, 0, 32, component.getRenderIcon(), 32, 64, button -> {}, message);*/
        //? >1.20.1
        super(0, 0, TAB_WIDTH, TAB_HEIGHT, new WidgetSprites(CommonClass.id("textures/gui/sprite/" + component.getIdentifier() + "/non-highlight.png"), CommonClass.id("textures/gui/sprite/" + component.getIdentifier() + "highlight"), CommonClass.id("textures/gui/sprite/" + component.getIdentifier() + "/highlight.png")), button -> {}, message);
        this.component = component;
        this.setTooltip(Tooltip.create(message));
        this.holder = holder;
        this.menu = menu;
    }

    //? 1.20.1 {
    /*@Override
    public void renderTexture(GuiGraphics guiGraphics, ResourceLocation texture, int x, int y, int uOffset, int vOffset, int textureDifference, int width, int height, int textureWidth, int textureHeight) {
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        pose.translate(0, 0, 10);
        if (!this.isHoveredOrFocused()) {
            guiGraphics.setColor(1, 1, 1, 0.7f);
            PlayerBuildScreen.vertexContainer.putBliz(resourceLocation, TextureBufferInfo.of((getX() + (getWidth() / 2.0f) - 4), getY(), getHeight(), getHeight(), 0, 0, 32, 32, 32, 64, pose.last().pose()));
            //guiGraphics.blit(resourceLocation, (int) (getX() + (getWidth() / 2.0f) - 4), getY(), getHeight(), getHeight(), 0, 0, 32, 32, 32, 64);
            guiGraphics.setColor(1, 1, 1, 1);
        } else {
            PlayerBuildScreen.vertexContainer.putBliz(resourceLocation, TextureBufferInfo.of((int) (getX() + (getWidth() / 2.0f) - 5), getY() - 1, getHeight() + 1, getHeight() + 1, 0, 32, 32, 32, 32, 64, pose.last().pose()));
            //guiGraphics.blit(resourceLocation, (int) (getX() + (getWidth() / 2.0f) - 5), getY() - 1, getHeight() + 1, getHeight() + 1, 0, 32, 32, 32, 32, 64);
        }
        pose.popPose();
    }
    *///?} else {

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        pose.translate(0, 0, 10);
        boolean hoveredOrFocused = this.isHoveredOrFocused();
        ResourceLocation resourceLocation = this.sprites.get(this.isActive(), hoveredOrFocused);
        if (hoveredOrFocused) {

            PlayerBuildScreen.vertexContainer.putBliz(resourceLocation, TextureBufferInfo.of((int) (getX() + (getWidth() / 2.0f) - 4), getY(), getHeight(), getHeight(), 0, 0, 32, 32, 32, 32, pose.last().pose()));

        } else {
            PlayerBuildScreen.vertexContainer.putBliz(resourceLocation, TextureBufferInfo.of((int) (getX() + (getWidth() / 2.0f) - 5), getY() - 1, getHeight() + 1, getHeight() + 1, 0, 32, 32, 32, 32, 32, pose.last().pose()));
        }
        pose.popPose();
    }


    //?}


    public abstract List<Slot> getNewSlots();

    @Override
    public final void onPress() {
        menu.rewriteSlots(getNewSlots());
    }


}
