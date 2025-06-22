package me.clefal.whats_your_build.network.s2c;


import com.clefal.nirvana_lib.network.newtoolchain.S2CModPacket;
import com.clefal.nirvana_lib.utils.DevUtils;
import me.clefal.whats_your_build.data.buildobject.Build;
import me.clefal.whats_your_build.data.handler.HandlerManager;
import me.clefal.whats_your_build.data.handler.IBuildComponent;
import me.clefal.whats_your_build.data.modules.ModulesManager;
import me.clefal.whats_your_build.network.NetworkHelper;
import net.minecraft.network.FriendlyByteBuf;

import java.util.List;
import java.util.UUID;

public class S2CReturnBuildPacket implements S2CModPacket<S2CReturnBuildPacket> {
    private UUID targetPlayer;
    private Build build;


    public S2CReturnBuildPacket(Build build, UUID targetPlayer) {
        this.targetPlayer = targetPlayer;
        this.build = build;
    }

    public S2CReturnBuildPacket() {
    }


    @Override
    public void handleClient() {
        if (DevUtils.isInDev()) {
            NetworkHelper.startPlayerBuildScreen(HandlerManager.getInstance().getBuildMenuTabFunction(build), targetPlayer);
        } else {
            if (!build.isEmpty()) {
                NetworkHelper.startPlayerBuildScreen(HandlerManager.getInstance().getBuildMenuTabFunction(build), targetPlayer);
            }
        }


    }


    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeUUID(targetPlayer);
        buf.writeJsonWithCodec(Build.CODEC, build);
    }

    @Override
    public void read(FriendlyByteBuf friendlyByteBuf) {
        targetPlayer = friendlyByteBuf.readUUID();
        build = friendlyByteBuf.readJsonWithCodec(Build.CODEC);
    }

    @Override
    public Class<S2CReturnBuildPacket> getSelfClass() {
        return S2CReturnBuildPacket.class;
    }


}
