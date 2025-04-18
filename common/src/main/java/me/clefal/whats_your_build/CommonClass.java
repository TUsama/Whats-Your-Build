package me.clefal.whats_your_build;

import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.Event;
import me.clefal.whats_your_build.config.WYBClientConfig;
import me.clefal.whats_your_build.config.WYBServerConfig;
import me.clefal.whats_your_build.control.PermissionChecker;
import me.clefal.whats_your_build.event.client.ClientEvent;
import me.clefal.whats_your_build.event.server.ServerEvent;
import me.clefal.whats_your_build.data.handler.HandlerManager;
import me.clefal.whats_your_build.network.Packets;
import net.minecraft.resources.ResourceLocation;


public class CommonClass {

    public static void packetInit() {
        Packets.registerAllC2SPackets();
        Packets.registerAllS2CPackets();
    }

    public static void clientInit() {
        WYBServerConfig.init();
        WYBClientConfig.init();
        /*for (IComponentClientHandler<?> clientHandler : HandlerManager.INSTANCE.clientHandlers) {
            Constants.clientBus.register(clientHandler);
        }*/
    }

    public static void serverInit() {
        WYBServerConfig.init();
        HandlerManager.getInstance().forServerHandlers().forEachRemaining(Constants.serverBus::register);
        PermissionChecker.registerPermissionChecker(Constants.serverBus);
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