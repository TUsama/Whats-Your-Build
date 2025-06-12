
package me.clefal.whats_your_build.loaders;

import me.clefal.whats_your_build.CommonClass;
//? forge || neoforge
import me.clefal.whats_your_build.data.modules.compat.curios.CuriosCompatModule;
import me.clefal.whats_your_build.data.modules.compat.forge_1_20_1.advancedteam.AdvancedTeamCompatModule;

public class WhatsYourBuildModulesRegister {
    public static void registerModules(){
        //? forge || neoforge
        CommonClass.registerAtServer(CuriosCompatModule.getInstance());
        CommonClass.registerAtServer(AdvancedTeamCompatModule.getInstance());
    }
}
