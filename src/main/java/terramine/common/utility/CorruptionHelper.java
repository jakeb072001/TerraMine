package terramine.common.utility;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LightEngine;
import org.jetbrains.annotations.NotNull;
import terramine.TerraMine;
import terramine.common.block.evil.CorruptedSnowLayer;
import terramine.common.init.ModBlocks;
import terramine.common.init.ModCommands;
import terramine.common.init.ModComponents;
import terramine.datagen.ModBiomes;

import static terramine.common.utility.Utilities.setBiome;
import static terramine.common.utility.Utilities.updateChunkAfterBiomeChange;

// todo: have a way to increase biome spread speed (for some events such as entering hardcore mode or for killing Plantera slowdown the spread again)
// todo: have a way for the corruption to spread up trees or something, they remain green at the top while everything else is tinted correctly,
//  maybe for spreadBiome call have a for loop to spread biome for a certain amount of blocks around blockPos
public class CorruptionHelper extends SpreadingSnowyDirtBlock  {
    protected CorruptionHelper(Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull MapCodec<? extends SpreadingSnowyDirtBlock> codec() {
        return GrassBlock.CODEC;
    }

    protected static boolean canBeGrass(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.above();
        BlockState blockState2 = levelReader.getBlockState(blockPos2);
        if (blockState2.is(Blocks.SNOW) && blockState2.getValue(SnowLayerBlock.LAYERS) == 1) {
            return true;
        }
        if (blockState2.is(ModBlocks.CORRUPTED_SNOW_LAYER.BLOCK) && blockState2.getValue(CorruptedSnowLayer.LAYERS) == 1) {
            return true;
        }
        if (blockState2.getFluidState().getAmount() == 8) {
            return false;
        }
        int i = LightEngine.getLightBlockInto(blockState, blockState2, Direction.UP, blockState2.getLightBlock());
        return i < 15;
    }

    protected static boolean canPropagate(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.above();
        return canBeGrass(blockState, levelReader, blockPos) && !levelReader.getFluidState(blockPos2).is(FluidTags.WATER);
    }

    @Override
    public void randomTick(@NotNull BlockState blockState, @NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos, @NotNull RandomSource randomSource) {
        if (serverLevel.getGameRules().getBoolean(ModCommands.EVIL_SPREAD)) { // allows user to disable spreading with gamerule
            BlockState snow_layer = ModBlocks.CORRUPTED_SNOW_LAYER.BLOCK.defaultBlockState();

            for (int i = 0; i < 4; ++i) { // spread layered snow
                BlockPos blockPos2 = blockPos.offset(randomSource.nextInt(3) - 1, randomSource.nextInt(3) - 1, randomSource.nextInt(3) - 1);
                if (serverLevel.getBlockState(blockPos2).is(Blocks.SNOW)) {
                    serverLevel.setBlockAndUpdate(blockPos2, snow_layer.setValue(CorruptedSnowLayer.LAYERS, serverLevel.getBlockState(blockPos2).getValue(SnowLayerBlock.LAYERS)));
                }
            }

            spreadBlockGrass(Blocks.DIRT, serverLevel, blockPos, randomSource);
            spreadBlockGrass(Blocks.GRASS_BLOCK, serverLevel, blockPos, randomSource);

            if (ModComponents.HARDMODE.get(serverLevel.getLevelData()).get()) {
                ModBlocks.corruptionSpreadBlocks.forEach((blockItemRegister, block) -> {
                    if (!blockItemRegister.equals(ModBlocks.CORRUPTED_GRASS.BLOCK) && !blockItemRegister.equals(ModBlocks.CORRUPTED_SNOW_LAYER.BLOCK)) {
                        spreadBlock(blockItemRegister, block, serverLevel, blockPos, randomSource);
                    }
                });
            }
        }
    }

    private void spreadBlock(Block toSpread, Block spreadTo, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        for (int i = 0; i < 4; ++i) {
            if (randomSource.nextInt(TerraMine.CONFIG.general.evilSpreadRarity + 1) == 1) {
                BlockState block = toSpread.defaultBlockState();
                BlockPos blockPos2 = blockPos.offset(randomSource.nextInt(3) - 1, randomSource.nextInt(3) - 1, randomSource.nextInt(3) - 1);
                if (serverLevel.getBlockState(blockPos2).is(spreadTo)) {
                    serverLevel.setBlockAndUpdate(blockPos2, block.setValue(SNOWY, isSnowySetting(serverLevel.getBlockState(blockPos2.above()))));
                    spreadBiome(serverLevel, blockPos2, false);
                }
            }
        }
    }

    private void spreadBlockGrass(Block spreadTo, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        for (int i = 0; i < 4; ++i) {
            if (randomSource.nextInt(TerraMine.CONFIG.general.evilSpreadRarity / 3 + 1) == 1) {
                BlockState block = ModBlocks.CORRUPTED_GRASS.BLOCK.defaultBlockState();
                BlockPos blockPos2 = blockPos.offset(randomSource.nextInt(3) - 1, randomSource.nextInt(3) - 1, randomSource.nextInt(3) - 1);
                if (serverLevel.getBlockState(blockPos2).is(spreadTo) && canPropagate(block, serverLevel, blockPos2)) {
                    serverLevel.setBlockAndUpdate(blockPos2, block.setValue(SNOWY, isSnowySetting(serverLevel.getBlockState(blockPos2.above()))));
                    spreadBiome(serverLevel, blockPos2, false);
                }
            }
        }
    }

    public static void spreadBiome(ServerLevel serverLevel, BlockPos blockPos, boolean isCrimson) {
        if (!serverLevel.getBiome(blockPos).is(ModBiomes.CORRUPTION) && !serverLevel.getBiome(blockPos).is(ModBiomes.CORRUPTION_DESERT)
                && !serverLevel.getBiome(blockPos).is(ModBiomes.CRIMSON) && !serverLevel.getBiome(blockPos).is(ModBiomes.CRIMSON_DESERT)) {
            if (!isCrimson) {
                if (serverLevel.getBiome(blockPos).is(Biomes.DESERT)) {
                    setBiome(serverLevel, blockPos, ModBiomes.CORRUPTION_DESERT);
                } else {
                    setBiome(serverLevel, blockPos, ModBiomes.CORRUPTION);
                }
            } else {
                if (serverLevel.getBiome(blockPos).is(Biomes.DESERT)) {
                    setBiome(serverLevel, blockPos, ModBiomes.CRIMSON_DESERT);
                } else {
                    setBiome(serverLevel, blockPos, ModBiomes.CRIMSON);
                }
            }
            updateChunkAfterBiomeChange(serverLevel, new ChunkPos(blockPos));
        }
    }
}
