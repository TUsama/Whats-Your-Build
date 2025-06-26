package me.clefal.whats_your_build.event.server;

import com.google.common.collect.ImmutableList;
import me.clefal.whats_your_build.data.buildobject.Build;
import me.clefal.whats_your_build.data.handler.IBuildComponent;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.List;

public class ServerGatherBuildComponentEvent extends ServerEvent {
    public final ServerPlayer target;
    private final List<IBuildComponent<?>> components = new ArrayList<>();

    public ServerGatherBuildComponentEvent(ServerPlayer target) {
        this.target = target;
    }

    public void addComponent(IBuildComponent<?> component){
        this.components.add(component);
    }

    public Build getResultBuild(){
        return new Build(com.clefal.nirvana_lib.relocated.io.vavr.collection.List.ofAll(components), "current_build");
    }

}
