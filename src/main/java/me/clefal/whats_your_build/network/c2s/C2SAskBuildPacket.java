package me.clefal.whats_your_build.network.c2s;


import com.clefal.nirvana_lib.network.newtoolchain.C2SModPacket;
import com.clefal.nirvana_lib.utils.DevUtils;
import com.clefal.nirvana_lib.utils.NetworkUtils;
import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.event.server.ServerAskBuildPermissionCheckEvent;
import me.clefal.whats_your_build.event.server.ServerGatherBuildComponentEvent;
import me.clefal.whats_your_build.network.s2c.S2CReturnBuildPacket;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;

public class C2SAskBuildPacket implements C2SModPacket<C2SAskBuildPacket> {
    public UUID target;
    public boolean forceAllow = false;

    public C2SAskBuildPacket(UUID target) {
        this.target = target;
    }

    public C2SAskBuildPacket(UUID target, boolean forceAllow) {
        this.target = target;
        this.forceAllow = forceAllow;
    }


    @Override
    public void handleServer(ServerPlayer serverPlayer, C2SAskBuildPacket c2SAskBuildPacket, boolean b) {
        ServerPlayer targetPlayer = serverPlayer.getServer().getPlayerList().getPlayer(target);

        if (targetPlayer != null) {
            boolean allow = forceAllow || DevUtils.isInDev();
            if (!forceAllow){
                ServerAskBuildPermissionCheckEvent serverAskBuildPermissionCheckEvent = CommonClass.post(new ServerAskBuildPermissionCheckEvent(targetPlayer, serverPlayer));
                allow = serverAskBuildPermissionCheckEvent.isAllowed;
            }

            if (allow){
                ServerGatherBuildComponentEvent post = CommonClass.post(new ServerGatherBuildComponentEvent(targetPlayer));
                S2CReturnBuildPacket s2CReturnBuildPacket = new S2CReturnBuildPacket(post.getComponents(), target, post.getIndex());
                NetworkUtils.sendToClient(s2CReturnBuildPacket, serverPlayer);
            } else {
                serverPlayer.sendSystemMessage(Component.translatable("wyb.ask.reject"));
            }
        } else {
            DevUtils.runWhenOnDev(() -> {
                ServerGatherBuildComponentEvent post = CommonClass.post(new ServerGatherBuildComponentEvent(serverPlayer));
                S2CReturnBuildPacket s2CReturnBuildPacket = new S2CReturnBuildPacket(post.getComponents(), serverPlayer.getUUID(), post.getIndex());
                NetworkUtils.sendToClient(s2CReturnBuildPacket, serverPlayer);
            });
        }
    }


    public final static StreamCodec<FriendlyByteBuf, C2SAskBuildPacket> CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, c2SAskBuildPacket -> c2SAskBuildPacket.target,
            ByteBufCodecs.BOOL, c2SAskBuildPacket -> c2SAskBuildPacket.forceAllow,
            C2SAskBuildPacket::new
    );

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeUUID(target);
        friendlyByteBuf.writeBoolean(forceAllow);
    }

    @Override
    public void read(FriendlyByteBuf friendlyByteBuf) {
        target = friendlyByteBuf.readUUID();
        forceAllow = friendlyByteBuf.readBoolean();
    }

    @Override
    public Class<C2SAskBuildPacket> getSelfClass() {
        return C2SAskBuildPacket.class;
    }


}
