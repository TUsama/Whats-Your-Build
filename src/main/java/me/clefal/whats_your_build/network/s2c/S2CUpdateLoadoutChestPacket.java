package me.clefal.whats_your_build.network.s2c;

import com.clefal.nirvana_lib.network.newtoolchain.S2CModPacket;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import me.clefal.whats_your_build.world.block.entity.LoadoutChestEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

public class S2CUpdateLoadoutChestPacket implements S2CModPacket<S2CUpdateLoadoutChestPacket> {
    private List<ItemStack> stacks;
    private BlockPos blockPos;
    private float fillPercent;

    public S2CUpdateLoadoutChestPacket(List<ItemStack> stacks, BlockPos blockPos, float fillPercent) {
        this.stacks = stacks;
        this.blockPos = blockPos;
        this.fillPercent = fillPercent;
    }

    public S2CUpdateLoadoutChestPacket() {
    }

    @Override
    public void handleClient() {
        Minecraft mc = Minecraft.getInstance();
        mc.execute(() -> {
            if (mc.level == null) return;
            BlockState state = mc.level.getBlockState(blockPos);
            if (state.isAir()
                    || !state.hasBlockEntity()
                    || mc.level.getBlockEntity(blockPos) instanceof LoadoutChestEntity) {
                LoadoutChestEntity be = (LoadoutChestEntity) mc.level.getBlockEntity(blockPos);
                if (be == null) return; // make idea happy
                be.setLastItems(NonNullList.of(ItemStack.EMPTY, stacks.toJavaArray(ItemStack[]::new)));
                be.setFilledPercent(fillPercent);
            }
        });
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeCollection(stacks.toJavaList(), (buf, itemStack) -> buf.writeJsonWithCodec(ItemStack.CODEC, itemStack));
        friendlyByteBuf.writeBlockPos(blockPos);
        friendlyByteBuf.writeFloat(fillPercent);
    }

    @Override
    public void read(FriendlyByteBuf friendlyByteBuf) {
        this.stacks = List.ofAll((Iterable<? extends ItemStack>) friendlyByteBuf.readCollection(ArrayList::new, buf -> buf.readJsonWithCodec(ItemStack.CODEC)));
        this.blockPos = friendlyByteBuf.readBlockPos();
        this.fillPercent = friendlyByteBuf.readFloat();
    }

    @Override
    public Class<S2CUpdateLoadoutChestPacket> getSelfClass() {
        return S2CUpdateLoadoutChestPacket.class;
    }
}
