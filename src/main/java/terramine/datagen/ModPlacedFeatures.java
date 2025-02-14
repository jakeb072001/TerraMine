package terramine.datagen;

import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.material.Fluids;
import terramine.common.components.OreComponent;
import terramine.common.init.ModComponents;

import java.util.List;

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

    // Pre-Hardmode Ores
    public static final ResourceKey<PlacedFeature> ORE_TIN = registerPlaced("ore_tin");
    public static final ResourceKey<PlacedFeature> ORE_TIN_LARGE = registerPlaced("ore_tin_large");
    public static final ResourceKey<PlacedFeature> ORE_LEAD_UPPER = registerPlaced("ore_lead_upper");
    public static final ResourceKey<PlacedFeature> ORE_LEAD_MIDDLE = registerPlaced("ore_lead_middle");
    public static final ResourceKey<PlacedFeature> ORE_LEAD_SMALL = registerPlaced("ore_lead_small");
    public static final ResourceKey<PlacedFeature> ORE_SILVER = registerPlaced("ore_silver");
    public static final ResourceKey<PlacedFeature> ORE_SILVER_SMALL = registerPlaced("ore_silver_small");
    public static final ResourceKey<PlacedFeature> ORE_TUNGSTEN = registerPlaced("ore_tungsten");
    public static final ResourceKey<PlacedFeature> ORE_TUNGSTEN_SMALL = registerPlaced("ore_tungsten_small");
    public static final ResourceKey<PlacedFeature> ORE_PLATINUM = registerPlaced("ore_platinum");
    public static final ResourceKey<PlacedFeature> ORE_PLATINUM_LOWER = registerPlaced("ore_platinum_lower");
    public static final ResourceKey<PlacedFeature> ORE_PLATINUM_EXTRA = registerPlaced("ore_platinum_extra");

    // Demonite
    public static final ResourceKey<PlacedFeature> ORE_DEMONITE_UPPER = registerPlaced("ore_demonite_upper");
    public static final ResourceKey<PlacedFeature> ORE_DEMONITE_MIDDLE = registerPlaced("ore_demonite_middle");
    public static final ResourceKey<PlacedFeature> ORE_DEMONITE_SMALL = registerPlaced("ore_demonite_small");

    // Crimtane
    public static final ResourceKey<PlacedFeature> ORE_CRIMTANE_UPPER = registerPlaced("ore_crimtane_upper");
    public static final ResourceKey<PlacedFeature> ORE_CRIMTANE_MIDDLE = registerPlaced("ore_crimtane_middle");
    public static final ResourceKey<PlacedFeature> ORE_CRIMTANE_SMALL = registerPlaced("ore_crimtane_small");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
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

        // Copper Alternative
        context.register(ORE_TIN, new PlacedFeature(getHolder(holderGetter, ModFeatures.ORE_TIN_FEATURE), commonOrePlacement(16, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(112)))));
        context.register(ORE_TIN_LARGE, new PlacedFeature(getHolder(holderGetter, ModFeatures.ORE_TIN_FEATURE), commonOrePlacement(16, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(112)))));

        // Iron Alternative
        context.register(ORE_LEAD_UPPER, new PlacedFeature(getHolder(holderGetter, ModFeatures.ORE_LEAD_FEATURE), commonOrePlacement(90, HeightRangePlacement.triangle(VerticalAnchor.absolute(80), VerticalAnchor.absolute(380)))));
        context.register(ORE_LEAD_MIDDLE, new PlacedFeature(getHolder(holderGetter, ModFeatures.ORE_LEAD_FEATURE), commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-10), VerticalAnchor.absolute(55)))));
        context.register(ORE_LEAD_SMALL, new PlacedFeature(getHolder(holderGetter, ModFeatures.ORE_LEAD_SMALL_FEATURE), commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.absolute(-10), VerticalAnchor.absolute(70)))));

        // New Tier
        context.register(ORE_SILVER, new PlacedFeature(getHolder(holderGetter, ModFeatures.ORE_SILVER_FEATURE), commonOrePlacement(5, HeightRangePlacement.triangle(VerticalAnchor.absolute(-50), VerticalAnchor.absolute(55)))));
        context.register(ORE_SILVER_SMALL, new PlacedFeature(getHolder(holderGetter, ModFeatures.ORE_SILVER_SMALL_FEATURE), commonOrePlacement(5, HeightRangePlacement.uniform(VerticalAnchor.absolute(-50), VerticalAnchor.absolute(70)))));

        context.register(ORE_TUNGSTEN, new PlacedFeature(getHolder(holderGetter, ModFeatures.ORE_TUNGSTEN_FEATURE), commonOrePlacement(5, HeightRangePlacement.triangle(VerticalAnchor.absolute(-50), VerticalAnchor.absolute(55)))));
        context.register(ORE_TUNGSTEN_SMALL, new PlacedFeature(getHolder(holderGetter, ModFeatures.ORE_TUNGSTEN_SMALL_FEATURE), commonOrePlacement(5, HeightRangePlacement.uniform(VerticalAnchor.absolute(-50), VerticalAnchor.absolute(70)))));

        // Gold Alternative
        context.register(ORE_PLATINUM, new PlacedFeature(getHolder(holderGetter, ModFeatures.ORE_PLATINUM_FEATURE), commonOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(32)))));
        context.register(ORE_PLATINUM_LOWER, new PlacedFeature(getHolder(holderGetter, ModFeatures.ORE_PLATINUM_FEATURE), orePlacement(CountPlacement.of(UniformInt.of(0, 1)), HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-48)))));
        context.register(ORE_PLATINUM_EXTRA, new PlacedFeature(getHolder(holderGetter, ModFeatures.ORE_PLATINUM_FEATURE), commonOrePlacement(50, HeightRangePlacement.uniform(VerticalAnchor.absolute(32), VerticalAnchor.absolute(256)))));

        // Corruption and Crimson
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
        //builder.addCarver(ModCarvers.CORRUPTION_PIT);
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
