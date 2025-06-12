package me.clefal.whats_your_build.data.modules;

import com.clefal.nirvana_lib.utils.ModUtils;
import me.clefal.whats_your_build.event.server.ServerGatherHandlerEvent;

public interface ICompatModule extends IModule{
    String getModID();
    void onRegister(ServerGatherHandlerEvent event);

    @Override
    default boolean shouldEnable(){
        return ModUtils.isModLoaded(getModID());
    }
}
