package me.clefal.whats_your_build.data.modules;

public abstract class InternalModule implements IModule{

    @Override
    public boolean shouldEnable() {
        return true;
    }
}
