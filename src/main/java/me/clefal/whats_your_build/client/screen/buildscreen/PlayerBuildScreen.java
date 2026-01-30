package me.clefal.whats_your_build.client.screen.buildscreen;

import com.clefal.nirvana_lib.client.render.batch.DrawStringBufferInfo;
import com.clefal.nirvana_lib.client.render.batch.TextureBufferInfo;
import com.clefal.nirvana_lib.client.render.batch.VertexContainer;
import com.clefal.nirvana_lib.client.render.rendertype.RenderTypeCreator;
import com.clefal.nirvana_lib.relocated.io.vavr.Tuple;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.Map;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
//? mas {
/*import com.robertx22.mine_and_slash.database.data.talent_tree.TalentTree;
import me.clefal.whats_your_build.data.modules.compat.mas.talent.MASSkillComponent;
import me.clefal.whats_your_build.data.modules.compat.mas.talent.client.MASSkillViewButton;
*///?}
import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.Constants;
import me.clefal.whats_your_build.client.screen.WYBScreen;
import me.clefal.whats_your_build.data.buildobject.Build;
import me.clefal.whats_your_build.data.handler.HandlerManager;
import me.clefal.whats_your_build.data.handler.IBuildComponent;
import me.clefal.whats_your_build.data.modules.armor.VanillaArmorComponent;
//? curios
import me.clefal.whats_your_build.data.modules.compat.curios.CuriosComponent;

import me.clefal.whats_your_build.utils.IBufferSourceProvider;
import me.clefal.whats_your_build.world.BuildMenu;
import me.clefal.whats_your_build.world.IBuildHandler;
import me.clefal.whats_your_build.world.IRewritable;
import me.clefal.whats_your_build.world.NonInteractiveResultSlot;
import me.clefal.whats_your_build.world.player_build.PlayerBuildMenu;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.HashMap;

public class PlayerBuildScreen extends WYBScreen<PlayerBuildMenu> implements IRewritable, IBuildHandler {

    public final static ResourceLocation COMPONENT = CommonClass.id("textures/gui/component.png");
    protected static int BACKGROUND_WIDTH = 128;
    protected static int BACKGROUND_HEIGHT = 128;
    public VertexContainer vertexContainer = new VertexContainer();
    protected java.util.Map<String, BuildMenu.ComponentHandler> placePlan = new HashMap<>();
    protected java.util.Map<String, List<? extends AbstractWidget>> widgets = new HashMap<>();
    private List<BuildMenuTab<?>> tabs;

    public PlayerBuildScreen(PlayerBuildMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.tabs = HandlerManager.getInstance().getImmutableBuildMenuTabFunction(menu.targetBuild).map(x -> x.apply(this, this));
        handleBuild(menu.targetBuild);
    }


    @Override
    protected void init() {
        super.init();
        this.imageWidth = BACKGROUND_WIDTH;
        this.imageHeight = BACKGROUND_HEIGHT;
        this.leftPos = ((int) (Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2.0f - BACKGROUND_WIDTH / 2.0f));
        this.topPos = ((int) (Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2.0f - BACKGROUND_HEIGHT / 2.0f));
        vertexContainer = new VertexContainer();
        tabs.forEachWithIndex((buildMenuTab, value) -> {
            buildMenuTab.setPosition(leftPos + 20 + (buildMenuTab.getWidth() - 2) * value, topPos + 36);
            this.addRenderableWidget(buildMenuTab);
        });
        tabs.headOption().forEach(x -> {
            this.setInitialFocus(x);
            x.onPress();
        });


    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
        vertexContainer.draw(((IBufferSourceProvider) guiGraphics).whats_Your_Build$getBufferSource(), RenderTypeCreator.guiBlend);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        PoseStack pose = guiGraphics.pose();
        RenderSystem.enableDepthTest();

        pose.pushPose();


        float portion = 8.0f;
        float interval = BACKGROUND_WIDTH * (1 / portion);
        float lineStartX = leftPos + interval;
        float lineStartY = topPos + 46;

        {
            //tab line
            pose.pushPose();
            pose.translate(lineStartX, lineStartY, 0.1);
            vertexContainer.putBliz(COMPONENT, TextureBufferInfo.of(0, 0, (int) (BACKGROUND_WIDTH * ((portion - 2) / portion)), 1, 128, 0, 128, 1, 256, 256, pose.last().pose()));
            //guiGraphics.blit(COMPONENT ,0, 0, (int)(BACKGROUND_WIDTH * ((portion - 2) / portion)), 1, 128, 0, 128, 1, 256, 256);
            pose.popPose();
        }
        {
            pose.pushPose();

            {
                //background

                pose.translate(leftPos, topPos, 0);
                vertexContainer.putBlitNineSliced(COMPONENT, 0, 0, 128, 128, (int) (6), 8, 128, 256, 0, 0, pose.last().pose());
                pose.translate(0, 10, 0);
                //guiGraphics.blitNineSliced(COMPONENT, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT, (int) (6 * scale), 8, 128, 256, 0, 0);
            }

            {
                //title
                pose.pushPose();
                float scale = 0.8f;
                float scaleReciprocal = 1.0f / scale;
                pose.scale(scale, scale, 1.0f);
                MutableComponent translatable = Component.translatable("wyb.screen.player_build.player_build");
                float mx = BACKGROUND_WIDTH / 2.0f - Minecraft.getInstance().font.width(translatable.getString()) * scale / 2.0f;
                float my = BACKGROUND_HEIGHT / (2.0f * 4.0f) - Minecraft.getInstance().font.lineHeight * scale / 2.0f;
                pose.translate(mx * scaleReciprocal, my * scaleReciprocal, 0);
                //guiGraphics.drawString(Minecraft.getInstance().font, translatable, 0, 0, ChatFormatting.BLACK.getColor(), false);
                vertexContainer.putString(DrawStringBufferInfo.of(translatable.getString(), 0, 0, ChatFormatting.BLACK.getColor(), pose.last().pose()));


                pose.popPose();
            }

            pose.popPose();
        }

        //menu


        pose.pushPose();
        pose.popPose();


        pose.popPose();
    }


    @Override
    public VertexContainer getVertexContainer() {
        return vertexContainer;
    }

    public void rewriteSlots(String id) {
        BuildMenu.ComponentHandler componentHandler = placePlan.get(id);
        if (componentHandler == null){
            Constants.LOG.info("No {} component in this loadout!", id);
        } else {
            menu.rewriteSlots(id);
            widgets.values().forEach(x -> x.forEach(this::removeWidget));
            placePlan.get(id).handle();
        }

    }

    @Override
    public void handleBuild(Build build) {
        Map<String, ? extends IBuildComponent<?>> map = build.getComponents()
                .map((aByte, iBuildComponent) -> Tuple.of(iBuildComponent.getIdentifier(), iBuildComponent));
        map
                .get(VanillaArmorComponent.ID)
                .forEach(iBuildComponent -> placePlan.put(VanillaArmorComponent.ID,
                        () -> {
                            var simpleContainer = ((VanillaArmorComponent) iBuildComponent).asContainer();
                            int k = 0;
                            int j = 0;
                            for (int i = 0; i < simpleContainer.getContainerSize(); i++) {
                                if (k >= 4) {
                                    j++;
                                    k = 0;
                                }
                                menu.addSlotFromScreen(new NonInteractiveResultSlot(simpleContainer, i, j * 18 + 24, k * 18 + 52));
                                k++;
                            }
                        }));
        //? curios {

        map
                .get(CuriosComponent.ID)
                .forEach(iBuildComponent -> placePlan.put(CuriosComponent.ID,
                        () -> {
                            var simpleContainer = ((CuriosComponent) iBuildComponent).asContainer();

                            int k = 0;
                            int j = 0;
                            for (int i = 0; i < simpleContainer.getContainerSize(); i++) {
                                if (k >= 4) {
                                    j++;
                                    k = 0;
                                }
                                menu.addSlotFromScreen(new NonInteractiveResultSlot(simpleContainer, i, j * 18 + 24, k * 18 + 52));
                                k++;
                            }
                        }));
        //?}

        //? mas {

        /*map
                .get(MASSkillComponent.ID)
                .forEach(iBuildComponent -> {
                    //keep only one button instance, otherwise the remove in rewriteSlots will not work.
                    widgets.putIfAbsent(MASSkillComponent.ID, Util.make(() -> List.of(
                            MASSkillViewButton.getViewButton(TalentTree.SchoolType.TALENTS, this, (MASSkillComponent) iBuildComponent),
                            MASSkillViewButton.getViewButton(TalentTree.SchoolType.ASCENDANCY, this, (MASSkillComponent) iBuildComponent)
                    )));
                    placePlan.put(MASSkillComponent.ID, () -> widgets.get(MASSkillComponent.ID).forEachWithIndex((x, y) -> {
                        x.setPosition(leftPos + (BACKGROUND_WIDTH / 2) - (x.getWidth() / 2), topPos + 70 + y * x.getHeight());
                        addRenderableWidget(x);
                    }));

                });
        *///?}
    }

}
