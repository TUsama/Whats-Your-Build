//? neoforge {

package me.clefal.whats_your_build.network.c2s;

import com.clefal.nirvana_lib.network.newtoolchain.C2SModPacket;
import me.clefal.whats_your_build.loaders.WYBRegistrate;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.neoforged.neoforge.capabilities.Capabilities;
import org.jetbrains.annotations.Nullable;

public class C2SOpenBuildManagementScreenPacket implements C2SModPacket<C2SOpenBuildManagementScreenPacket> {

    public C2SOpenBuildManagementScreenPacket() {
    }

    @Override
    public void handleServer(ServerPlayer serverPlayer, C2SOpenBuildManagementScreenPacket c2SOpenBuildManagementScreenPacket, boolean b) {

        var handler = serverPlayer.getCapability(Capabilities.ItemHandler.ENTITY);
        serverPlayer.openMenu(new MenuProvider() {
            @Override
            public Component getDisplayName() {
                return Component.translatable("wyb.container.title.armory");
            }

            @Override
            public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
                return WYBRegistrate.playerBuildMenu.asProvider().createMenu(containerId, playerInventory, player);
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
//?}
