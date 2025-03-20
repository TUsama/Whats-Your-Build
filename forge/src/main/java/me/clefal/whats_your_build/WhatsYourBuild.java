package me.clefal.whats_your_build;

import me.clefal.whats_your_build.client.keybind.WYBKeys;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Arrays;

@Mod(Constants.MOD_ID)
public class WhatsYourBuild {
    
    public WhatsYourBuild() {
        CommonClass.init();
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> (new DistExecutor.SafeRunnable() {
            @Override
            public void run() {
                CommonClass.clientInit();
            }
        }));
        CommonClass.serverInit();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.<RegisterKeyMappingsEvent>addListener(registerKeyMappingsEvent -> WYBKeys.registerAllKey(keyMappings -> Arrays.stream(keyMappings).forEach(registerKeyMappingsEvent::register)));

        IEventBus eventBus = MinecraftForge.EVENT_BUS;
        eventBus.<TickEvent.ClientTickEvent>addListener(event -> {
            WYBKeys.consumerKeys();
        });

    }
}