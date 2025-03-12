package me.clefal.whats_your_build.handler;

import com.google.common.collect.ImmutableList;
import me.clefal.whats_your_build.modules.ModulesManager;
import me.clefal.whats_your_build.modules.armor.VanillaArmorComponentClientHandler;
import net.minecraft.network.FriendlyByteBuf;

import java.util.ArrayList;
import java.util.List;

public class HandlerManager {
    public static final HandlerManager INSTANCE = new HandlerManager();
    public List<IComponentServerHandler> serverHandlers = new ArrayList<>();
    public List<IComponentClientHandler<?>> clientHandlers = new ArrayList<>();

    static {
        ModulesManager.init();
    }

    public List<IBuildComponent<?>> readBuf(List<Byte> index, FriendlyByteBuf buf){
        ImmutableList.Builder<IBuildComponent<?>> builder = ImmutableList.builder();
        for (Byte b : index) {
            builder.add(clientHandlers.get(b).readBuf(buf));
        }
        return builder.build();
    }




}
