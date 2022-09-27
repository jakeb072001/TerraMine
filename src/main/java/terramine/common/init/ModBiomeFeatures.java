package terramine.common.init;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.material.Fluids;
import terramine.TerraMine;

import java.util.List;

public class ModBiomeFeatures {
    // Plants
    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> CORRUPTION_PLANTS = FeatureUtils.register("corruption_plants", Feature.FLOWER, grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.single(ModBlocks.VILE_MUSHROOM.defaultBlockState())), 64));
    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> CRIMSON_PLANTS = FeatureUtils.register("crimson_plants", Feature.FLOWER, grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.single(ModBlocks.VICIOUS_MUSHROOM.defaultBlockState())), 64));

    // World Gen
    public static final Holder<ConfiguredFeature<DiskConfiguration, ?>> DISK_CORRUPT_SAND_FEATURE = FeatureUtils.register("disk_corrupt_sand", Feature.DISK, new DiskConfiguration(RuleBasedBlockStateProvider.simple(ModBlocks.CORRUPTED_SAND), BlockPredicate.matchesBlocks(List.of(Blocks.DIRT, ModBlocks.CORRUPTED_GRASS)), UniformInt.of(2, 6), 2));
    public static final Holder<PlacedFeature> DISK_CORRUPT_SAND = PlacementUtils.register("disk_corrupt_sand", DISK_CORRUPT_SAND_FEATURE, CountPlacement.of(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)), BiomeFilter.biome());
    public static final Holder<ConfiguredFeature<DiskConfiguration, ?>> DISK_CORRUPT_GRAVEL_FEATURE = FeatureUtils.register("disk_corrupt_gravel", Feature.DISK, new DiskConfiguration(RuleBasedBlockStateProvider.simple(ModBlocks.CORRUPTED_GRAVEL), BlockPredicate.matchesBlocks(List.of(Blocks.DIRT, ModBlocks.CORRUPTED_GRASS)), UniformInt.of(2, 5), 2));
    public static final Holder<PlacedFeature> DISK_CORRUPT_GRAVEL = PlacementUtils.register("disk_corrupt_gravel", DISK_CORRUPT_GRAVEL_FEATURE, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)), BiomeFilter.biome());
    public static final Holder<ConfiguredFeature<DiskConfiguration, ?>> DISK_CRIMSON_SAND_FEATURE = FeatureUtils.register("disk_crimson_sand", Feature.DISK, new DiskConfiguration(RuleBasedBlockStateProvider.simple(ModBlocks.CRIMSON_SAND), BlockPredicate.matchesBlocks(List.of(Blocks.DIRT, ModBlocks.CRIMSON_GRASS)), UniformInt.of(2, 6), 2));
    public static final Holder<PlacedFeature> DISK_CRIMSON_SAND = PlacementUtils.register("disk_crimson_sand", DISK_CRIMSON_SAND_FEATURE, CountPlacement.of(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)), BiomeFilter.biome());
    public static final Holder<ConfiguredFeature<DiskConfiguration, ?>> DISK_CRIMSON_GRAVEL_FEATURE = FeatureUtils.register("disk_crimson_gravel", Feature.DISK, new DiskConfiguration(RuleBasedBlockStateProvider.simple(ModBlocks.CRIMSON_GRAVEL), BlockPredicate.matchesBlocks(List.of(Blocks.DIRT, ModBlocks.CRIMSON_GRASS)), UniformInt.of(2, 5), 2));
    public static final Holder<PlacedFeature> DISK_CRIMSON_GRAVEL = PlacementUtils.register("disk_crimson_gravel", DISK_CRIMSON_GRAVEL_FEATURE, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)), BiomeFilter.biome());
    public static final Holder<PlacedFeature> CORRUPTION_PLANT = PlacementUtils.register("corruption_plant", CORRUPTION_PLANTS, RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
    public static final Holder<PlacedFeature> CRIMSON_PLANT = PlacementUtils.register("crimson_plant", CRIMSON_PLANTS, RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    // Ore Gen
    public static final RuleTest STONE_ORE_REPLACEABLES = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
    public static final RuleTest DEEPSLATE_ORE_REPLACEABLES = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
    public static final RuleTest CORRUPTION_STONE_ORE_REPLACEABLES = new TagMatchTest(TagKey.create(Registry.BLOCK_REGISTRY, TerraMine.id("corruption_stone_ore_replaceables")));
    public static final RuleTest CORRUPTION_DEEPSLATE_ORE_REPLACEABLES = new TagMatchTest(TagKey.create(Registry.BLOCK_REGISTRY, TerraMine.id("corruption_deepslate_ore_replaceables")));
    public static final RuleTest CRIMSON_STONE_ORE_REPLACEABLES = new TagMatchTest(TagKey.create(Registry.BLOCK_REGISTRY, TerraMine.id("crimson_stone_ore_replaceables")));
    public static final RuleTest CRIMSON_DEEPSLATE_ORE_REPLACEABLES = new TagMatchTest(TagKey.create(Registry.BLOCK_REGISTRY, TerraMine.id("crimson_deepslate_ore_replaceables")));

    // Demonite
    public static final List<OreConfiguration.TargetBlockState> ORE_DEMONITE_TARGET_LIST = List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.DEMONITE_ORE.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_DEMONITE_ORE.defaultBlockState()), OreConfiguration.target(CORRUPTION_STONE_ORE_REPLACEABLES, ModBlocks.DEMONITE_ORE.defaultBlockState()), OreConfiguration.target(CORRUPTION_DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_DEMONITE_ORE.defaultBlockState()));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_DEMONITE_FEATURE = FeatureUtils.register("ore_demonite", Feature.ORE, new OreConfiguration(ORE_DEMONITE_TARGET_LIST, 9));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_DEMONITE_SMALL_FEATURE = FeatureUtils.register("ore_demonite_small", Feature.ORE, new OreConfiguration(ORE_DEMONITE_TARGET_LIST, 4));
    public static final Holder<PlacedFeature> ORE_DEMONITE_UPPER = PlacementUtils.register("ore_demonite_upper", ORE_DEMONITE_FEATURE, commonOrePlacement(90, HeightRangePlacement.triangle(VerticalAnchor.absolute(80), VerticalAnchor.absolute(380))));
    public static final Holder<PlacedFeature> ORE_DEMONITE_MIDDLE = PlacementUtils.register("ore_demonite_middle", ORE_DEMONITE_FEATURE, commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-10), VerticalAnchor.absolute(55))));
    public static final Holder<PlacedFeature> ORE_DEMONITE_SMALL = PlacementUtils.register("ore_demonite_small", ORE_DEMONITE_SMALL_FEATURE, commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.absolute(-10), VerticalAnchor.absolute(70))));

    // Crimtane
    public static final List<OreConfiguration.TargetBlockState> ORE_CRIMTANE_TARGET_LIST = List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.CRIMTANE_ORE.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_CRIMTANE_ORE.defaultBlockState()), OreConfiguration.target(CRIMSON_STONE_ORE_REPLACEABLES, ModBlocks.CRIMTANE_ORE.defaultBlockState()), OreConfiguration.target(CRIMSON_DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_CRIMTANE_ORE.defaultBlockState()));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_CRIMTANE_FEATURE = FeatureUtils.register("ore_crimtane", Feature.ORE, new OreConfiguration(ORE_CRIMTANE_TARGET_LIST, 9));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_CRIMTANE_SMALL_FEATURE = FeatureUtils.register("ore_crimtane_small", Feature.ORE, new OreConfiguration(ORE_CRIMTANE_TARGET_LIST, 4));
    public static final Holder<PlacedFeature> ORE_CRIMTANE_UPPER = PlacementUtils.register("ore_crimtane_upper", ORE_CRIMTANE_FEATURE, commonOrePlacement(90, HeightRangePlacement.triangle(VerticalAnchor.absolute(80), VerticalAnchor.absolute(380))));
    public static final Holder<PlacedFeature> ORE_CRIMTANE_MIDDLE = PlacementUtils.register("ore_crimtane_middle", ORE_CRIMTANE_FEATURE, commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-10), VerticalAnchor.absolute(55))));
    public static final Holder<PlacedFeature> ORE_CRIMTANE_SMALL = PlacementUtils.register("ore_crimtane_small", ORE_CRIMTANE_SMALL_FEATURE, commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.absolute(-10), VerticalAnchor.absolute(70))));

    public static void addDefaultCorruptSoftDisks(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, DISK_CORRUPT_SAND);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, MiscOverworldPlacements.DISK_CLAY);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, DISK_CORRUPT_GRAVEL);
    }
    public static void addDefaultCorruptOres(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ORE_DEMONITE_UPPER);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ORE_DEMONITE_MIDDLE);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ORE_DEMONITE_SMALL);
    }
    public static void addDefaultCorruptVegetation(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_PLAINS);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CORRUPTION_PLANT);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
    }
    public static void addDefaultCrimsonSoftDisks(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, DISK_CRIMSON_SAND);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, MiscOverworldPlacements.DISK_CLAY);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, DISK_CRIMSON_GRAVEL);
    }
    public static void addDefaultCrimsonOres(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ORE_CRIMTANE_UPPER);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ORE_CRIMTANE_MIDDLE);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ORE_CRIMTANE_SMALL);
    }
    public static void addDefaultCrimsonVegetation(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_PLAINS);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CRIMSON_PLANT);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
    }
    public static void addCorruptionCaveCarver(BiomeGenerationSettings.Builder builder) {
        builder.addCarver(GenerationStep.Carving.AIR, ModCarvers.CORRUPTION_PIT);
    }
    private static List<PlacementModifier> commonOrePlacement(int i, PlacementModifier placementModifier) {
        return orePlacement(CountPlacement.of(i), placementModifier);
    }
    private static List<PlacementModifier> orePlacement(PlacementModifier placementModifier, PlacementModifier placementModifier2) {
        return List.of(placementModifier, InSquarePlacement.spread(), placementModifier2, BiomeFilter.biome());
    }
    private static RandomPatchConfiguration grassPatch(BlockStateProvider blockStateProvider, int i) {
        return FeatureUtils.simpleRandomPatchConfiguration(i, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(blockStateProvider)));
    }
}
