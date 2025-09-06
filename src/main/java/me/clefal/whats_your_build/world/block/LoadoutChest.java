package me.clefal.whats_your_build.world.block;

import com.mojang.serialization.MapCodec;
import me.clefal.whats_your_build.CommonClass;
import me.clefal.whats_your_build.data.buildobject.Build;
import me.clefal.whats_your_build.event.server.ServerGatherBuildComponentEvent;
import me.clefal.whats_your_build.loaders.WYBRegistrate;
import me.clefal.whats_your_build.world.block.entity.LoadoutChestEntity;
import me.clefal.whats_your_build.world.loadout.LoadoutMenuProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class LoadoutChest extends AbstractChestBlock<LoadoutChestEntity> implements SimpleWaterloggedBlock {
    public static final Supplier<MapCodec<LoadoutChest>> CODEC = () -> simpleCodec(LoadoutChest::new);

    public LoadoutChest(Properties properties) {
        super(properties, WYBRegistrate.loadoutEntity::get);

    }

    @Override
    protected MapCodec<? extends AbstractChestBlock<LoadoutChestEntity>> codec() {
        return CODEC.get();
    }


    @Override
    public DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> combine(BlockState state, Level level, BlockPos pos, boolean override) {
        return DoubleBlockCombiner.Combiner::acceptNone;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof LoadoutChestEntity loadoutChestEntity) {
                Build resultBuild = CommonClass.post(new ServerGatherBuildComponentEvent(((ServerPlayer) player))).getResultBuild();
                player.openMenu(new LoadoutMenuProvider(resultBuild, loadoutChestEntity), buf -> buf.writeJsonWithCodec(Build.CODEC, resultBuild));
            }


            return InteractionResult.CONSUME;
        }
    }


    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LoadoutChestEntity(WYBRegistrate.loadoutEntity.get(), pos, state);
    }
}
