package me.clefal.whats_your_build.utils;

import com.clefal.nirvana_lib.utils.NetworkUtils;
import me.clefal.whats_your_build.chat.BuildClickEvent;
import me.clefal.whats_your_build.network.c2s.C2SAskBuildPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

public class MixinHelper {

    public static void screenMixin(Style style, CallbackInfoReturnable<Boolean> cir) {
        ClickEvent clickEvent = style.getClickEvent();
        if (clickEvent instanceof BuildClickEvent buildClickEvent) {
            UUID uuid = UUID.fromString(buildClickEvent.getValue());
            Player playerByUUID = Minecraft.getInstance().level.getPlayerByUUID(uuid);
            if (playerByUUID != null) {
                NetworkUtils.sendToServer(new C2SAskBuildPacket(uuid, true));
            }
            cir.setReturnValue(true);
        }
    }
}
