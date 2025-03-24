package me.clefal.whats_your_build.network;

import com.clefal.nirvana_lib.network.SafeMSGInvoker;
import com.clefal.nirvana_lib.utils.NetworkUtil;
import me.clefal.whats_your_build.network.c2s.C2SAskBuildPacket;
import me.clefal.whats_your_build.network.s2c.S2CReturnBuildPacket;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Function;

public class Packets {

    public static void registerAllS2CPackets(){
        NetworkUtil.registerClientMessage(S2CReturnBuildPacket.class, S2CReturnBuildPacket::new);
    }

    public static void registerAllC2SPackets(){
        NetworkUtil.registerServerMessage(C2SAskBuildPacket.class, C2SAskBuildPacket::new);
    }
}
