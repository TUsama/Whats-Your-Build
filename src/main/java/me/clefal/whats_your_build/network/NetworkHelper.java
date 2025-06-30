package me.clefal.whats_your_build.network;

import com.clefal.nirvana_lib.utils.DevUtils;
import me.clefal.whats_your_build.Constants;
import me.clefal.whats_your_build.client.screen.buildscreen.PlayerBuildScreen;
import me.clefal.whats_your_build.data.buildobject.Build;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class NetworkHelper {
    public static void startPlayerBuildScreen(Build build, UUID target) {
        Player playerByUUID = Minecraft.getInstance().player.level().getPlayerByUUID(target);
        if (playerByUUID != null) {
            Minecraft.getInstance().setScreen(new PlayerBuildScreen(build, playerByUUID));
        } else if (DevUtils.isInDev()) {
            Constants.LOG.debug("open screen in debug mode");
            Minecraft.getInstance().setScreen(new PlayerBuildScreen(build, Minecraft.getInstance().player));
        }


    }
}
