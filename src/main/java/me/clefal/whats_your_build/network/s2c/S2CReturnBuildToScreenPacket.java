package me.clefal.whats_your_build.network.s2c;

import com.clefal.nirvana_lib.network.newtoolchain.S2CModPacket;
import me.clefal.whats_your_build.client.screen.loadoutscreen.LoadoutScreen;
import me.clefal.whats_your_build.data.buildobject.Build;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;

public class S2CReturnBuildToScreenPacket implements S2CModPacket<S2CReturnBuildToScreenPacket> {

    private Build build;

    public S2CReturnBuildToScreenPacket(Build build) {
        this.build = build;
    }

    public S2CReturnBuildToScreenPacket() {
    }

    @Override
    public void handleClient() {
        if (Minecraft.getInstance().screen instanceof LoadoutScreen screen){
            screen.initTemplate(build);
        }
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeJsonWithCodec(Build.CODEC, build);
    }

    @Override
    public void read(FriendlyByteBuf friendlyByteBuf) {
        this.build = friendlyByteBuf.readJsonWithCodec(Build.CODEC);
    }

    @Override
    public Class<S2CReturnBuildToScreenPacket> getSelfClass() {
        return S2CReturnBuildToScreenPacket.class;
    }
}
