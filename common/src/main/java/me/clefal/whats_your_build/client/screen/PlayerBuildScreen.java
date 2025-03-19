package me.clefal.whats_your_build.client.screen;

import com.clefal.nirvana_lib.client.render.batch.TextureBufferInfo;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.renderer.MultiBufferSource;
import org.jetbrains.annotations.Nullable;

public class PlayerBuildScreen extends BasePlayerBuildScreen {

    private final List<BuildMenuTab<?, ?>> tabs;
    @Nullable
    private BuildMenu<?> currentMenu;
    private final float tabOriginalX;
    private final float tabOriginalY;


    public PlayerBuildScreen(List<BuildMenuTab<?, ?>> tabs) {
        this.tabs = tabs;
        this.currentMenu = tabs.lastOption().isEmpty() ? null : tabs.lastOption().get().menu.get();
        this.tabOriginalX = topLeftX + BasePlayerBuildScreen.WIDTH / 4.0f;
        this.tabOriginalY = topLeftY + BasePlayerBuildScreen.HEIGHT / 6.0f;
    }

    public void setNewMenu(BuildMenu<?> menu){
        this.currentMenu = menu;
    }



    @Override
    protected void init() {
        super.init();
        int i = (int) tabOriginalX;
        for (BuildMenuTab<?, ?> tab : tabs) {
            tab.setPosition(i, (int) tabOriginalY);
            addRenderableOnly(tab);
            i += tab.getWidth();
        }
        if (!tabs.isEmpty()){
            setInitialFocus(tabs.get(0));
        }

    }


    @Override
    public void setFocused(@Nullable GuiEventListener listener) {
        if (!(listener instanceof BuildMenuTab<?, ?> tab)) return;
        super.setFocused(tab);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        pose.translate(tabOriginalX, tabOriginalY, 0);
        BasePlayerBuildScreen.container.putBliz(BasePlayerBuildScreen.COMPONENT, new TextureBufferInfo(0, width, Minecraft.getInstance().font.lineHeight, Minecraft.getInstance().font.lineHeight + 1, 0, 128, 256, 0, 1, pose.last().pose(), TextureBufferInfo.RenderInfo.ofOpacity(1.0f)));

        if (currentMenu != null){

            pose.pushPose();
            //for the Minecraft.getInstance().font.lineHeight + 1 above
            pose.translate(0, 1, 0);
            currentMenu.render(guiGraphics, mouseX, mouseY, partialTick);
            pose.popPose();
        }
        pose.popPose();
        BasePlayerBuildScreen.container.draw(guiGraphics.bufferSource(), BuildRenderType.gui);
    }
}
