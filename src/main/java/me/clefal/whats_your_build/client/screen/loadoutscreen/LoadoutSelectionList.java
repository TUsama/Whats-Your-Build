//? neoforge {
package me.clefal.whats_your_build.client.screen.loadoutscreen;

import com.mojang.blaze3d.vertex.PoseStack;
import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.client.components.WYBImageButton;
import me.clefal.whats_your_build.client.screen.loadoutscreen.components.BuildEntryFunctionButton;
import me.clefal.whats_your_build.data.buildobject.Build;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;

import javax.annotation.Nullable;
import java.util.ArrayDeque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Queue;
import java.util.function.BiFunction;

public class LoadoutSelectionList extends AbstractSelectionList<LoadoutSelectionList.BuildEntry> {

    static final int WIDTH = 244;
    static final int HEIGHT = 12;
    private int rowWidth = 200;
    public static final int buttonXInterval = 24;
    public static final int buttonYInterval = 8;
    private static final ResourceLocation TEXTURE = CommonClass.id("textures/gui/screen_background.png");
    protected final LoadoutScreen screen;
    private Queue<LoadoutSelectionList.BuildEntry> deletedEntry;

    public LoadoutSelectionList(int width, int height, int y0, int y1, LoadoutScreen screen) {
        //? 1.20.1
        /*super(Minecraft.getInstance(), width, height, y0, y1, HEIGHT);*/
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
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
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
        build.name = Component.translatable("wyb.screen.loadout.initial_build_name", this.children().size()).getString();
        addEntry(new BuildEntry(build));
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

    @Override
    public int getRowWidth() {
        return width;
    }

    @Override
    protected boolean scrollbarVisible() {
        return false;
    }

    @Override
    public void setFocused(@org.jetbrains.annotations.Nullable GuiEventListener focused) {
        //release old entry state
        if (getFocused() instanceof BuildEntry buildEntry){
            buildEntry.changeState(new BuildEntryState.Waiting(buildEntry));
        }
        super.setFocused(focused);
        if (focused instanceof BuildEntry buildEntry){
            System.out.println("set focus!");
            buildEntry.changeState(new BuildEntryState.Editing(buildEntry));
        }
    }

    public class BuildEntry extends AbstractSelectionList.Entry<BuildEntry>{

        BuildEntryState currentState;
        private List<WYBImageButton> buttons;
        private static final ResourceLocation BACKGROUND = CommonClass.gui("menu_button_background");
        private BiFunction<String, BuildEntryFunctionButton.EntryAction, BuildEntryFunctionButton> getButton = (string, action) -> Util.make(() -> {
            BuildEntryFunctionButton buildEntryFunctionButton = new BuildEntryFunctionButton(string, this, action, Component.translatable("wyb.screen.loadout.right_click_menu." + string)) {
                @Override
                public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
                    PoseStack pose = guiGraphics.pose();
                    pose.pushPose();
                    guiGraphics.blit(BACKGROUND, getX(), getY(), getWidth(), getHeight(), 0, 0, 34, 10, 34, 10);
                    pose.translate(4, 4, 0);
                    ResourceLocation resourcelocation = this.sprites.get(this.isActive(), this.isHoveredOrFocused());
                    guiGraphics.blitSprite(resourcelocation, this.getX(), this.getY(), 8, 8);
                    pose.translate(1 + 8 + 1.5f, 0, 0);
                    guiGraphics.drawString(Minecraft.getInstance().font, this.getMessage().getString(), getX(), getY(), ChatFormatting.WHITE.getColor());
                    pose.popPose();

                }
            };
            buildEntryFunctionButton.setSize(51, 15);
            return buildEntryFunctionButton;
        });

        public final BuildEntryFunctionButton save = getButton.apply("save", button -> button.entry.save(screen.getSlots()));
        public final BuildEntryFunctionButton reset = getButton.apply("reset", button -> button.entry.abortChanges());

        public final BuildEntryFunctionButton clear = getButton.apply("clear", button -> button.entry.clear());
        public final BuildEntryFunctionButton delete = getButton.apply("delete", button -> {
            LoadoutSelectionList list1 = (LoadoutSelectionList) button.entry.list;
            list1.removeEntry(button.entry);
        });



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
            screen.rewriteSlots();
        }

        private void save(LinkedHashMap<String, NonNullList<Slot>> currentSlots){
            currentState.save(currentSlots);
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
            screen.rewriteSlots(screen.currentAt);
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
//?}
