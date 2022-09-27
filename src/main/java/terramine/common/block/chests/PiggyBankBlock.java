package terramine.common.block.chests;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import terramine.TerraMine;
import terramine.common.entity.block.ChestEntity;
import terramine.common.entity.block.PiggyBankEntity;

import java.util.function.BiPredicate;
import java.util.function.Supplier;

public class PiggyBankBlock extends BaseChest {
    protected static final VoxelShape SHAPE_X = Block.box(3.0, 0.0, 5.0, 13.0, 6.0, 11.0);
    protected static final VoxelShape SHAPE_Z = Block.box(5.0, 0.0, 3.0, 11.0, 6.0, 13.0);

    public PiggyBankBlock(Properties properties, Supplier<BlockEntityType<? extends ChestBlockEntity>> supplier) {
        super(properties, false, supplier);
    }

    @Override
    public VoxelShape getShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext collisionContext) {
        Direction direction = blockState.getValue(FACING);
        return direction.getAxis() == Direction.Axis.X ? SHAPE_X : SHAPE_Z;
    }

    @Override
    public DoubleBlockCombiner.NeighborCombineResult<? extends ChestEntity> combine(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos2, boolean bl) {
        BiPredicate<LevelAccessor, BlockPos> biPredicate = (levelAccessor, blockPos) -> false;
        return DoubleBlockCombiner.combineWithNeigbour((BlockEntityType)this.blockEntityType.get(), BaseChest::getBlockType, BaseChest::getConnectedDirection, FACING, blockState, level, blockPos2, biPredicate);
    }

    @Override
    public ResourceLocation getTexture() {
        return TerraMine.id("block/chests/player/piggy_bank/piggy_bank");
    }

    @Override
    public RenderShape getRenderShape(@NotNull BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new PiggyBankEntity(blockPos, blockState);
    }

    @Override
    public BlockEntityType<? extends PiggyBankEntity> blockEntityType() {
        return (BlockEntityType)this.blockEntityType.get();
    }
}
