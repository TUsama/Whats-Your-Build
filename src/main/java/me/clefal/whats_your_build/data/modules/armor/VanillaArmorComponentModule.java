package me.clefal.whats_your_build.data.modules.armor;

import me.clefal.whats_your_build.config.WYBServerConfig;
import me.clefal.whats_your_build.data.handler.HandlerManager;
import me.clefal.whats_your_build.data.handler.IComponentClientHandler;
import me.clefal.whats_your_build.data.modules.InternalModule;

import java.util.function.Supplier;

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
        instance.addHandlers(VanillaArmorComponentServerHandler.getInstance(), new HandlerManager.safeInvoker() {
            @Override
            public Supplier<IComponentClientHandler<?>> get() {
                return VanillaArmorComponentClientHandler::getInstance;
            }
        });

    }
}
