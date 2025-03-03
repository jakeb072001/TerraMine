package terramine.common.block.fluids;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import terramine.common.utility.CustomCauldronInteractions;

public class ShimmerCauldronBlock extends AbstractCauldronBlock {
    public static final MapCodec<ShimmerCauldronBlock> CODEC = simpleCodec(ShimmerCauldronBlock::new);

    public @NotNull MapCodec<ShimmerCauldronBlock> codec() {
        return CODEC;
    }

    public ShimmerCauldronBlock(BlockBehaviour.Properties properties) {
        super(properties, CustomCauldronInteractions.SHIMMER);
    }

    protected double getContentHeight(BlockState blockState) {
        return 0.9375;
    }

    public boolean isFull(BlockState blockState) {
        return true;
    }

    protected void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        if (this.isFull(blockState)) {
            ShimmerFluidBlock.handleShimmer(level, blockPos, entity);
        }
    }

    protected int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos blockPos) {
        return 4;
    }
}
