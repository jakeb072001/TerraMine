package terramine.common.init;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.StructureType;
import terramine.TerraMine;
import terramine.common.world.CaveChestFeature;
import terramine.common.world.NetherChestFeature;
import terramine.common.world.SurfaceChestFeature;
import terramine.common.world.TerrariaJigsawStructure;

import java.util.List;

import static terramine.TerraMine.CONFIG;
import static terramine.TerraMine.id;

public class ModFeatures {

	public static final Feature<NoneFeatureConfiguration> CAVE_CHEST = Registry.register(
			Registry.FEATURE,
			id("cave_chest"),
			new CaveChestFeature()
	);
	public static final Feature<NoneFeatureConfiguration> SURFACE_CHEST = Registry.register(
			Registry.FEATURE,
			id("surface_chest"),
			new SurfaceChestFeature()
	);
	public static final Feature<NoneFeatureConfiguration> NETHER_CHEST = Registry.register(
			Registry.FEATURE,
			id("nether_chest"),
			new NetherChestFeature()
	);
	public static final PlacedFeature PLACED_HELLSTONE_ORE;
	public static final PlacedFeature PLACED_CAVE_CHEST;
	public static final PlacedFeature PLACED_SURFACE_CHEST;
	public static final PlacedFeature PLACED_NETHER_CHEST;
	public static StructureType<TerrariaJigsawStructure> TERRARIA_JIGSAW_STRUCTURE = () -> TerrariaJigsawStructure.CODEC;

	public static void register() {

		// Chests
		if (CONFIG.worldgen.caveChest.chestRarity < 10) {
			BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
					GenerationStep.Decoration.UNDERGROUND_STRUCTURES,
					BuiltinRegistries.PLACED_FEATURE.getResourceKey(PLACED_CAVE_CHEST)
							.orElseThrow(() -> new RuntimeException("Failed to get feature from registry")));
		}
		if (CONFIG.worldgen.caveChest.chestRarity < 10) {
			BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
					GenerationStep.Decoration.UNDERGROUND_STRUCTURES,
					BuiltinRegistries.PLACED_FEATURE.getResourceKey(PLACED_SURFACE_CHEST)
							.orElseThrow(() -> new RuntimeException("Failed to get feature from registry")));
		}
		if (CONFIG.worldgen.caveChest.chestRarity < 10) {
			BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(),
					GenerationStep.Decoration.SURFACE_STRUCTURES,
					BuiltinRegistries.PLACED_FEATURE.getResourceKey(PLACED_NETHER_CHEST)
							.orElseThrow(() -> new RuntimeException("Failed to get feature from registry")));
		}

		// Ores
		BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(),
				GenerationStep.Decoration.UNDERGROUND_ORES,
				BuiltinRegistries.PLACED_FEATURE.getResourceKey(PLACED_HELLSTONE_ORE)
						.orElseThrow(() -> new RuntimeException("Failed to get feature from registry")));

		// Structures
		Registry.register(Registry.STRUCTURE_TYPES, TerraMine.id("terraria_jigsaw_structure"), TERRARIA_JIGSAW_STRUCTURE);
	}

	static {
		ConfiguredFeature<?, ?> HELLSTONE_ORE_CONFIGURED = Registry.register(
				BuiltinRegistries.CONFIGURED_FEATURE,
				id("hellstone_ore"),
				new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(
						OreFeatures.NETHERRACK,
						ModBlocks.HELLSTONE_ORE.defaultBlockState(),
						5))
		);

		ConfiguredFeature<?, ?> CAVE_CHEST_CONFIGURED = Registry.register(
				BuiltinRegistries.CONFIGURED_FEATURE,
				id("cave_chest"),
				new ConfiguredFeature<>(CAVE_CHEST, FeatureConfiguration.NONE)
		);

		ConfiguredFeature<?, ?> SURFACE_CHEST_CONFIGURED = Registry.register(
				BuiltinRegistries.CONFIGURED_FEATURE,
				id("surface_chest"),
				new ConfiguredFeature<>(SURFACE_CHEST, FeatureConfiguration.NONE)
		);

		ConfiguredFeature<?, ?> NETHER_CHEST_CONFIGURED = Registry.register(
				BuiltinRegistries.CONFIGURED_FEATURE,
				id("nether_chest"),
				new ConfiguredFeature<>(NETHER_CHEST, FeatureConfiguration.NONE)
		);


		ResourceKey<ConfiguredFeature<?, ?>> HELLSTONE_ORE_KEY = BuiltinRegistries.CONFIGURED_FEATURE.getResourceKey(HELLSTONE_ORE_CONFIGURED).orElseThrow();
		Holder<ConfiguredFeature<?, ?>> HELLSTONE_ORE_HOLDER = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolder(HELLSTONE_ORE_KEY).get().orThrow();

		ResourceKey<ConfiguredFeature<?, ?>> CAVE_CHEST_KEY = BuiltinRegistries.CONFIGURED_FEATURE.getResourceKey(CAVE_CHEST_CONFIGURED).orElseThrow();
		Holder<ConfiguredFeature<?, ?>> CAVE_CHEST_HOLDER = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolder(CAVE_CHEST_KEY).get().orThrow();

		ResourceKey<ConfiguredFeature<?, ?>> SURFACE_CHEST_KEY = BuiltinRegistries.CONFIGURED_FEATURE.getResourceKey(SURFACE_CHEST_CONFIGURED).orElseThrow();
		Holder<ConfiguredFeature<?, ?>> SURFACE_CHEST_HOLDER = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolder(SURFACE_CHEST_KEY).get().orThrow();

		ResourceKey<ConfiguredFeature<?, ?>> NETHER_CHEST_KEY = BuiltinRegistries.CONFIGURED_FEATURE.getResourceKey(NETHER_CHEST_CONFIGURED).orElseThrow();
		Holder<ConfiguredFeature<?, ?>> NETHER_CHEST_HOLDER = BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateHolder(NETHER_CHEST_KEY).get().orThrow();


		PLACED_HELLSTONE_ORE = Registry.register(
				BuiltinRegistries.PLACED_FEATURE,
				id("hellstone_ore"),
				new PlacedFeature(HELLSTONE_ORE_HOLDER,
						List.of(RarityFilter.onAverageOnceEvery(CONFIG.worldgen.hellstoneRarity),
								CountPlacement.of(5),
								InSquarePlacement.spread(),
								PlacementUtils.RANGE_10_10,
								BiomeFilter.biome()
						)
				)
		);

		PLACED_CAVE_CHEST = Registry.register(
				BuiltinRegistries.PLACED_FEATURE,
				id("underground_cave_chest"),
				new PlacedFeature(CAVE_CHEST_HOLDER,
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
				id("surface_cave_chest"),
				new PlacedFeature(SURFACE_CHEST_HOLDER,
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

		PLACED_NETHER_CHEST = Registry.register(
				BuiltinRegistries.PLACED_FEATURE,
				id("nether_chest"),
				new PlacedFeature(NETHER_CHEST_HOLDER,
						List.of(RarityFilter.onAverageOnceEvery(CONFIG.worldgen.caveChest.chestRarity * 2),
								InSquarePlacement.spread(),
								HeightRangePlacement.uniform(
										VerticalAnchor.aboveBottom(20),
										VerticalAnchor.aboveBottom(100)
								),
								EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 32),
								RandomOffsetPlacement.vertical(ConstantInt.of(1)),
								BiomeFilter.biome())
				)
		);
	}
}
