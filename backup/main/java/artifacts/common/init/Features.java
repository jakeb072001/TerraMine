package terracraft.common.init;

import terracraft.Artifacts;
import terracraft.common.world.CaveChestFeature;
import terracraft.common.world.SurfaceChestFeature;
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
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

import java.util.List;

import static terracraft.Artifacts.CONFIG;

// Biome Modifications API is experimental, remove suppress warning when stable
@SuppressWarnings("deprecation")
public class Features {

	public static final Feature<NoneFeatureConfiguration> CAVE_CHEST = Registry.register(
			Registry.FEATURE,
			Artifacts.id("cave_chest"),
			new CaveChestFeature()
	);
	public static final Feature<NoneFeatureConfiguration> SURFACE_CHEST = Registry.register(
			Registry.FEATURE,
			Artifacts.id("surface_chest"),
			new SurfaceChestFeature()
	);
	public static final PlacedFeature PLACED_CAVE_CHEST;
	public static final PlacedFeature PLACED_SURFACE_CHEST;

	public static void register() {
		if (CONFIG.worldgen.caveChest.caveChestRarity < 10_000) {
			BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
					GenerationStep.Decoration.UNDERGROUND_STRUCTURES,
					BuiltinRegistries.PLACED_FEATURE.getResourceKey(PLACED_CAVE_CHEST)
							.orElseThrow(() -> new RuntimeException("Failed to get feature from registry")));
		}
		if (CONFIG.worldgen.caveChest.caveChestRarity < 10_000) {
			BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
					GenerationStep.Decoration.UNDERGROUND_STRUCTURES,
					BuiltinRegistries.PLACED_FEATURE.getResourceKey(PLACED_SURFACE_CHEST)
							.orElseThrow(() -> new RuntimeException("Failed to get feature from registry")));
		}
	}

	static {
		ConfiguredFeature<?, ?> configuredFeature = Registry.register(
				BuiltinRegistries.CONFIGURED_FEATURE,
				Artifacts.id("cave_chest"),
				new ConfiguredFeature<>(CAVE_CHEST, FeatureConfiguration.NONE)
		);
		ConfiguredFeature<?, ?> configuredFeature2 = Registry.register(
				BuiltinRegistries.CONFIGURED_FEATURE,
				Artifacts.id("surface_chest"),
				new ConfiguredFeature<>(SURFACE_CHEST, FeatureConfiguration.NONE)
		);
		ResourceKey<ConfiguredFeature<?, ?>> featureKey = BuiltinRegistries.CONFIGURED_FEATURE.getResourceKey(configuredFeature).orElseThrow();
		Holder<ConfiguredFeature<?, ?>> featureHolder = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolder(featureKey);
		ResourceKey<ConfiguredFeature<?, ?>> featureKey2 = BuiltinRegistries.CONFIGURED_FEATURE.getResourceKey(configuredFeature2).orElseThrow();
		Holder<ConfiguredFeature<?, ?>> featureHolder2 = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolder(featureKey2);

		PLACED_CAVE_CHEST = Registry.register(
				BuiltinRegistries.PLACED_FEATURE,
				Artifacts.id("underground_cave_chest"),
				new PlacedFeature(featureHolder,
						List.of(RarityFilter.onAverageOnceEvery(CONFIG.worldgen.caveChest.caveChestRarity),
								InSquarePlacement.spread(),
								HeightRangePlacement.uniform(
										VerticalAnchor.aboveBottom(10), // -65 is bottom, so -55 is minimum
										VerticalAnchor.aboveBottom(90) // 25 is max because -65 + 90 is 25
								),
								EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 32),
								RandomOffsetPlacement.vertical(ConstantInt.of(1)),
								BiomeFilter.biome())
				)
		);
		PLACED_SURFACE_CHEST = Registry.register(
				BuiltinRegistries.PLACED_FEATURE,
				Artifacts.id("surface_cave_chest"),
				new PlacedFeature(featureHolder2,
						List.of(RarityFilter.onAverageOnceEvery(CONFIG.worldgen.caveChest.caveChestRarity),
								InSquarePlacement.spread(),
								HeightRangePlacement.uniform(
										VerticalAnchor.aboveBottom(90),
										VerticalAnchor.aboveBottom(120)
								),
								EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 32),
								RandomOffsetPlacement.vertical(ConstantInt.of(1)),
								BiomeFilter.biome())
				)
		);
	}
}
