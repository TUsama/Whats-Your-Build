package me.clefal.whats_your_build.compat.curios;

import com.mojang.serialization.Codec;
import me.clefal.whats_your_build.client.screen.BuildMenuTab;
import me.clefal.whats_your_build.client.screen.PlayerBuildScreen;
import me.clefal.whats_your_build.compat.curios.menu.CuriosMenuTab;
import me.clefal.whats_your_build.data.handler.ComponentType;
import me.clefal.whats_your_build.data.handler.IComponentClientHandler;

import java.util.function.Function;

public class CuriosClientHandler implements IComponentClientHandler<CuriosComponent> {

    private static CuriosClientHandler INSTANCE;

    public static CuriosClientHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CuriosClientHandler();
        }
        return INSTANCE;
    }
    @Override
    public byte getIndex() {
        return ComponentType.CURIOS;
    }

    @Override
    public Function<PlayerBuildScreen, BuildMenuTab<?, ?>> getBuildMenuTabFunction(Object component) {
        return screen -> new CuriosMenuTab(((CuriosComponent) component), screen);
    }

    @Override
    public Codec<CuriosComponent> getCodeC() {
        return CuriosComponent.CODEC;
    }
}
