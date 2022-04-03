package terracraft.common.utility;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LayerLightEngine;
import terracraft.TerraCraft;
import terracraft.common.block.CorruptedSnowLayer;
import terracraft.common.init.ModBlocks;

import java.util.Random;

public class CorruptionHelper extends SpreadingSnowyDirtBlock  {
    protected CorruptionHelper(Properties properties) {
        super(properties);
    }

    public static boolean canBeGrass(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.above();
        BlockState blockState2 = levelReader.getBlockState(blockPos2);
        if (blockState2.is(Blocks.SNOW) && blockState2.getValue(SnowLayerBlock.LAYERS) == 1) {
            return true;
        }
        if (blockState2.is(ModBlocks.CORRUPTED_SNOW_LAYER) && blockState2.getValue(CorruptedSnowLayer.LAYERS) == 1) {
            return true;
        }
        if (blockState2.getFluidState().getAmount() == 8) {
            return false;
        }
        int i = LayerLightEngine.getLightBlockInto(levelReader, blockState, blockPos, blockState2, blockPos2, Direction.UP, blockState2.getLightBlock(levelReader, blockPos2));
        return i < levelReader.getMaxLightLevel();
    }

    private static boolean canPropagate(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.above();
        return canBeGrass(blockState, levelReader, blockPos) && !levelReader.getFluidState(blockPos2).is(FluidTags.WATER);
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, Random random) {
        // needs to be redone better, right now for each corruption block i need a new for loop, need it to not do that for optimisation and better formatting
        // also need to make spreading slower as its too fast atm, especially vertically (up and down)

        BlockState grass = ModBlocks.CORRUPTED_GRASS.defaultBlockState();
        BlockState gravel = ModBlocks.CORRUPTED_GRAVEL.defaultBlockState();
        BlockState sand = ModBlocks.CORRUPTED_SAND.defaultBlockState();
        BlockState glass = ModBlocks.CORRUPTED_GLASS.defaultBlockState();
        BlockState sandstone = ModBlocks.CORRUPTED_SANDSTONE.defaultBlockState();
        BlockState andesite = ModBlocks.CORRUPTED_ANDESITE.defaultBlockState();
        BlockState diorite = ModBlocks.CORRUPTED_DIORITE.defaultBlockState();
        BlockState granite = ModBlocks.CORRUPTED_GRANITE.defaultBlockState();
        BlockState stone = ModBlocks.CORRUPTED_STONE.defaultBlockState();
        BlockState deepslate = ModBlocks.CORRUPTED_DEEPSLATE.defaultBlockState();
        BlockState cobblestone = ModBlocks.CORRUPTED_COBBLESTONE.defaultBlockState();
        BlockState cobbled_deepslate = ModBlocks.CORRUPTED_COBBLED_DEEPSLATE.defaultBlockState();
        BlockState coal_ore = ModBlocks.CORRUPTED_COAL_ORE.defaultBlockState();
        BlockState iron_ore = ModBlocks.CORRUPTED_IRON_ORE.defaultBlockState();
        BlockState copper_ore = ModBlocks.CORRUPTED_COPPER_ORE.defaultBlockState();
        BlockState gold_ore = ModBlocks.CORRUPTED_GOLD_ORE.defaultBlockState();
        BlockState lapis_ore = ModBlocks.CORRUPTED_LAPIS_ORE.defaultBlockState();
        BlockState redstone_ore = ModBlocks.CORRUPTED_REDSTONE_ORE.defaultBlockState();
        BlockState diamond_ore = ModBlocks.CORRUPTED_DIAMOND_ORE.defaultBlockState();
        BlockState emerald_ore = ModBlocks.CORRUPTED_EMERALD_ORE.defaultBlockState();
        BlockState deepslate_coal_ore = ModBlocks.CORRUPTED_DEEPSLATE_COAL_ORE.defaultBlockState();
        BlockState deepslate_iron_ore = ModBlocks.CORRUPTED_DEEPSLATE_IRON_ORE.defaultBlockState();
        BlockState deepslate_copper_ore = ModBlocks.CORRUPTED_DEEPSLATE_COPPER_ORE.defaultBlockState();
        BlockState deepslate_gold_ore = ModBlocks.CORRUPTED_DEEPSLATE_GOLD_ORE.defaultBlockState();
        BlockState deepslate_lapis_ore = ModBlocks.CORRUPTED_DEEPSLATE_LAPIS_ORE.defaultBlockState();
        BlockState deepslate_redstone_ore = ModBlocks.CORRUPTED_DEEPSLATE_REDSTONE_ORE.defaultBlockState();
        BlockState deepslate_diamond_ore = ModBlocks.CORRUPTED_DEEPSLATE_DIAMOND_ORE.defaultBlockState();
        BlockState deepslate_emerald_ore = ModBlocks.CORRUPTED_DEEPSLATE_EMERALD_ORE.defaultBlockState();
        BlockState snow_layer = ModBlocks.CORRUPTED_SNOW_LAYER.defaultBlockState();
        BlockState snow = ModBlocks.CORRUPTED_SNOW.defaultBlockState();
        BlockState ice = ModBlocks.CORRUPTED_ICE.defaultBlockState();
        BlockState packed_ice = ModBlocks.CORRUPTED_PACKED_ICE.defaultBlockState();

        if (!TerraCraft.CONFIG.general.disableCorruptionSpread) { // allows user to disable spreading in configs
            for (int i = 0; i < 4; ++i) {
                BlockPos blockPos2 = blockPos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                if (!serverLevel.getBlockState(blockPos2).is(Blocks.GRASS_BLOCK) || !canPropagate(grass, serverLevel, blockPos2)) continue;
                serverLevel.setBlockAndUpdate(blockPos2, grass.setValue(SNOWY, (serverLevel.getBlockState(blockPos2.above()).is(Blocks.SNOW) || serverLevel.getBlockState(blockPos2.above()).is(ModBlocks.CORRUPTED_SNOW_LAYER))));
            }
            for (int i = 0; i < 4; ++i) {
                BlockPos blockPos2 = blockPos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                if (!serverLevel.getBlockState(blockPos2).is(Blocks.DIRT) || !canPropagate(grass, serverLevel, blockPos2)) continue;
                serverLevel.setBlockAndUpdate(blockPos2, grass.setValue(SNOWY, (serverLevel.getBlockState(blockPos2.above()).is(Blocks.SNOW) || serverLevel.getBlockState(blockPos2.above()).is(ModBlocks.CORRUPTED_SNOW_LAYER))));
            }
            for (int i = 0; i < 4; ++i) {
                BlockPos blockPos2 = blockPos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                if (!serverLevel.getBlockState(blockPos2).is(Blocks.SNOW)) continue;
                serverLevel.setBlockAndUpdate(blockPos2, snow_layer.setValue(CorruptedSnowLayer.LAYERS, serverLevel.getBlockState(blockPos2).getValue(SnowLayerBlock.LAYERS)));
            }
            spreadBlock(gravel, Blocks.GRAVEL, serverLevel, blockPos, random);
            spreadBlock(sand, Blocks.SAND, serverLevel, blockPos, random);
            spreadBlock(glass, Blocks.GLASS, serverLevel, blockPos, random);
            spreadBlock(sandstone, Blocks.SANDSTONE, serverLevel, blockPos, random);
            spreadBlock(andesite, Blocks.ANDESITE, serverLevel, blockPos, random);
            spreadBlock(diorite, Blocks.DIORITE, serverLevel, blockPos, random);
            spreadBlock(granite, Blocks.GRANITE, serverLevel, blockPos, random);
            spreadBlock(stone, Blocks.STONE, serverLevel, blockPos, random);
            spreadBlock(deepslate, Blocks.DEEPSLATE, serverLevel, blockPos, random);
            spreadBlock(cobblestone, Blocks.COBBLESTONE, serverLevel, blockPos, random);
            spreadBlock(cobbled_deepslate, Blocks.COBBLED_DEEPSLATE, serverLevel, blockPos, random);
            spreadBlock(coal_ore, Blocks.COAL_ORE, serverLevel, blockPos, random);
            spreadBlock(iron_ore, Blocks.IRON_ORE, serverLevel, blockPos, random);
            spreadBlock(copper_ore, Blocks.COPPER_ORE, serverLevel, blockPos, random);
            spreadBlock(gold_ore, Blocks.GOLD_ORE, serverLevel, blockPos, random);
            spreadBlock(lapis_ore, Blocks.LAPIS_ORE, serverLevel, blockPos, random);
            spreadBlock(redstone_ore, Blocks.REDSTONE_ORE, serverLevel, blockPos, random);
            spreadBlock(diamond_ore, Blocks.DIAMOND_ORE, serverLevel, blockPos, random);
            spreadBlock(emerald_ore, Blocks.EMERALD_ORE, serverLevel, blockPos, random);
            spreadBlock(deepslate_coal_ore, Blocks.DEEPSLATE_COAL_ORE, serverLevel, blockPos, random);
            spreadBlock(deepslate_iron_ore, Blocks.DEEPSLATE_IRON_ORE, serverLevel, blockPos, random);
            spreadBlock(deepslate_copper_ore, Blocks.DEEPSLATE_COPPER_ORE, serverLevel, blockPos, random);
            spreadBlock(deepslate_gold_ore, Blocks.DEEPSLATE_GOLD_ORE, serverLevel, blockPos, random);
            spreadBlock(deepslate_lapis_ore, Blocks.DEEPSLATE_LAPIS_ORE, serverLevel, blockPos, random);
            spreadBlock(deepslate_redstone_ore, Blocks.DEEPSLATE_REDSTONE_ORE, serverLevel, blockPos, random);
            spreadBlock(deepslate_diamond_ore, Blocks.DEEPSLATE_DIAMOND_ORE, serverLevel, blockPos, random);
            spreadBlock(deepslate_emerald_ore, Blocks.DEEPSLATE_EMERALD_ORE, serverLevel, blockPos, random);
            spreadBlock(snow, Blocks.SNOW_BLOCK, serverLevel, blockPos, random);
            spreadBlock(ice, Blocks.ICE, serverLevel, blockPos, random);
            spreadBlock(packed_ice, Blocks.PACKED_ICE, serverLevel, blockPos, random);
        }
    }

    private void spreadBlock(BlockState toSpread, Block spreadTo, ServerLevel serverLevel, BlockPos blockPos, Random random) {
        for (int i = 0; i < 4; ++i) {
            BlockPos blockPos2 = blockPos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
            if (!serverLevel.getBlockState(blockPos2).is(spreadTo)) continue;
            serverLevel.setBlockAndUpdate(blockPos2, toSpread.setValue(SNOWY, (serverLevel.getBlockState(blockPos2.above()).is(Blocks.SNOW)  || serverLevel.getBlockState(blockPos2.above()).is(ModBlocks.CORRUPTED_SNOW_LAYER))));
        }
    }
}
