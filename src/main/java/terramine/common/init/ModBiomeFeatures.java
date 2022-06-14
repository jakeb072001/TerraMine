package terramine.common.init;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
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
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModBiomeFeatures {
    // World Gen
    public static final Holder<ConfiguredFeature<DiskConfiguration, ?>> DISK_CORRUPT_SAND_FEATURE = FeatureUtils.register("disk_corrupt_sand", Feature.DISK, new DiskConfiguration(RuleBasedBlockStateProvider.simple(ModBlocks.CORRUPTED_SAND), BlockPredicate.matchesBlocks(List.of(Blocks.DIRT, ModBlocks.CORRUPTED_GRASS)), UniformInt.of(2, 6), 2));
    public static final Holder<PlacedFeature> DISK_CORRUPT_SAND = PlacementUtils.register("disk_corrupt_sand", DISK_CORRUPT_SAND_FEATURE, CountPlacement.of(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
    public static final Holder<ConfiguredFeature<DiskConfiguration, ?>> DISK_CORRUPT_GRAVEL_FEATURE = FeatureUtils.register("disk_corrupt_gravel", Feature.DISK, new DiskConfiguration(RuleBasedBlockStateProvider.simple(ModBlocks.CORRUPTED_GRAVEL), BlockPredicate.matchesBlocks(List.of(Blocks.DIRT, ModBlocks.CORRUPTED_GRASS)), UniformInt.of(2, 5), 2));
    public static final Holder<PlacedFeature> DISK_CORRUPT_GRAVEL = PlacementUtils.register("disk_corrupt_gravel", DISK_CORRUPT_GRAVEL_FEATURE, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());

    // Ore Gen
    public static final RuleTest STONE_ORE_REPLACEABLES = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
    public static final RuleTest DEEPSLATE_ORE_REPLACEABLES = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
    public static final List<OreConfiguration.TargetBlockState> ORE_DEMONITE_TARGET_LIST = List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.DEMONITE_ORE.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_DEMONITE_ORE.defaultBlockState()));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_DEMONITE_FEATURE = FeatureUtils.register("ore_demonite", Feature.ORE, new OreConfiguration(ORE_DEMONITE_TARGET_LIST, 9));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_DEMONITE_SMALL_FEATURE = FeatureUtils.register("ore_demonite_small", Feature.ORE, new OreConfiguration(ORE_DEMONITE_TARGET_LIST, 4));
    public static final Holder<PlacedFeature> ORE_DEMONITE_UPPER = PlacementUtils.register("ore_demonite_upper", ORE_DEMONITE_FEATURE, commonOrePlacement(90, HeightRangePlacement.triangle(VerticalAnchor.absolute(80), VerticalAnchor.absolute(384))));
    public static final Holder<PlacedFeature> ORE_DEMONITE_MIDDLE = PlacementUtils.register("ore_demonite_middle", ORE_DEMONITE_FEATURE, commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56))));
    public static final Holder<PlacedFeature> ORE_DEMONITE_SMALL = PlacementUtils.register("ore_demonite_small", ORE_DEMONITE_SMALL_FEATURE, commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(72))));

    public static void addDefaultCorruptSoftDisks(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, DISK_CORRUPT_SAND);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, MiscOverworldPlacements.DISK_CLAY);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, DISK_CORRUPT_GRAVEL);
    }
    public static void addDefaultCorruptOres(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModBiomeFeatures.ORE_DEMONITE_UPPER);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModBiomeFeatures.ORE_DEMONITE_MIDDLE);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModBiomeFeatures.ORE_DEMONITE_SMALL);
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
}
