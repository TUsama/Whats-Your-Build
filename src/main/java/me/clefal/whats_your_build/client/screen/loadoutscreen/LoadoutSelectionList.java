//? neoforge {
package me.clefal.whats_your_build.client.screen.loadoutscreen;

import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.client.components.WYBImageButton;
import me.clefal.whats_your_build.client.screen.loadoutscreen.components.BuildEntryFunctionButton;
import me.clefal.whats_your_build.data.buildobject.Build;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class LoadoutSelectionList extends AbstractSelectionList<LoadoutSelectionList.BuildEntry> {

    static final int WIDTH = 244;
    static final int HEIGHT = 12;
    private int rowWidth = 200;
    public static final int buttonXInterval = 24;
    public static final int buttonYInterval = 8;
    private static final ResourceLocation TEXTURE = CommonClass.id("textures/gui/screen_background.png");
    private final LoadoutScreen screen;
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
        int targetAmount = 10;
        if (children().size() < targetAmount){
            int i = targetAmount - children().size();
            while (i > 0){
                addEntry(new BuildEntry(Build.EMPTY));
                i--;
            }
        }
    }

    public boolean noBuild(){
        return getCurrentBuild() == Build.EMPTY;
    }

    public Build getCurrentBuild(){
        if (getFocused() != null) return getFocused().currentState.presentBuild();
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


    public class BuildEntry extends AbstractSelectionList.Entry<BuildEntry>{

        private BuildEntryState currentState;
        private List<WYBImageButton> buttons;

        public final BuildEntryFunctionButton save = new BuildEntryFunctionButton(CommonClass.gui("save"), this) {
            @Override
            public void execute() {
                entry.save();
            }
        };

        public final BuildEntryFunctionButton reset = new BuildEntryFunctionButton(CommonClass.gui("reset"), this) {
            @Override
            public void execute() {
                entry.abortChanges();
            }
        };

        public final BuildEntryFunctionButton clear = new BuildEntryFunctionButton(CommonClass.gui("clear"), this) {
            @Override
            public void execute() {
                entry.clear();
            }
        };

        public final BuildEntryFunctionButton delete = new BuildEntryFunctionButton(CommonClass.gui("reset"), this) {
            @Override
            public void execute() {
                LoadoutSelectionList list1 = (LoadoutSelectionList) entry.list;
                list1.removeEntry(entry);
            }
        };


        public BuildEntry(@Nullable Build storageBuild) {
            BuildEntryState.Waiting waiting = new BuildEntryState.Waiting(this);
            waiting.storageBuild = storageBuild;
            changeState(waiting);
            this.buttons = List.of(save, reset, clear, delete);
        }

        protected void changeState(BuildEntryState state){
            if (this.currentState != null) {
                this.currentState.onChangeState(state);
            }
            this.currentState = state;
            this.currentState.manipulateEntryComponents();
        }

        private void save(){
            currentState.save();
        }

        private void abortChanges(){
            currentState.abortChanges();
        }

        private void clear(){
            currentState.clear();
        }

        @Override
        public void render(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean hovering, float partialTick) {
            if (!isHoveredOrFocused()) return;
            for (int i = 0; i < buttons.size(); i++) {
                WYBImageButton wybImageButton = buttons.get(i);
                if (wybImageButton.isActive()){
                    wybImageButton.setPosition(top + height / 2, left + width - 2 - i * 8);
                    wybImageButton.render(guiGraphics, mouseX, mouseY, partialTick);
                }
            }
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            boolean flag = false;
            for (WYBImageButton wybImageButton : this.buttons) {
                flag = flag || wybImageButton.mouseClicked(mouseX, mouseY, button);
            }
            return flag;
        }

        @Override
        public void renderBack(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTick) {
            currentState.renderBack(guiGraphics, index, top, left, width, height, mouseX, mouseY, isMouseOver, partialTick, screen.vertexContainer);
        }
    }
}
//?}
