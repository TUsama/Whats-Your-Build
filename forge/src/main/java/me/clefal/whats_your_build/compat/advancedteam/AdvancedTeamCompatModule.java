package me.clefal.whats_your_build.compat.advancedteam;

import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import com.clefal.nirvana_lib.utils.SideUtils;
import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.Constants;
import me.clefal.whats_your_build.data.modules.ICompatModule;
import me.clefal.whats_your_build.event.server.ServerGatherHandlerEvent;

public class AdvancedTeamCompatModule implements ICompatModule {
    private static AdvancedTeamCompatModule INSTANCE;

    public static AdvancedTeamCompatModule getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AdvancedTeamCompatModule();
        }
        return INSTANCE;
    }

    @Override
    public String getModID() {
        return "teams";
    }

    @Override
    @SubscribeEvent
    public void onRegister(ServerGatherHandlerEvent event) {
        event.modules.add(this);
    }

    @Override
    public void whenEnable() {
        Constants.LOG.info("enable AdvancedTeamCompatModule");
        CommonClass.registerAtServer(TeamsPermissionChecker.INSTANCE);
        if (SideUtils.isClient()) {
            CommonClass.registerAtClient(TeamsPermissionRegister.INSTANCE);
        }
    }
}
