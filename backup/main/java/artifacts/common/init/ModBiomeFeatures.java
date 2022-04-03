package terracraft.common.init;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

public class ModBiomeFeatures {
    public static final Holder<ConfiguredFeature<DiskConfiguration, ?>> DISK_CORRUPT_SAND_FEATURE = FeatureUtils.register("disk_corrupt_sand", Feature.DISK, new DiskConfiguration(ModBlocks.CORRUPTED_SAND.defaultBlockState(), UniformInt.of(2, 6), 2, List.of(Blocks.DIRT.defaultBlockState(), ModBlocks.CORRUPTED_GRASS.defaultBlockState())));
    public static final Holder<PlacedFeature> DISK_CORRUPT_SAND = PlacementUtils.register("disk_corrupt_sand", DISK_CORRUPT_SAND_FEATURE, CountPlacement.of(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
    public static final Holder<ConfiguredFeature<DiskConfiguration, ?>> DISK_CORRUPT_GRAVEL_FEATURE = FeatureUtils.register("disk_corrupt_gravel", Feature.DISK, new DiskConfiguration(ModBlocks.CORRUPTED_GRAVEL.defaultBlockState(), UniformInt.of(2, 5), 2, List.of(Blocks.DIRT.defaultBlockState(), ModBlocks.CORRUPTED_GRASS.defaultBlockState())));
    public static final Holder<PlacedFeature> DISK_CORRUPT_GRAVEL = PlacementUtils.register("disk_corrupt_gravel", DISK_CORRUPT_GRAVEL_FEATURE, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());

    public static void addDefaultCorruptSoftDisks(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, DISK_CORRUPT_SAND);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, MiscOverworldPlacements.DISK_CLAY);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, DISK_CORRUPT_GRAVEL);
    }
}
