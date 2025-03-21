package me.clefal.whats_your_build;

import me.clefal.whats_your_build.client.keybind.WYBKeys;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.Arrays;

public class WhatsYourBuildForgeClient {

    public static void eventInit(IEventBus modBus, IEventBus forgeBus){
        modBus.<RegisterKeyMappingsEvent>addListener(registerKeyMappingsEvent -> WYBKeys.registerAllKey(keyMappings -> keyMappings.forEach(registerKeyMappingsEvent::register)));

        forgeBus.<TickEvent.ClientTickEvent>addListener(event -> {
            WYBKeys.consumerKeys();
        });
    }
}
