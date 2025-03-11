package me.clefal.whats_your_build.config;

import me.clefal.whats_your_build.CommonClass;
import me.fzzyhmstrs.fzzy_config.annotations.Action;
import me.fzzyhmstrs.fzzy_config.annotations.RequiresAction;
import me.fzzyhmstrs.fzzy_config.annotations.WithPerms;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import me.fzzyhmstrs.fzzy_config.config.Config;
@WithPerms(opLevel = 2)
public class WYBServerConfig extends Config {
    public static WYBServerConfig config = ConfigApiJava.registerAndLoadConfig(WYBServerConfig::new, RegisterType.BOTH);
    public WYBServerConfig() {
        super(CommonClass.id("wyb_server_config"));
    }
    @RequiresAction(action = Action.RESTART)
    public boolean enableArmor = true;
}
