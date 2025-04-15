package me.clefal.whats_your_build.chat;

import com.clefal.nirvana_lib.utils.NetworkUtils;
import me.clefal.whats_your_build.network.c2s.C2SAskBuildPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class BuildClickEventMixinLogic {

    public static boolean whenClick(ClickEvent event) {
        if (event == null) return false;
        if (event instanceof BuildClickEvent buildClickEvent) {
            UUID uuid = UUID.fromString(buildClickEvent.getValue());
            Player playerByUUID = Minecraft.getInstance().level.getPlayerByUUID(uuid);
            if (playerByUUID != null) {
                NetworkUtils.sendToServer(new C2SAskBuildPacket(uuid, true));
            }
        }

        return event instanceof BuildClickEvent;
    }
}
