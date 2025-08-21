package me.clefal.whats_your_build.client.screen.buildscreen.vanilla;

import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import me.clefal.whats_your_build.client.screen.IBuildMenuContainerHolder;
import me.clefal.whats_your_build.client.screen.buildscreen.BuildMenuTab;
import me.clefal.whats_your_build.data.modules.armor.VanillaArmorComponent;
import me.clefal.whats_your_build.world.IRewritableMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;

public class VanillaArmorMenuTab extends BuildMenuTab<VanillaArmorComponent> {


    public VanillaArmorMenuTab(VanillaArmorComponent component, IBuildMenuContainerHolder<?> holder, IRewritableMenu menu) {
        super(Component.translatable("wyb.screen.tab.armor"), component, holder, menu);
    }

    @Override
    public List<Slot> getNewSlots() {
        ArrayList<Slot> slots = new ArrayList<>();
        SimpleContainer simpleContainer = new SimpleContainer(component.armors().values().toArray(ItemStack[]::new));

        for (int k = 0; k < 4; k++) {
            slots.add(new Slot(simpleContainer, k, 8, 8 + k * 18));
        }


        return List.ofAll(slots);
    }

}
