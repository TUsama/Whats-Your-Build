//? mas {
/*package me.clefal.whats_your_build.data.modules.compat.mas.talent.client;

import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import com.robertx22.mine_and_slash.capability.player.PlayerData;
import com.robertx22.mine_and_slash.database.data.talent_tree.TalentTree;
import com.robertx22.mine_and_slash.saveclasses.perks.SchoolData;
import me.clefal.whats_your_build.client.screen.loadoutscreen.LoadoutScreen;
import me.clefal.whats_your_build.data.modules.compat.mas.talent.MASSkillComponent;
import me.clefal.whats_your_build.mixinhelper.ITalentDataGetter;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class MASSkillViewButton extends PlainTextButton {

    @Nullable
    public PlayerData data;

    public MASSkillViewButton(TalentTree.SchoolType type, OnPress onPress) {
        super(0, 0, 16, 8, (type.equals(TalentTree.SchoolType.TALENTS) ? Component.translatable("wyb.compat.mas.view_talent") : Component.translatable("wyb.compat.mas.view_asc")).withStyle(ChatFormatting.WHITE), onPress, Minecraft.getInstance().font);
        setWidth(Minecraft.getInstance().font.width(getMessage().getString()));
    }


    @NotNull
    public static List<MASSkillViewButton> getViewButtonInLoadoutScreen(LoadoutScreen screen, MASSkillComponent iBuildComponent) {
        Map<TalentTree.SchoolType, SchoolData> sharedPerks = iBuildComponent.getPerks();
        PlayerData playerData = new PlayerData(Minecraft.getInstance().player);
        HashMap<TalentTree.SchoolType, SchoolData> perks = ((ITalentDataGetter) playerData.talents).getPerks();
        perks.clear();
        perks.putAll(sharedPerks);
        return List.of(TalentTree.SchoolType.values())
                .map(schoolType -> {
                    MASSkillViewButton masSkillViewButton = new MASSkillViewButton(schoolType, button -> {
                        MASNestedSkillScreen masNestedSkillScreen = new MASNestedSkillScreen(schoolType, screen, true);
                        masNestedSkillScreen.setPD(playerData);
                        Minecraft.getInstance().setScreen(masNestedSkillScreen);
                    });
                    masSkillViewButton.data = playerData;
                    return masSkillViewButton;
                });
    }

    @NotNull
    public static MASSkillViewButton getViewButton(TalentTree.SchoolType schoolType, Screen screen, MASSkillComponent iBuildComponent) {
        return getViewButton(schoolType, screen, iBuildComponent, false);
    }

    @NotNull
    public static MASSkillViewButton getViewButton(TalentTree.SchoolType schoolType, Screen screen, MASSkillComponent iBuildComponent, boolean editable) {
        PlayerData playerData = new PlayerData(Minecraft.getInstance().player);
        var masSkillViewButton =  new MASSkillViewButton(schoolType, button -> {
            MASNestedSkillScreen masNestedSkillScreen = new MASNestedSkillScreen(schoolType, screen, editable);


            HashMap<TalentTree.SchoolType, SchoolData> perks = ((ITalentDataGetter) playerData.talents).getPerks();
            perks.clear();
            perks.putAll(iBuildComponent.getPerks());

            masNestedSkillScreen.setPD(playerData);
            Minecraft.getInstance().setScreen(masNestedSkillScreen);
        });
        masSkillViewButton.data = playerData;
        return masSkillViewButton;
    }
}
*///?}