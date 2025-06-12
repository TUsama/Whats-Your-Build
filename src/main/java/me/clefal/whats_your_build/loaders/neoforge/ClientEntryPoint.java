//? if neoforge {
package me.clefal.whats_your_build.loaders.neoforge;

import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.Constants;
import me.clefal.whats_your_build.client.keybind.WYBKeys;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
public class ClientEntryPoint {

    public ClientEntryPoint(IEventBus modBus) {
        CommonClass.clientInit();

        modBus.<RegisterKeyMappingsEvent>addListener(registerKeyMappingsEvent -> WYBKeys.registerAllKey(keyMappings -> keyMappings.forEach(registerKeyMappingsEvent::register)));

        NeoForge.EVENT_BUS.<ClientTickEvent>addListener(event -> {
            WYBKeys.consumerKeys();
        });

    }
}
//?}