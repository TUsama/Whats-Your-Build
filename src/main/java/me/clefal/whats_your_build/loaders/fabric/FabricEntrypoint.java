//? if fabric {
/*package me.clefal.whats_your_build.loaders.fabric;

import com.mojang.logging.LogUtils;
import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.client.keybind.WYBKeys;
import me.clefal.whats_your_build.loaders.WhatsYourBuildModulesRegister;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.slf4j.Logger;

public class FabricEntrypoint implements ModInitializer, ClientModInitializer {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        WhatsYourBuildModulesRegister.registerModules();
        CommonClass.serverInit();
        CommonClass.packetInit();
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
