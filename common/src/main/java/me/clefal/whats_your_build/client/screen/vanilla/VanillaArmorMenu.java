package me.clefal.whats_your_build.client.screen.vanilla;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import me.clefal.whats_your_build.client.screen.BuildMenu;
import me.clefal.whats_your_build.client.screen.PlayerBuildScreen;
import me.clefal.whats_your_build.modules.armor.VanillaArmorComponent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;

import java.util.List;
import java.util.stream.IntStream;

public class VanillaArmorMenu extends BuildMenu<VanillaArmorComponent> {
    private int holderRadius = 15;
    private final List<ArmorHolder> holders;

    public VanillaArmorMenu(VanillaArmorComponent component, PlayerBuildScreen screen) {
        super(component, screen);
        holders = IntStream.of(0, 1, 2, 3).boxed()
                .map(x -> {
                    if (component.armors().size() - 1 >= x){
                        return new ArmorHolder(holderRadius, component.armors().get(x));
                    }
                    return new ArmorHolder(holderRadius, null);
                })
                .toList();

    }



    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        pose.scale(screen.scale, screen.scale, 1.0f);
        int i = (int) screen.getTabOriginalX();
        int j = (int) screen.getTabOriginalY();
        //guiGraphics.drawString(Minecraft.getInstance().font, "1", 0, 0, ChatFormatting.BLACK.getColor());

        pose.pushPose();
        // two magic numbers
        pose.translate(20, 80, 0);
        InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, 0, 0, 32, (float) (i + 51) - mouseX, (float) (j + 75 - 50) - mouseY, super.minecraft.player);
        pose.popPose();

        int startX = 0;
        int time = 0;
        pose.translate(55, 20, 0);
        for (var holder : holders) {
            pose.pushPose();
            holder.setPosition(startX, ((int) ((holderRadius + holderRadius * (1 / 5.0f)) * time)));

            time++;
            if (time == 4) {
                time = 0;
                startX = holderRadius + 8;
            }
            pose.popPose();
        }

        pose.popPose();
    }
}
