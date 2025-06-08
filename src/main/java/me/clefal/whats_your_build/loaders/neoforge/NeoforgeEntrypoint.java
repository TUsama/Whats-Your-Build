package me.clefal.whats_your_build.loaders.neoforge;//? if neoforge {

import com.mojang.logging.LogUtils;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod("examplemod")
public class NeoforgeEntrypoint {
    private static final Logger LOGGER = LogUtils.getLogger();

    public NeoforgeEntrypoint() {
        LOGGER.info("Hello from NeoforgeEntrypoint!");
    }
}
//?}
