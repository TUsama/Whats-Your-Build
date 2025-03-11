package me.clefal.whats_your_build;

import com.clefal.nirvana_lib.NirvanaLibConstants;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.Event;
import me.clefal.whats_your_build.event.client.ClientEvent;
import me.clefal.whats_your_build.event.server.ServerEvent;
import net.minecraft.resources.ResourceLocation;


public class CommonClass {

    public static void init() {

    }

    public static <T extends Event> T post(T t) {
        if (t instanceof ServerEvent) {
            Constants.serverBus.post(t);
        } else if (t instanceof ClientEvent) {
            Constants.clientBus.post(t);
        }
        return t;
    }

    public static void registerAtClient(Object o) {
        Constants.clientBus.register(o);
    }

    public static void registerAtServer(Object o) {
        Constants.serverBus.register(o);
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(Constants.MOD_ID, path);
    }
}