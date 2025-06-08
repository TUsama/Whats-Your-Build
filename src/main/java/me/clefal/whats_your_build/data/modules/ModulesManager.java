package me.clefal.whats_your_build.data.modules;

import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.event.server.ServerGatherHandlerEvent;
import me.clefal.whats_your_build.data.modules.armor.VanillaArmorComponentModule;

import java.util.ArrayList;
import java.util.List;

public class ModulesManager {
    private static ModulesManager INSTANCE;
    public List<IModule> modules = new ArrayList<>();

    public static ModulesManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ModulesManager();
        }
        return INSTANCE;
    }

    public static void init(){

    }

    static {
        getInstance().modules.add(VanillaArmorComponentModule.getInstance());
        ServerGatherHandlerEvent serverGatherHandlerEvent = CommonClass.post(new ServerGatherHandlerEvent());
        getInstance().modules.addAll(serverGatherHandlerEvent.modules);

        getInstance().modules.forEach(IModule::tryEnable);
    }
}
