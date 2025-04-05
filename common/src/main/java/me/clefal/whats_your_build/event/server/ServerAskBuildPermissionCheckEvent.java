package me.clefal.whats_your_build.event.server;

import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.ICancellableEvent;
import net.minecraft.server.level.ServerPlayer;

public class ServerAskBuildPermissionCheckEvent extends ServerEvent implements ICancellableEvent {
    public final ServerPlayer beAsked;
    public final ServerPlayer asker;
    public boolean isAllowed = false;

    public ServerAskBuildPermissionCheckEvent(ServerPlayer beAsked, ServerPlayer asker) {
        this.beAsked = beAsked;
        this.asker = asker;
    }

}
