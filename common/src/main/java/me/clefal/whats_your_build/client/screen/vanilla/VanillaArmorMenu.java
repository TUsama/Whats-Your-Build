package me.clefal.whats_your_build.client.screen.vanilla;

import com.clefal.nirvana_lib.client.render.batch.TextureBufferInfo;
import com.clefal.nirvana_lib.client.render.batch.VertexContainer;
import com.mojang.blaze3d.vertex.PoseStack;
import me.clefal.whats_your_build.client.screen.BasePlayerBuildScreen;
import me.clefal.whats_your_build.client.screen.BuildMenu;
import me.clefal.whats_your_build.modules.armor.VanillaArmorComponent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;

public class VanillaArmorMenu extends BuildMenu<VanillaArmorComponent> {

    public VanillaArmorMenu(VanillaArmorComponent component) {
        super(component);
    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        int i = 0;
        int j = 0;
        InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, i + 51, j + 75, 30, (float) (i + 51) - mouseX, (float) (j + 75 - 50) - mouseY, super.minecraft.player);
        pose.translate(0, 100, 0);
        //VertexContainer container = BasePlayerBuildScreen.container;
        int startY = 0;
        int time = 0;
        int radius = 20;
        for (ItemStack armor : component.armors()) {
            pose.pushPose();
            pose.translate((radius + 8) * time, startY, 0);
            //container.putBliz(BasePlayerBuildScreen.COMPONENT, new TextureBufferInfo(0, radius, 0, radius, 0, 128, 128 + 17, 40, 40 + 17, pose.last().pose(), TextureBufferInfo.RenderInfo.ofOpacity(0.2f)));
            guiGraphics.renderItem(armor, 0, 0);
            time++;
            if (time == 4) {
                time = 0;
                startY = radius + 8;
            }
            pose.popPose();
        }

        pose.popPose();
    }
}
