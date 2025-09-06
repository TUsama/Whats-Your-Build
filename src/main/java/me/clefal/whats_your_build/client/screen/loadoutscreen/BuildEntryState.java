package me.clefal.whats_your_build.client.screen.loadoutscreen;

import com.clefal.nirvana_lib.client.render.batch.VertexContainer;
import me.clefal.whats_your_build.client.components.WYBImageButton;
import me.clefal.whats_your_build.client.storage.LoadoutsClientHandler;
import me.clefal.whats_your_build.data.buildobject.Build;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.Slot;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

public abstract class BuildEntryState {

    protected final LoadoutSelectionList.BuildEntry buildEntry;

    protected BuildEntryState(LoadoutSelectionList.BuildEntry buildEntry) {
        this.buildEntry = buildEntry;
    }

    public abstract List<WYBImageButton> provideButtons();
    public abstract void onChangeState(BuildEntryState next);
    public abstract void save(LinkedHashMap<String, NonNullList<Slot>> currentSlots);
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
        public List<WYBImageButton> provideButtons() {
            return List.of(super.buildEntry.save, super.buildEntry.reset, super.buildEntry.delete, super.buildEntry.clear);
        }


        @Override
        public void onChangeState(BuildEntryState next) {
            if (next instanceof Waiting waiting){
                waiting.storageBuild = baseBuild.copy();
            }
        }

        @Override
        public void save(LinkedHashMap<String, NonNullList<Slot>> currentSlots) {
            currentSlots.forEach((string, slots) -> {
                this.baseBuild = this.baseBuild.createNewBuildFromContainer(string, slots.isEmpty() ? new SimpleContainer() : slots.getFirst().container);
            });
            try {
                LoadoutsClientHandler.writeToLocal(this.baseBuild);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        @Override
        public void abortChanges() {

        }

        @Override
        public void clear() {
            this.baseBuild = this.baseBuild.cleanCopy();
        }

        @Override
        public Build presentBuild() {
            return baseBuild;
        }

        @Override
        public void mouseClick(double mouseX, double mouseY, int button) {

        }

        @Override
        public void renderBack(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTick, VertexContainer vertexContainer) {
            if (isMouseOver || buildEntry.isFocused()){
                guiGraphics.drawString(Minecraft.getInstance().font, presentBuild().name, left + 1, top - 1, ChatFormatting.WHITE.getColor());
            } else {
                guiGraphics.drawString(Minecraft.getInstance().font, presentBuild().name, left, top, ChatFormatting.WHITE.getColor());
            }
        }
    }

    public static class Waiting extends BuildEntryState{

        public Build storageBuild;
        protected Waiting(LoadoutSelectionList.BuildEntry buildEntry) {
            super(buildEntry);
        }


        @Override
        public List<WYBImageButton> provideButtons() {
            return List.of(super.buildEntry.delete, super.buildEntry.clear);
        }


        @Override
        public void onChangeState(BuildEntryState next) {
            if (next instanceof Editing editing){
                Build copy = storageBuild.copy();
                editing.baseBuild = copy;
            }
        }

        @Override
        public void save(LinkedHashMap<String, NonNullList<Slot>> currentSlots) {

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
            System.out.println(storageBuild.getName());
        }

        @Override
        public void renderBack(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTick, VertexContainer vertexContainer) {
            if (isMouseOver || buildEntry.isFocused()){
                guiGraphics.drawString(Minecraft.getInstance().font, presentBuild().name, left + 1, top - 1, ChatFormatting.WHITE.getColor());
            } else {
                guiGraphics.drawString(Minecraft.getInstance().font, presentBuild().name, left, top, ChatFormatting.WHITE.getColor());
            }

        }
    }
}
