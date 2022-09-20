package terramine.common.utility;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LayerLightEngine;
import org.jetbrains.annotations.NotNull;
import terramine.TerraMine;
import terramine.common.block.CrimsonSnowLayer;
import terramine.common.init.ModBlocks;

public class CrimsonHelper extends SpreadingSnowyDirtBlock  {
    protected CrimsonHelper(Properties properties) {
        super(properties);
    }

    public static boolean canNotBeGrass(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.above();
        BlockState blockState2 = levelReader.getBlockState(blockPos2);
        if (blockState2.is(Blocks.SNOW) && blockState2.getValue(SnowLayerBlock.LAYERS) == 1) {
            return false;
        }
        if (blockState2.is(ModBlocks.CRIMSON_SNOW_LAYER) && blockState2.getValue(CrimsonSnowLayer.LAYERS) == 1) {
            return false;
        }
        if (blockState2.getFluidState().getAmount() == 8) {
            return true;
        }
        int i = LayerLightEngine.getLightBlockInto(levelReader, blockState, blockPos, blockState2, blockPos2, Direction.UP, blockState2.getLightBlock(levelReader, blockPos2));
        return i >= levelReader.getMaxLightLevel();
    }

    private static boolean canNotPropagate(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.above();
        return canNotBeGrass(blockState, levelReader, blockPos) || levelReader.getFluidState(blockPos2).is(FluidTags.WATER);
    }

    @Override
    public void randomTick(@NotNull BlockState blockState, @NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos, @NotNull RandomSource random) {
        if (!TerraMine.CONFIG.general.disableEvilSpread) { // allows user to disable spreading in configs
            BlockState grass = ModBlocks.CRIMSON_GRASS.defaultBlockState();
            BlockState snow_layer = ModBlocks.CRIMSON_SNOW_LAYER.defaultBlockState();

            for (int i = 0; i < 4; ++i) { // crimson grass spread to grass and dirt
                BlockPos blockPos2 = blockPos.offset(random.nextInt(3) - 1, random.nextInt(3) - 1, random.nextInt(3) - 1);
                if ((!serverLevel.getBlockState(blockPos2).is(Blocks.GRASS_BLOCK) && !serverLevel.getBlockState(blockPos2).is(Blocks.DIRT)) || canNotPropagate(grass, serverLevel, blockPos2)) continue;
                serverLevel.setBlockAndUpdate(blockPos2, grass.setValue(SNOWY, (serverLevel.getBlockState(blockPos2.above()).is(Blocks.SNOW) || serverLevel.getBlockState(blockPos2.above()).is(ModBlocks.CRIMSON_SNOW_LAYER))));
                CorruptionHelper.spreadBiome(serverLevel, blockPos2, true);
            }
            for (int i = 0; i < 4; ++i) { // spread layered snow
                BlockPos blockPos2 = blockPos.offset(random.nextInt(3) - 1, random.nextInt(3) - 1, random.nextInt(3) - 1);
                if (!serverLevel.getBlockState(blockPos2).is(Blocks.SNOW)) continue;
                serverLevel.setBlockAndUpdate(blockPos2, snow_layer.setValue(CrimsonSnowLayer.LAYERS, serverLevel.getBlockState(blockPos2).getValue(SnowLayerBlock.LAYERS)));
            }

            spreadBlock(ModBlocks.CRIMSON_GRAVEL, Blocks.GRAVEL, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CRIMSON_SAND, Blocks.SAND, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CRIMSON_GLASS, Blocks.GLASS, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CRIMSON_SANDSTONE, Blocks.SANDSTONE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CRIMSON_ANDESITE, Blocks.ANDESITE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CRIMSON_DIORITE, Blocks.DIORITE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CRIMSON_GRANITE, Blocks.GRANITE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CRIMSON_STONE, Blocks.STONE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CRIMSON_DEEPSLATE, Blocks.DEEPSLATE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CRIMSON_COBBLESTONE, Blocks.COBBLESTONE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CRIMSON_COBBLED_DEEPSLATE, Blocks.COBBLED_DEEPSLATE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CRIMSON_COAL_ORE, Blocks.COAL_ORE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CRIMSON_IRON_ORE, Blocks.IRON_ORE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CRIMSON_COPPER_ORE, Blocks.COPPER_ORE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CRIMSON_GOLD_ORE, Blocks.GOLD_ORE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CRIMSON_LAPIS_ORE, Blocks.LAPIS_ORE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CRIMSON_REDSTONE_ORE, Blocks.REDSTONE_ORE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CRIMSON_DIAMOND_ORE, Blocks.DIAMOND_ORE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CRIMSON_EMERALD_ORE, Blocks.EMERALD_ORE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CRIMSON_DEEPSLATE_COAL_ORE, Blocks.DEEPSLATE_COAL_ORE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CRIMSON_DEEPSLATE_IRON_ORE, Blocks.DEEPSLATE_IRON_ORE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CRIMSON_DEEPSLATE_COPPER_ORE, Blocks.DEEPSLATE_COPPER_ORE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CRIMSON_DEEPSLATE_GOLD_ORE, Blocks.DEEPSLATE_GOLD_ORE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CRIMSON_DEEPSLATE_LAPIS_ORE, Blocks.DEEPSLATE_LAPIS_ORE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CRIMSON_DEEPSLATE_REDSTONE_ORE, Blocks.DEEPSLATE_REDSTONE_ORE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CRIMSON_DEEPSLATE_DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CRIMSON_DEEPSLATE_EMERALD_ORE, Blocks.DEEPSLATE_EMERALD_ORE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CRIMSON_SNOW, Blocks.SNOW_BLOCK, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CRIMSON_ICE, Blocks.ICE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CRIMSON_PACKED_ICE, Blocks.PACKED_ICE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CRIMSON_BLUE_ICE, Blocks.BLUE_ICE, serverLevel, blockPos, random);
        }
    }

    private void spreadBlock(Block toSpread, Block spreadTo, ServerLevel serverLevel, BlockPos blockPos, RandomSource random) {
        for (int i = 0; i < 4; ++i) {
            if (random.nextInt(TerraMine.CONFIG.general.evilSpreadRarity + 1) == 1) {
                BlockState block = toSpread.defaultBlockState();
                BlockPos blockPos2 = blockPos.offset(random.nextInt(3) - 1, random.nextInt(3) - 1, random.nextInt(3) - 1);
                if (!serverLevel.getBlockState(blockPos2).is(spreadTo)) continue;
                serverLevel.setBlockAndUpdate(blockPos2, block.setValue(SNOWY, (serverLevel.getBlockState(blockPos2.above()).is(Blocks.SNOW) || serverLevel.getBlockState(blockPos2.above()).is(ModBlocks.CRIMSON_SNOW_LAYER))));
                CorruptionHelper.spreadBiome(serverLevel, blockPos2, true);
            }
        }
    }
}
