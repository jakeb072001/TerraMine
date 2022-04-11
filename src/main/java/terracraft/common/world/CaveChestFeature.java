package terracraft.common.world;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import terracraft.TerraCraft;
import terracraft.common.entity.MimicEntity;
import terracraft.common.init.ModBlocks;
import terracraft.common.init.ModEntities;
import terracraft.common.init.ModLootTables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CaveChestFeature extends Feature<NoneFeatureConfiguration> {

	public CaveChestFeature() {
		super(NoneFeatureConfiguration.CODEC);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		BlockPos origin = context.origin();
		WorldGenLevel level = context.level();
		Random random = context.random();

		List<BlockPos> positions = new ArrayList<>();
		BlockPos.betweenClosedStream(origin.offset(-1, 0, -1), origin.offset(1, 0, 1)).forEach((pos -> positions.add(pos.immutable())));
		positions.remove(origin);
		positions.removeIf(currentPos -> blockWaterCheck(currentPos, level));
		positions.removeIf(currentPos -> blockWaterCheck(currentPos.above(), level));
		positions.removeIf(currentPos -> !level.getBlockState(currentPos.below()).getMaterial().blocksMotion());
		if (positions.size() < 1) {
			return false;
		}
		Collections.shuffle(positions);

		generateContainer(level, positions.remove(0), random);

		return true;
	}

	public boolean blockWaterCheck(BlockPos pos, WorldGenLevel level) {
		if (!level.isEmptyBlock(pos)) {
			return !level.isWaterAt(pos);
		}
		return false;
	}

	public void generateContainer(WorldGenLevel world, BlockPos pos, Random random) { // change chest type and loot table depending on the biome. Will need to make terraria style chests first.
		BlockPos offsetPos = pos.atY(100);
		boolean frozen = false;
		if (world.getBiome(offsetPos).value().coldEnoughToSnow(offsetPos)) {
			frozen = true;
		}
		if (random.nextInt(100) < TerraCraft.CONFIG.worldgen.caveChest.mimicChance) {
			MimicEntity mimic = ModEntities.MIMIC.create(world.getLevel());
			if (mimic != null) {
				mimic.setDormant();
				mimic.setPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
				world.addFreshEntity(mimic);
			}
		} else {
			if (random.nextInt(5) == 0) {
				if (frozen) {
					this.setBlock(world, pos, ModBlocks.TRAPPED_FROZEN_CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random)));
				} else {
					this.setBlock(world, pos, ModBlocks.TRAPPED_GOLD_CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random)));
				}
				this.setBlock(world, pos.below(), Blocks.TNT.defaultBlockState());
			} else {
				if (frozen) {
					this.setBlock(world, pos, ModBlocks.FROZEN_CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random)));
				} else {
					this.setBlock(world, pos, ModBlocks.GOLD_CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random)));
				}
			}
			if (pos.getY() <= TerraCraft.CONFIG.worldgen.caveChest.deepCaveY) {
				RandomizableContainerBlockEntity.setLootTable(world, random, pos, ModLootTables.DEEP_CAVE_CHEST);
			} else {
				if (frozen) {
					RandomizableContainerBlockEntity.setLootTable(world, random, pos, ModLootTables.FROZEN_CAVE_CHEST);
				} else {
					RandomizableContainerBlockEntity.setLootTable(world, random, pos, ModLootTables.CAVE_CHEST);
				}
			}
		}
	}
}
