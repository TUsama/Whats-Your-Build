package me.clefal.whats_your_build.client.screen.buildscreen.vanilla;

import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import me.clefal.whats_your_build.client.screen.buildscreen.RenderContext;
import me.clefal.whats_your_build.client.screen.component.NormalArmorHolder;
import me.clefal.whats_your_build.data.modules.armor.VanillaArmorComponent;
import net.minecraft.world.entity.EquipmentSlot;


public class VanillaArmorMenu extends NormalArmorHolder<VanillaArmorComponent> {

    public VanillaArmorMenu(VanillaArmorComponent component, RenderContext context) {
        super(component, context);
    }

    @Override
    public void initHolders() {
        holders = List.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET)
                .map(EquipmentSlot::getName)
                .map(str -> new ArmorHolder(holderRadius, component.armors().get(str).getOrNull()));
    }


}
