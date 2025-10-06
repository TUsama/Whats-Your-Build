package me.clefal.whats_your_build.data.modules.compat.mas.talent.client;

import com.robertx22.mine_and_slash.capability.player.PlayerData;
import com.robertx22.mine_and_slash.database.data.talent_tree.TalentTree;
import com.robertx22.mine_and_slash.gui.screens.skill_tree.SkillTreeScreen;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import me.clefal.whats_your_build.client.screen.loadoutscreen.LoadoutScreen;
import me.clefal.whats_your_build.client.screen.loadoutscreen.LoadoutSelectionList;
import me.clefal.whats_your_build.mixinhelper.ISkillScreenHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class MASNestedSkillScreen extends SkillTreeScreen {

    private final Screen nestedScreen;
    public boolean editable;

    public MASNestedSkillScreen(TalentTree.SchoolType type, Screen nestedScreen, boolean editable) {
        super(type);
        this.nestedScreen = nestedScreen;
        this.editable = editable;
    }


    @Override
    public void onClose() {
        super.onClose();
        Minecraft.getInstance().screen = nestedScreen;
        Minecraft.getInstance().mouseHandler.releaseMouse();
        KeyMapping.releaseAll();
        Minecraft.getInstance().noRender = false;

    }

    @Override
    public void render(GuiGraphics gui, int x, int y, float ticks) {
        super.render(gui, x, y, ticks);
        gui.drawString(Minecraft.getInstance().font, Component.translatable("wyb.compat.mas.used_point", getUsedPoint()).getString(), 0, 0, ChatFormatting.WHITE.getColor());
    }

    public void setPD(PlayerData playerData){
        ((ISkillScreenHelper) this).setPlayerData(playerData);
    }

    public int getUsedPoint(){
        return ((ISkillScreenHelper) this).getPlayerData().talents.getAllocatedPoints(schoolType);
    }

    @Override
    public boolean shouldAlert() {
        return false;
    }

    @Override
    public ResourceLocation iconLocation() {
        return null;
    }

    @Override
    public Words screenName() {
        return null;
    }
}
