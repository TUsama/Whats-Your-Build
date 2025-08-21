package me.clefal.whats_your_build.client.screen.buildscreen.vanilla;

import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import me.clefal.whats_your_build.client.screen.RenderContext;
import me.clefal.whats_your_build.client.screen.component.ArmorMenu;
import me.clefal.whats_your_build.data.modules.armor.VanillaArmorComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;


public class VanillaArmorMenu extends ArmorMenu<VanillaArmorComponent> {

    public VanillaArmorMenu(@Nullable MenuType<?> menuType, int containerId, VanillaArmorComponent component, RenderContext context, List<ArmorHolder> holders) {
        super(menuType, containerId, component, context, holders);
    }

    @Override
    public void initHolders() {
        holders = List.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET)
                .map(EquipmentSlot::getName)
                .map(str -> new ArmorHolder(holderRadius, component.armors().get(str).getOrElse(ItemStack.EMPTY)));
    }


}
