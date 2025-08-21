//? if neoforge || forge {
package me.clefal.whats_your_build.data.modules.compat.curios.menu;

import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import me.clefal.whats_your_build.client.screen.IBuildMenuContainerHolder;
import me.clefal.whats_your_build.client.screen.buildscreen.BuildMenuTab;
import me.clefal.whats_your_build.data.modules.compat.curios.CuriosComponent;
import me.clefal.whats_your_build.world.IRewritableMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;

public class CuriosMenuTab extends BuildMenuTab<CuriosComponent> {

    public CuriosMenuTab(CuriosComponent component, IBuildMenuContainerHolder<?> holder, IRewritableMenu menu) {
        super(Component.translatable("wyb.compat.screen.tab.curios"), component, holder, menu);
    }

    @Override
    public List<Slot> getNewSlots() {
        ArrayList<Slot> slots = new ArrayList<>();
        SimpleContainer simpleContainer = new SimpleContainer(component.curios().toJavaArray(ItemStack[]::new));

        for (int k = 0; k < 4; k++) {
            slots.add(new Slot(simpleContainer, k, 8, 8 + k * 18));
        }


        return List.ofAll(slots);
    }
}
//?}