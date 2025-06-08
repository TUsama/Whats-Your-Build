package me.clefal.whats_your_build.data.modules.armor;

import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import me.clefal.whats_your_build.event.server.ServerGatherBuildComponentEvent;
import me.clefal.whats_your_build.data.handler.ComponentType;
import me.clefal.whats_your_build.data.handler.IComponentServerHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;


public class VanillaArmorComponentServerHandler implements IComponentServerHandler {
    private static VanillaArmorComponentServerHandler INSTANCE;

    public static VanillaArmorComponentServerHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new VanillaArmorComponentServerHandler();
        }
        return INSTANCE;
    }

    @Override
    @SubscribeEvent
    public void onGather(ServerGatherBuildComponentEvent event) {
        ServerPlayer player = event.target;
        event.addComponent(new VanillaArmorComponent(List.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET)
                .filter(x -> player.inventoryMenu.slots.get(8 - x.getIndex()).hasItem())
                .map(x -> player.inventoryMenu.slots.get(8 - x.getIndex()).getItem())
                .asJava()));

    }



    @Override
    public byte getIndex() {
        return ComponentType.VANILLA_ARMOR;
    }
}
