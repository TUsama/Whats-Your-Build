package me.clefal.whats_your_build.network;

import com.clefal.nirvana_lib.utils.DevUtils;
import me.clefal.whats_your_build.Constants;
import me.clefal.whats_your_build.client.screen.BaseBuildScreen;
import me.clefal.whats_your_build.client.screen.buildscreen.BuildMenuTab;
import me.clefal.whats_your_build.client.screen.buildscreen.PlayerBuildScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public class NetworkHelper {
    public static void startPlayerBuildScreen(List<Function<BaseBuildScreen, BuildMenuTab<?, ?>>> list, UUID target) {
        Player playerByUUID = Minecraft.getInstance().player.level().getPlayerByUUID(target);
        if (playerByUUID != null){
            Minecraft.getInstance().setScreen(new PlayerBuildScreen(com.clefal.nirvana_lib.relocated.io.vavr.collection.List.ofAll(list), playerByUUID));
        } else if (DevUtils.isInDev()){
            Constants.LOG.debug("open screen in debug mode");
            Minecraft.getInstance().setScreen(new PlayerBuildScreen(com.clefal.nirvana_lib.relocated.io.vavr.collection.List.ofAll(list), Minecraft.getInstance().player));
        }


    }
}
