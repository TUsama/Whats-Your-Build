package me.clefal.whats_your_build.network.c2s;

import com.clefal.nirvana_lib.network.C2SModPacket;
import com.clefal.nirvana_lib.utils.DevUtils;
import com.clefal.nirvana_lib.utils.NetworkUtils;
import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.event.server.ServerGatherBuildComponentEvent;
import me.clefal.whats_your_build.network.s2c.S2CReturnBuildPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;
import java.util.UUID;

public class C2SAskBuildPacket implements C2SModPacket {
    public UUID target;

    public C2SAskBuildPacket(UUID target) {
        this.target = target;
    }

    public C2SAskBuildPacket(FriendlyByteBuf buf) {
        this.target = buf.readUUID();
    }

    @Override
    public void handleServer(ServerPlayer player) {
        ServerPlayer targetPlayer = player.getServer().getPlayerList().getPlayer(target);
        if (targetPlayer != null) {
            ServerGatherBuildComponentEvent post = CommonClass.post(new ServerGatherBuildComponentEvent(targetPlayer));
            S2CReturnBuildPacket s2CReturnBuildPacket = new S2CReturnBuildPacket(post.getComponents(), target, post.getIndex());
            NetworkUtils.sendToClient(s2CReturnBuildPacket, player);
        } else {
            DevUtils.runWhenOnDev(() -> {
                ServerGatherBuildComponentEvent post = CommonClass.post(new ServerGatherBuildComponentEvent(player));
                S2CReturnBuildPacket s2CReturnBuildPacket = new S2CReturnBuildPacket(post.getComponents(), player.getUUID(), post.getIndex());
                NetworkUtils.sendToClient(s2CReturnBuildPacket, player);
            });
        }
    }

    @Override
    public void write(FriendlyByteBuf buf) {

        buf.writeUUID(target);
    }
}
