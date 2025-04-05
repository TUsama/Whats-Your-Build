package me.clefal.whats_your_build.compat.curios;

import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import me.clefal.whats_your_build.event.server.ServerGatherBuildComponentEvent;
import me.clefal.whats_your_build.data.handler.ComponentType;
import me.clefal.whats_your_build.data.handler.IComponentServerHandler;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CuriosServerHandler implements IComponentServerHandler {

    private static CuriosServerHandler INSTANCE;

    public static CuriosServerHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CuriosServerHandler();
        }
        return INSTANCE;
    }

    @Override
    @SubscribeEvent
    public void onGather(ServerGatherBuildComponentEvent event) {
        CuriosApi.getCuriosInventory(event.target).ifPresent(x -> {
            Map<String, ICurioStacksHandler> curios = x.getCurios();
            List<ItemStack> empty = new ArrayList<>();
            for (ICurioStacksHandler value : curios.values()) {
                int slots = value.getStacks().getSlots();
                for (int i = 0; i < slots; i++) {
                    ItemStack stackInSlot = value.getStacks().getStackInSlot(i);
                    empty.add(stackInSlot);
                }
            }
            
            CuriosComponent curiosComponent = new CuriosComponent(com.clefal.nirvana_lib.relocated.io.vavr.collection.List.ofAll(empty));
            event.addComponent(curiosComponent);
        });
    }

    @Override
    public byte getIndex() {
        return ComponentType.CURIOS;
    }
}
