package me.clefal.whats_your_build.network.s2c;

import com.clefal.nirvana_lib.network.S2CModPacket;
import com.google.common.collect.ImmutableList;
import me.clefal.whats_your_build.client.screen.BuildMenuTab;
import me.clefal.whats_your_build.client.screen.PlayerBuildScreen;
import me.clefal.whats_your_build.handler.HandlerManager;
import me.clefal.whats_your_build.handler.IBuildComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.FriendlyByteBuf;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class S2CReturnBuildPacket implements S2CModPacket {
    private final List<IBuildComponent<?>> components;
    private final List<Byte> index;
    private com.clefal.nirvana_lib.relocated.io.vavr.collection.List<Function<PlayerBuildScreen, BuildMenuTab<?, ?>>> list = com.clefal.nirvana_lib.relocated.io.vavr.collection.List.empty();

    public S2CReturnBuildPacket(List<IBuildComponent<?>> components, List<Byte> index) {
        this.components = components;
        this.index = index;
    }


    public S2CReturnBuildPacket(FriendlyByteBuf buf) {
        this.index = buf.readList(FriendlyByteBuf::readByte);
        Map<IBuildComponent<?>, Function<PlayerBuildScreen, BuildMenuTab<?, ?>>> iBuildComponentFunctionMap = HandlerManager.getInstance().readBuf(index, buf);
        this.components = ImmutableList.copyOf(iBuildComponentFunctionMap.keySet());
        this.list = com.clefal.nirvana_lib.relocated.io.vavr.collection.List.ofAll(iBuildComponentFunctionMap.values());
    }

    @Override
    public void handleClient() {
        Screen screen = Minecraft.getInstance().screen;
        if (screen == null){
            Minecraft.getInstance().screen = new PlayerBuildScreen(this.list);
        }

    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeCollection(index, (buf1, aByte) -> buf1.writeByte(aByte));
        for (IBuildComponent component : components) {
            buf.writeJsonWithCodec(component.getCodeC(), component);
        }
    }
}
