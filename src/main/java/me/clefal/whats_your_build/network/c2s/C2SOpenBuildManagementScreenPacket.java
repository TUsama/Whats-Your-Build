package me.clefal.whats_your_build.network.c2s;

import com.clefal.nirvana_lib.network.newtoolchain.C2SModPacket;
import me.clefal.whats_your_build.world.loadout.BuildManagementMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class C2SOpenBuildManagementScreenPacket implements C2SModPacket<C2SOpenBuildManagementScreenPacket> {
    @Override
    public void handleServer(ServerPlayer serverPlayer, C2SOpenBuildManagementScreenPacket c2SOpenBuildManagementScreenPacket, boolean b) {
        serverPlayer.openMenu(new MenuProvider() {
            @Override
            public Component getDisplayName() {
                return Component.literal("");
            }

            @Override
            public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
                return new BuildManagementMenu(containerId, playerInventory);
            }
        });
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {

    }

    @Override
    public void read(FriendlyByteBuf friendlyByteBuf) {

    }

    @Override
    public Class<C2SOpenBuildManagementScreenPacket> getSelfClass() {
        return C2SOpenBuildManagementScreenPacket.class;
    }
}
