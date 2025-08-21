package me.clefal.whats_your_build.world.player_build;

import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import me.clefal.whats_your_build.data.buildobject.Build;
import me.clefal.whats_your_build.world.IRewritableMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class PlayerBuildMenu extends AbstractContainerMenu implements IRewritableMenu {

    private Build targetBuild;


    protected PlayerBuildMenu(@Nullable MenuType<?> menuType, int containerId) {
        this(menuType, containerId, new Build(List.empty(), "client_build"));
    }

    protected PlayerBuildMenu(@Nullable MenuType<?> menuType, int containerId, Build build) {
        super(menuType, containerId);
        this.targetBuild = build;
    }

    public void rewriteSlots(List<Slot> slots){
        this.slots.clear();
        this.slots.addAll(slots.toJavaList());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
