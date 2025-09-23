package me.clefal.whats_your_build.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import me.clefal.whats_your_build.world.block.entity.LoadoutChestEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;


public class LoadoutChestRenderer implements BlockEntityRenderer<LoadoutChestEntity> {

    private ItemRenderer renderer;

    public LoadoutChestRenderer(BlockEntityRendererProvider.Context context) {
        renderer = context.getItemRenderer();
    }

    @Override
    public void render(LoadoutChestEntity loadoutChestEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {
        poseStack.pushPose();
        int count = 0;
        NonNullList<ItemStack> target = loadoutChestEntity.getLastItems();
        BlockPos blockPos = loadoutChestEntity.getBlockPos();
        Vec3 center = blockPos.getCenter();
        poseStack.translate(center.x - blockPos.getX(), center.y - blockPos.getY() - 0.3f + loadoutChestEntity.getFilledPercent() * 0.5, center.z - blockPos.getZ());
        poseStack.scale(0.7f, 0.7f, 0.7f);
        poseStack.mulPose(Axis.XP.rotationDegrees(90));

        for (int i2 = 0; i2 < target.size(); i2++) {
            if (count >= loadoutChestEntity.getLimitation()) break;
            ItemStack item = target.get(i2);
            if (item.isEmpty()) continue;
            float v1 = 0.2f;
            poseStack.pushPose();
            switch (i2){
                case 1 -> poseStack.translate(-v1, -v1, -0.1f);
                case 2 -> poseStack.translate(v1, -v1, -0.15f);
                case 3 -> poseStack.translate(v1, v1, -0.2f);
                case 4 -> poseStack.translate(-v1, v1, -0.25f);
            }
            renderer.renderStatic(item, ItemDisplayContext.FIXED, 15728880, i1, poseStack, multiBufferSource, Minecraft.getInstance().level, 0);
            count++;
            poseStack.popPose();
        }
        poseStack.popPose();
    }
}
