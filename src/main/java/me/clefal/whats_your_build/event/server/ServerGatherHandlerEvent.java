package me.clefal.whats_your_build.event.server;

import me.clefal.whats_your_build.data.modules.IModule;

import java.util.ArrayList;
import java.util.List;

public class ServerGatherHandlerEvent extends ServerEvent {
    public final List<IModule> modules = new ArrayList<>();
}
