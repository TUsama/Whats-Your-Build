package me.clefal.whats_your_build.mixin;

import me.clefal.whats_your_build.utils.MixinHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//? 1.20.1
/*@Mixin(value = Screen.class)*/
//? >1.20.1
@Mixin(value = Screen.class, remap = false)
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
