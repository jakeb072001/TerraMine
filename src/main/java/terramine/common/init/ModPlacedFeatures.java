package terramine.common.init;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.material.Fluids;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static terramine.TerraMine.CONFIG;
import static terramine.TerraMine.id;

public class ModPlacedFeatures {

    // Misc
    public static final ResourceKey<PlacedFeature> PLACED_HELLSTONE_ORE = registerPlaced("hellstone_ore");
    public static final ResourceKey<PlacedFeature> PLACED_CAVE_CHEST = registerPlaced("cave_chest");
    public static final ResourceKey<PlacedFeature> PLACED_SURFACE_CHEST = registerPlaced("surface_chest");
    public static final ResourceKey<PlacedFeature> PLACED_NETHER_CHEST = registerPlaced("nether_chest");

    // Plants
    public static final ResourceKey<PlacedFeature> CORRUPTION_PLANT = registerPlaced("corruption_plant");
    public static final ResourceKey<PlacedFeature> CRIMSON_PLANT = registerPlaced("crimson_plant");

    // World Gen
    public static final ResourceKey<PlacedFeature> DISK_CORRUPT_SAND = registerPlaced("disk_corrupt_sand");
    public static final ResourceKey<PlacedFeature> DISK_CORRUPT_GRAVEL = registerPlaced("disk_corrupt_gravel");
    public static final ResourceKey<PlacedFeature> DISK_CRIMSON_SAND = registerPlaced("disk_crimson_sand");
    public static final ResourceKey<PlacedFeature> DISK_CRIMSON_GRAVEL = registerPlaced("disk_crimson_gravel");

    // Demonite
    public static final ResourceKey<PlacedFeature> ORE_DEMONITE_UPPER = registerPlaced("ore_demonite_upper");
    public static final ResourceKey<PlacedFeature> ORE_DEMONITE_MIDDLE = registerPlaced("ore_demonite_middle");
    public static final ResourceKey<PlacedFeature> ORE_DEMONITE_SMALL = registerPlaced("ore_demonite_small");

    // Crimtane
    public static final ResourceKey<PlacedFeature> ORE_CRIMTANE_UPPER = registerPlaced("ore_crimtane_upper");
    public static final ResourceKey<PlacedFeature> ORE_CRIMTANE_MIDDLE = registerPlaced("ore_crimtane_middle");
    public static final ResourceKey<PlacedFeature> ORE_CRIMTANE_SMALL = registerPlaced("ore_crimtane_small");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> holderGetter = context.lookup(Registries.CONFIGURED_FEATURE);
        context.register(PLACED_HELLSTONE_ORE, new PlacedFeature(getHolder(holderGetter, ModFeatures.HELLSTONE_ORE_CONFIGURED),
                List.of(RarityFilter.onAverageOnceEvery(CONFIG.worldgen.hellstoneRarity),
                        CountPlacement.of(5),
                        InSquarePlacement.spread(),
                        PlacementUtils.RANGE_10_10,
                        BiomeFilter.biome()
                )));
        context.register(PLACED_CAVE_CHEST, new PlacedFeature(getHolder(holderGetter, ModFeatures.CAVE_CHEST_CONFIGURED),
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
        context.register(PLACED_SURFACE_CHEST, new PlacedFeature(getHolder(holderGetter, ModFeatures.SURFACE_CHEST_CONFIGURED),
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
        context.register(PLACED_NETHER_CHEST, new PlacedFeature(getHolder(holderGetter, ModFeatures.NETHER_CHEST_CONFIGURED),
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

        context.register(ORE_DEMONITE_UPPER, new PlacedFeature(getHolder(holderGetter, ModFeatures.ORE_DEMONITE_FEATURE), commonOrePlacement(90, HeightRangePlacement.triangle(VerticalAnchor.absolute(80), VerticalAnchor.absolute(380)))));
        context.register(ORE_DEMONITE_MIDDLE, new PlacedFeature(getHolder(holderGetter, ModFeatures.ORE_DEMONITE_FEATURE), commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-10), VerticalAnchor.absolute(55)))));
        context.register(ORE_DEMONITE_SMALL, new PlacedFeature(getHolder(holderGetter, ModFeatures.ORE_DEMONITE_SMALL_FEATURE), commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.absolute(-10), VerticalAnchor.absolute(70)))));
        context.register(ORE_CRIMTANE_UPPER, new PlacedFeature(getHolder(holderGetter, ModFeatures.ORE_CRIMTANE_FEATURE), commonOrePlacement(90, HeightRangePlacement.triangle(VerticalAnchor.absolute(80), VerticalAnchor.absolute(380)))));
        context.register(ORE_CRIMTANE_MIDDLE, new PlacedFeature(getHolder(holderGetter, ModFeatures.ORE_CRIMTANE_FEATURE), commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-10), VerticalAnchor.absolute(55)))));
        context.register(ORE_CRIMTANE_SMALL, new PlacedFeature(getHolder(holderGetter, ModFeatures.ORE_CRIMTANE_SMALL_FEATURE), commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.absolute(-10), VerticalAnchor.absolute(70)))));
        context.register(CORRUPTION_PLANT, new PlacedFeature(getHolder(holderGetter, ModFeatures.CORRUPTION_PLANTS), List.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));
        context.register(CRIMSON_PLANT, new PlacedFeature(getHolder(holderGetter, ModFeatures.CRIMSON_PLANTS), List.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));
        context.register(DISK_CORRUPT_SAND, new PlacedFeature(getHolder(holderGetter, ModFeatures.DISK_CORRUPT_SAND_FEATURE), List.of(CountPlacement.of(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)), BiomeFilter.biome())));
        context.register(DISK_CORRUPT_GRAVEL, new PlacedFeature(getHolder(holderGetter, ModFeatures.DISK_CORRUPT_GRAVEL_FEATURE), List.of(InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)), BiomeFilter.biome())));
        context.register(DISK_CRIMSON_SAND, new PlacedFeature(getHolder(holderGetter, ModFeatures.DISK_CRIMSON_SAND_FEATURE), List.of(CountPlacement.of(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)), BiomeFilter.biome())));
        context.register(DISK_CRIMSON_GRAVEL, new PlacedFeature(getHolder(holderGetter, ModFeatures.DISK_CRIMSON_GRAVEL_FEATURE), List.of(InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)), BiomeFilter.biome())));
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

    private static Holder<ConfiguredFeature<?, ?>> getHolder(HolderGetter<ConfiguredFeature<?, ?>> holderGetter, ResourceKey<ConfiguredFeature<?, ?>> feature) {
        return holderGetter.getOrThrow(feature);
    }

    private static ResourceKey<PlacedFeature> registerPlaced(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, id(name));
    }
}
