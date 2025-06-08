package me.clefal.whats_your_build.data.handler;

import me.clefal.whats_your_build.event.server.ServerAskBuildPermissionCheckEvent;
import me.clefal.whats_your_build.event.server.ServerGatherBuildComponentEvent;

public interface IComponentServerHandler {
    void onGather(ServerGatherBuildComponentEvent event);
    byte getIndex();
}
