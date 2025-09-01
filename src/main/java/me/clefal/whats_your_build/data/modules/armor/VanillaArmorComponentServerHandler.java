package me.clefal.whats_your_build.data.modules.armor;

import com.clefal.nirvana_lib.relocated.io.vavr.API;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.LinkedHashMap;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import me.clefal.whats_your_build.data.handler.ComponentType;
import me.clefal.whats_your_build.data.handler.IComponentServerHandler;
import me.clefal.whats_your_build.event.server.ServerGatherBuildComponentEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.io.StringReader;
import java.util.EnumMap;


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
        LinkedHashMap<EquipmentSlot, ItemStack> transform = List.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET)
                .filter(x -> player.inventoryMenu.slots.get(8 - x.getIndex()).hasItem())
                .map(x -> API.Tuple(x, player.inventoryMenu.slots.get(8 - x.getIndex()).getItem()))
                .transform(x -> LinkedHashMap.ofEntries(x)
                        .put(EquipmentSlot.MAINHAND, player.getMainHandItem())
                        .put(EquipmentSlot.OFFHAND, player.getOffhandItem())
                        .reject((k, v) -> v.isEmpty()));

        event.addComponent(new VanillaArmorComponent(transform.toJavaMap()));

    }


    @Override
    public byte getIndex() {
        return ComponentType.VANILLA_ARMOR;
    }
}
