package me.clefal.whats_your_build.data.handler;

import com.clefal.nirvana_lib.relocated.io.vavr.API;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.Iterator;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.Map;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.Seq;
import com.clefal.nirvana_lib.utils.SideUtils;
import me.clefal.whats_your_build.client.screen.WYBScreen;
import me.clefal.whats_your_build.client.screen.buildscreen.BuildMenuTab;
import me.clefal.whats_your_build.data.buildobject.Build;
import me.clefal.whats_your_build.data.modules.ModulesManager;
import me.clefal.whats_your_build.world.IRewritable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class HandlerManager {
    private static HandlerManager INSTANCE;
    private final List<IComponentServerHandler> serverHandlers = new ArrayList<>();
    private final List<IComponentClientHandler> clientHandlers = new ArrayList<>();

    public static HandlerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HandlerManager();
        }
        return INSTANCE;
    }
    static {
        ModulesManager.init();
    }

    public abstract static class safeInvoker implements Supplier<Supplier<IComponentClientHandler>>{}

    public void addHandlers(IComponentServerHandler serverHandler, safeInvoker clientHandler){
        if (!this.serverHandlers.contains(serverHandler)) this.serverHandlers.add(serverHandler.getIndex(), serverHandler);

        if (!this.clientHandlers.contains(clientHandler) && SideUtils.isClient()) this.clientHandlers.add(clientHandler.get().get().getIndex(), clientHandler.get().get());
    }

    public Iterator<IComponentServerHandler> forServerHandlers(){
        return API.For(serverHandlers).yield();
    }

    public Iterator<IComponentClientHandler> forClientHandlers(){
        return API.For(clientHandlers).yield();
    }


    public com.clefal.nirvana_lib.relocated.io.vavr.collection.List<BiFunction<WYBScreen<?>, IRewritable, BuildMenuTab<?>>> getImmutableBuildMenuTabFunction(Build build){
        Map<Byte, ? extends IBuildComponent<?>> components = build.getComponents();
        return this.getClientHandlers(build)
                .map(x -> x.getBuildMenuTabFunction(components.get(x.getIndex()).get())).toList();

    }

    public com.clefal.nirvana_lib.relocated.io.vavr.collection.List<BiFunction<WYBScreen<?>, IRewritable, BuildMenuTab<?>>> getWritableBuildMenuTabFunction(Build build){
        Map<Byte, ? extends IBuildComponent<?>> components = build.getComponents();
        return this.getClientHandlers(build)
                .map(x -> x.getBuildMenuTabFunction(components.get(x.getIndex()).get())).toList();

    }

    public Seq<IComponentClientHandler> getClientHandlers(Build build){
        return build.getComponents().values().map(x -> clientHandlers.get(x.getHandlerIndex()));
    }




}
