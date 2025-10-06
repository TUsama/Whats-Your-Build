
package me.clefal.whats_your_build.loaders;

import me.clefal.whats_your_build.CommonClass;
//? curios
import me.clefal.whats_your_build.data.modules.compat.curios.CuriosCompatModule;
//? 1.20.1
/*import me.clefal.whats_your_build.data.modules.compat.forge_1_20_1.advancedteam.AdvancedTeamCompatModule;*/
//? mas
/*import me.clefal.whats_your_build.data.modules.compat.mas.talent.MASCompatModule;*/

public class WhatsYourBuildModulesRegister {
    public static void registerModules(){
        //? forge || neoforge
        CommonClass.registerAtServer(CuriosCompatModule.getInstance());
        //? 1.20.1
        /*CommonClass.registerAtServer(AdvancedTeamCompatModule.getInstance());*/
        //? mas
        /*CommonClass.registerAtServer(MASCompatModule.getInstance());*/
    }
}
