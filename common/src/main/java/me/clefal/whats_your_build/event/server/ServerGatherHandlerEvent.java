package me.clefal.whats_your_build.event.server;

import me.clefal.whats_your_build.modules.IModule;

import java.util.ArrayList;
import java.util.List;

public class ServerGatherHandlerEvent extends ServerEvent {
    public final List<IModule> modules = new ArrayList<>();
}
