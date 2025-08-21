package me.clefal.whats_your_build.network.c2s;

import com.clefal.nirvana_lib.network.newtoolchain.C2SModPacket;
import com.clefal.nirvana_lib.utils.NetworkUtils;
import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.event.server.ServerGatherBuildComponentEvent;
import me.clefal.whats_your_build.network.s2c.S2CReturnBuildToScreenPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class C2SAskTemplateBuildPacket implements C2SModPacket<C2SAskTemplateBuildPacket> {
    @Override
    public void handleServer(ServerPlayer serverPlayer, C2SAskTemplateBuildPacket c2SAskEmptyBuildPacket, boolean b) {
        ServerGatherBuildComponentEvent post = CommonClass.post(new ServerGatherBuildComponentEvent(serverPlayer));
        var s2CReturnBuildPacket = new S2CReturnBuildToScreenPacket(post.getResultBuild());
        NetworkUtils.sendToClient(s2CReturnBuildPacket, serverPlayer);
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {

    }

    @Override
    public void read(FriendlyByteBuf friendlyByteBuf) {

    }

    @Override
    public Class<C2SAskTemplateBuildPacket> getSelfClass() {
        return C2SAskTemplateBuildPacket.class;
    }
}
