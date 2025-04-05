package me.clefal.whats_your_build;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class WhatsYourBuild {

    public WhatsYourBuild() {
        WhatsYourBuildForgeModules.registerModules();

        CommonClass.serverInit();
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> new DistExecutor.SafeRunnable() {
            @Override
            public void run() {
                CommonClass.clientInit();
                IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
                IEventBus eventBus = MinecraftForge.EVENT_BUS;
                WhatsYourBuildForgeClient.clientEventInit(modEventBus, eventBus);
            }
        });

        CommonClass.packetInit();


    }
}