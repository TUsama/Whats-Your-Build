package me.clefal.whats_your_build.client.screen.loadoutscreen;

import com.mojang.blaze3d.vertex.PoseStack;
import lombok.experimental.ExtensionMethod;
import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.Constants;
import me.clefal.whats_your_build.client.screen.loadoutscreen.components.BuildEntryFunctionButton;
import me.clefal.whats_your_build.client.storage.LoadoutsClientHandler;
import me.clefal.whats_your_build.data.buildobject.Build;
import me.clefal.whats_your_build.utils.WidgetHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
@ExtensionMethod(WidgetHelper.class)
public class LoadoutSelectionList extends AbstractSelectionList<LoadoutSelectionList.BuildEntry> {

    static final int WIDTH = 244;
    static final int HEIGHT = 12;
    protected final LoadoutScreen screen;
    private Queue<BuildEntry> deletedEntry;

    private static final ResourceLocation BACKGROUND = CommonClass.gui("sprites/loadout/menu_button_background");
    private final BiFunction<String, BuildEntryFunctionButton.EntryAction, BuildEntryFunctionButton> getButton;

    public final BuildEntryFunctionButton save;
    public final BuildEntryFunctionButton reset;

    public final BuildEntryFunctionButton clear;
    public final BuildEntryFunctionButton delete;
    public final BuildEntryFunctionButton rename;

    public LoadoutSelectionList(int width, int height, int y0, int y1, LoadoutScreen screen) {
        //? 1.20.1
        //super(Minecraft.getInstance(), width, height, y0, y1, HEIGHT);
        //? >1.20.1
        super(Minecraft.getInstance(), width, height, y1 - y0, HEIGHT);
        //setLeftPos(-10);

        //? !=1.21.4
        this.setRenderHeader(false, 0);
        //? 1.20.1 {
        /*this.setRenderBackground(false);
        this.setRenderTopAndBottom(false);
        this.setRenderSelection(false);

        *///?}
        this.screen = screen;
        this.deletedEntry = new ArrayDeque<>();
        this.getButton = (string, action) -> Util.make(() -> {
            BuildEntryFunctionButton buildEntryFunctionButton = new BuildEntryFunctionButton(string, this, action, Component.translatable("wyb.screen.loadout.right_click_menu." + string), screen.vertexContainer) {
                @Override
                public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
                    PoseStack pose = guiGraphics.pose();
                    pose.pushPose();

                    guiGraphics.blit(BACKGROUND, getX(), getY(), getWidth(), getHeight(), 0, 0, 34, 10, 34, 10);
                    pose.translate(4, 4, 0);
                    /*
                    ResourceLocation resourcelocation = this.sprites.get(this.isActive(), this.isHoveredOrFocused());

                    screen.getVertexContainer().putBliz(resourcelocation, TextureBufferInfo.of(this.getX(), this.getY(), 16, 16, 0, 0, 32, 32, 32, 32, pose.last().pose()));
*/
                    //guiGraphics.blit(resourcelocation, this.getX(), this.getY(), 0, 0, 8, 8);
                    pose.translate(1 + 8 + 1.5f, 0, 0);
                    guiGraphics.drawString(Minecraft.getInstance().font, this.getMessage().getString(), getX(), getY(), ChatFormatting.WHITE.getColor());
                    pose.popPose();

                }
            };

            buildEntryFunctionButton.setSize(51, 15);
            return buildEntryFunctionButton;
        });

        this.save = getButton.apply("save", button -> button.entry.save(screen));

        this.reset = getButton.apply("reset", button -> button.entry.abortChanges());

        this.clear = getButton.apply("clear", button -> button.entry.clear());

        this.delete = getButton.apply("delete", button -> {
            LoadoutSelectionList list1 = button.list;
            list1.removeEntry(button.entry);
            try {
                LoadoutsClientHandler.deleteFromLocal(button.entry.currentState.presentBuild().name);
            } catch (IOException e) {
                Constants.LOG.error("Failed to load builds from local when delete entry: {}", button.entry.currentState.presentBuild().name, e);
            }
            list1.setFocused(null);
        });

        this.rename = getButton.apply("rename", button -> {
            int i = this.children().indexOf(button.entry);
            int rowBottom = getRowBottom(i);
            int rowTop = getRowTop(i);
            screen.addNameInput(button.entry.currentState instanceof BuildEntryState.Editing && button.entry.isFocused() ? getRowLeft() + Minecraft.getInstance().font.width(BuildEntryState.TRIANGLE.getString()): getRowLeft(), rowTop, getRowWidth(), rowBottom - rowTop);
            button.entry.isRenaming = true;
        });

    }
    //? >1.20.1 {
    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
    //?}
    public void addBuildsOnInit(List<Build> builds){
        for (Build build : builds) {
            addEntry(new BuildEntry(build));
        }
    }


    @Nullable
    public BuildEntry getCurrentEntry(double mouseX, double mouseY){
        return getEntryAtPosition(mouseX, mouseY);
    }

    protected void addSelfBuildEntry(Build build){
        List<Build> builds;
        try {
            builds = LoadoutsClientHandler.readAllFromLocal(Minecraft.getInstance().player.getUUID());
        } catch (IOException e) {
            builds = List.of();
            Constants.LOG.error("Failed to load builds from local when add new entry: {}", String.valueOf(e));
        }
        Set<String> collect = builds.stream().map(Build::getName).collect(Collectors.toSet());
        String name = Component.translatable("wyb.screen.loadout.initial_build_name", this.children().size()).getString();
        while (collect.contains(name)){
            name += "(1)";
        }
        build.name = name;
        BuildEntry buildEntry = new BuildEntry(build);
        buildEntry.currentState.isUnsaved = true;
        addEntry(buildEntry);
    }

    public void renderHoveredTooltips(GuiGraphics guiGraphics, int mouseX, int mouseY){
        if (getHovered()!= null && getHovered().currentState instanceof BuildEntryState.Editing editing && editing.isUnsaved){
            guiGraphics.renderTooltip(Minecraft.getInstance().font, Component.translatable("wyb.screen.loadout.save_tip"), mouseX, mouseY);
        }

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean b = super.mouseClicked(mouseX, mouseY, button);
        for (BuildEntry buildEntry : deletedEntry) {
            removeEntry(buildEntry);
        }
        deletedEntry.clear();
        return b;
    }


    public boolean noBuild(){
        return getCurrentBuild() == Build.EMPTY;
    }

    public Build getCurrentBuild(){
        if (getFocused() != null) {
            return getFocused().currentState.presentBuild();
        }
        return Build.EMPTY;
    }

    public void finishRename(String rename){
        this.children().stream().filter(x -> x.isRenaming).findFirst()
                .ifPresent(x -> {
                    boolean flag = false;
                    if (!rename.isBlank()){
                        flag = x.currentState.rename(rename);
                    }
                    x.isRenaming = false;
                    if (flag){
                        x.save(screen);
                    }

                });
    }

    @Override
    public int getRowWidth() {
        return width;
    }


    //? fabric {
    /*public int getHeight(){
        return height;
    }
    *///?}

    //? >1.20.1 {
    @Override
    protected boolean scrollbarVisible() {
        return false;
    }

    //?} else {
    /*@Override
    protected int getScrollbarPosition() {
        return x0 + this.width + 999;
    }
    *///?}



    @Override
    public void setFocused(@org.jetbrains.annotations.Nullable GuiEventListener focused) {
        //release old entry state
        //? >1.20.1 {
        if (getFocused() instanceof BuildEntry buildEntry){
            buildEntry.changeState(new BuildEntryState.Waiting(buildEntry));
        }
        //?} else {
        /*if (getFocused() != null){
            if (getFocused().equals(focused)) return;
            getFocused().changeState(new BuildEntryState.Waiting(getFocused()));
        }
        *///?}

        super.setFocused(focused);
        if (focused instanceof BuildEntry buildEntry){
            buildEntry.changeState(new BuildEntryState.Editing(buildEntry));
        }
    }

    public void resetRename(){
        for (BuildEntry child : this.children()) {
            child.isRenaming = false;
        }
    }
    //? 1.20.1 {
    /*@Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {

    }
    *///?}

    public class BuildEntry extends Entry<BuildEntry>{

        BuildEntryState currentState;
        boolean isRenaming;

        public BuildEntry(@Nullable Build storageBuild) {
            BuildEntryState.Waiting waiting = new BuildEntryState.Waiting(this);
            waiting.storageBuild = storageBuild;
            changeState(waiting);
        }




        protected void changeState(BuildEntryState state){
            if (this.currentState != null) {
                this.currentState.onChangeState(state);
            }
            this.currentState = state;
            screen.handleBuild(this.currentState.presentBuild());
            screen.rewriteSlotsToFirst();
            if (state instanceof BuildEntryState.Editing){
                screen.setCurrentEditingEntry(this);
            } else {
                screen.setCurrentEditingEntry(null);
            }
        }

        private void save(LoadoutScreen screen){
            currentState.save(screen);
            screen.handleBuild(currentState.presentBuild());
            screen.rewriteSlots(screen.currentAt);
        }

        private void abortChanges(){
            currentState.abortChanges();
            if (!screen.currentAt.isEmpty()) {
                screen.rewriteSlots(screen.currentAt);
            }
        }

        private void clear(){
            currentState.clear();
            screen.handleBuild(currentState.presentBuild());
            screen.rewriteSlotsToFirst();
        }

        @Override
        public void render(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean hovering, float partialTick) {
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            boolean flag = false;
            /*for (WYBImageButton wybImageButton : this.buttons) {
                flag = flag || wybImageButton.mouseClicked(mouseX, mouseY, button);
                currentState.mouseClick(mouseX, mouseY, button);
            }*/
            return flag || isMouseOver(mouseX, mouseY);
        }

        @Override
        public void renderBack(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTick) {

            currentState.renderBack(guiGraphics, index, top, left, width, height, mouseX, mouseY, isMouseOver, partialTick, screen.vertexContainer);
        }
    }
}
