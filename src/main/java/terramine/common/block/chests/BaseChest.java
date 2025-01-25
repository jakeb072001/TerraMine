package terramine.common.block.chests;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import terramine.common.entity.block.ChestEntity;

import java.util.function.BiPredicate;
import java.util.function.Supplier;

public class BaseChest extends ChestBlock {
    public static final BooleanProperty TRAPPED = BooleanProperty.create("trapped");

    public BaseChest(Properties properties, boolean trapped, Supplier<BlockEntityType<? extends ChestBlockEntity>> supplier) {
        super(supplier, properties);
        registerDefaultState(defaultBlockState().setValue(TRAPPED, trapped));
    }

    public ResourceLocation getTexture() {
        return null;
    }

    @Override
    public @NotNull InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (level instanceof ServerLevel serverLevel) {
            MenuProvider menuProvider = blockState.getMenuProvider(level, blockPos);
            player.openMenu(menuProvider);
            player.awardStat(this.getOpenChestStat());
            PiglinAi.angerNearbyPiglins(serverLevel, player, true);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void tick(@NotNull BlockState blockState, ServerLevel serverLevel, @NotNull BlockPos blockPos, @NotNull RandomSource randomSource) {
        BlockEntity blockEntity = serverLevel.getBlockEntity(blockPos);
        if (blockEntity instanceof ChestEntity) {
            ((ChestEntity)blockEntity).recheckOpen();
        }
    }

    @Override
    public @NotNull BlockState updateShape(BlockState blockState, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos blockPos, Direction direction, BlockPos blockPos2, BlockState blockState2, RandomSource randomSource) {
        if (direction == Direction.DOWN && !this.canSurvive(blockState, levelReader, blockPos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(blockState, levelReader, scheduledTickAccess, blockPos, direction, blockPos2, blockState2, randomSource);
    }

    @Override
    public boolean canSurvive(@NotNull BlockState blockState, @NotNull LevelReader levelReader, BlockPos blockPos) {
        return BaseChest.canSupportCenter(levelReader, blockPos.below(), Direction.UP);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        Direction direction = blockPlaceContext.getHorizontalDirection().getOpposite();
        FluidState fluidState = blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos());
        return this.defaultBlockState().setValue(FACING, direction).setValue(TYPE, ChestType.SINGLE).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public DoubleBlockCombiner.@NotNull NeighborCombineResult<? extends ChestEntity> combine(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos2, boolean bl) {
        BiPredicate<LevelAccessor, BlockPos> biPredicate = bl ? (levelAccessor, blockPos) -> false : BaseChest::isChestBlockedAt;
        return DoubleBlockCombiner.combineWithNeigbour((BlockEntityType)this.blockEntityType.get(), BaseChest::getBlockType, BaseChest::getConnectedDirection, FACING, blockState, level, blockPos2, biPredicate);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, TYPE, WATERLOGGED, TRAPPED);
    }

    @Override
    protected @NotNull Stat<ResourceLocation> getOpenChestStat() {
        if (this.defaultBlockState().getValue(TRAPPED)) {
            return Stats.CUSTOM.get(Stats.TRIGGER_TRAPPED_CHEST);
        } else {
            return Stats.CUSTOM.get(Stats.OPEN_CHEST);
        }
    }

    @Override
    public boolean isSignalSource(@NotNull BlockState blockState) {
        return blockState.getValue(TRAPPED);
    }

    @Override
    public int getSignal(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull Direction direction) {
        if (blockState.getValue(TRAPPED)) {
            return Mth.clamp(ChestEntity.getOpenCount(blockGetter, blockPos), 0, 15);
        } else {
            return 0;
        }
    }

    @Override
    public int getDirectSignal(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull Direction direction) {
        if (direction == Direction.UP && blockState.getValue(TRAPPED)) {
            return blockState.getSignal(blockGetter, blockPos, direction);
        }
        return 0;
    }

    public @NotNull BlockEntityType<? extends ChestEntity> blockEntityType() {
        return (BlockEntityType)this.blockEntityType.get();
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState blockState, @NotNull BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? BaseChest.createTickerHelper(blockEntityType, this.blockEntityType(), ChestEntity::clientTick) : null;
    }
}
