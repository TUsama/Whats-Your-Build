//? if forge {

/*package me.clefal.whats_your_build.loaders.forge;

import com.clefal.nirvana_lib.utils.NetworkUtils;
import com.mojang.logging.LogUtils;
import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.Constants;
import me.clefal.whats_your_build.loaders.WYBRegistrate;
import me.clefal.whats_your_build.loaders.WhatsYourBuildModulesRegister;
import me.clefal.whats_your_build.network.s2c.S2CAskConfigPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
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
        MinecraftForge.EVENT_BUS.<PlayerEvent.PlayerLoggedInEvent>addListener(x -> {
            if (x.getEntity() instanceof ServerPlayer serverPlayer) NetworkUtils.sendToClient(new S2CAskConfigPacket(), serverPlayer);
        });

        WYBRegistrate.register();
    }
}
*///?}
