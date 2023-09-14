package terramine.common.init;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import terramine.TerraMine;
import terramine.common.world.CaveChestFeature;
import terramine.common.world.NetherChestFeature;
import terramine.common.world.SurfaceChestFeature;
import terramine.common.world.TerrariaJigsawStructure;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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
	public static final ResourceKey<ConfiguredFeature<?, ?>> HELLSTONE_ORE_CONFIGURED = registerConfigured("hellstone_ore");

	// Demonite
	public static final List<OreConfiguration.TargetBlockState> ORE_DEMONITE_TARGET_LIST = List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.DEMONITE_ORE.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_DEMONITE_ORE.defaultBlockState()), OreConfiguration.target(CORRUPTION_STONE_ORE_REPLACEABLES, ModBlocks.DEMONITE_ORE.defaultBlockState()), OreConfiguration.target(CORRUPTION_DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_DEMONITE_ORE.defaultBlockState()));
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_DEMONITE_FEATURE = registerConfigured("ore_demonite");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_DEMONITE_SMALL_FEATURE = registerConfigured("ore_demonite_small");

	// Crimtane
	public static final List<OreConfiguration.TargetBlockState> ORE_CRIMTANE_TARGET_LIST = List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.CRIMTANE_ORE.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_CRIMTANE_ORE.defaultBlockState()), OreConfiguration.target(CRIMSON_STONE_ORE_REPLACEABLES, ModBlocks.CRIMTANE_ORE.defaultBlockState()), OreConfiguration.target(CRIMSON_DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_CRIMTANE_ORE.defaultBlockState()));
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

		// Structures
		Registry.register(BuiltInRegistries.STRUCTURE_TYPE, TerraMine.id("terraria_jigsaw_structure"), TERRARIA_JIGSAW_STRUCTURE);
	}

	public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
		context.register(HELLSTONE_ORE_CONFIGURED, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(new BlockMatchTest(Blocks.NETHERRACK), ModBlocks.HELLSTONE_ORE.defaultBlockState(),5)));
		context.register(CAVE_CHEST_CONFIGURED, new ConfiguredFeature<>(CAVE_CHEST, FeatureConfiguration.NONE));
		context.register(SURFACE_CHEST_CONFIGURED, new ConfiguredFeature<>(SURFACE_CHEST, FeatureConfiguration.NONE));
		context.register(NETHER_CHEST_CONFIGURED, new ConfiguredFeature<>(NETHER_CHEST, FeatureConfiguration.NONE));

		context.register(ORE_DEMONITE_FEATURE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ORE_DEMONITE_TARGET_LIST, 9)));
		context.register(ORE_DEMONITE_SMALL_FEATURE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ORE_DEMONITE_TARGET_LIST, 4)));
		context.register(ORE_CRIMTANE_FEATURE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ORE_CRIMTANE_TARGET_LIST, 9)));
		context.register(ORE_CRIMTANE_SMALL_FEATURE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ORE_CRIMTANE_TARGET_LIST, 4)));
		context.register(CORRUPTION_PLANTS, new ConfiguredFeature<>(Feature.FLOWER, grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.single(ModBlocks.VILE_MUSHROOM.defaultBlockState())), 64)));
		context.register(CRIMSON_PLANTS, new ConfiguredFeature<>(Feature.FLOWER, grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.single(ModBlocks.VICIOUS_MUSHROOM.defaultBlockState())), 64)));
		context.register(DISK_CORRUPT_SAND_FEATURE, new ConfiguredFeature<>(Feature.DISK, new DiskConfiguration(RuleBasedBlockStateProvider.simple(ModBlocks.CORRUPTED_SAND), BlockPredicate.matchesBlocks(List.of(Blocks.DIRT, ModBlocks.CORRUPTED_GRASS)), UniformInt.of(2, 6), 2)));
		context.register(DISK_CORRUPT_GRAVEL_FEATURE, new ConfiguredFeature<>(Feature.DISK, new DiskConfiguration(RuleBasedBlockStateProvider.simple(ModBlocks.CORRUPTED_GRAVEL), BlockPredicate.matchesBlocks(List.of(Blocks.DIRT, ModBlocks.CORRUPTED_GRASS)), UniformInt.of(2, 5), 2)));
		context.register(DISK_CRIMSON_SAND_FEATURE, new ConfiguredFeature<>(Feature.DISK, new DiskConfiguration(RuleBasedBlockStateProvider.simple(ModBlocks.CRIMSON_SAND), BlockPredicate.matchesBlocks(List.of(Blocks.DIRT, ModBlocks.CRIMSON_GRASS)), UniformInt.of(2, 6), 2)));
		context.register(DISK_CRIMSON_GRAVEL_FEATURE, new ConfiguredFeature<>(Feature.DISK, new DiskConfiguration(RuleBasedBlockStateProvider.simple(ModBlocks.CRIMSON_GRAVEL), BlockPredicate.matchesBlocks(List.of(Blocks.DIRT, ModBlocks.CRIMSON_GRASS)), UniformInt.of(2, 5), 2)));
	}

	private static RandomPatchConfiguration grassPatch(BlockStateProvider blockStateProvider, int i) {
		return FeatureUtils.simpleRandomPatchConfiguration(i, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(blockStateProvider)));
	}
}
