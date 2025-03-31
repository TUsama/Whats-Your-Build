package me.clefal.whats_your_build.modules;

import com.clefal.nirvana_lib.platform.Services;

public interface ICompatModule extends IModule{
    String getModID();

    @Override
    default boolean shouldEnable(){
        return Services.PLATFORM.isModLoaded(getModID());
    }
}
