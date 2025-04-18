package me.clefal.whats_your_build.compat.advancedteam;

import com.clefal.nirvana_lib.config.ConfigValue;
import com.clefal.nirvana_lib.config.PersonalConfigData;
import com.clefal.nirvana_lib.config.StringListValue;
import com.clefal.nirvana_lib.config.SyncingPersonalConfig;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import com.clefal.teams.server.ATServerTeam;
import com.clefal.teams.server.ATServerTeamData;
import com.clefal.teams.server.IHasTeam;
import me.clefal.whats_your_build.event.server.ServerAskBuildPermissionCheckEvent;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;

public class TeamsPermissionChecker {
    public static final TeamsPermissionChecker INSTANCE = new TeamsPermissionChecker();

    @SubscribeEvent
    public void onCheck(ServerAskBuildPermissionCheckEvent event) {
        ServerPlayer beAsked = event.beAsked;
        PersonalConfigData data = SyncingPersonalConfig.INSTANCE.getData(beAsked.getUUID());
        if (data != null) {
            ConfigValue<List<String>> showYourBuildFor = ((StringListValue) data.configMap.get("showYourBuildFor"));
            if (showYourBuildFor != null) {
                List<String> value = showYourBuildFor.value;
                if (value.contains("teammates")) {
                    var asker = event.asker;
                    if (((IHasTeam) beAsked).hasTeam() && ((IHasTeam) asker).hasTeam() && ((IHasTeam) beAsked).getTeam().equals(((IHasTeam) asker).getTeam())) {
                        event.isAllowed = true;
                    }
                }
            }

        }
    }
}
