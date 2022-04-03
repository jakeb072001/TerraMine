package terracraft.common.world;

import terracraft.Artifacts;
import terracraft.common.entity.MimicEntity;
import terracraft.common.init.Entities;
import terracraft.common.init.LootTables;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SurfaceChestFeature extends Feature<NoneFeatureConfiguration> {

	public SurfaceChestFeature() {
		super(NoneFeatureConfiguration.CODEC);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		BlockPos origin = context.origin();
		WorldGenLevel level = context.level();
		Random random = context.random();


		List<BlockPos> positions = new ArrayList<>();
		BlockPos.betweenClosedStream(origin.offset(-3, 0, -3), origin.offset(3, 0, 3)).forEach((pos -> positions.add(pos.immutable())));
		positions.remove(origin);
		positions.removeIf(currentPos -> !level.isEmptyBlock(currentPos));
		positions.removeIf(currentPos -> !level.getBlockState(currentPos.below()).getMaterial().blocksMotion());
		if (positions.size() < 12) {
			return false;
		}
		Collections.shuffle(positions);

		generateContainer(level, positions.remove(0), random);

		return true;
	}

	public void generateContainer(WorldGenLevel world, BlockPos pos, Random random) { // change chest type and loot table depending on the biome. Will need to make terraria style chests first.
		if (random.nextInt(100) < Artifacts.CONFIG.worldgen.caveChest.mimicChance) {
			MimicEntity mimic = Entities.MIMIC.create(world.getLevel());
			if (mimic != null) {
				mimic.setDormant();
				mimic.setPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
				world.addFreshEntity(mimic);
			}
		} else {
			if (random.nextInt(5) == 0) {
				this.setBlock(world, pos, Blocks.TRAPPED_CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random)));
				this.setBlock(world, pos.below(), Blocks.TNT.defaultBlockState());
			} else {
				// TODO: random wooden chest with tag
				this.setBlock(world, pos, Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random)));
			}
			RandomizableContainerBlockEntity.setLootTable(world, random, pos, LootTables.SURFACE_CHEST);
		}
	}
}
