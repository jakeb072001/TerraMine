package terramine.common.init;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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
import java.util.concurrent.CompletableFuture;

import static terramine.TerraMine.id;

public class ModBiomeFeatures extends FabricDynamicRegistryProvider {
    // Plants
    public static final ResourceKey<ConfiguredFeature<?, ?>> CORRUPTION_PLANTS = createConfiguredKey("corruption_plant");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CRIMSON_PLANTS = createConfiguredKey("crimson_plant");
    public static final ResourceKey<PlacedFeature> CORRUPTION_PLANT = createPlacedKey("corruption_plant");
    public static final ResourceKey<PlacedFeature> CRIMSON_PLANT = createPlacedKey("crimson_plant");

    // World Gen
    public static final ResourceKey<ConfiguredFeature<?, ?>> DISK_CORRUPT_SAND_FEATURE = createConfiguredKey("disk_corrupt_sand");
    public static final ResourceKey<PlacedFeature> DISK_CORRUPT_SAND = createPlacedKey("disk_corrupt_sand");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DISK_CORRUPT_GRAVEL_FEATURE = createConfiguredKey("disk_corrupt_gravel");
    public static final ResourceKey<PlacedFeature> DISK_CORRUPT_GRAVEL = createPlacedKey("disk_corrupt_gravel");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DISK_CRIMSON_SAND_FEATURE = createConfiguredKey("disk_crimson_sand");
    public static final ResourceKey<PlacedFeature> DISK_CRIMSON_SAND = createPlacedKey("disk_crimson_sand");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DISK_CRIMSON_GRAVEL_FEATURE = createConfiguredKey("disk_crimson_gravel");
    public static final ResourceKey<PlacedFeature> DISK_CRIMSON_GRAVEL = createPlacedKey("disk_crimson_gravel");

    // Ore Gen
    public static final RuleTest STONE_ORE_REPLACEABLES = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
    public static final RuleTest DEEPSLATE_ORE_REPLACEABLES = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
    public static final RuleTest CORRUPTION_STONE_ORE_REPLACEABLES = new TagMatchTest(TagKey.create(Registries.BLOCK, id("corruption_stone_ore_replaceables")));
    public static final RuleTest CORRUPTION_DEEPSLATE_ORE_REPLACEABLES = new TagMatchTest(TagKey.create(Registries.BLOCK, id("corruption_deepslate_ore_replaceables")));
    public static final RuleTest CRIMSON_STONE_ORE_REPLACEABLES = new TagMatchTest(TagKey.create(Registries.BLOCK, id("crimson_stone_ore_replaceables")));
    public static final RuleTest CRIMSON_DEEPSLATE_ORE_REPLACEABLES = new TagMatchTest(TagKey.create(Registries.BLOCK, id("crimson_deepslate_ore_replaceables")));

    // Demonite
    public static final List<OreConfiguration.TargetBlockState> ORE_DEMONITE_TARGET_LIST = List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.DEMONITE_ORE.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_DEMONITE_ORE.defaultBlockState()), OreConfiguration.target(CORRUPTION_STONE_ORE_REPLACEABLES, ModBlocks.DEMONITE_ORE.defaultBlockState()), OreConfiguration.target(CORRUPTION_DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_DEMONITE_ORE.defaultBlockState()));
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_DEMONITE_FEATURE = createConfiguredKey("ore_demonite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_DEMONITE_SMALL_FEATURE = createConfiguredKey("ore_demonite_small");
    public static final ResourceKey<PlacedFeature> ORE_DEMONITE_UPPER = createPlacedKey("ore_demonite_upper");
    public static final ResourceKey<PlacedFeature> ORE_DEMONITE_MIDDLE = createPlacedKey("ore_demonite_middle");
    public static final ResourceKey<PlacedFeature> ORE_DEMONITE_SMALL = createPlacedKey("ore_demonite_small");

    // Crimtane
    public static final List<OreConfiguration.TargetBlockState> ORE_CRIMTANE_TARGET_LIST = List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.CRIMTANE_ORE.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_CRIMTANE_ORE.defaultBlockState()), OreConfiguration.target(CRIMSON_STONE_ORE_REPLACEABLES, ModBlocks.CRIMTANE_ORE.defaultBlockState()), OreConfiguration.target(CRIMSON_DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_CRIMTANE_ORE.defaultBlockState()));
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_CRIMTANE_FEATURE = createConfiguredKey("ore_crimtane");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_CRIMTANE_SMALL_FEATURE = createConfiguredKey("ore_crimtane_small");
    public static final ResourceKey<PlacedFeature> ORE_CRIMTANE_UPPER = createPlacedKey("ore_crimtane_upper");
    public static final ResourceKey<PlacedFeature> ORE_CRIMTANE_MIDDLE = createPlacedKey("ore_crimtane_middle");
    public static final ResourceKey<PlacedFeature> ORE_CRIMTANE_SMALL = createPlacedKey("ore_crimtane_small");

    public ModBiomeFeatures(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

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

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        entries.add(ORE_DEMONITE_FEATURE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ORE_DEMONITE_TARGET_LIST, 9)));
        entries.add(ORE_DEMONITE_SMALL_FEATURE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ORE_DEMONITE_TARGET_LIST, 4)));
        entries.add(ORE_CRIMTANE_FEATURE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ORE_CRIMTANE_TARGET_LIST, 9)));
        entries.add(ORE_CRIMTANE_SMALL_FEATURE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ORE_CRIMTANE_TARGET_LIST, 4)));
        entries.add(CORRUPTION_PLANTS, new ConfiguredFeature<>(Feature.FLOWER, grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.single(ModBlocks.VILE_MUSHROOM.defaultBlockState())), 64)));
        entries.add(CRIMSON_PLANTS, new ConfiguredFeature<>(Feature.FLOWER, grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.single(ModBlocks.VICIOUS_MUSHROOM.defaultBlockState())), 64)));
        entries.add(DISK_CORRUPT_SAND_FEATURE, new ConfiguredFeature<>(Feature.DISK, new DiskConfiguration(RuleBasedBlockStateProvider.simple(ModBlocks.CORRUPTED_SAND), BlockPredicate.matchesBlocks(List.of(Blocks.DIRT, ModBlocks.CORRUPTED_GRASS)), UniformInt.of(2, 6), 2)));
        entries.add(DISK_CORRUPT_GRAVEL_FEATURE, new ConfiguredFeature<>(Feature.DISK, new DiskConfiguration(RuleBasedBlockStateProvider.simple(ModBlocks.CORRUPTED_GRAVEL), BlockPredicate.matchesBlocks(List.of(Blocks.DIRT, ModBlocks.CORRUPTED_GRASS)), UniformInt.of(2, 5), 2)));
        entries.add(DISK_CRIMSON_SAND_FEATURE, new ConfiguredFeature<>(Feature.DISK, new DiskConfiguration(RuleBasedBlockStateProvider.simple(ModBlocks.CRIMSON_SAND), BlockPredicate.matchesBlocks(List.of(Blocks.DIRT, ModBlocks.CRIMSON_GRASS)), UniformInt.of(2, 6), 2)));
        entries.add(DISK_CRIMSON_GRAVEL_FEATURE, new ConfiguredFeature<>(Feature.DISK, new DiskConfiguration(RuleBasedBlockStateProvider.simple(ModBlocks.CRIMSON_GRAVEL), BlockPredicate.matchesBlocks(List.of(Blocks.DIRT, ModBlocks.CRIMSON_GRASS)), UniformInt.of(2, 5), 2)));

        HolderGetter<ConfiguredFeature<?, ?>> holderGetter = entries.getLookup(Registries.CONFIGURED_FEATURE);
        entries.add(ORE_DEMONITE_UPPER, new PlacedFeature(getHolder(holderGetter, ORE_DEMONITE_FEATURE), commonOrePlacement(90, HeightRangePlacement.triangle(VerticalAnchor.absolute(80), VerticalAnchor.absolute(380)))));
        entries.add(ORE_DEMONITE_MIDDLE, new PlacedFeature(getHolder(holderGetter, ORE_DEMONITE_FEATURE), commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-10), VerticalAnchor.absolute(55)))));
        entries.add(ORE_DEMONITE_SMALL, new PlacedFeature(getHolder(holderGetter, ORE_DEMONITE_SMALL_FEATURE), commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.absolute(-10), VerticalAnchor.absolute(70)))));
        entries.add(ORE_CRIMTANE_UPPER, new PlacedFeature(getHolder(holderGetter, ORE_CRIMTANE_FEATURE), commonOrePlacement(90, HeightRangePlacement.triangle(VerticalAnchor.absolute(80), VerticalAnchor.absolute(380)))));
        entries.add(ORE_CRIMTANE_MIDDLE, new PlacedFeature(getHolder(holderGetter, ORE_CRIMTANE_FEATURE), commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-10), VerticalAnchor.absolute(55)))));
        entries.add(ORE_CRIMTANE_SMALL, new PlacedFeature(getHolder(holderGetter, ORE_CRIMTANE_SMALL_FEATURE), commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.absolute(-10), VerticalAnchor.absolute(70)))));
        entries.add(CORRUPTION_PLANT, new PlacedFeature(getHolder(holderGetter, CORRUPTION_PLANTS), List.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));
        entries.add(CRIMSON_PLANT, new PlacedFeature(getHolder(holderGetter, CRIMSON_PLANTS), List.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));
        entries.add(DISK_CORRUPT_SAND, new PlacedFeature(getHolder(holderGetter, DISK_CORRUPT_SAND_FEATURE), List.of(CountPlacement.of(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)), BiomeFilter.biome())));
        entries.add(DISK_CORRUPT_GRAVEL, new PlacedFeature(getHolder(holderGetter, DISK_CORRUPT_GRAVEL_FEATURE), List.of(InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)), BiomeFilter.biome())));
        entries.add(DISK_CRIMSON_SAND, new PlacedFeature(getHolder(holderGetter, DISK_CRIMSON_SAND_FEATURE), List.of(CountPlacement.of(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)), BiomeFilter.biome())));
        entries.add(DISK_CRIMSON_GRAVEL, new PlacedFeature(getHolder(holderGetter, DISK_CRIMSON_GRAVEL_FEATURE), List.of(InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)), BiomeFilter.biome())));
    }

    private Holder<ConfiguredFeature<?, ?>> getHolder(HolderGetter<ConfiguredFeature<?, ?>> holderGetter, ResourceKey<ConfiguredFeature<?, ?>> feature) {
        return holderGetter.getOrThrow(feature);
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> createConfiguredKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, id(name));
    }
    public static ResourceKey<PlacedFeature> createPlacedKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, id(name));
    }

    @Override
    public String getName() {
        return "Terramine";
    }
}
