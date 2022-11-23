package terramine.common.world;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import terramine.TerraMine;
import terramine.common.entity.mobs.MimicEntity;
import terramine.common.init.ModBlocks;
import terramine.common.init.ModComponents;
import terramine.common.init.ModEntities;
import terramine.common.init.ModLootTables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NetherChestFeature extends Feature<NoneFeatureConfiguration> {

	public NetherChestFeature() {
		super(NoneFeatureConfiguration.CODEC);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		BlockPos origin = context.origin();
		WorldGenLevel level = context.level();
		RandomSource random = context.random();

		List<BlockPos> positions = new ArrayList<>();
		BlockPos.betweenClosedStream(origin.offset(-1, 0, -1), origin.offset(1, 0, 1)).forEach((pos -> positions.add(pos.immutable())));
		positions.remove(origin);
		positions.removeIf(currentPos -> blockLavaCheck(currentPos, level));
		positions.removeIf(currentPos -> blockLavaCheck(currentPos.above(), level));
		positions.removeIf(currentPos -> !level.getBlockState(currentPos.below()).getMaterial().blocksMotion());
		if (positions.size() < 1) {
			return false;
		}
		Collections.shuffle(positions);

		generateContainer(level, positions.remove(0), random);

		return !TerraMine.CONFIG.worldgen.caveChest.disableChests;
	}

	public boolean blockLavaCheck(BlockPos pos, WorldGenLevel level) {
		if (!level.isEmptyBlock(pos)) {
			return !level.getFluidState(pos).isSource();
		}
		return false;
	}

	public void generateContainer(WorldGenLevel level, BlockPos pos, RandomSource random) {
		if (ModComponents.HARDMODE.get(level.getLevelData()).get() && random.nextFloat() * 100 < TerraMine.CONFIG.worldgen.caveChest.mimicChance) {
			MimicEntity mimic = ModEntities.MIMIC.create(level.getLevel());
			if (mimic != null) {
				mimic.setDormant(true);
				mimic.setFacing(Direction.Plane.HORIZONTAL.getRandomDirection(random));
				mimic.setPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
				mimic.setMimicType(4);
				level.addFreshEntity(mimic);
			}
		} else {
			this.setBlock(level, pos, ModBlocks.SHADOW_CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random)));
			RandomizableContainerBlockEntity.setLootTable(level, random, pos, ModLootTables.SHADOW_CHEST);
		}
	}
}
