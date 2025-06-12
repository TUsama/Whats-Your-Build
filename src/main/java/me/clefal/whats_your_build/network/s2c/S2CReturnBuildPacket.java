package me.clefal.whats_your_build.network.s2c;


import com.clefal.nirvana_lib.network.newtoolchain.S2CModPacket;
import com.clefal.nirvana_lib.utils.DevUtils;
import me.clefal.whats_your_build.data.handler.HandlerManager;
import me.clefal.whats_your_build.data.handler.IBuildComponent;
import me.clefal.whats_your_build.network.NetworkHelper;
import net.minecraft.network.FriendlyByteBuf;

import java.util.List;
import java.util.UUID;

public class S2CReturnBuildPacket implements S2CModPacket<S2CReturnBuildPacket> {
    private List<IBuildComponent<?>> components;
    private UUID targetPlayer;
    private List<Byte> index;


    public S2CReturnBuildPacket(List<IBuildComponent<?>> components, UUID targetPlayer, List<Byte> index) {
        this.components = components;
        this.targetPlayer = targetPlayer;
        this.index = index;
    }


    @Override
    public void handleClient() {

        if (DevUtils.isInDev()) {
            NetworkHelper.startPlayerBuildScreen(HandlerManager.getInstance().getBuildMenuTabFunction(index, components), targetPlayer);
        } else {
            if (!index.isEmpty()) {
                NetworkHelper.startPlayerBuildScreen(HandlerManager.getInstance().getBuildMenuTabFunction(index, components), targetPlayer);
            }
        }


    }


    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeUUID(targetPlayer);
        try {
            buf.writeCollection(index, (buf1, aByte) -> buf1.writeByte(aByte));
            for (IBuildComponent component : components) {
                buf.writeJsonWithCodec(component.getCodec(), component);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void read(FriendlyByteBuf friendlyByteBuf) {
        targetPlayer = friendlyByteBuf.readUUID();
        index = friendlyByteBuf.readList(FriendlyByteBuf::readByte);
        this.components = HandlerManager.getInstance().readBuf(index, friendlyByteBuf);
    }

    @Override
    public Class<S2CReturnBuildPacket> getSelfClass() {
        return S2CReturnBuildPacket.class;
    }


}
