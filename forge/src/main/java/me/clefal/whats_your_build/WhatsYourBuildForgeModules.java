package me.clefal.whats_your_build;

import me.clefal.whats_your_build.compat.advancedteam.AdvancedTeamCompatModule;
import me.clefal.whats_your_build.compat.curios.CuriosCompatModule;

public class WhatsYourBuildForgeModules {
    public static void registerModules(){
        CommonClass.registerAtServer(CuriosCompatModule.getInstance());
        CommonClass.registerAtServer(AdvancedTeamCompatModule.getInstance());
    }
}
