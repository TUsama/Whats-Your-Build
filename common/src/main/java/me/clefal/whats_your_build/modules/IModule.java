package me.clefal.whats_your_build.modules;

public interface IModule {
    boolean shouldEnable();
    void whenEnable();
    default void tryEnable(){
        if (shouldEnable()){
            whenEnable();
        }
    }
}
