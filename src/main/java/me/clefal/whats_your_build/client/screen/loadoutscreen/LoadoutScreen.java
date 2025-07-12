//? neoforge {
package me.clefal.whats_your_build.client.screen.loadoutscreen;

import com.clefal.nirvana_lib.client.render.batch.VertexContainer;
import com.clefal.nirvana_lib.client.render.rendertype.RenderTypeCreator;
import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.client.storage.LoadoutsClientHandler;
import me.clefal.whats_your_build.data.buildobject.Build;
import me.clefal.whats_your_build.world.loadout.LoadoutMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;

public class LoadoutScreen extends AbstractContainerScreen<LoadoutMenu> {
    public final LoadoutSelectionList buildList;

    @Nullable
    public BuildWritableContainer container;
    public static final VertexContainer vertexContainer = new VertexContainer();
    public static final ResourceLocation INVENTORY_LOCATION = CommonClass.id("textures/gui/container/background.png");
    public static final ResourceLocation ARMORY = CommonClass.id("textures/gui/container/armory.png");

    public LoadoutScreen(LoadoutMenu menu, Inventory playerInventory) {
        super(menu, playerInventory, Component.literal(""));
        this.buildList = new LoadoutSelectionList(80, 60, 20, 100,this);

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

    public void changeContainer(BuildWritableContainer container){
        if (this.container != null) removeWidget(this.container);
        this.container = container;
        addRenderableWidget(this.container);
    }

    @Override
    protected void containerTick() {
        super.containerTick();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
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
}
//?}