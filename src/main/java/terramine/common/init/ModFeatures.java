package terramine.common.init;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import terramine.TerraMine;
import terramine.common.world.CaveChestFeature;
import terramine.common.world.NetherChestFeature;
import terramine.common.world.SurfaceChestFeature;
import terramine.common.world.TerrariaJigsawStructure;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static terramine.TerraMine.CONFIG;
import static terramine.TerraMine.id;

public class ModFeatures extends FabricDynamicRegistryProvider {

	public static final Feature<NoneFeatureConfiguration> CAVE_CHEST = Registry.register(
			BuiltInRegistries.FEATURE,
			id("cave_chest"),
			new CaveChestFeature()
	);
	public static final Feature<NoneFeatureConfiguration> SURFACE_CHEST = Registry.register(
			BuiltInRegistries.FEATURE,
			id("surface_chest"),
			new SurfaceChestFeature()
	);
	public static final Feature<NoneFeatureConfiguration> NETHER_CHEST = Registry.register(
			BuiltInRegistries.FEATURE,
			id("nether_chest"),
			new NetherChestFeature()
	);
	ResourceKey<ConfiguredFeature<?, ?>> HELLSTONE_ORE_CONFIGURED;
	ResourceKey<ConfiguredFeature<?, ?>> CAVE_CHEST_CONFIGURED;
	ResourceKey<ConfiguredFeature<?, ?>> SURFACE_CHEST_CONFIGURED;
	ResourceKey<ConfiguredFeature<?, ?>> NETHER_CHEST_CONFIGURED;
	public static final ResourceKey<PlacedFeature> PLACED_HELLSTONE_ORE = register(id("hellstone_ore"));
	public static final ResourceKey<PlacedFeature> PLACED_CAVE_CHEST = register(id("cave_chest"));
	public static final ResourceKey<PlacedFeature> PLACED_SURFACE_CHEST = register(id("surface_chest"));
	public static final ResourceKey<PlacedFeature> PLACED_NETHER_CHEST = register(id("nether_chest"));
	public static StructureType<TerrariaJigsawStructure> TERRARIA_JIGSAW_STRUCTURE = () -> TerrariaJigsawStructure.CODEC;

	public ModFeatures(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	private static ResourceKey<PlacedFeature> register(ResourceLocation name) {
		return ResourceKey.create(Registries.PLACED_FEATURE, name);
	}

	public static void register() {

		// Chests
		if (CONFIG.worldgen.caveChest.chestRarity < 10) {
			BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
					GenerationStep.Decoration.UNDERGROUND_STRUCTURES, PLACED_CAVE_CHEST);
		}
		if (CONFIG.worldgen.caveChest.chestRarity < 10) {
			BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
					GenerationStep.Decoration.UNDERGROUND_STRUCTURES, PLACED_SURFACE_CHEST);
		}
		if (CONFIG.worldgen.caveChest.chestRarity < 10) {
			BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(),
					GenerationStep.Decoration.SURFACE_STRUCTURES, PLACED_NETHER_CHEST);
		}

		// Ores
		BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(),
				GenerationStep.Decoration.UNDERGROUND_ORES, PLACED_HELLSTONE_ORE);

		// Structures
		Registry.register(BuiltInRegistries.STRUCTURE_TYPE, TerraMine.id("terraria_jigsaw_structure"), TERRARIA_JIGSAW_STRUCTURE);
	}

	@Override
	protected void configure(HolderLookup.Provider registries, Entries entries) {
		entries.add(HELLSTONE_ORE_CONFIGURED, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(
				new BlockMatchTest(Blocks.NETHERRACK), ModBlocks.HELLSTONE_ORE.defaultBlockState(),5)));
		entries.add(CAVE_CHEST_CONFIGURED, new ConfiguredFeature<>(CAVE_CHEST, FeatureConfiguration.NONE));
		entries.add(SURFACE_CHEST_CONFIGURED, new ConfiguredFeature<>(SURFACE_CHEST, FeatureConfiguration.NONE));
		entries.add(NETHER_CHEST_CONFIGURED, new ConfiguredFeature<>(NETHER_CHEST, FeatureConfiguration.NONE));

		Holder<ConfiguredFeature<?, ?>> HELLSTONE_ORE_HOLDER = entries.getLookup(Registries.CONFIGURED_FEATURE).getOrThrow(HELLSTONE_ORE_CONFIGURED);
		Holder<ConfiguredFeature<?, ?>> CAVE_CHEST_HOLDER = entries.getLookup(Registries.CONFIGURED_FEATURE).getOrThrow(CAVE_CHEST_CONFIGURED);
		Holder<ConfiguredFeature<?, ?>> SURFACE_CHEST_HOLDER = entries.getLookup(Registries.CONFIGURED_FEATURE).getOrThrow(SURFACE_CHEST_CONFIGURED);
		Holder<ConfiguredFeature<?, ?>> NETHER_CHEST_HOLDER = entries.getLookup(Registries.CONFIGURED_FEATURE).getOrThrow(NETHER_CHEST_CONFIGURED);

		entries.add(PLACED_HELLSTONE_ORE, new PlacedFeature(HELLSTONE_ORE_HOLDER,
				List.of(RarityFilter.onAverageOnceEvery(CONFIG.worldgen.hellstoneRarity),
						CountPlacement.of(5),
						InSquarePlacement.spread(),
						PlacementUtils.RANGE_10_10,
						BiomeFilter.biome()
				)));
		entries.add(PLACED_CAVE_CHEST, new PlacedFeature(CAVE_CHEST_HOLDER,
				List.of(RarityFilter.onAverageOnceEvery(CONFIG.worldgen.caveChest.chestRarity),
						InSquarePlacement.spread(),
						HeightRangePlacement.uniform(
								VerticalAnchor.aboveBottom(CONFIG.worldgen.caveChest.minCaveY + 64), // -64 is bottom, so -54 is minimum
								VerticalAnchor.aboveBottom(CONFIG.worldgen.caveChest.maxCaveY + 64) // 24 is max because -64 + 90 is 24
						),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 32),
						RandomOffsetPlacement.vertical(ConstantInt.of(1)),
						BiomeFilter.biome())
		));
		entries.add(PLACED_SURFACE_CHEST, new PlacedFeature(SURFACE_CHEST_HOLDER,
				List.of(RarityFilter.onAverageOnceEvery(CONFIG.worldgen.caveChest.chestRarity),
						InSquarePlacement.spread(),
						HeightRangePlacement.uniform(
								VerticalAnchor.aboveBottom(CONFIG.worldgen.caveChest.minSurfaceY + 64),
								VerticalAnchor.aboveBottom(CONFIG.worldgen.caveChest.maxSurfaceY + 64)
						),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 32),
						RandomOffsetPlacement.vertical(ConstantInt.of(1)),
						BiomeFilter.biome())
		));
		entries.add(PLACED_NETHER_CHEST, new PlacedFeature(NETHER_CHEST_HOLDER,
				List.of(RarityFilter.onAverageOnceEvery(CONFIG.worldgen.caveChest.chestRarity * 2),
						InSquarePlacement.spread(),
						HeightRangePlacement.uniform(
								VerticalAnchor.aboveBottom(20),
								VerticalAnchor.aboveBottom(100)
						),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 32),
						RandomOffsetPlacement.vertical(ConstantInt.of(1)),
						BiomeFilter.biome())
		));
	}

	@Override
	public String getName() {
		return "Terramine";
	}
}
