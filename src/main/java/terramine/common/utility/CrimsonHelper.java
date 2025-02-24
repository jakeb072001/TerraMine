package terramine.common.utility;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LightEngine;
import org.jetbrains.annotations.NotNull;
import terramine.TerraMine;
import terramine.common.block.CrimsonSnowLayer;
import terramine.common.init.ModBlocks;
import terramine.common.init.ModCommands;
import terramine.common.init.ModComponents;

// todo: have a way to increase biome spread speed (for some events such as entering hardcore mode or for killing Plantera slowdown the spread again)
// todo: have a way for the corruption to spread up trees or something, they remain green at the top while everything else is tinted correctly

// todo: have a way to undo the evil spread (using either a command or using the Clentaminator),
//  both return blocks to original state (easy, will need a non evil version of evil ores though) and return biome to original biome. Use level().getNoiseBiome(x, y, z) to get the original biome (even replaces original evil biomes!)
public class CrimsonHelper extends SpreadingSnowyDirtBlock  {
    protected CrimsonHelper(Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull MapCodec<? extends SpreadingSnowyDirtBlock> codec() {
        return GrassBlock.CODEC;
    }

    public static boolean canBeGrass(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.above();
        BlockState blockState2 = levelReader.getBlockState(blockPos2);
        if (blockState2.is(Blocks.SNOW) && blockState2.getValue(SnowLayerBlock.LAYERS) == 1) {
            return true;
        }
        if (blockState2.is(ModBlocks.CRIMSON_SNOW_LAYER.BLOCK) && blockState2.getValue(CrimsonSnowLayer.LAYERS) == 1) {
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
            BlockState snow_layer = ModBlocks.CRIMSON_SNOW_LAYER.BLOCK.defaultBlockState();

            for (int i = 0; i < 4; ++i) { // spread layered snow
                BlockPos blockPos2 = blockPos.offset(randomSource.nextInt(3) - 1, randomSource.nextInt(3) - 1, randomSource.nextInt(3) - 1);
                if (serverLevel.getBlockState(blockPos2).is(Blocks.SNOW)) {
                    serverLevel.setBlockAndUpdate(blockPos2, snow_layer.setValue(CrimsonSnowLayer.LAYERS, serverLevel.getBlockState(blockPos2).getValue(SnowLayerBlock.LAYERS)));
                }
            }

            spreadBlockGrass(ModBlocks.CRIMSON_GRASS, Blocks.DIRT, serverLevel, blockPos, randomSource);
            spreadBlockGrass(ModBlocks.CRIMSON_GRASS, Blocks.GRASS_BLOCK, serverLevel, blockPos, randomSource);
            if (ModComponents.HARDMODE.get(serverLevel.getLevelData()).get()) {
                spreadBlock(ModBlocks.CRIMSON_GRAVEL, Blocks.GRAVEL, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_SAND, Blocks.SAND, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_GLASS, Blocks.GLASS, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_SANDSTONE, Blocks.SANDSTONE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_ANDESITE, Blocks.ANDESITE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_DIORITE, Blocks.DIORITE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_GRANITE, Blocks.GRANITE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_STONE, Blocks.STONE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_DEEPSLATE, Blocks.DEEPSLATE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_COBBLESTONE, Blocks.COBBLESTONE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_COBBLED_DEEPSLATE, Blocks.COBBLED_DEEPSLATE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_COAL_ORE, Blocks.COAL_ORE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_IRON_ORE, Blocks.IRON_ORE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_LEAD_ORE, ModBlocks.LEAD_ORE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_COPPER_ORE, Blocks.COPPER_ORE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_TIN_ORE, ModBlocks.TIN_ORE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_SILVER_ORE, ModBlocks.SILVER_ORE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_TUNGSTEN_ORE, ModBlocks.TUNGSTEN_ORE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_GOLD_ORE, Blocks.GOLD_ORE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_PLATINUM_ORE, ModBlocks.PLATINUM_ORE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_LAPIS_ORE, Blocks.LAPIS_ORE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_REDSTONE_ORE, Blocks.REDSTONE_ORE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_DIAMOND_ORE, Blocks.DIAMOND_ORE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_EMERALD_ORE, Blocks.EMERALD_ORE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_DEEPSLATE_COAL_ORE, Blocks.DEEPSLATE_COAL_ORE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_DEEPSLATE_IRON_ORE, Blocks.DEEPSLATE_IRON_ORE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_DEEPSLATE_LEAD_ORE, ModBlocks.DEEPSLATE_LEAD_ORE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_DEEPSLATE_COPPER_ORE, Blocks.DEEPSLATE_COPPER_ORE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_DEEPSLATE_TIN_ORE, ModBlocks.DEEPSLATE_TIN_ORE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_DEEPSLATE_SILVER_ORE, ModBlocks.DEEPSLATE_SILVER_ORE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_DEEPSLATE_TUNGSTEN_ORE, ModBlocks.DEEPSLATE_TUNGSTEN_ORE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_DEEPSLATE_GOLD_ORE, Blocks.DEEPSLATE_GOLD_ORE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_DEEPSLATE_PLATINUM_ORE, ModBlocks.DEEPSLATE_PLATINUM_ORE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_DEEPSLATE_LAPIS_ORE, Blocks.DEEPSLATE_LAPIS_ORE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_DEEPSLATE_REDSTONE_ORE, Blocks.DEEPSLATE_REDSTONE_ORE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_DEEPSLATE_DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_DEEPSLATE_EMERALD_ORE, Blocks.DEEPSLATE_EMERALD_ORE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_SNOW, Blocks.SNOW_BLOCK, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_ICE, Blocks.ICE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_PACKED_ICE, Blocks.PACKED_ICE, serverLevel, blockPos, randomSource);
                spreadBlock(ModBlocks.CRIMSON_BLUE_ICE, Blocks.BLUE_ICE, serverLevel, blockPos, randomSource);
            }
        }
    }
    private void spreadBlock(BlockItemRegister toSpread, BlockItemRegister spreadTo, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        spreadBlock(toSpread, spreadTo.BLOCK, serverLevel, blockPos, randomSource);
    }
    private void spreadBlock(BlockItemRegister toSpread, Block spreadTo, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        for (int i = 0; i < 4; ++i) {
            if (randomSource.nextInt(TerraMine.CONFIG.general.evilSpreadRarity + 1) == 1) {
                BlockState block = toSpread.BLOCK.defaultBlockState();
                BlockPos blockPos2 = blockPos.offset(randomSource.nextInt(3) - 1, randomSource.nextInt(3) - 1, randomSource.nextInt(3) - 1);
                if (serverLevel.getBlockState(blockPos2).is(spreadTo)) {
                    serverLevel.setBlockAndUpdate(blockPos2, block.setValue(SNOWY, isSnowySetting(serverLevel.getBlockState(blockPos2.above()))));
                    CorruptionHelper.spreadBiome(serverLevel, blockPos2, true);
                }
            }
        }
    }

    private void spreadBlockGrass(BlockItemRegister toSpread, Block spreadTo, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        for (int i = 0; i < 4; ++i) {
            if (randomSource.nextInt(TerraMine.CONFIG.general.evilSpreadRarity / 3 + 1) == 1) {
                BlockState block = toSpread.BLOCK.defaultBlockState();
                BlockPos blockPos2 = blockPos.offset(randomSource.nextInt(3) - 1, randomSource.nextInt(3) - 1, randomSource.nextInt(3) - 1);
                if (serverLevel.getBlockState(blockPos2).is(spreadTo) && canPropagate(block, serverLevel, blockPos2)) {
                    serverLevel.setBlockAndUpdate(blockPos2, block.setValue(SNOWY, isSnowySetting(serverLevel.getBlockState(blockPos2.above()))));
                    CorruptionHelper.spreadBiome(serverLevel, blockPos2, true);
                }
            }
        }
    }
}
