package me.clefal.whats_your_build.network.c2s;

import com.clefal.nirvana_lib.network.C2SModPacket;
import me.clefal.whats_your_build.chat.BuildClickEvent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class C2SSendGlobalBuildPacket implements C2SModPacket {

    public C2SSendGlobalBuildPacket() {
    }

    public C2SSendGlobalBuildPacket(FriendlyByteBuf buf) {
    }

    @Override
    public void handleServer(ServerPlayer serverPlayer) {
        for (Player player : serverPlayer.getServer().getPlayerList().getPlayers()) {
            player.sendSystemMessage(Component.translatable("wyb.chat.receive_build", serverPlayer.getName(), Component.translatable("wyb.chat.build",serverPlayer.getName()).withStyle(Style.EMPTY.withClickEvent(new BuildClickEvent(serverPlayer.getUUID())))));
        }
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {

    }
}
