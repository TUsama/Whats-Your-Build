package me.clefal.whats_your_build.handler;

import com.clefal.nirvana_lib.relocated.io.vavr.Tuple2;
import com.google.common.collect.Comparators;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import me.clefal.whats_your_build.client.screen.BuildMenuTab;
import me.clefal.whats_your_build.client.screen.PlayerBuildScreen;
import me.clefal.whats_your_build.modules.ModulesManager;
import me.clefal.whats_your_build.modules.armor.VanillaArmorComponentClientHandler;
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

    public Map<IBuildComponent<?>, Function<PlayerBuildScreen, BuildMenuTab<?, ?>>> readBuf(List<Byte> index, FriendlyByteBuf buf){
        Map<IBuildComponent<?>, Function<PlayerBuildScreen, BuildMenuTab<?, ?>>> map = new LinkedHashMap<>();

        for (Byte b : index) {
            var iBuildComponentFunctionTuple2 = clientHandlers.get(b).readBuf(buf);
            map.put(iBuildComponentFunctionTuple2._1(), iBuildComponentFunctionTuple2._2());
        }
        return map;
    }




}
