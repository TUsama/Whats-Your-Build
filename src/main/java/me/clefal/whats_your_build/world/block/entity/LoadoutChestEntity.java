package me.clefal.whats_your_build.world.block.entity;


import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import commonnetwork.api.Dispatcher;
import lombok.Getter;
import lombok.Setter;
import me.clefal.whats_your_build.network.s2c.S2CUpdateLoadoutChestPacket;
import me.clefal.whats_your_build.world.loadout.LoadoutMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;


public class LoadoutChestEntity extends BaseContainerBlockEntity {

    private NonNullList<ItemStack> items = NonNullList.withSize(LoadoutMenu.size, ItemStack.EMPTY);
    @Getter
    private final int limitation = 5;
    @Getter
    @Setter
    private NonNullList<ItemStack> lastItems = NonNullList.withSize(5, ItemStack.EMPTY);
    @Getter
    @Setter
    private float filledPercent;

    public LoadoutChestEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("wyb.container.title.armory");
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

//? >1.20.1 {
    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, this.items, registries);

    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, this.items, registries);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }
    //?} else {




    /*@Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, this.items);
        saveAllItems(tag, this.lastItems, "last");
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, this.items);
        this.lastItems.clear();
        loadAllItems(tag, this.lastItems, "last");
    }


    *///?}
    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return null;
    }


    @Override
    public int getContainerSize() {
        return LoadoutMenu.size;
    }



    //? 1.20.1 {

    /*@Override
    public boolean isEmpty() {
        Iterator<ItemStack> var1 = this.items.iterator();
        System.out.println(this.items.isEmpty());
        ItemStack itemstack;
        do {
            if (!var1.hasNext()) {
                return true;
            }

            itemstack = var1.next();
        } while(itemstack.isEmpty());

        return false;
    }

    @Override
    public ItemStack getItem(int i) {
        return i >= 0 && i < this.items.size() ? (ItemStack)this.items.get(i) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int i, int i1) {
        ItemStack itemstack = ContainerHelper.removeItem(this.items, i, i1);
        if (!itemstack.isEmpty()) {
            this.setChanged();
        }

        return itemstack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int i) {
        ItemStack itemstack = this.items.get(i);
        if (itemstack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            this.items.set(i, ItemStack.EMPTY);
            return itemstack;
        }
    }


    @Override
    public void setItem(int i, ItemStack itemStack) {
        this.items.set(i, itemStack);
        if (!itemStack.isEmpty() && itemStack.getCount() > this.getMaxStackSize()) {
            itemStack.setCount(this.getMaxStackSize());
        }
        this.setChanged();
        Level level = this.level;
        if (level!= null && !level.isClientSide){
            updateOnChanged();

            Dispatcher.sendToAllClients(new S2CUpdateLoadoutChestPacket(List.ofAll(this.lastItems), this.getBlockPos(), this.filledPercent), level.getServer());
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        this.items.clear();
        this.setChanged();
    }

    *///?}

    private void updateOnChanged(){
        if (this.items.isEmpty()) return;
        List<ItemStack> reject = List.ofAll(this.items).reject(ItemStack::isEmpty);
        this.lastItems = NonNullList.of(ItemStack.EMPTY, reject.takeRight(5).toJavaArray(ItemStack[]::new));
        this.filledPercent = reject.size() * 1.0f / this.getContainerSize();
        System.out.println(filledPercent);
    }

    public static CompoundTag saveAllItems(CompoundTag tag, NonNullList<ItemStack> list, String name) {
        ListTag listtag = new ListTag();

        for(int i = 0; i < list.size(); ++i) {
            ItemStack itemstack = (ItemStack)list.get(i);
            if (!itemstack.isEmpty()) {
                CompoundTag compoundtag = new CompoundTag();
                compoundtag.putByte("Slot", (byte)i);
                itemstack.save(compoundtag);
                listtag.add(compoundtag);
            }
        }

        if (!listtag.isEmpty()) {
            tag.put(name, listtag);
        }

        return tag;
    }

    public static void loadAllItems(CompoundTag tag, NonNullList<ItemStack> list, String name) {
        ListTag listtag = tag.getList(name, 10);

        for(int i = 0; i < listtag.size(); ++i) {
            CompoundTag compoundtag = listtag.getCompound(i);
            int j = compoundtag.getByte("Slot") & 255;
            if (j >= 0 && j < list.size()) {
                list.set(j, ItemStack.of(compoundtag));
            }
        }

    }
}
