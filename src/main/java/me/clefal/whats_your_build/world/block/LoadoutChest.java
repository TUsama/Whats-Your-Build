package me.clefal.whats_your_build.world.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.data.buildobject.Build;
import me.clefal.whats_your_build.event.server.ServerGatherBuildComponentEvent;
import me.clefal.whats_your_build.loaders.WYBRegistrate;
import me.clefal.whats_your_build.world.block.entity.LoadoutChestEntity;
import me.clefal.whats_your_build.world.loadout.LoadoutMenuProvider;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Supplier;

public class LoadoutChest extends AbstractChestBlock<LoadoutChestEntity> implements SimpleWaterloggedBlock {
    public LoadoutChest(Properties properties) {
        super(properties, WYBRegistrate.loadoutEntity::get);

    }

    @Override
    public DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> combine(BlockState state, Level level, BlockPos pos, boolean override) {
        return DoubleBlockCombiner.Combiner::acceptNone;
    }



    @NotNull
    private static InteractionResult whenUse(Level level, BlockPos pos, Player player) {
        if (!(player instanceof ServerPlayer)) {
            return InteractionResult.SUCCESS;
        } else {
            ServerPlayer player1 = (ServerPlayer) player;
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof LoadoutChestEntity loadoutChestEntity) {
                Build resultBuild = CommonClass.post(new ServerGatherBuildComponentEvent(player1)).getResultBuild();
                WYBRegistrate.loadoutMenu.open(player1, Component.literal(""), new LoadoutMenuProvider(resultBuild, loadoutChestEntity), buf -> buf.writeJsonWithCodec(Build.CODEC, resultBuild));
            }
            return InteractionResult.CONSUME;
        }
    }

    //? >1.20.1 {


    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    public static final Supplier<MapCodec<LoadoutChest>> CODEC = () -> simpleCodec(LoadoutChest::new);
    @Override
    protected MapCodec<? extends AbstractChestBlock<LoadoutChestEntity>> codec() {
        return CODEC.get();
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        return whenUse(level, pos, player);
    }

    //?} else {

    /*@Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        return whenUse(player.level(), pos, player);
    }

    *///?}
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LoadoutChestEntity(WYBRegistrate.loadoutEntity.get(), pos, state);
    }
}
