//? if fabric {
/*package me.clefal.whats_your_build.loaders.fabric;

import com.clefal.nirvana_lib.utils.NetworkUtils;
import com.mojang.logging.LogUtils;
import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.client.keybind.WYBKeys;
import me.clefal.whats_your_build.loaders.WhatsYourBuildModulesRegister;
import me.clefal.whats_your_build.network.s2c.S2CAskConfigPacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.client.KeyMapping;
import net.minecraft.server.level.ServerPlayer;
import org.slf4j.Logger;

public class FabricEntrypoint implements ModInitializer, ClientModInitializer {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        WhatsYourBuildModulesRegister.registerModules();
        CommonClass.serverInit();
        CommonClass.packetInit();
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayer player = handler.player;
            NetworkUtils.sendToClient(new S2CAskConfigPacket(), player);
        });

        WYBRegistrate.register();
    }

    @Override
    public void onInitializeClient() {
        CommonClass.clientInit();
        WYBKeys.registerAllKey(keyMappings -> {
            for (KeyMapping keyMapping : keyMappings) {
                KeyBindingHelper.registerKeyBinding(keyMapping);
            }
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> WYBKeys.consumerKeys());


    }
}
*///?}
