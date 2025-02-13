package terramine.common.block.fluids;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;
import terramine.common.init.ModBlocks;
import terramine.common.init.ModFluids;
import terramine.common.init.ModItems;

public abstract class ShimmerFluid extends FlowingFluid {
    @Override
    public @NotNull Fluid getSource() {
        return ModFluids.STILL_SHIMMER;
    }

    @Override
    public @NotNull Fluid getFlowing() {
        return ModFluids.FLOWING_SHIMMER;
    }

    @Override
    public @NotNull Item getBucket() {
        return ModItems.SHIMMER_BUCKET;
    }

    @Override
    protected boolean canBeReplacedWith(FluidState fluidState, BlockGetter blockGetter, BlockPos blockPos, Fluid fluid, Direction direction) {
        return false;
    }

    @Override
    protected void beforeDestroyingBlock(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {}

    @Override
    protected int getSlopeFindDistance(LevelReader world) {
        return 4;
    }

    @Override
    protected int getDropOff(LevelReader world) {
        return 1;
    }

    @Override
    public int getTickDelay(LevelReader levelReader) {
        return 5;
    }

    @Override
    protected float getExplosionResistance() {
        return 100F;
    }

    @Override
    protected @NotNull BlockState createLegacyBlock(FluidState fluidState) {
        return ModBlocks.SHIMMER_BLOCK.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(fluidState));
    }

    @Override
    public boolean isSame(Fluid fluid) {
        return fluid == ModFluids.STILL_SHIMMER || fluid == ModFluids.FLOWING_SHIMMER;
    }

    public static class Flowing extends ShimmerFluid {
        @Override
        protected boolean canConvertToSource(ServerLevel serverLevel) {
            return false;
        }

        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        public int getAmount(FluidState fluidState) {
            return fluidState.getValue(LEVEL);
        }

        public boolean isSource(FluidState fluidState) {
            return false;
        }
    }

    public static class Source extends ShimmerFluid {
        @Override
        protected boolean canConvertToSource(ServerLevel serverLevel) {
            return false;
        }

        public int getAmount(FluidState fluidState) {
            return 8;
        }

        public boolean isSource(FluidState fluidState) {
            return true;
        }
    }
}
