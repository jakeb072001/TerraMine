package terramine.common.block.fluids;

import com.google.common.collect.UnmodifiableIterator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.redstone.Orientation;
import org.jetbrains.annotations.Nullable;
import terramine.datagen.ModTags;

// todo: make look a bit fancier, also need to make items float better
public class HoneyFluidBlock extends LiquidBlock {
    public HoneyFluidBlock(FlowingFluid flowingFluid, Properties properties) {
        super(flowingFluid, properties);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        // slowdown player
    }

    @Override
    protected void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (this.shouldSpreadLiquid(level, blockPos)) {
            level.scheduleTick(blockPos, blockState.getFluidState().getType(), this.fluid.getTickDelay(level));
        }
    }

    @Override
    protected void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, @Nullable Orientation orientation, boolean bl) {
        if (this.shouldSpreadLiquid(level, blockPos)) {
            level.scheduleTick(blockPos, blockState.getFluidState().getType(), this.fluid.getTickDelay(level));
        }
    }

    private boolean shouldSpreadLiquid(Level level, BlockPos blockPos) {
        if (this.fluid.is(ModTags.HONEY_FLUID)) {
            for (Direction direction : POSSIBLE_FLOW_DIRECTIONS) {
                BlockPos blockPos2 = blockPos.relative(direction.getOpposite());
                //if (level.getFluidState(blockPos2).is(FluidTags.WATER)) { // todo: currently honey is in the water FluidTag, causing issues, so just manually checking for now, need custom physics at some point (also for shimmer)
                if (level.getFluidState(blockPos2).is(Fluids.WATER) || level.getFluidState(blockPos2).is(Fluids.FLOWING_WATER)) {
                    //Block block = level.getFluidState(blockPos).isSource() ? Blocks.HONEY_BLOCK : ModBlocks.HONEY_LAYER; // todo: maybe add honey layers?
                    Block block = level.getFluidState(blockPos).isSource() ? Blocks.HONEY_BLOCK : Blocks.AIR;
                    level.setBlockAndUpdate(blockPos, block.defaultBlockState());
                    level.playSound(null, blockPos, SoundEvents.HONEY_BLOCK_PLACE, SoundSource.BLOCKS);
                    return false;
                }
            }
        }

        return true;
    }
}
