package terramine.datagen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import terramine.TerraMine;
import terramine.common.components.OreComponent;
import terramine.common.init.ModBlocks;
import terramine.common.init.ModComponents;
import terramine.common.world.CaveChestFeature;
import terramine.common.world.NetherChestFeature;
import terramine.common.world.SurfaceChestFeature;
import terramine.common.world.TerrariaJigsawStructure;

import java.util.List;

import static terramine.TerraMine.CONFIG;
import static terramine.TerraMine.id;

public class ModFeatures {

	// Features
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

	// Ore Gen
	public static final RuleTest STONE_ORE_REPLACEABLES = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
	public static final RuleTest DEEPSLATE_ORE_REPLACEABLES = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
	public static final RuleTest CORRUPTION_STONE_ORE_REPLACEABLES = new TagMatchTest(TagKey.create(Registries.BLOCK, id("corruption_stone_ore_replaceables")));
	public static final RuleTest CORRUPTION_DEEPSLATE_ORE_REPLACEABLES = new TagMatchTest(TagKey.create(Registries.BLOCK, id("corruption_deepslate_ore_replaceables")));
	public static final RuleTest CRIMSON_STONE_ORE_REPLACEABLES = new TagMatchTest(TagKey.create(Registries.BLOCK, id("crimson_stone_ore_replaceables")));
	public static final RuleTest CRIMSON_DEEPSLATE_ORE_REPLACEABLES = new TagMatchTest(TagKey.create(Registries.BLOCK, id("crimson_deepslate_ore_replaceables")));

	// Misc
	public static final ResourceKey<ConfiguredFeature<?, ?>> CAVE_CHEST_CONFIGURED = registerConfigured("cave_chest");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SURFACE_CHEST_CONFIGURED = registerConfigured("surface_chest");
	public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_CHEST_CONFIGURED = registerConfigured("nether_chest");

	// Plants
	public static final ResourceKey<ConfiguredFeature<?, ?>> CORRUPTION_PLANTS = registerConfigured("corruption_plant");
	public static final ResourceKey<ConfiguredFeature<?, ?>> CRIMSON_PLANTS = registerConfigured("crimson_plant");

	// World Gen
	public static final ResourceKey<ConfiguredFeature<?, ?>> DISK_CORRUPT_SAND_FEATURE = registerConfigured("disk_corrupt_sand");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DISK_CORRUPT_GRAVEL_FEATURE = registerConfigured("disk_corrupt_gravel");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DISK_CRIMSON_SAND_FEATURE = registerConfigured("disk_crimson_sand");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DISK_CRIMSON_GRAVEL_FEATURE = registerConfigured("disk_crimson_gravel");

	// Ore
	public static final List<OreConfiguration.TargetBlockState> ORE_TIN_TARGET_LIST = List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.TIN_ORE.BLOCK.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_TIN_ORE.BLOCK.defaultBlockState()));
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_TIN_FEATURE = registerConfigured("ore_tin");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_TIN_LARGE_FEATURE = registerConfigured("ore_tin_large");
	public static final List<OreConfiguration.TargetBlockState> ORE_LEAD_TARGET_LIST = List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.LEAD_ORE.BLOCK.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_LEAD_ORE.BLOCK.defaultBlockState()));
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_LEAD_FEATURE = registerConfigured("ore_lead");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_LEAD_SMALL_FEATURE = registerConfigured("ore_lead_small");
	public static final List<OreConfiguration.TargetBlockState> ORE_SILVER_TARGET_LIST = List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.SILVER_ORE.BLOCK.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_SILVER_ORE.BLOCK.defaultBlockState()));
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SILVER_FEATURE = registerConfigured("ore_silver");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SILVER_SMALL_FEATURE = registerConfigured("ore_silver_small");
	public static final List<OreConfiguration.TargetBlockState> ORE_TUNGSTEN_TARGET_LIST = List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.TUNGSTEN_ORE.BLOCK.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_TUNGSTEN_ORE.BLOCK.defaultBlockState()));
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_TUNGSTEN_FEATURE = registerConfigured("ore_tungsten");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_TUNGSTEN_SMALL_FEATURE = registerConfigured("ore_tungsten_small");
	public static final List<OreConfiguration.TargetBlockState> ORE_PLATINUM_TARGET_LIST = List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.PLATINUM_ORE.BLOCK.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_PLATINUM_ORE.BLOCK.defaultBlockState()));
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_PLATINUM_FEATURE = registerConfigured("ore_platinum");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_PLATINUM_SMALL_FEATURE = registerConfigured("ore_platinum_small");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_NETHER_PLATINUM_FEATURE = registerConfigured("ore_nether_platinum");
	public static final ResourceKey<ConfiguredFeature<?, ?>> HELLSTONE_ORE_CONFIGURED = registerConfigured("hellstone_ore");

	// Demonite
	public static final List<OreConfiguration.TargetBlockState> ORE_DEMONITE_TARGET_LIST = List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.DEMONITE_ORE.BLOCK.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_DEMONITE_ORE.BLOCK.defaultBlockState()), OreConfiguration.target(CORRUPTION_STONE_ORE_REPLACEABLES, ModBlocks.DEMONITE_ORE.BLOCK.defaultBlockState()), OreConfiguration.target(CORRUPTION_DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_DEMONITE_ORE.BLOCK.defaultBlockState()));
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_DEMONITE_FEATURE = registerConfigured("ore_demonite");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_DEMONITE_SMALL_FEATURE = registerConfigured("ore_demonite_small");

	// Crimtane
	public static final List<OreConfiguration.TargetBlockState> ORE_CRIMTANE_TARGET_LIST = List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.CRIMTANE_ORE.BLOCK.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_CRIMTANE_ORE.BLOCK.defaultBlockState()), OreConfiguration.target(CRIMSON_STONE_ORE_REPLACEABLES, ModBlocks.CRIMTANE_ORE.BLOCK.defaultBlockState()), OreConfiguration.target(CRIMSON_DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_CRIMTANE_ORE.BLOCK.defaultBlockState()));
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_CRIMTANE_FEATURE = registerConfigured("ore_crimtane");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_CRIMTANE_SMALL_FEATURE = registerConfigured("ore_crimtane_small");

	public static StructureType<TerrariaJigsawStructure> TERRARIA_JIGSAW_STRUCTURE = () -> TerrariaJigsawStructure.CODEC;

	private static ResourceKey<ConfiguredFeature<?, ?>> registerConfigured(String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, id(name));
	}

	public static void register() {
		// Chests
		if (CONFIG.worldgen.caveChest.chestRarity < 10) {
			BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
					GenerationStep.Decoration.UNDERGROUND_STRUCTURES, ModPlacedFeatures.PLACED_CAVE_CHEST);
		}
		if (CONFIG.worldgen.caveChest.chestRarity < 10) {
			BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
					GenerationStep.Decoration.UNDERGROUND_STRUCTURES, ModPlacedFeatures.PLACED_SURFACE_CHEST);
		}
		if (CONFIG.worldgen.caveChest.chestRarity < 10) {
			BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(),
					GenerationStep.Decoration.SURFACE_STRUCTURES, ModPlacedFeatures.PLACED_NETHER_CHEST);
		}

		// Ores
		BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(),
				GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.PLACED_HELLSTONE_ORE);

		// todo: need to also replace massive ore veins, the ones that also place raw blocks, OreVeinifier and NoiseChunk?
		BiomeModifications.create(id("terraria_ores"))
				.add(ModificationPhase.REPLACEMENTS,
						context -> context.canGenerateIn(LevelStem.NETHER) && context.hasPlacedFeature(OrePlacements.ORE_GOLD_NETHER),
						context -> {
							OreComponent oreComponent = ModComponents.ORE_TYPES.get(OreComponent.getLevelData());
							if (!oreComponent.getIfGold()) {
								context.getGenerationSettings().removeFeature(OrePlacements.ORE_GOLD_NETHER);
								context.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModPlacedFeatures.ORE_NETHER_PLATINUM);
							}
						}
				)
				.add(ModificationPhase.REPLACEMENTS,
						context -> {
							boolean hasCopper = context.hasPlacedFeature(OrePlacements.ORE_COPPER_LARGE);
							return context.getBiomeKey().equals(Biomes.DRIPSTONE_CAVES) && context.canGenerateIn(LevelStem.OVERWORLD) && hasCopper;
						},
						context -> {
							OreComponent oreComponent = ModComponents.ORE_TYPES.get(OreComponent.getLevelData());
							if (!oreComponent.getIfCopper()) {
								context.getGenerationSettings().removeFeature(OrePlacements.ORE_COPPER_LARGE);
								context.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.ORE_TIN_LARGE);
							}
						}
				)
				.add(ModificationPhase.REPLACEMENTS,
						context -> {
							boolean hasCopper = context.hasPlacedFeature(OrePlacements.ORE_COPPER);
							return !context.getBiomeKey().equals(Biomes.DRIPSTONE_CAVES) && context.canGenerateIn(LevelStem.OVERWORLD) && hasCopper;
						},
						context -> {
							OreComponent oreComponent = ModComponents.ORE_TYPES.get(OreComponent.getLevelData());
							if (!oreComponent.getIfCopper()) {
								context.getGenerationSettings().removeFeature(OrePlacements.ORE_COPPER);
								context.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.ORE_TIN);
							}
						}
				)
				.add(ModificationPhase.REPLACEMENTS, context -> {
					boolean hasIron = context.hasPlacedFeature(OrePlacements.ORE_IRON_UPPER) && context.hasPlacedFeature(OrePlacements.ORE_IRON_MIDDLE) && context.hasPlacedFeature(OrePlacements.ORE_IRON_SMALL);
					boolean hasGold = context.hasPlacedFeature(OrePlacements.ORE_GOLD) && context.hasPlacedFeature(OrePlacements.ORE_GOLD_LOWER);
					return context.canGenerateIn(LevelStem.OVERWORLD) && hasIron && hasGold;
				}, context -> {
					OreComponent oreComponent = ModComponents.ORE_TYPES.get(OreComponent.getLevelData());

					if (!oreComponent.getIfIron()) {
						context.getGenerationSettings().removeFeature(OrePlacements.ORE_IRON_UPPER);
						context.getGenerationSettings().removeFeature(OrePlacements.ORE_IRON_MIDDLE);
						context.getGenerationSettings().removeFeature(OrePlacements.ORE_IRON_SMALL);
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.ORE_LEAD_UPPER);
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.ORE_LEAD_MIDDLE);
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.ORE_LEAD_SMALL);
					}
					if (!oreComponent.getIfSilver()) {
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.ORE_TUNGSTEN);
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.ORE_TUNGSTEN_SMALL);
					} else {
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.ORE_SILVER);
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.ORE_SILVER_SMALL);
					}
					if (!oreComponent.getIfGold()) {
						context.getGenerationSettings().removeFeature(OrePlacements.ORE_GOLD);
						context.getGenerationSettings().removeFeature(OrePlacements.ORE_GOLD_LOWER);
						context.getGenerationSettings().removeFeature(OrePlacements.ORE_GOLD_EXTRA);
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.ORE_PLATINUM);
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.ORE_PLATINUM_LOWER);
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.ORE_PLATINUM_EXTRA);
					}
				});

		// Structures
		Registry.register(BuiltInRegistries.STRUCTURE_TYPE, TerraMine.id("terraria_jigsaw_structure"), TERRARIA_JIGSAW_STRUCTURE);
	}

	public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
		context.register(HELLSTONE_ORE_CONFIGURED, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(new BlockMatchTest(Blocks.NETHERRACK), ModBlocks.HELLSTONE_ORE.BLOCK.defaultBlockState(),5)));
		context.register(CAVE_CHEST_CONFIGURED, new ConfiguredFeature<>(CAVE_CHEST, FeatureConfiguration.NONE));
		context.register(SURFACE_CHEST_CONFIGURED, new ConfiguredFeature<>(SURFACE_CHEST, FeatureConfiguration.NONE));
		context.register(NETHER_CHEST_CONFIGURED, new ConfiguredFeature<>(NETHER_CHEST, FeatureConfiguration.NONE));

		context.register(ORE_TIN_FEATURE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ORE_TIN_TARGET_LIST, 10)));
		context.register(ORE_TIN_LARGE_FEATURE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ORE_TIN_TARGET_LIST, 20)));
		context.register(ORE_LEAD_FEATURE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ORE_LEAD_TARGET_LIST, 9)));
		context.register(ORE_LEAD_SMALL_FEATURE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ORE_LEAD_TARGET_LIST, 4)));
		context.register(ORE_SILVER_FEATURE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ORE_SILVER_TARGET_LIST, 9)));
		context.register(ORE_SILVER_SMALL_FEATURE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ORE_SILVER_TARGET_LIST, 4)));
		context.register(ORE_TUNGSTEN_FEATURE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ORE_TUNGSTEN_TARGET_LIST, 9)));
		context.register(ORE_TUNGSTEN_SMALL_FEATURE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ORE_TUNGSTEN_TARGET_LIST, 4)));
		context.register(ORE_PLATINUM_FEATURE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ORE_PLATINUM_TARGET_LIST, 9)));
		context.register(ORE_PLATINUM_SMALL_FEATURE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ORE_PLATINUM_TARGET_LIST, 4)));
		context.register(ORE_NETHER_PLATINUM_FEATURE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(new BlockMatchTest(Blocks.NETHERRACK), ModBlocks.NETHER_PLATINUM_ORE.BLOCK.defaultBlockState(), 10)));
		context.register(ORE_DEMONITE_FEATURE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ORE_DEMONITE_TARGET_LIST, 4)));
		context.register(ORE_DEMONITE_SMALL_FEATURE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ORE_DEMONITE_TARGET_LIST, 2)));
		context.register(ORE_CRIMTANE_FEATURE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ORE_CRIMTANE_TARGET_LIST, 4)));
		context.register(ORE_CRIMTANE_SMALL_FEATURE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ORE_CRIMTANE_TARGET_LIST, 2)));
		context.register(CORRUPTION_PLANTS, new ConfiguredFeature<>(Feature.FLOWER, grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.single(ModBlocks.VILE_MUSHROOM.BLOCK.defaultBlockState())), 64)));
		context.register(CRIMSON_PLANTS, new ConfiguredFeature<>(Feature.FLOWER, grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.single(ModBlocks.VICIOUS_MUSHROOM.BLOCK.defaultBlockState())), 64)));
		context.register(DISK_CORRUPT_SAND_FEATURE, new ConfiguredFeature<>(Feature.DISK, new DiskConfiguration(RuleBasedBlockStateProvider.simple(ModBlocks.CORRUPTED_SAND.BLOCK), BlockPredicate.matchesBlocks(List.of(Blocks.DIRT, ModBlocks.CORRUPTED_GRASS.BLOCK)), UniformInt.of(2, 6), 2)));
		context.register(DISK_CORRUPT_GRAVEL_FEATURE, new ConfiguredFeature<>(Feature.DISK, new DiskConfiguration(RuleBasedBlockStateProvider.simple(ModBlocks.CORRUPTED_GRAVEL.BLOCK), BlockPredicate.matchesBlocks(List.of(Blocks.DIRT, ModBlocks.CORRUPTED_GRASS.BLOCK)), UniformInt.of(2, 5), 2)));
		context.register(DISK_CRIMSON_SAND_FEATURE, new ConfiguredFeature<>(Feature.DISK, new DiskConfiguration(RuleBasedBlockStateProvider.simple(ModBlocks.CRIMSON_SAND.BLOCK), BlockPredicate.matchesBlocks(List.of(Blocks.DIRT, ModBlocks.CRIMSON_GRASS.BLOCK)), UniformInt.of(2, 6), 2)));
		context.register(DISK_CRIMSON_GRAVEL_FEATURE, new ConfiguredFeature<>(Feature.DISK, new DiskConfiguration(RuleBasedBlockStateProvider.simple(ModBlocks.CRIMSON_GRAVEL.BLOCK), BlockPredicate.matchesBlocks(List.of(Blocks.DIRT, ModBlocks.CRIMSON_GRASS.BLOCK)), UniformInt.of(2, 5), 2)));
	}

	private static RandomPatchConfiguration grassPatch(BlockStateProvider blockStateProvider, int i) {
		return FeatureUtils.simpleRandomPatchConfiguration(i, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(blockStateProvider)));
	}
}
