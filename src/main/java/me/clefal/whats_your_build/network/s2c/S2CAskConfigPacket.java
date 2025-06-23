package me.clefal.whats_your_build.network.s2c;

import com.clefal.nirvana_lib.network.newtoolchain.S2CModPacket;
import me.clefal.whats_your_build.config.WYBClientConfig;
import net.minecraft.network.FriendlyByteBuf;

public class S2CAskConfigPacket implements S2CModPacket<S2CAskConfigPacket> {
    @Override
    public void handleClient() {
        WYBClientConfig.syncConfig();
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {

    }

    @Override
    public void read(FriendlyByteBuf friendlyByteBuf) {

    }

    @Override
    public Class<S2CAskConfigPacket> getSelfClass() {
        return S2CAskConfigPacket.class;
    }
}
