package me.clefal.whats_your_build.network.c2s;


import com.clefal.nirvana_lib.network.newtoolchain.C2SModPacket;
import com.clefal.nirvana_lib.utils.DevUtils;
import com.clefal.nirvana_lib.utils.NetworkUtils;
import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.data.buildobject.Build;
import me.clefal.whats_your_build.event.server.ServerAskBuildPermissionCheckEvent;
import me.clefal.whats_your_build.event.server.ServerGatherBuildComponentEvent;
import me.clefal.whats_your_build.loaders.WYBRegistrate;
import me.clefal.whats_your_build.network.s2c.S2CReturnBuildPacket;
import me.clefal.whats_your_build.world.player_build.PlayerBuildMenu;
import me.clefal.whats_your_build.world.player_build.PlayerBuildMenuProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.Nullable;

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

    public C2SAskBuildPacket() {
    }


    @Override
    public void handleServer(ServerPlayer serverPlayer, C2SAskBuildPacket c2SAskBuildPacket, boolean b) {
        ServerPlayer targetPlayer = serverPlayer.getServer().getPlayerList().getPlayer(target);

        if (targetPlayer != null) {
            boolean allow = forceAllow || DevUtils.isInDev();
            if (!forceAllow) {
                ServerAskBuildPermissionCheckEvent serverAskBuildPermissionCheckEvent = CommonClass.post(new ServerAskBuildPermissionCheckEvent(targetPlayer, serverPlayer));
                allow = serverAskBuildPermissionCheckEvent.isAllowed;
            }

            if (allow) {
                ServerGatherBuildComponentEvent post = CommonClass.post(new ServerGatherBuildComponentEvent(targetPlayer));
                serverPlayer.openMenu(new PlayerBuildMenuProvider(post.getResultBuild()), buf -> buf.writeJsonWithCodec(Build.CODEC, post.getResultBuild()));
            } else {
                serverPlayer.sendSystemMessage(Component.translatable("wyb.ask.reject"));
            }
        } else {
            DevUtils.runWhenOnDev(() -> {
                ServerGatherBuildComponentEvent post = CommonClass.post(new ServerGatherBuildComponentEvent(serverPlayer));
                serverPlayer.openMenu(new PlayerBuildMenuProvider(post.getResultBuild()), buf -> buf.writeJsonWithCodec(Build.CODEC, post.getResultBuild()));
            });
        }
    }


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
