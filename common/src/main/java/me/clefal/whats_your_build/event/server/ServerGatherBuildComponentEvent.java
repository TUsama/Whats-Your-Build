package me.clefal.whats_your_build.event.server;

import me.clefal.whats_your_build.handler.IBuildComponent;
import net.minecraft.server.level.ServerPlayer;

import javax.swing.plaf.PanelUI;
import java.util.ArrayList;
import java.util.List;

public class ServerGatherBuildComponentEvent extends ServerEvent {
    public final ServerPlayer target;
    public final List<IBuildComponent<?>> components = new ArrayList<>();

    public ServerGatherBuildComponentEvent(ServerPlayer target) {
        this.target = target;
    }
}
