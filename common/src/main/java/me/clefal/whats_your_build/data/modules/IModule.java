package me.clefal.whats_your_build.data.modules;

public interface IModule {
    boolean shouldEnable();
    void whenEnable();
    default void tryEnable(){
        if (shouldEnable()){
            whenEnable();
        }
    }
}
