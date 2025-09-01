package me.clefal.whats_your_build.client.screen.loadoutscreen;

import com.clefal.nirvana_lib.client.render.batch.VertexContainer;
import me.clefal.whats_your_build.data.buildobject.Build;
import net.minecraft.client.gui.GuiGraphics;

public abstract class BuildEntryState {

    protected final LoadoutSelectionList.BuildEntry buildEntry;

    protected BuildEntryState(LoadoutSelectionList.BuildEntry buildEntry) {
        this.buildEntry = buildEntry;
    }

    public abstract void manipulateEntryComponents();
    public abstract void onChangeState(BuildEntryState next);
    public abstract void save();
    public abstract void abortChanges();
    public abstract void clear();
    public abstract Build presentBuild();
    public abstract void mouseClick(double mouseX, double mouseY, int button);
    public abstract void renderBack(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTick, VertexContainer vertexContainer);


    public static BuildEntryState getEditingState(LoadoutSelectionList.BuildEntry entry){
        return new Editing(entry);
    }

    public static BuildEntryState getWaitingState(LoadoutSelectionList.BuildEntry entry, Build build){
        Waiting waiting = new Waiting(entry);
        waiting.storageBuild = build;
        return waiting;
    }

    public static class Editing extends BuildEntryState{
        public Build baseBuild;
        public Build editingBuild;

        protected Editing(LoadoutSelectionList.BuildEntry buildEntry) {
            super(buildEntry);
        }

        @Override
        public void manipulateEntryComponents() {
            super.buildEntry.save.active = true;
            super.buildEntry.reset.active = true;
            super.buildEntry.delete.active = true;
            super.buildEntry.clear.active = true;
        }


        @Override
        public void onChangeState(BuildEntryState next) {

        }

        @Override
        public void save() {
            this.baseBuild = this.editingBuild.copy();
        }

        @Override
        public void abortChanges() {
            this.editingBuild = this.baseBuild.copy();
        }

        @Override
        public void clear() {
            this.editingBuild = this.editingBuild.cleanCopy();
        }

        @Override
        public Build presentBuild() {
            return editingBuild;
        }

        @Override
        public void mouseClick(double mouseX, double mouseY, int button) {

        }

        @Override
        public void renderBack(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTick, VertexContainer vertexContainer) {

        }
    }

    public static class Waiting extends BuildEntryState{

        public Build storageBuild;
        protected Waiting(LoadoutSelectionList.BuildEntry buildEntry) {
            super(buildEntry);
        }

        @Override
        public void manipulateEntryComponents() {
            super.buildEntry.save.active = false;
            super.buildEntry.reset.active = true;
            super.buildEntry.delete.active = true;
            super.buildEntry.clear.active = true;
        }


        @Override
        public void onChangeState(BuildEntryState next) {
            if (next instanceof Editing editing){
                editing.editingBuild = storageBuild.copy();
            }
        }

        @Override
        public void save() {

        }

        @Override
        public void abortChanges() {

        }

        @Override
        public void clear() {

        }

        @Override
        public Build presentBuild() {
            return storageBuild;
        }

        @Override
        public void mouseClick(double mouseX, double mouseY, int button) {

        }

        @Override
        public void renderBack(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTick, VertexContainer vertexContainer) {

        }
    }
}
