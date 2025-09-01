//? if neoforge || forge {
package me.clefal.whats_your_build.data.modules.compat.curios;

import me.clefal.whats_your_build.client.screen.WYBScreen;
import me.clefal.whats_your_build.client.screen.buildscreen.BuildMenuTab;
import me.clefal.whats_your_build.data.handler.ComponentType;
import me.clefal.whats_your_build.data.handler.IBuildComponent;
import me.clefal.whats_your_build.data.handler.IComponentClientHandler;
import me.clefal.whats_your_build.data.modules.compat.curios.menu.CuriosMenuTab;
import me.clefal.whats_your_build.world.IRewritableMenu;

import java.util.function.BiFunction;

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
    public BiFunction<WYBScreen<?>, IRewritableMenu, BuildMenuTab<?>> getBuildMenuTabFunction(IBuildComponent<?> component) {
        return (x, y) -> new CuriosMenuTab((CuriosComponent) component, x, y);
    }


}
//?}