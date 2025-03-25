package me.clefal.whats_your_build.handler;

import com.clefal.nirvana_lib.relocated.io.vavr.API;
import com.google.common.collect.ImmutableList;
import me.clefal.whats_your_build.client.screen.BuildMenuTab;
import me.clefal.whats_your_build.client.screen.PlayerBuildScreen;
import me.clefal.whats_your_build.modules.ModulesManager;
import net.minecraft.network.FriendlyByteBuf;

import java.util.*;
import java.util.function.Function;

public class HandlerManager {
    private static HandlerManager INSTANCE;
    public List<IComponentServerHandler> serverHandlers = new ArrayList<>();
    public List<IComponentClientHandler<?>> clientHandlers = new ArrayList<>();

    public static HandlerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HandlerManager();
        }
        return INSTANCE;
    }
    static {
        ModulesManager.init();
    }

    public List<IBuildComponent<?>> readBuf(List<Byte> index, FriendlyByteBuf buf){
        return API.For(index)
                .yield(x -> clientHandlers.get(x).readBuf(buf))
                .collect(ImmutableList.toImmutableList());
    }

    public List<Function<PlayerBuildScreen, BuildMenuTab<?, ?>>> getBuildMenuTabFunction(List<Byte> index, List<IBuildComponent<?>> components){
        return API.For(index, components)
                .yield((x, y) -> clientHandlers.get(x).getBuildMenuTabFunction(y.self()))
                .collect(ImmutableList.toImmutableList());
    }




}
