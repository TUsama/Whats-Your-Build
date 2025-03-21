package me.clefal.whats_your_build.modules.armor;

import com.clefal.nirvana_lib.utils.SideUtils;
import me.clefal.whats_your_build.config.WYBServerConfig;
import me.clefal.whats_your_build.handler.HandlerManager;
import me.clefal.whats_your_build.modules.InternalModule;

public class VanillaArmorComponentModule extends InternalModule {
    private static VanillaArmorComponentModule INSTANCE;

    public static VanillaArmorComponentModule getInstance() {
        if (INSTANCE == null) INSTANCE = new VanillaArmorComponentModule();
        return INSTANCE;
    }
    @Override
    public boolean shouldEnable() {
        return WYBServerConfig.config.enableArmor;
    }

    @Override
    public void whenEnable() {
        HandlerManager instance = HandlerManager.getInstance();
        if (SideUtils.isClient()) {
            instance.clientHandlers.add(VanillaArmorComponentClientHandler.getInstance().getIndex(), VanillaArmorComponentClientHandler.getInstance());
        }
        instance.serverHandlers.add(VanillaArmorComponentServerHandler.getInstance().getIndex(), VanillaArmorComponentServerHandler.getInstance());

    }
}
