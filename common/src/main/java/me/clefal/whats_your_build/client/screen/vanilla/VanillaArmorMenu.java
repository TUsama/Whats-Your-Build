package me.clefal.whats_your_build.client.screen.vanilla;

import com.clefal.nirvana_lib.relocated.io.vavr.collection.Stream;
import com.mojang.blaze3d.vertex.PoseStack;
import me.clefal.whats_your_build.client.screen.BuildMenu;
import me.clefal.whats_your_build.client.screen.PlayerBuildScreen;
import me.clefal.whats_your_build.modules.armor.VanillaArmorComponent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;

import java.util.List;

public class VanillaArmorMenu extends BuildMenu<VanillaArmorComponent> {
    private final List<ArmorHolder> holders;
    private int holderRadius = 15;

    public VanillaArmorMenu(VanillaArmorComponent component, PlayerBuildScreen screen) {
        super(0, 0, 0, 0, Component.literal(""), component, screen);
        holderRadius *= screen.scale;
        holders = Stream.of(0, 1, 2, 3)
                .map(integer -> {
                    if (component.armors().size() - 1 >= integer) {
                        return new ArmorHolder(holderRadius, component.armors().get(integer));
                    }
                    return new ArmorHolder(holderRadius, null);
                })
                .asJava();
    }


    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        int i = (int) screen.getTabOriginalX();
        int j = (int) screen.getTabOriginalY();
        pose.pushPose();
        // x and y, two magic numbers
        //4, 1.5 and (32 * (1 + screen.scale / 4.0f)) are all magic numbers
        InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, (int) (getX() + getWidth() / 4f), (int) (getY() + getHeight() / 1.3f), (int) (32 + (35f * Math.pow(screen.scale - 1, 1.0d))), (float) (i + 51) - mouseX, (float) (j + 75 - 50) - mouseY, super.minecraft.player);
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
