package me.clefal.whats_your_build.handler;

import com.clefal.nirvana_lib.relocated.io.vavr.API;
import com.clefal.nirvana_lib.utils.SideUtils;
import com.google.common.collect.ImmutableList;
import me.clefal.whats_your_build.client.screen.BuildMenuTab;
import me.clefal.whats_your_build.client.screen.PlayerBuildScreen;
import me.clefal.whats_your_build.modules.ModulesManager;
import net.minecraft.network.FriendlyByteBuf;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class HandlerManager {
    private static HandlerManager INSTANCE;
    private final List<IComponentServerHandler> serverHandlers = new ArrayList<>();
    private final List<IComponentClientHandler<?>> clientHandlers = new ArrayList<>();

    public static HandlerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HandlerManager();
        }
        return INSTANCE;
    }
    static {
        ModulesManager.init();
    }

    public abstract static class safeInvoker implements Supplier<Supplier<IComponentClientHandler<?>>>{}

    public void addHandlers(IComponentServerHandler serverHandler, safeInvoker clientHandler){
        if (!this.serverHandlers.contains(serverHandler)) this.serverHandlers.add(serverHandler.getIndex(), serverHandler);

        if (!this.clientHandlers.contains(clientHandler) && SideUtils.isClient()) this.clientHandlers.add(clientHandler.get().get().getIndex(), clientHandler.get().get());
    }

    public Iterator<IComponentServerHandler> forServerHandlers(){
        return API.For(serverHandlers).yield();
    }

    public Iterator<IComponentClientHandler<?>> forClientHandlers(){
        return API.For(clientHandlers).yield();
    }

    public List<IBuildComponent<?>> readBuf(List<Byte> index, FriendlyByteBuf buf){
        return API.For(index)
                .yield(x -> clientHandlers.get(x).readBuf(buf))
                .collect(ImmutableList.toImmutableList());
    }

    public List<Function<PlayerBuildScreen, BuildMenuTab<?, ?>>> getBuildMenuTabFunction(List<Byte> index, List<IBuildComponent<?>> components){
        return API.For(index)
                .yield(x -> clientHandlers.get(x).getBuildMenuTabFunction(components.get(x)))
                .collect(ImmutableList.toImmutableList());
    }




}
