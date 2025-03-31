package me.clefal.whats_your_build.compat.curios;

import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import me.clefal.whats_your_build.event.server.ServerGatherBuildComponentEvent;
import me.clefal.whats_your_build.handler.ComponentType;
import me.clefal.whats_your_build.handler.IComponentServerHandler;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

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
        System.out.println("111");
        CuriosApi.getCuriosInventory(event.target).ifPresent(x -> {
            System.out.println("present!");
            Map<String, ICurioStacksHandler> curios = x.getCurios();
            System.out.println(curios);
            for (ICurioStacksHandler value : curios.values()) {
                int slots = value.getStacks().getSlots();
                for (int i = 0; i < slots; i++) {
                    ItemStack stackInSlot = value.getStacks().getStackInSlot(i);
                    System.out.println(stackInSlot.getHoverName());
                }
            }
        });
    }

    @Override
    public byte getIndex() {
        return ComponentType.CURIOS;
    }
}
