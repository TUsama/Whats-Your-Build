//? if neoforge || forge {
package me.clefal.whats_your_build.data.modules.compat.curios;

import me.clefal.whats_your_build.client.screen.IBuildMenuContainerHolder;
import me.clefal.whats_your_build.client.screen.buildscreen.BuildMenuTab;
import me.clefal.whats_your_build.data.handler.ComponentType;
import me.clefal.whats_your_build.data.handler.IBuildComponent;
import me.clefal.whats_your_build.data.handler.IComponentClientHandler;
import me.clefal.whats_your_build.data.modules.compat.curios.menu.CuriosMenuTab;
import me.clefal.whats_your_build.data.modules.compat.curios.menu.WritableCuriosMenuTab;
import me.clefal.whats_your_build.world.IRewritableMenu;

import java.rmi.UnexpectedException;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CuriosClientHandler implements IComponentClientHandler {

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
    public BiFunction<IBuildMenuContainerHolder<?>, IRewritableMenu, BuildMenuTab<?>> getBuildMenuTabFunction(IBuildComponent<?> component) {
        return null;
    }


}
//?}