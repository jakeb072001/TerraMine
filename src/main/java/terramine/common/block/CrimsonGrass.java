package terramine.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.NotNull;
import terramine.common.utility.CrimsonHelper;

import java.util.List;
import java.util.Optional;

public class CrimsonGrass extends CrimsonHelper implements BonemealableBlock {
    public CrimsonGrass(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState, boolean bl) {
        return levelReader.getBlockState(blockPos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel serverLevel, @NotNull RandomSource random, BlockPos blockPos, @NotNull BlockState blockState) {
        BlockPos blockPos2 = blockPos.above();
        BlockState blockState2 = Blocks.GRASS.defaultBlockState();
        Optional<Holder.Reference<PlacedFeature>> optional = serverLevel.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolder(VegetationPlacements.GRASS_BONEMEAL);

        label49:
        for(int i = 0; i < 128; ++i) {
            BlockPos blockPos3 = blockPos2;

            for(int j = 0; j < i / 16; ++j) {
                blockPos3 = blockPos3.offset(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
                if (!serverLevel.getBlockState(blockPos3.below()).is(this) || serverLevel.getBlockState(blockPos3).isCollisionShapeFullBlock(serverLevel, blockPos3)) {
                    continue label49;
                }
            }

            BlockState blockState3 = serverLevel.getBlockState(blockPos3);
            if (blockState3.is(blockState2.getBlock()) && random.nextInt(10) == 0) {
                ((BonemealableBlock)blockState2.getBlock()).performBonemeal(serverLevel, random, blockPos3, blockState3);
            }

            if (blockState3.isAir()) {
                Holder holder;
                if (random.nextInt(8) == 0) {
                    List<ConfiguredFeature<?, ?>> list = serverLevel.getBiome(blockPos3).value().getGenerationSettings().getFlowerFeatures();
                    if (list.isEmpty()) {
                        continue;
                    }

                    holder = ((RandomPatchConfiguration)((ConfiguredFeature)list.get(0)).config()).feature();
                } else {
                    if (!optional.isPresent()) {
                        continue;
                    }

                    holder = optional.get();
                }

                ((PlacedFeature)holder.value()).place(serverLevel, serverLevel.getChunkSource().getGenerator(), random, blockPos3);
            }
        }
    }

    @Override
    public void randomTick(@NotNull BlockState blockState, @NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos, @NotNull RandomSource random) {
        super.randomTick(blockState, serverLevel, blockPos, random);
        if (canNotBeGrass(blockState, serverLevel, blockPos)) {
            serverLevel.setBlockAndUpdate(blockPos, Blocks.DIRT.defaultBlockState());
        }
    }
}
