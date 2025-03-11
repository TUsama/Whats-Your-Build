package me.clefal.whats_your_build.modules.armor;

import me.clefal.whats_your_build.config.WYBServerConfig;
import me.clefal.whats_your_build.handler.HandlerManager;
import me.clefal.whats_your_build.modules.InternalModule;

public class VanillaArmorComponentModule extends InternalModule {
    public static final VanillaArmorComponentModule INSTANCE = new VanillaArmorComponentModule();

    @Override
    public boolean shouldEnable() {
        return WYBServerConfig.config.enableArmor;
    }

    @Override
    public void whenEnable() {
        HandlerManager instance = HandlerManager.INSTANCE;
        instance.clientHandlers.add(VanillaArmorComponentClientHandler.getInstance().getIndex(), VanillaArmorComponentClientHandler.getInstance());
        instance.serverHandlers.add(VanillaArmorComponentServerHandler.getInstance().getIndex(), VanillaArmorComponentServerHandler.getInstance());

    }
}
