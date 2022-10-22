package terramine.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class RainCloudBlock extends Block {
    public RainCloudBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull Random randomSource) {
        BlockState blockState2 = level.getBlockState(blockPos.below());
        if (!blockState2.isFaceSturdy(level, blockPos.below(), Direction.getRandom(randomSource))) {
            for (int i = 0; i < 2; i++) {
                double d = blockPos.getX() + randomSource.nextDouble();
                double e = blockPos.getY() - 0.05;
                double f = blockPos.getZ() + randomSource.nextDouble();
                level.addParticle(ParticleTypes.FALLING_WATER, d, e, f, 0, 0, 0);
            }
        }
    }
}
