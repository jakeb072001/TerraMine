package terramine.common.world;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.RandomizableContainer;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import terramine.TerraMine;
import terramine.common.entity.mobs.hardmode.MimicEntity;
import terramine.common.init.ModBlocks;
import terramine.common.init.ModComponents;
import terramine.common.init.ModEntities;
import terramine.common.init.ModLootTables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class SurfaceChestFeature extends Feature<NoneFeatureConfiguration> {

	public SurfaceChestFeature() {
		super(NoneFeatureConfiguration.CODEC);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		BlockPos origin = context.origin();
		WorldGenLevel level = context.level();
		RandomSource random = context.random();

		List<BlockPos> positions = new ArrayList<>();
		BlockPos.betweenClosedStream(origin.offset(-5, -5, -5), origin.offset(5, 5, 5))
				.filter(pos -> (blockWaterCheck(pos, level) || blockEmptyCheck(pos, level)) && level.getBlockState(pos.below()).blocksMotion())
				.map(BlockPos::immutable)
				.forEach(positions::add);

		if (positions.isEmpty()) return false;
		Collections.shuffle(positions);
		BlockPos chestPos = positions.removeFirst();

		boolean inWater = blockWaterCheck(chestPos, level);
		if (!inWater) {
			BlockState belowState = level.getBlockState(chestPos.below());
			if (!Set.of(Blocks.GRASS_BLOCK, ModBlocks.CORRUPTED_GRASS.BLOCK, ModBlocks.CRIMSON_GRASS.BLOCK, Blocks.DIRT,
							Blocks.STONE, ModBlocks.CORRUPTED_STONE.BLOCK, ModBlocks.CRIMSON_STONE.BLOCK,
							Blocks.ANDESITE, ModBlocks.CORRUPTED_ANDESITE.BLOCK, ModBlocks.CRIMSON_ANDESITE.BLOCK,
							Blocks.GRANITE, ModBlocks.CORRUPTED_GRANITE.BLOCK, ModBlocks.CRIMSON_GRANITE.BLOCK,
							Blocks.DIORITE, ModBlocks.CORRUPTED_DIORITE.BLOCK, ModBlocks.CRIMSON_DIORITE.BLOCK)
					.contains(belowState.getBlock())) return false;

			boolean hasRoof = IntStream.rangeClosed(1, 40)
					.anyMatch(i -> !level.getBlockState(chestPos.above(i)).isAir());

			if (!hasRoof) return false;
		}

		generateContainer(level, chestPos, random);
		return !TerraMine.CONFIG.worldgen.caveChest.disableChests;
	}

	public boolean blockWaterCheck(BlockPos pos, WorldGenLevel level) {
		return level.isWaterAt(pos);
	}

	public boolean blockEmptyCheck(BlockPos pos, WorldGenLevel level) {
		return level.isEmptyBlock(pos);
	}

	public void generateContainer(WorldGenLevel level, BlockPos pos, RandomSource random) {
		if (level.isWaterAt(pos)) {
			this.setBlock(level, pos, ModBlocks.WATER_CHEST.BLOCK.defaultBlockState().setValue(ChestBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random)).setValue(ChestBlock.WATERLOGGED, true));
			RandomizableContainer.setBlockEntityLootTable(level, random, pos, ModLootTables.OCEAN_CHEST);
		} else {
			if (ModComponents.HARDMODE.get(level.getLevelData()).get() && random.nextFloat() * 100 < TerraMine.CONFIG.worldgen.caveChest.mimicChance) {
				MimicEntity mimic = ModEntities.MIMIC.create(level.getLevel(), EntitySpawnReason.STRUCTURE);
				if (mimic != null) {
					mimic.setDormant(true);
					mimic.setFacing(Direction.Plane.HORIZONTAL.getRandomDirection(random));
					mimic.setPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
					level.addFreshEntity(mimic);
				}
			} else {
				this.setBlock(level, pos, Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random)));
				RandomizableContainer.setBlockEntityLootTable(level, random, pos, ModLootTables.SURFACE_CHEST);
			}
		}
	}
}
