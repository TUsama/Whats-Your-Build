package me.clefal.whats_your_build.world.player_build;

import me.clefal.whats_your_build.data.buildobject.Build;
//? !fabric
import me.clefal.whats_your_build.world.BuildMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class PlayerBuildMenu extends BuildMenu {

    public final Build targetBuild;


    public PlayerBuildMenu(@Nullable MenuType<?> menuType, int containerId, FriendlyByteBuf buf) {
        this(menuType, containerId, buf.readJsonWithCodec(Build.CODEC));
    }

    public PlayerBuildMenu(@Nullable MenuType<?> menuType, int containerId, Build build) {
        super(menuType, containerId);
        this.targetBuild = build;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    public void addSlotFromScreen(Slot slot){
        this.addSlot(slot);
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    public void rewriteSlots(String id) {
        this.slots.clear();
    }
}
