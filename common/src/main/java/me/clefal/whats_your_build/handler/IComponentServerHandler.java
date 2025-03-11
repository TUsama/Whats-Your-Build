package me.clefal.whats_your_build.handler;

import me.clefal.whats_your_build.event.server.ServerGatherBuildComponentEvent;

public interface IComponentServerHandler {
    void onGather(ServerGatherBuildComponentEvent event);
    byte getIndex();
}
