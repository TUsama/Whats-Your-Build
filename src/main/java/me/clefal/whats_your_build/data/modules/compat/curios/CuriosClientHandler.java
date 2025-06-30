//? if neoforge || forge {
package me.clefal.whats_your_build.data.modules.compat.curios;

import me.clefal.whats_your_build.client.screen.IBuildMenuContainer;
import me.clefal.whats_your_build.client.screen.IBuildMenuContainerHolder;
import me.clefal.whats_your_build.client.screen.buildscreen.BuildMenuTab;
import me.clefal.whats_your_build.data.handler.ComponentType;
import me.clefal.whats_your_build.data.handler.IComponentClientHandler;
import me.clefal.whats_your_build.data.modules.compat.curios.menu.CuriosMenuTab;

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
    public Function<IBuildMenuContainerHolder<?>, BuildMenuTab<?, ?>> getBuildMenuTabFunction(Object component) {
        return holder -> new CuriosMenuTab(((CuriosComponent) component), holder);
    }


}
//?}