package me.clefal.whats_your_build.world.player_build;

import com.clefal.nirvana_lib.relocated.io.vavr.Tuple;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.Map;
import me.clefal.whats_your_build.data.buildobject.Build;
import me.clefal.whats_your_build.data.handler.IBuildComponent;
import me.clefal.whats_your_build.data.modules.armor.VanillaArmorComponent;
import me.clefal.whats_your_build.data.modules.compat.curios.CuriosComponent;
import me.clefal.whats_your_build.world.BuildMenu;
import me.clefal.whats_your_build.world.IRewritable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.NonInteractiveResultSlot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class PlayerBuildMenu extends BuildMenu implements IRewritable {

    public final Build targetBuild;
    protected java.util.Map<String, SlotPlacer> placePlan = new HashMap<>();

    public PlayerBuildMenu(@Nullable MenuType<?> menuType, int containerId, FriendlyByteBuf buf) {
        this(menuType, containerId, buf.readJsonWithCodec(Build.CODEC));
    }

    public PlayerBuildMenu(@Nullable MenuType<?> menuType, int containerId, Build build) {
        super(menuType, containerId);
        this.targetBuild = build;
        handleBuild(build);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    public void rewriteSlots(String id) {
        this.slots.clear();
        placePlan.get(id).place();
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void handleBuild(Build build) {
        Map<String, ? extends IBuildComponent<?>> map = build.getComponents()
                .map((aByte, iBuildComponent) -> Tuple.of(iBuildComponent.getIdentifier(), iBuildComponent));

        map
                .get(VanillaArmorComponent.ID)
                .forEach(iBuildComponent -> placePlan.put(VanillaArmorComponent.ID,
                        () -> {
                            var simpleContainer = iBuildComponent.asContainer();
                            int k = 0;
                            int j = 0;
                            for (int i = 0; i < simpleContainer.getContainerSize(); i++) {
                                if (k >= 4) {
                                    j++;
                                    k = 0;
                                }
                                addSlot(new NonInteractiveResultSlot(simpleContainer, i, j * 18 + 24, k * 18 + 52));
                                k++;
                            }
                        }));

        map
                .get(CuriosComponent.ID)
                .forEach(iBuildComponent -> placePlan.put(CuriosComponent.ID,
                        () -> {
                            var simpleContainer = iBuildComponent.asContainer();

                            int k = 0;
                            int j = 0;
                            for (int i = 0; i < simpleContainer.getContainerSize(); i++) {
                                if (k >= 4) {
                                    j++;
                                    k = 0;
                                }
                                addSlot(new NonInteractiveResultSlot(simpleContainer, i, j * 18 + 24, k * 18 + 52));
                                k++;
                            }
                        }));
    }
}
