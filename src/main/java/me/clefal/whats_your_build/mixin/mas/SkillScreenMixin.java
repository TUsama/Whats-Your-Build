package me.clefal.whats_your_build.mixin.mas;

import com.robertx22.mine_and_slash.capability.player.PlayerData;
import com.robertx22.mine_and_slash.gui.screens.skill_tree.SkillTreeScreen;
import com.robertx22.mine_and_slash.gui.screens.skill_tree.buttons.PerkButton;
import com.robertx22.mine_and_slash.saveclasses.PointData;
import me.clefal.whats_your_build.data.modules.compat.mas.talent.client.MASNestedSkillScreen;
import me.clefal.whats_your_build.mixinhelper.ISkillScreenHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
@Mixin(value = SkillTreeScreen.class, remap = false)
public abstract class SkillScreenMixin implements ISkillScreenHelper {
    @Shadow private HashMap<PointData, PerkButton> pointPerkButtonMap;

    @Shadow private PlayerData playerData;

    @Override
    public HashMap<PointData, PerkButton> getPointPerkButtonMap() {
        return this.pointPerkButtonMap;
    }

    @Override
    public PlayerData getPlayerData() {
        return this.playerData;
    }

    @Override
    public void setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
    }

    @Inject(
            method = "renderPanels", at = @At(
            value = "HEAD"
    ),
            cancellable = true)
    public void onClickBuildClickEvent(GuiGraphics gui, CallbackInfo ci) {
        if (Minecraft.getInstance().screen instanceof MASNestedSkillScreen) ci.cancel();
    }
}
