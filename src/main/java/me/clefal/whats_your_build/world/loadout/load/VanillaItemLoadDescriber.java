package me.clefal.whats_your_build.world.loadout.load;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class VanillaItemLoadDescriber extends ItemLoadDescriber {
    public static final String TYPE = "vanilla_item_load_describer";
    public static final MapCodec<VanillaItemLoadDescriber> CODEC = RecordCodecBuilder.mapCodec(vanillaItemLoadDescriberInstance ->
            vanillaItemLoadDescriberInstance.group(
                    ItemStack.CODEC.listOf().fieldOf("itemstacks").forGetter(x -> x.target)
            ).apply(vanillaItemLoadDescriberInstance, VanillaItemLoadDescriber::new)
    );

    public VanillaItemLoadDescriber(List<ItemStack> target) {
        super(target);
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void tryWear(ServerPlayer player, Container armory) {
        List<ItemStack> itemStacks = getArmory(armory);
        for (ItemStack buildStack : target) {
            itemStacks.stream()
                    .filter(x -> ItemStack.matches(x, buildStack))
                    .findFirst()
                    .ifPresent(sameItem -> {
                        EquipmentSlot equipmentslot = player.getEquipmentSlotForItem(buildStack);
                        InventoryMenu inventoryMenu = player.inventoryMenu;

                        if (//? >1.20.1
                                equipmentslot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR
                            //? 1.20.1
                        /*equipmentslot.getType() == EquipmentSlot.Type.ARMOR*/
                        ) {
                            int targetPosition = 8 - equipmentslot.getIndex();
                            equipAndConsumeSource(player, inventoryMenu, targetPosition, equipmentslot, buildStack, sameItem);

                        } else if (equipmentslot.equals(EquipmentSlot.OFFHAND)) {
                            int targetPosition = 45;
                            equipAndConsumeSource(player, inventoryMenu, targetPosition, equipmentslot, buildStack, sameItem);

                        } else if (equipmentslot.equals(EquipmentSlot.MAINHAND)) {
                            giveToMainHandOrDrop(player, buildStack, sameItem);
                        }
                    });
        }
    }



    /**
     * 尝试在指定槽位装备新物品，并处理旧物品的取出与放回。
     */
    private boolean equipOrSwap(Player player, InventoryMenu inventoryMenu, int slotIndex, EquipmentSlot equipmentslot, ItemStack newItem) {
        if (inventoryMenu.slots.get(slotIndex).hasItem()) {
            ItemStack oldEquipment = inventoryMenu.slots.get(slotIndex).getItem();
            if (ItemStack.matches(oldEquipment, newItem)) return false;
            if (!player.addItem(oldEquipment)) {
                player.spawnAtLocation(oldEquipment, 1F);
            }
        }
        player.setItemSlot(equipmentslot, newItem);
        return true;
    }

    /**
     * 装备新物品到指定槽位，并清空来源物品堆。
     */
    private void equipAndConsumeSource(Player player, InventoryMenu inventoryMenu, int slotIndex, EquipmentSlot equipmentslot, ItemStack newItem, ItemStack sourceItem) {
        if (equipOrSwap(player, inventoryMenu, slotIndex, equipmentslot, newItem)) sourceItem.setCount(0); // 清空来源
    }

    /**
     * 给玩家放入主手物品（若背包放不下则掉落），并清空来源物品堆。
     */
    private void giveToMainHandOrDrop(Player player, ItemStack newItem, ItemStack sourceItem) {
        if (!player.addItem(newItem)) {
            player.spawnAtLocation(newItem, 1F);
        }
        sourceItem.setCount(0);
    }

}
