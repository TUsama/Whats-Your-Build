package me.clefal.whats_your_build.mixinhelper;

import com.robertx22.mine_and_slash.capability.player.PlayerData;
import com.robertx22.mine_and_slash.gui.screens.skill_tree.buttons.PerkButton;
import com.robertx22.mine_and_slash.saveclasses.PointData;

import java.util.HashMap;

public interface ISkillScreenHelper {
    HashMap<PointData, PerkButton> getPointPerkButtonMap();
    void setPlayerData(PlayerData playerData);
    PlayerData getPlayerData();
}
