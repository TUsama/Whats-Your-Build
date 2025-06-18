package me.clefal.whats_your_build.network.c2s;

import com.clefal.nirvana_lib.network.newtoolchain.C2SModPacket;
import me.clefal.whats_your_build.chat.BuildClickEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class C2SSendGlobalBuildPacket implements C2SModPacket<C2SSendGlobalBuildPacket> {

    public C2SSendGlobalBuildPacket() {
    }


    @Override
    public void handleServer(ServerPlayer serverPlayer, C2SSendGlobalBuildPacket c2SSendGlobalBuildPacket, boolean b) {
        for (Player player : serverPlayer.getServer().getPlayerList().getPlayers()) {
            MutableComponent message = Component.translatable("wyb.chat.receive_build", serverPlayer.getName(), Component.translatable("wyb.chat.build", serverPlayer.getName())
                    .withStyle(Style.EMPTY
                            .withClickEvent(new BuildClickEvent(serverPlayer.getUUID()))
                            .applyFormat(ChatFormatting.AQUA)
                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.translatable("wyb.chat.click_to_show_build")))
                            .withUnderlined(true)));
            //? if < 1.21.4
            player.sendSystemMessage(message);
            //? if 1.21.4
            /*player.displayClientMessage(message, false);*/
        }
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {

    }

    @Override
    public void read(FriendlyByteBuf friendlyByteBuf) {

    }

    @Override
    public Class<C2SSendGlobalBuildPacket> getSelfClass() {
        return C2SSendGlobalBuildPacket.class;
    }



}
