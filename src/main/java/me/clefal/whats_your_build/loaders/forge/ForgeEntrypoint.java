//? if forge {

/*package me.clefal.whats_your_build.loaders.forge;

import com.mojang.logging.LogUtils;
import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.Constants;
import me.clefal.whats_your_build.loaders.WhatsYourBuildModulesRegister;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Constants.MOD_ID)
public class ForgeEntrypoint {
    private static final Logger LOGGER = LogUtils.getLogger();

    public ForgeEntrypoint() {
        WhatsYourBuildModulesRegister.registerModules();

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
*///?}
