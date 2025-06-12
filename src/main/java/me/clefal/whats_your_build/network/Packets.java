package me.clefal.whats_your_build.network;

import com.clefal.nirvana_lib.utils.NetworkUtils;
import me.clefal.whats_your_build.network.c2s.C2SAskBuildPacket;
import me.clefal.whats_your_build.network.c2s.C2SSendGlobalBuildPacket;
import me.clefal.whats_your_build.network.s2c.S2CReturnBuildPacket;


public class Packets {

    public static void registerAllS2CPackets(){
        //? if =1.20.1 {
        /*NetworkUtils.registerClientMessage(S2CReturnBuildPacket.class, S2CReturnBuildPacket::new);
        *///?} else {
        //?}
    }

    public static void registerAllC2SPackets(){
        //? if =1.20.1 {
        /*NetworkUtils.registerServerMessage(C2SAskBuildPacket.class, C2SAskBuildPacket::new);
        NetworkUtils.registerServerMessage(C2SSendGlobalBuildPacket.class, C2SSendGlobalBuildPacket::new);
        *///?} else {

        //?}
    }
}
