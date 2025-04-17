package me.clefal.whats_your_build.mixin;

import com.clefal.nirvana_lib.utils.NetworkUtils;
import me.clefal.whats_your_build.chat.BuildClickEvent;
import me.clefal.whats_your_build.network.c2s.C2SAskBuildPacket;
import me.clefal.whats_your_build.utils.MixinHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(value = Screen.class)
public class ScreenMixin {
    @Inject(
            method = "handleComponentClicked", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/network/chat/ClickEvent;getAction()Lnet/minecraft/network/chat/ClickEvent$Action;"
    ),
            cancellable = true)
    public void onClickBuildClickEvent(Style style, CallbackInfoReturnable<Boolean> cir) {
        MixinHelper.screenMixin(style, cir);
    }


}
