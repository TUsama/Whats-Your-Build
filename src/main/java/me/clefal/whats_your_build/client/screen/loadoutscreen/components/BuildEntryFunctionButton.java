package me.clefal.whats_your_build.client.screen.loadoutscreen.components;

import me.clefal.whats_your_build.client.components.WYBImageButton;
import me.clefal.whats_your_build.client.screen.loadoutscreen.LoadoutSelectionList;
import net.minecraft.resources.ResourceLocation;

public abstract class BuildEntryFunctionButton extends WYBImageButton {
    protected LoadoutSelectionList.BuildEntry entry;

    public BuildEntryFunctionButton(ResourceLocation allInOne, LoadoutSelectionList.BuildEntry entry) {
        super(0, 0, 8, 8, button -> {}, allInOne);
        this.entry = entry;
    }

    public abstract void execute();

    @Override
    public void onPress() {
        execute();
    }
}
