package me.clefal.whats_your_build.config;

import me.clefal.whats_your_build.CommonClass;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import me.fzzyhmstrs.fzzy_config.config.Config;

public class WYBClientConfig extends Config {
    public static WYBClientConfig config = ConfigApiJava.registerAndLoadConfig(WYBClientConfig::new, RegisterType.CLIENT);

    public WYBClientConfig() {
        super(CommonClass.id("wyb_client_config"));
    }

    public float global_scale = 1.3f;

    public static void init() {

    }
}
