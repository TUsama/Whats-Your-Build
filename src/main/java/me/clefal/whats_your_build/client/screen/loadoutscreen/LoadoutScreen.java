//? neoforge {
package me.clefal.whats_your_build.client.screen.loadoutscreen;

import com.clefal.nirvana_lib.client.render.batch.DrawStringBufferInfo;
import com.clefal.nirvana_lib.client.render.batch.VertexContainer;
import com.clefal.nirvana_lib.client.render.rendertype.RenderTypeCreator;
import com.clefal.nirvana_lib.utils.NetworkUtils;
import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.client.screen.WYBScreen;
import me.clefal.whats_your_build.client.storage.LoadoutsClientHandler;
import me.clefal.whats_your_build.data.buildobject.Build;
import me.clefal.whats_your_build.network.c2s.C2SAskTemplateBuildPacket;
import me.clefal.whats_your_build.world.loadout.LoadoutMenu;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;

public class LoadoutScreen extends WYBScreen<LoadoutMenu> {
    public static final ResourceLocation INVENTORY_LOCATION = CommonClass.id("textures/gui/container/background.png");
    public static final ResourceLocation ARMORY = CommonClass.id("textures/gui/container/armory.png");
    public final LoadoutSelectionList buildList;
    @Nullable
    public Build template;

    public VertexContainer vertexContainer = new VertexContainer();


    public LoadoutScreen(LoadoutMenu menu, Inventory playerInventory) {
        super(menu, playerInventory, Component.literal(""));
        this.buildList = new LoadoutSelectionList(80, 60, 20, 100, this);
        NetworkUtils.sendToServer(new C2SAskTemplateBuildPacket());
    }

    public void initTemplate(Build build) {
        this.template = build;
    }

    @Override
    protected void init() {
        super.init();
        try {
            List<Build> builds = LoadoutsClientHandler.readAllFromLocal(minecraft.player.getUUID());
            buildList.addBuildsOnInit(builds);
            addRenderableWidget(buildList);
            buildList.setX(leftPos);
            buildList.setY(topPos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

/*
    public void changeContainer(BuildWritableContainer container){
        if (this.container != null) removeWidget(this.container);
        this.container = container;
        this.container.initTabs(this);
        RenderContext renderContext = generateRenderContext();
        this.container.initTabsPosition(renderContext.tabOriginalX(), renderContext.tabOriginalY());
        addRenderableWidget(this.container);
    }
*/

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (buildList.noBuild()) {

            vertexContainer.putString(DrawStringBufferInfo.of(Component.translatable("wyb.screen.loadout.no_loadout").getString(), leftPos + buildList.getWidth() + ((menu.startX - leftPos + buildList.getWidth()) / 2), topPos + buildList.getHeight() / 2, ChatFormatting.GRAY.getColor(), guiGraphics.pose().last().pose()));
        } else {
            /*RenderContext renderContext = generateRenderContext();
            container.setCurrentMenuPosition(renderContext.tabOriginalX(), renderContext.tabOriginalY());
            container.setCurrentMenuSize(100, 100);*/
        }
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        vertexContainer.draw(guiGraphics.bufferSource(), RenderTypeCreator.guiBlend);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(INVENTORY_LOCATION, leftPos, topPos + buildList.getHeight() + 12, 0, 0, 256, 256);
        guiGraphics.blit(ARMORY, leftPos + imageWidth, topPos, 0, 0, 256, 256);
    }


    @Override
    public VertexContainer getVertexContainer() {
        return vertexContainer;
    }
}
//?}