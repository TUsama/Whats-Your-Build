package me.clefal.whats_your_build.client.screen.loadoutscreen.components;

import com.clefal.nirvana_lib.client.render.batch.VertexContainer;
import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.client.components.WYBImageButton;
import me.clefal.whats_your_build.client.screen.loadoutscreen.LoadoutSelectionList;
//? >1.20.1
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Component;

public class BuildEntryFunctionButton extends WYBImageButton {
    public LoadoutSelectionList.BuildEntry entry;
    private EntryAction action;
    public BuildEntryFunctionButton(String allInOneName, LoadoutSelectionList.BuildEntry entry, EntryAction action, Component message, VertexContainer container) {
        super(0, 0, 8, 8, button -> {},
                //? >1.20.1 {
                new WidgetSprites(CommonClass.id("textures/gui/loadout/button/" + allInOneName + "-enabled.png"), CommonClass.id("textures/gui/loadout/button/" + allInOneName + "-disabled.png"), CommonClass.id("textures/gui/loadout/button/" + allInOneName + "-enabled-focused.png")),
                //?} else {
                /*"loadout/button/" + allInOneName,
                *///?}
                container);
        this.entry = entry;
        this.action = action;
        this.setMessage(message);
    }

    @Override
    public void onPress() {
        action.action(this);
    }

    @FunctionalInterface
    public interface EntryAction{
        void action(BuildEntryFunctionButton button);
    }

}
