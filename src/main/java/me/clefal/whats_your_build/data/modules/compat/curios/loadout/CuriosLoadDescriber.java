//? curios {
package me.clefal.whats_your_build.data.modules.compat.curios.loadout;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.clefal.whats_your_build.world.loadout.load.ItemLoadDescriber;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Tuple;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CuriosLoadDescriber extends ItemLoadDescriber {
    public static final String TYPE = "curios_load_describer";
    public static final MapCodec<CuriosLoadDescriber> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    ItemStack.CODEC.listOf().fieldOf("itemstacks").forGetter(x -> x.target)
            ).apply(instance, CuriosLoadDescriber::new)
    );

    public CuriosLoadDescriber(List<ItemStack> target) {
        super(target);
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void tryWear(ServerPlayer player, Container armory) {

        for (ItemStack fakeItemTarget : target) {
            getArmory(armory)
                    .stream()
                    .filter(x -> ItemStack.matches(x, fakeItemTarget))
                    .findFirst()
                    .ifPresent(itemInArmory ->
                    {
                        CuriosApi.getCurio(itemInArmory).ifPresent((curio) -> {
                            CuriosApi.getCuriosInventory(player).ifPresent((handler) -> {

                                Map<String, ICurioStacksHandler> curios = handler.getCurios();
                                Tuple<IDynamicStackHandler, SlotContext> firstSlot = null;
                                Iterator<Map.Entry<String, ICurioStacksHandler>> var7 = curios.entrySet().iterator();

                                while (var7.hasNext()) {
                                    Map.Entry<String, ICurioStacksHandler> entry = var7.next();
                                    IDynamicStackHandler stackHandlerx = entry.getValue().getStacks();
                                    //? > 1.20.1
                                    NonNullList<Boolean> activeStates = entry.getValue().getActiveStates();

                                    for (int i = 0; i < stackHandlerx.getSlots(); ++i) {
                                        //? > 1.20.1 {
                                        boolean active = activeStates.size() > i && activeStates.get(i);
                                        if (active) {
                                            //?}
                                        String id = entry.getKey();
                                        NonNullList<Boolean> renderStates = entry.getValue().getRenders();
                                        SlotContext slotContext = new SlotContext(id, player, i, false, renderStates.size() > i && renderStates.get(i));
                                        if (stackHandlerx.isItemValid(i, itemInArmory) && curio.canEquipFromUse(slotContext)) {
                                            ItemStack present = stackHandlerx.getStackInSlot(i);
                                            if (present.isEmpty()) {
                                                stackHandlerx.setStackInSlot(i, itemInArmory.copy());
                                                curio.onEquipFromUse(slotContext);
                                                if (!player.isCreative()) {
                                                    int count = itemInArmory.getCount();
                                                    itemInArmory.shrink(count);
                                                }

                                                return;
                                            }

                                            if (firstSlot == null && stackHandlerx.extractItem(i, itemInArmory.getMaxStackSize(), true).getCount() == itemInArmory.getCount()) {
                                                firstSlot = new Tuple<>(stackHandlerx, slotContext);
                                            }
                                        }
                                        //? > 1.20.1
                                        }
                                    }
                                }

                                if (firstSlot != null) {
                                    IDynamicStackHandler stackHandler = (IDynamicStackHandler) firstSlot.getA();
                                    SlotContext slotContextx = firstSlot.getB();
                                    int ix = slotContextx.index();
                                    ItemStack presentx = stackHandler.getStackInSlot(ix);
                                    stackHandler.setStackInSlot(ix, itemInArmory.copy());
                                    if (!player.isCreative()) {
                                        int count = itemInArmory.getCount();
                                        itemInArmory.shrink(count);
                                    }
                                    curio.onEquipFromUse(slotContextx);
                                    ItemStack copy = presentx.copy();
                                    if (!player.addItem(copy)) {
                                        player.spawnAtLocation(copy, 1F);
                                    }
                                }

                            });
                        });
                    });

        }
    }
}
//?}