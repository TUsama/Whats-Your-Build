package me.clefal.whats_your_build.modules;

import me.clefal.whats_your_build.modules.armor.VanillaArmorComponentModule;

import java.util.ArrayList;
import java.util.List;

public class ModulesManager {
    public static final ModulesManager INSTANCE = new ModulesManager();
    public List<IModule> modules = new ArrayList<>();

    public static void init(){

    }

    static {
        INSTANCE.modules.add(VanillaArmorComponentModule.INSTANCE);


        INSTANCE.modules.forEach(IModule::tryEnable);
    }
}
