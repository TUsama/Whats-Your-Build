package me.clefal.whats_your_build.network.s2c;

import com.clefal.nirvana_lib.network.S2CModPacket;
import com.clefal.nirvana_lib.platform.Services;
import me.clefal.whats_your_build.handler.HandlerManager;
import me.clefal.whats_your_build.handler.IBuildComponent;
import me.clefal.whats_your_build.network.NetworkHelper;
import net.minecraft.network.FriendlyByteBuf;

import java.util.List;
import java.util.UUID;

public class S2CReturnBuildPacket implements S2CModPacket {
    private final List<IBuildComponent<?>> components;
    private final UUID targetPlayer;
    private final List<Byte> index;



    public S2CReturnBuildPacket(List<IBuildComponent<?>> components, UUID targetPlayer, List<Byte> index) {
        this.components = components;
        this.targetPlayer = targetPlayer;
        this.index = index;
    }


    public S2CReturnBuildPacket(FriendlyByteBuf buf) {
        this.targetPlayer = buf.readUUID();
        this.index = buf.readList(FriendlyByteBuf::readByte);
        this.components = HandlerManager.getInstance().readBuf(index, buf);
    }

    @Override
    public void handleClient() {
        
        if (Services.PLATFORM.isDevelopmentEnvironment()) {
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
                buf.writeJsonWithCodec(component.getCodeC(), component);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
