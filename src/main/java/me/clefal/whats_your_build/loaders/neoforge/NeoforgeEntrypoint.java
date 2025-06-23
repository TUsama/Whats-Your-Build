//? if neoforge {
package me.clefal.whats_your_build.loaders.neoforge;

import com.clefal.nirvana_lib.utils.NetworkUtils;
import com.mojang.logging.LogUtils;
import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.Constants;
import me.clefal.whats_your_build.loaders.WhatsYourBuildModulesRegister;
import me.clefal.whats_your_build.network.s2c.S2CAskConfigPacket;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.slf4j.Logger;

@Mod(value = Constants.MOD_ID)
public class NeoforgeEntrypoint {
    private static final Logger LOGGER = LogUtils.getLogger();

    public NeoforgeEntrypoint(IEventBus modBus) {
        WhatsYourBuildModulesRegister.registerModules();
        CommonClass.packetInit();
        CommonClass.serverInit();
        NeoForge.EVENT_BUS.<PlayerEvent.PlayerLoggedInEvent>addListener(x -> {
            if (x.getEntity() instanceof ServerPlayer serverPlayer) NetworkUtils.sendToClient(new S2CAskConfigPacket(), serverPlayer);
        });
    }
}
//?}
