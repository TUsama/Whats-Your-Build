//? if neoforge {
package me.clefal.whats_your_build.loaders.neoforge;

import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.Constants;
import me.clefal.whats_your_build.client.keybind.WYBKeys;
import me.clefal.whats_your_build.client.screen.loadoutscreen.BuildManagementScreen;
import me.clefal.whats_your_build.loaders.WYBMenuType;
import me.clefal.whats_your_build.world.loadout.BuildManagementMenu;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
public class ClientEntryPoint {

    public ClientEntryPoint(IEventBus modBus) {
        CommonClass.clientInit();

        modBus.<RegisterKeyMappingsEvent>addListener(registerKeyMappingsEvent -> WYBKeys.registerAllKey(keyMappings -> keyMappings.forEach(registerKeyMappingsEvent::register)));
        //? if < 1.21.1 {
        /*NeoForge.EVENT_BUS.<ClientTickEvent>addListener(event -> {
            WYBKeys.consumerKeys();
        });
        *///?} else {
        NeoForge.EVENT_BUS.<ClientTickEvent.Post>addListener(event -> {
            WYBKeys.consumerKeys();
        });

        NeoForge.EVENT_BUS.<RegisterMenuScreensEvent>addListener(x -> {
            x.register(WYBMenuType.buildManagementMenuType.get(), (menu, inventory, title) -> new BuildManagementScreen(menu, inventory, ));
        });

        //?}

    }
}
//?}