package terracraft.common.init;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import terracraft.TerraCraft;
import terracraft.common.world.CaveChestFeature;
import terracraft.common.world.SurfaceChestFeature;

import java.util.List;

import static terracraft.TerraCraft.CONFIG;

// Biome Modifications API is experimental, remove suppress warning when stable
@SuppressWarnings("deprecation")
public class ModFeatures {

	public static final Feature<NoneFeatureConfiguration> CAVE_CHEST = Registry.register(
			Registry.FEATURE,
			TerraCraft.id("cave_chest"),
			new CaveChestFeature()
	);
	public static final Feature<NoneFeatureConfiguration> SURFACE_CHEST = Registry.register(
			Registry.FEATURE,
			TerraCraft.id("surface_chest"),
			new SurfaceChestFeature()
	);
	public static final PlacedFeature PLACED_CAVE_CHEST;
	public static final PlacedFeature PLACED_SURFACE_CHEST;

	public static void register() {
		if (CONFIG.worldgen.caveChest.chestRarity < 10_000) {
			BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
					GenerationStep.Decoration.UNDERGROUND_STRUCTURES,
					BuiltinRegistries.PLACED_FEATURE.getResourceKey(PLACED_CAVE_CHEST)
							.orElseThrow(() -> new RuntimeException("Failed to get feature from registry")));
		}
		if (CONFIG.worldgen.caveChest.chestRarity < 10_000) {
			BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
					GenerationStep.Decoration.UNDERGROUND_STRUCTURES,
					BuiltinRegistries.PLACED_FEATURE.getResourceKey(PLACED_SURFACE_CHEST)
							.orElseThrow(() -> new RuntimeException("Failed to get feature from registry")));
		}
	}

	static {
		ConfiguredFeature<?, ?> configuredFeature = Registry.register(
				BuiltinRegistries.CONFIGURED_FEATURE,
				TerraCraft.id("cave_chest"),
				new ConfiguredFeature<>(CAVE_CHEST, FeatureConfiguration.NONE)
		);
		ConfiguredFeature<?, ?> configuredFeature2 = Registry.register(
				BuiltinRegistries.CONFIGURED_FEATURE,
				TerraCraft.id("surface_chest"),
				new ConfiguredFeature<>(SURFACE_CHEST, FeatureConfiguration.NONE)
		);
		ResourceKey<ConfiguredFeature<?, ?>> featureKey = BuiltinRegistries.CONFIGURED_FEATURE.getResourceKey(configuredFeature).orElseThrow();
		Holder<ConfiguredFeature<?, ?>> featureHolder = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolder(featureKey);
		ResourceKey<ConfiguredFeature<?, ?>> featureKey2 = BuiltinRegistries.CONFIGURED_FEATURE.getResourceKey(configuredFeature2).orElseThrow();
		Holder<ConfiguredFeature<?, ?>> featureHolder2 = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolder(featureKey2);

		PLACED_CAVE_CHEST = Registry.register(
				BuiltinRegistries.PLACED_FEATURE,
				TerraCraft.id("underground_cave_chest"),
				new PlacedFeature(featureHolder,
						List.of(RarityFilter.onAverageOnceEvery(CONFIG.worldgen.caveChest.chestRarity),
								InSquarePlacement.spread(),
								HeightRangePlacement.uniform(
										VerticalAnchor.aboveBottom(CONFIG.worldgen.caveChest.minCaveY + 64), // -64 is bottom, so -54 is minimum
										VerticalAnchor.aboveBottom(CONFIG.worldgen.caveChest.maxCaveY + 64) // 24 is max because -64 + 90 is 24
								),
								EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 32),
								RandomOffsetPlacement.vertical(ConstantInt.of(1)),
								BiomeFilter.biome())
				)
		);
		PLACED_SURFACE_CHEST = Registry.register(
				BuiltinRegistries.PLACED_FEATURE,
				TerraCraft.id("surface_cave_chest"),
				new PlacedFeature(featureHolder2,
						List.of(RarityFilter.onAverageOnceEvery(CONFIG.worldgen.caveChest.chestRarity),
								InSquarePlacement.spread(),
								HeightRangePlacement.uniform(
										VerticalAnchor.aboveBottom(CONFIG.worldgen.caveChest.minSurfaceY + 64),
										VerticalAnchor.aboveBottom(CONFIG.worldgen.caveChest.maxSurfaceY + 64)
								),
								EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 32),
								RandomOffsetPlacement.vertical(ConstantInt.of(1)),
								BiomeFilter.biome())
				)
		);
	}
}
