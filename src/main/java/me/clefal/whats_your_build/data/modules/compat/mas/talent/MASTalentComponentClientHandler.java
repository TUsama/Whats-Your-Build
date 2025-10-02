//? mas {
/*package me.clefal.whats_your_build.data.modules.compat.mas.talent;

import me.clefal.whats_your_build.client.screen.WYBScreen;
import me.clefal.whats_your_build.client.screen.buildscreen.BuildMenuTab;
import me.clefal.whats_your_build.data.handler.ComponentType;
import me.clefal.whats_your_build.data.handler.IBuildComponent;
import me.clefal.whats_your_build.data.handler.IComponentClientHandler;
import me.clefal.whats_your_build.data.modules.compat.mas.talent.client.MASTalentTab;
import me.clefal.whats_your_build.world.IRewritable;

import java.util.function.BiFunction;

public class MASTalentComponentClientHandler implements IComponentClientHandler{
    @Override
    public byte getIndex() {
        return ComponentType.MAS_TALENT;
    }

    @Override
    public BiFunction<WYBScreen<?>, IRewritable, BuildMenuTab<?>> getBuildMenuTabFunction(IBuildComponent<?> component) {
        return (screen, iRewritable) -> new MASTalentTab(((MASTalentComponent) component), screen, iRewritable);
    }
}
*///?}