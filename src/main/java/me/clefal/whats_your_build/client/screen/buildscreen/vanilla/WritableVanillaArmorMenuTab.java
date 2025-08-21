package me.clefal.whats_your_build.client.screen.buildscreen.vanilla;

import com.clefal.nirvana_lib.client.render.batch.TextureBufferInfo;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import me.clefal.whats_your_build.client.screen.IBuildMenuContainerHolder;
import me.clefal.whats_your_build.client.screen.loadoutscreen.LoadoutScreen;
import me.clefal.whats_your_build.data.modules.armor.VanillaArmorComponent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;

public class WritableVanillaArmorMenuTab extends VanillaArmorMenuTab{
    public WritableVanillaArmorMenuTab(VanillaArmorComponent component, IBuildMenuContainerHolder<?> holder) {
        super(component, holder);
    }

    @Override
    public List<Slot> getNewSlots() {
        return () -> new WritableVanillaArmorMenu(component, holder.generateRenderContext());
    }

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

            LoadoutScreen.vertexContainer.putBliz(resourceLocation, TextureBufferInfo.of((int) (getX() + (getWidth() / 2.0f) - 4), getY(), getHeight(), getHeight(), 0, 0, 32, 32, 32, 32, pose.last().pose()));

        } else {
            LoadoutScreen.vertexContainer.putBliz(resourceLocation, TextureBufferInfo.of((int) (getX() + (getWidth() / 2.0f) - 5), getY() - 1, getHeight() + 1, getHeight() + 1, 0, 32, 32, 32, 32, 32, pose.last().pose()));
        }
        pose.popPose();
    }
}
