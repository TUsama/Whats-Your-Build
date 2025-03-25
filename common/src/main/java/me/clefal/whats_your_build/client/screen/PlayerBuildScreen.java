package me.clefal.whats_your_build.client.screen;

import com.clefal.nirvana_lib.client.render.batch.TextureBufferInfo;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import com.mojang.blaze3d.vertex.PoseStack;
import me.clefal.whats_your_build.client.screen.vanilla.VanillaArmorMenuTab;
import me.clefal.whats_your_build.modules.armor.VanillaArmorComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;


import javax.annotation.Nullable;
import java.util.function.Function;

public class PlayerBuildScreen extends BasePlayerBuildScreen {

    private final List<BuildMenuTab<?, ?>> tabs;
    private final float tabOriginalX;
    private final float tabOriginalY;
    @Nullable
    private BuildMenu<?> currentMenu;


    public PlayerBuildScreen(List<Function<PlayerBuildScreen, BuildMenuTab<?, ?>>> tabs) {
        this.tabs = tabs.map(x -> x.apply(this));
        this.currentMenu = this.tabs.lastOption().isEmpty() ? null : this.tabs.lastOption().get().getMenu().get();
        this.tabOriginalX = topLeftX + BasePlayerBuildScreen.BACKGROUND_WIDTH / 5.0f;
        this.tabOriginalY = topLeftY + BasePlayerBuildScreen.BACKGROUND_HEIGHT / 5.0f;
    }

    public void setNewMenu(BuildMenu<?> menu) {
        this.currentMenu = menu;
    }


    @Override
    protected void init() {
        super.init();
        int i = (int) tabOriginalX;
        for (BuildMenuTab<?, ?> tab : List.of(new VanillaArmorMenuTab(new VanillaArmorComponent(java.util.List.of()), this))) {
            tab.setPosition(i, (int) tabOriginalY);
            addRenderableWidget(tab);
            i += tab.getWidth();
        }
        if (!tabs.isEmpty()) {
            setInitialFocus(tabs.get(0));
        }

    }

    @Override
    public void resize(Minecraft minecraft, int width, int height) {
        super.resize(minecraft, width, height);
        if (!tabs.isEmpty()) {
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
        //BasePlayerBuildScreen.container.putBliz(BasePlayerBuildScreen.COMPONENT, new TextureBufferInfo(0, BACKGROUND_WIDTH, 0, Minecraft.getInstance().font.lineHeight + 1, 0, 128, 256, 0, 1, pose.last().pose(), TextureBufferInfo.RenderInfo.ofOpacity(1.0f)));

        if (currentMenu != null) {

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
