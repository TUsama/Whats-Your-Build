package me.clefal.whats_your_build.modules;

import me.clefal.whats_your_build.modules.armor.VanillaArmorComponentModule;

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


        getInstance().modules.forEach(IModule::tryEnable);
    }
}
