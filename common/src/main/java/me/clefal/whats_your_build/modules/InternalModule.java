package me.clefal.whats_your_build.modules;

public abstract class InternalModule implements IModule{

    @Override
    public boolean shouldEnable() {
        return true;
    }
}
