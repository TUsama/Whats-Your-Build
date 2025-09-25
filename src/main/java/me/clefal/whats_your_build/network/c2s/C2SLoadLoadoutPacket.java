package me.clefal.whats_your_build.network.c2s;

import com.clefal.nirvana_lib.network.newtoolchain.C2SModPacket;
import me.clefal.whats_your_build.Constants;
import me.clefal.whats_your_build.world.block.entity.LoadoutChestEntity;
import me.clefal.whats_your_build.world.loadout.load.LoadDescriber;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class C2SLoadLoadoutPacket implements C2SModPacket<C2SLoadLoadoutPacket> {
    private BlockPos blockPos;
    private LoadDescriber<?> loadDescriber;

    public C2SLoadLoadoutPacket(BlockPos blockPos, LoadDescriber<?> loadDescriber) {
        this.blockPos = blockPos;
        this.loadDescriber = loadDescriber;
    }

    public C2SLoadLoadoutPacket() {
    }

    @Override
    public void handleServer(ServerPlayer serverPlayer, C2SLoadLoadoutPacket c2SLoadLoadoutPacket, boolean b) {
        if (serverPlayer.level().getBlockEntity(blockPos) instanceof LoadoutChestEntity loadoutChestEntity){
            loadDescriber.tryWear(serverPlayer, loadoutChestEntity);
        } else{
            Constants.LOG.error("Found a BlockEntity that is not a LoadoutChestEntity when tried to load loadout!");
        }

    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBlockPos(blockPos);
        friendlyByteBuf.writeJsonWithCodec(LoadDescriber.CODEC, loadDescriber);
    }

    @Override
    public void read(FriendlyByteBuf friendlyByteBuf) {
        this.blockPos = friendlyByteBuf.readBlockPos();
        this.loadDescriber = friendlyByteBuf.readJsonWithCodec(LoadDescriber.CODEC);
    }

    @Override
    public Class<C2SLoadLoadoutPacket> getSelfClass() {
        return C2SLoadLoadoutPacket.class;
    }

}
