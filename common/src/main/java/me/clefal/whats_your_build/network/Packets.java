package me.clefal.whats_your_build.network;

import com.clefal.nirvana_lib.network.SafeMSGInvoker;
import com.clefal.nirvana_lib.utils.NetworkUtil;
import me.clefal.whats_your_build.network.c2s.C2SAskBuildPacket;
import me.clefal.whats_your_build.network.s2c.S2CReturnBuildPacket;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Function;

public class Packets {

    public static void registerAllS2CPackets(){
        NetworkUtil.registerClientMessage(S2CReturnBuildPacket.class, new SafeMSGInvoker<S2CReturnBuildPacket>() {
            @Override
            public Function<FriendlyByteBuf, S2CReturnBuildPacket> get() {
                return buf -> new S2CReturnBuildPacket(buf);
            }
        });
    }

    public static void registerAllC2SPackets(){
        NetworkUtil.registerServerMessage(C2SAskBuildPacket.class, new SafeMSGInvoker<C2SAskBuildPacket>() {
            @Override
            public Function<FriendlyByteBuf, C2SAskBuildPacket> get() {
                return buf -> new C2SAskBuildPacket(buf);
            }
        });
    }
}
