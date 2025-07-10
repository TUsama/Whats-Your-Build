//? neoforge {
package me.clefal.whats_your_build.client.screen.loadoutscreen;

import com.clefal.nirvana_lib.client.render.batch.VertexContainer;
import com.clefal.nirvana_lib.client.render.rendertype.RenderTypeCreator;
import me.clefal.whats_your_build.client.storage.LoadoutsClientHandler;
import me.clefal.whats_your_build.data.buildobject.Build;
import me.clefal.whats_your_build.world.loadout.LoadoutMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import java.io.IOException;
import java.util.List;

public class LoadoutScreen extends AbstractContainerScreen<LoadoutMenu> {
    public final LoadoutSelectionList buildList;
    public static final VertexContainer vertexContainer = new VertexContainer();


    public LoadoutScreen(LoadoutMenu menu, Inventory playerInventory) {
        super(menu, playerInventory, Component.literal(""));
        this.buildList = new LoadoutSelectionList(80, 60, 20, 100);

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
            System.out.println(buildList.children().size());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {

    }
}
//?}