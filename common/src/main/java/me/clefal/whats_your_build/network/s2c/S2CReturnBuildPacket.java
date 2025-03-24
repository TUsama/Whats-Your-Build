package me.clefal.whats_your_build.network.s2c;

import com.clefal.nirvana_lib.network.S2CModPacket;
import me.clefal.whats_your_build.handler.HandlerManager;
import me.clefal.whats_your_build.handler.IBuildComponent;
import me.clefal.whats_your_build.network.NetworkHelper;
import net.minecraft.network.FriendlyByteBuf;

import java.util.List;

public class S2CReturnBuildPacket implements S2CModPacket {
    private final List<IBuildComponent<?>> components;
    private final List<Byte> index;


    public S2CReturnBuildPacket(List<IBuildComponent<?>> components, List<Byte> index) {
        this.components = components;
        this.index = index;
    }


    public S2CReturnBuildPacket(FriendlyByteBuf buf) {
        this.index = buf.readList(FriendlyByteBuf::readByte);
        this.components = HandlerManager.getInstance().readBuf(index, buf);
    }

    @Override
    public void handleClient() {
        System.out.println("handle here!");
        if (!index.isEmpty()){
            NetworkHelper.startPlayerBuildScreen(HandlerManager.getInstance().getBuildMenuTabFunction(index, components));
        }

    }

    @Override
    public void write(FriendlyByteBuf buf) {
        System.out.println("write!");
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
