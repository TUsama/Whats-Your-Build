package me.clefal.whats_your_build.client.screen.component;

import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import lombok.Getter;
import me.clefal.whats_your_build.client.screen.IBuildMenuContainer;
import me.clefal.whats_your_build.client.screen.IBuildMenuContainerHolder;
import me.clefal.whats_your_build.client.screen.buildscreen.AbstractBuildMenu;
import me.clefal.whats_your_build.client.screen.buildscreen.BuildMenuTab;
import me.clefal.whats_your_build.data.buildobject.Build;
import me.clefal.whats_your_build.data.handler.HandlerManager;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractContainerWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

public abstract class BuildPresentContainer extends AbstractContainerWidget implements IBuildMenuContainer {

    public final Player targetPlayer;
    @Getter
    protected Build build;
    @Nullable
    public List<BuildMenuTab<?, ?>> tabs;
    @Nullable
    protected AbstractBuildMenu<?> currentMenu;

    public BuildPresentContainer(Player targetPlayer, Build build) {
        super(0, 0, 0, 0, Component.literal(""));
        this.targetPlayer = targetPlayer;
        this.build = build;
    }

    public void setCurrentMenuSize(int width, int height) {
        if (currentMenu != null) currentMenu.setSize(width, height);
    }

    public void setCurrentMenuPosition(int x, int y) {
        if (currentMenu != null) currentMenu.setPosition(x, y);
    }

    public void initTabsPosition(int x, int y) {
        int i = x;
        for (BuildMenuTab<?, ?> tab : tabs) {
            tab.setPosition(i, y);
            i += tab.getWidth();
        }
    }


    @Override
    public List<BuildMenuTab<?, ?>> getTabs() {
        return tabs;
    }


    @Override
    public void setNewMenu(AbstractBuildMenu<?> menu) {
        this.currentMenu = menu;
    }

    @Override
    public void initTabs(IBuildMenuContainerHolder<?> holder) {
        this.tabs = HandlerManager.getInstance().getImmutableBuildMenuTabFunction(build).map(x -> x.apply(holder));
        tabs.headOption()
                .forEach(x -> {
                    this.currentMenu = x.getNewSlots().get();
                    setFocused(x);
                });
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

    @Override
    public java.util.List<? extends GuiEventListener> children() {
        return tabs.asJava();
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (tabs != null){
            for (BuildMenuTab<?, ?> tab : tabs) {
                tab.render(guiGraphics, mouseX, mouseY, partialTick);
            }
        }

        if (currentMenu != null) currentMenu.render(guiGraphics, mouseX, mouseY, partialTick);
    }
}
