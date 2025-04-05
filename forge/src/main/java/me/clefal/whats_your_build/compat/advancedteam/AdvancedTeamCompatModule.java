package me.clefal.whats_your_build.compat.advancedteam;

import me.clefal.whats_your_build.event.server.ServerGatherHandlerEvent;
import me.clefal.whats_your_build.data.modules.ICompatModule;

public class AdvancedTeamCompatModule implements ICompatModule {
    @Override
    public String getModID() {
        return "teams";
    }

    @Override
    public void onRegister(ServerGatherHandlerEvent event) {

    }

    @Override
    public void whenEnable() {

    }
}
