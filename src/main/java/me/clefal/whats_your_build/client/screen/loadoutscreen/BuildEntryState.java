package me.clefal.whats_your_build.client.screen.loadoutscreen;

import com.clefal.nirvana_lib.client.render.batch.VertexContainer;
import com.clefal.nirvana_lib.relocated.io.vavr.Tuple;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.Map;
import me.clefal.whats_your_build.Constants;
import me.clefal.whats_your_build.client.storage.LoadoutsClientHandler;
import me.clefal.whats_your_build.data.buildobject.Build;
import me.clefal.whats_your_build.data.handler.IBuildComponent;
import me.clefal.whats_your_build.data.handler.IItemBuildComponent;
//? mas {
/*import me.clefal.whats_your_build.data.modules.compat.mas.talent.MASSkillComponent;
import me.clefal.whats_your_build.data.modules.compat.mas.talent.client.MASSkillViewButton;
import me.clefal.whats_your_build.mixinhelper.ITalentDataGetter;
*///?}
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.Slot;

import java.io.IOException;
import java.util.LinkedHashMap;

public abstract class BuildEntryState {

    protected final LoadoutSelectionList.BuildEntry buildEntry;
    public static final Component TRIANGLE = Component.literal("➤").withStyle(ChatFormatting.WHITE);
    public boolean isUnsaved = false;

    protected BuildEntryState(LoadoutSelectionList.BuildEntry buildEntry) {
        this.buildEntry = buildEntry;
    }

    public abstract void onChangeState(BuildEntryState next);
    public abstract void save(LoadoutScreen screen);
    public abstract void abortChanges();
    public abstract void clear();
    public abstract Build presentBuild();
    public abstract boolean rename(String name);
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


        protected Editing(LoadoutSelectionList.BuildEntry buildEntry) {
            super(buildEntry);
        }


        @Override
        public void onChangeState(BuildEntryState next) {
            if (next instanceof Waiting waiting){
                waiting.storageBuild = baseBuild.copy();
            }
        }

        @Override
        public void save(LoadoutScreen screen) {
            //todo it would be nice to done all the save jobs on each handler, but the permission limitation can't be ignored.
            Map<String, ? extends IBuildComponent<?>> map = baseBuild.getComponents().map((b, c) -> Tuple.of(c.getIdentifier(), c));
            LinkedHashMap<String, NonNullList<Slot>> slotMap = screen.getSlotMap();
            this.baseBuild = map.map((string, component) -> {
                if (component instanceof IItemBuildComponent<?> itemBuildComponent) {
                    NonNullList<Slot> slots = slotMap.get(string);
                    if (slots != null) {
                        IBuildComponent<?> fromContainer = itemBuildComponent.getFromContainer(slots.get(0).container);
                        return Tuple.of(string, fromContainer);
                    }

                }
                //? mas {
                /*//if mas skill compat is enable.
                else if (component instanceof MASSkillComponent) {
                    List<? extends AbstractWidget> abstractWidgets = screen.getWidgets().get(MASSkillComponent.ID);
                    if (abstractWidgets != null) {
                        //there is no way abstractWidgets.get(0) is null, cuz in this case, player should select the other build, and the select build is on Waiting state, the save button is hided, means this can't be invoked.
                        ITalentDataGetter talents = (ITalentDataGetter) ((MASSkillViewButton) abstractWidgets.get(0)).data.talents;
                        return Tuple.of(MASSkillComponent.ID, new MASSkillComponent(talents.getPerks()));
                    }
                }
                *///?}
                return Tuple.of(string, component);
            }).transform(
                    x -> new Build(List.ofAll(x.values()), this.baseBuild.name)
            );


            try {
                LoadoutsClientHandler.writeToLocal(this.baseBuild);
            } catch (IOException e) {
                Constants.LOG.error("Failed to save build: {}", this.baseBuild.name, e);
            }
            this.isUnsaved = false;
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
        public boolean rename(String name) {
            boolean flag;
            try {
                flag = LoadoutsClientHandler.rename(presentBuild().name, name);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (flag){
                presentBuild().name = name;
            }
            return flag;
        }

        @Override
        public void mouseClick(double mouseX, double mouseY, int button) {

        }

        @Override
        public void renderBack(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTick, VertexContainer vertexContainer) {
            Font font = Minecraft.getInstance().font;
            int color = isUnsaved ? ChatFormatting.YELLOW.getColor() : ChatFormatting.WHITE.getColor();
            if (buildEntry.isFocused()){
                guiGraphics.drawString(font, Component.literal("➤").withStyle(ChatFormatting.WHITE), left + 1, top - 1, ChatFormatting.WHITE.getColor());
                if (!buildEntry.isRenaming){
                    guiGraphics.drawString(font, presentBuild().name, left + 10, top - 1, color);
                }
            } else {
                guiGraphics.drawString(font, presentBuild().name, left, top, color);
            }

        }
    }

    public static class Waiting extends BuildEntryState{

        public Build storageBuild;
        protected Waiting(LoadoutSelectionList.BuildEntry buildEntry) {
            super(buildEntry);
        }



        @Override
        public void onChangeState(BuildEntryState next) {
            if (next instanceof Editing editing){
                Build copy = storageBuild.copy();
                editing.baseBuild = copy;
                editing.isUnsaved = isUnsaved;
            }
        }

        @Override
        public void save(LoadoutScreen screen) {
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
        public boolean rename(String name) {
            presentBuild().name = name;
            isUnsaved = true;
            return false;
        }

        @Override
        public void mouseClick(double mouseX, double mouseY, int button) {
        }

        @Override
        public void renderBack(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTick, VertexContainer vertexContainer) {
            if (buildEntry.isRenaming) return;
            int color = isUnsaved ? ChatFormatting.YELLOW.getColor() : ChatFormatting.WHITE.getColor();
            if (isMouseOver || buildEntry.isFocused()){
                guiGraphics.drawString(Minecraft.getInstance().font, presentBuild().name, left + 1, top - 1, color);
            } else {
                guiGraphics.drawString(Minecraft.getInstance().font, presentBuild().name, left, top, color);
            }

        }
    }
}
