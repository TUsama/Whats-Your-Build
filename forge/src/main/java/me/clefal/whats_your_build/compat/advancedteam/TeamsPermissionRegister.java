package me.clefal.whats_your_build.compat.advancedteam;

import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import me.clefal.whats_your_build.event.client.ClientAddConfigChoiceEvent;

public class TeamsPermissionRegister {
    public static final TeamsPermissionRegister INSTANCE = new TeamsPermissionRegister();

    @SubscribeEvent
    public void onRegister(ClientAddConfigChoiceEvent event){
        System.out.println("register!");
        event.configs.add("teammates");
    }
}
