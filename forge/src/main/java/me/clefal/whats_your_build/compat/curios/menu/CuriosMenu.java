package me.clefal.whats_your_build.compat.curios.menu;

import com.mojang.blaze3d.vertex.PoseStack;
import me.clefal.whats_your_build.client.screen.BuildMenu;
import me.clefal.whats_your_build.client.screen.PlayerBuildScreen;
import me.clefal.whats_your_build.client.screen.vanilla.ArmorHolder;
import me.clefal.whats_your_build.compat.curios.CuriosComponent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;

import java.util.List;

public class CuriosMenu extends BuildMenu<CuriosComponent> {

    private final List<ArmorHolder> holders;
    private int holderRadius = 16;

    public CuriosMenu(CuriosComponent component, PlayerBuildScreen screen) {
        super(component, screen);

        holderRadius = (int) (holderRadius * screen.scale);
        //todo should render all slot but here need to be refactored
        holders = component.curios()
                .map(x -> new ArmorHolder(holderRadius, x))
                .asJava();
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        {
            PoseStack pose = guiGraphics.pose();
            pose.pushPose();
            int i = (int) screen.getTabOriginalX();
            int j = (int) screen.getTabOriginalY();
            pose.pushPose();
            // x and y, two magic numbers
            //4, 1.5 and (32 * (1 + screen.scale / 4.0f)) are all magic numbers
            if (screen.targetPlayer != null) {
                InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, (int) (getX() + getWidth() / 4f), (int) (getY() + getHeight() / 1.3f), (int) (32 + (35f * Math.pow(screen.scale - 1, 1.0d))), (float) (i + 51) - mouseX, (float) (j + 75 - 50) - mouseY, screen.targetPlayer);
            }

            pose.popPose();

            pose.pushPose();
            int holderOffsetX = (int) (getX() + getWidth() / 1.8f);
            int holderOffsetY = (int) (getY() + getHeight() / 12f);
            int time = 0;
            for (var holder : holders) {
                pose.pushPose();
                holder.setPosition(holderOffsetX, holderOffsetY + ((int) ((holderRadius + holderRadius * (1 / 5.0f)) * time)));
                holder.render(guiGraphics, mouseX, mouseY, partialTick);
                time++;
                if (time == 4) {
                    time = 0;
                    float xChange = holderRadius * (1 / 5.0f);
                    holderOffsetX = holderOffsetX + (int) (holderRadius + xChange);
                    pose.translate(xChange, 0, 0);
                }
                pose.popPose();
            }
            pose.popPose();

            pose.popPose();
        }
    }
}
