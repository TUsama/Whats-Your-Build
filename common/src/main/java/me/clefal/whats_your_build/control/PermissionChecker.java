package me.clefal.whats_your_build.control;

import com.clefal.nirvana_lib.config.ConfigValue;
import com.clefal.nirvana_lib.config.PersonalConfigData;
import com.clefal.nirvana_lib.config.StringListValue;
import com.clefal.nirvana_lib.config.SyncingPersonalConfig;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.IEventBus;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import me.clefal.whats_your_build.event.server.ServerAskBuildPermissionCheckEvent;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;

public class PermissionChecker {
    public static final PermissionChecker INSTANCE = new PermissionChecker();

    public static void registerPermissionChecker(IEventBus bus){
        bus.register(INSTANCE);
    }

    @SubscribeEvent
    public void onCheck(ServerAskBuildPermissionCheckEvent event){
        ServerPlayer beAsked = event.beAsked;
        PersonalConfigData data = SyncingPersonalConfig.INSTANCE.getData(beAsked.getUUID(), beAsked.getServer().overworld());
        if (data != null){
            ConfigValue<List<String>> showYourBuildFor = ((StringListValue) data.configMap.get("showYourBuildFor"));
            if (showYourBuildFor != null){
                List<String> value = showYourBuildFor.value;
                if (value.contains("all")){
                    event.isAllowed = true;
                    event.setCanceled(true);
                } else if (value.contains("no_one")){
                    event.isAllowed = false;
                    event.setCanceled(true);
                }
            }

        }
    }
}
