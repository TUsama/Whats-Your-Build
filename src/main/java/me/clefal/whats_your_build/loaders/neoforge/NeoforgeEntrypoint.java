//? if neoforge {
package me.clefal.whats_your_build.loaders.neoforge;

import com.mojang.logging.LogUtils;
import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.Constants;
import me.clefal.whats_your_build.loaders.WhatsYourBuildModulesRegister;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(value = Constants.MOD_ID)
public class NeoforgeEntrypoint {
    private static final Logger LOGGER = LogUtils.getLogger();

    public NeoforgeEntrypoint(IEventBus modBus) {
        WhatsYourBuildModulesRegister.registerModules();
        CommonClass.packetInit();
    }
}
//?}
