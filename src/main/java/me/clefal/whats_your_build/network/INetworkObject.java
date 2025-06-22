package me.clefal.whats_your_build.network;

import net.minecraft.network.FriendlyByteBuf;

public interface INetworkObject {
    void write(FriendlyByteBuf buf);
    void read(FriendlyByteBuf buf);
}
