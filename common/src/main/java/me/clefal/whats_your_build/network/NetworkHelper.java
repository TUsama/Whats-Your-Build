package me.clefal.whats_your_build.network;

import me.clefal.whats_your_build.client.screen.BuildMenuTab;
import me.clefal.whats_your_build.client.screen.PlayerBuildScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

import java.util.List;
import java.util.function.Function;

public class NetworkHelper {
    public static void startPlayerBuildScreen(List<Function<PlayerBuildScreen, BuildMenuTab<?, ?>>> list){
        Screen screen = Minecraft.getInstance().screen;
        if (screen == null){
            Minecraft.getInstance().screen = new PlayerBuildScreen(com.clefal.nirvana_lib.relocated.io.vavr.collection.List.ofAll(list));
        }
    }
}
