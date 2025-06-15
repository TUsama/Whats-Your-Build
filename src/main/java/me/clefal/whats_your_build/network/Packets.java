package me.clefal.whats_your_build.network;

import com.clefal.nirvana_lib.utils.NetworkUtils;
import me.clefal.whats_your_build.network.c2s.C2SAskBuildPacket;
import me.clefal.whats_your_build.network.c2s.C2SSendGlobalBuildPacket;
import me.clefal.whats_your_build.network.s2c.S2CReturnBuildPacket;


public class Packets {

    public static void registerAllS2CPackets(){
        NetworkUtils.registerPacket(S2CReturnBuildPacket::new);
    }

    public static void registerAllC2SPackets(){
        NetworkUtils.registerPacket(C2SAskBuildPacket::new);
        NetworkUtils.registerPacket(C2SSendGlobalBuildPacket::new);
    }
}
