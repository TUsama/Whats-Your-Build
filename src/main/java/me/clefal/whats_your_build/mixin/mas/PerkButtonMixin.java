package me.clefal.whats_your_build.mixin.mas;

import com.robertx22.mine_and_slash.capability.player.PlayerData;
import com.robertx22.mine_and_slash.database.data.talent_tree.TalentTree;
import com.robertx22.mine_and_slash.gui.screens.skill_tree.SkillTreeScreen;
import com.robertx22.mine_and_slash.gui.screens.skill_tree.buttons.PerkButton;
import com.robertx22.mine_and_slash.saveclasses.PointData;
import me.clefal.whats_your_build.data.modules.compat.mas.talent.client.MASNestedSkillScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PerkButton.class, remap = false)
public abstract class PerkButtonMixin extends AbstractWidget{

    @Shadow private SkillTreeScreen screen;

    @Shadow public PointData point;

    @Shadow public PlayerData playerData;

    @Shadow public TalentTree school;

    public PerkButtonMixin(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
    }


    @Inject(
            method = "mouseClicked", at = @At(
            value = "HEAD"
    ),
            cancellable = true)
    public void onClickBuildClickEvent(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (Minecraft.getInstance().screen instanceof MASNestedSkillScreen masNestedSkillScreen) {
            if (masNestedSkillScreen.editable){
                this.screen.mouseRecentlyClickedTicks = 25;
                this.screen.pointClicked = this.point;
                mouseX = (double)(1.0F / this.screen.zoom) * mouseX;
                mouseY = (double)(1.0F / this.screen.zoom) * mouseY;
                if (this.active && this.visible) {
                    boolean bl = this.clicked(mouseX, mouseY);
                    if (bl) {
                        this.playDownSound(Minecraft.getInstance().getSoundManager());
                        if (button == 0) {
                            this.playerData.talents.allocate(Minecraft.getInstance().player, this.school, point);
                        }

                        if (button == 1) {
                            this.playerData.talents.remove(this.school.getSchool_type(), point);
                        }

                        this.onClick(mouseX, mouseY);
                        cir.setReturnValue(true);
                    } else {
                        cir.setReturnValue(false);
                    }
                } else {
                    cir.setReturnValue(false);
                }
            } else {
                cir.setReturnValue(false);
            }
        }

    }
}
