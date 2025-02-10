package terramine.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class TerraMineRegistryProvider extends FabricDynamicRegistryProvider {

    public TerraMineRegistryProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    // todo: need a better way of doing this
    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        final HolderLookup.RegistryLookup<ConfiguredFeature<?, ?>> configuredFeatureRegistry = registries.lookupOrThrow(Registries.CONFIGURED_FEATURE);
        final HolderLookup.RegistryLookup<PlacedFeature> placedFeatureRegistry = registries.lookupOrThrow(Registries.PLACED_FEATURE);
        final HolderLookup.RegistryLookup<ConfiguredWorldCarver<?>> carverRegistry = registries.lookupOrThrow(Registries.CONFIGURED_CARVER);
        final HolderLookup.RegistryLookup<Biome> biomeRegistry = registries.lookupOrThrow(Registries.BIOME);

        // Configured Features
        entries.add(ModFeatures.CAVE_CHEST_CONFIGURED, configuredFeatureRegistry.getOrThrow(ModFeatures.CAVE_CHEST_CONFIGURED).value());
        entries.add(ModFeatures.SURFACE_CHEST_CONFIGURED, configuredFeatureRegistry.getOrThrow(ModFeatures.SURFACE_CHEST_CONFIGURED).value());
        entries.add(ModFeatures.NETHER_CHEST_CONFIGURED, configuredFeatureRegistry.getOrThrow(ModFeatures.NETHER_CHEST_CONFIGURED).value());
        entries.add(ModFeatures.CORRUPTION_PLANTS, configuredFeatureRegistry.getOrThrow(ModFeatures.CORRUPTION_PLANTS).value());
        entries.add(ModFeatures.CRIMSON_PLANTS, configuredFeatureRegistry.getOrThrow(ModFeatures.CRIMSON_PLANTS).value());
        entries.add(ModFeatures.DISK_CORRUPT_SAND_FEATURE, configuredFeatureRegistry.getOrThrow(ModFeatures.DISK_CORRUPT_SAND_FEATURE).value());
        entries.add(ModFeatures.DISK_CORRUPT_GRAVEL_FEATURE, configuredFeatureRegistry.getOrThrow(ModFeatures.DISK_CORRUPT_GRAVEL_FEATURE).value());
        entries.add(ModFeatures.DISK_CRIMSON_SAND_FEATURE, configuredFeatureRegistry.getOrThrow(ModFeatures.DISK_CRIMSON_SAND_FEATURE).value());
        entries.add(ModFeatures.DISK_CRIMSON_GRAVEL_FEATURE, configuredFeatureRegistry.getOrThrow(ModFeatures.DISK_CRIMSON_GRAVEL_FEATURE).value());
        entries.add(ModFeatures.HELLSTONE_ORE_CONFIGURED, configuredFeatureRegistry.getOrThrow(ModFeatures.HELLSTONE_ORE_CONFIGURED).value());
        entries.add(ModFeatures.ORE_DEMONITE_FEATURE, configuredFeatureRegistry.getOrThrow(ModFeatures.ORE_DEMONITE_FEATURE).value());
        entries.add(ModFeatures.ORE_DEMONITE_SMALL_FEATURE, configuredFeatureRegistry.getOrThrow(ModFeatures.ORE_DEMONITE_SMALL_FEATURE).value());
        entries.add(ModFeatures.ORE_CRIMTANE_FEATURE, configuredFeatureRegistry.getOrThrow(ModFeatures.ORE_CRIMTANE_FEATURE).value());
        entries.add(ModFeatures.ORE_CRIMTANE_SMALL_FEATURE, configuredFeatureRegistry.getOrThrow(ModFeatures.ORE_CRIMTANE_SMALL_FEATURE).value());
        entries.add(ModFeatures.ORE_TIN_FEATURE, configuredFeatureRegistry.getOrThrow(ModFeatures.ORE_TIN_FEATURE).value());
        entries.add(ModFeatures.ORE_TIN_SMALL_FEATURE, configuredFeatureRegistry.getOrThrow(ModFeatures.ORE_TIN_SMALL_FEATURE).value());
        entries.add(ModFeatures.ORE_LEAD_FEATURE, configuredFeatureRegistry.getOrThrow(ModFeatures.ORE_LEAD_FEATURE).value());
        entries.add(ModFeatures.ORE_LEAD_SMALL_FEATURE, configuredFeatureRegistry.getOrThrow(ModFeatures.ORE_LEAD_SMALL_FEATURE).value());
        entries.add(ModFeatures.ORE_SILVER_FEATURE, configuredFeatureRegistry.getOrThrow(ModFeatures.ORE_SILVER_FEATURE).value());
        entries.add(ModFeatures.ORE_SILVER_SMALL_FEATURE, configuredFeatureRegistry.getOrThrow(ModFeatures.ORE_SILVER_SMALL_FEATURE).value());
        entries.add(ModFeatures.ORE_TUNGSTEN_FEATURE, configuredFeatureRegistry.getOrThrow(ModFeatures.ORE_TUNGSTEN_FEATURE).value());
        entries.add(ModFeatures.ORE_TUNGSTEN_SMALL_FEATURE, configuredFeatureRegistry.getOrThrow(ModFeatures.ORE_TUNGSTEN_SMALL_FEATURE).value());
        entries.add(ModFeatures.ORE_PLATINUM_FEATURE, configuredFeatureRegistry.getOrThrow(ModFeatures.ORE_PLATINUM_FEATURE).value());
        entries.add(ModFeatures.ORE_PLATINUM_SMALL_FEATURE, configuredFeatureRegistry.getOrThrow(ModFeatures.ORE_PLATINUM_SMALL_FEATURE).value());

        // Placed Features
        entries.add(ModPlacedFeatures.PLACED_HELLSTONE_ORE, placedFeatureRegistry.getOrThrow(ModPlacedFeatures.PLACED_HELLSTONE_ORE).value());
        entries.add(ModPlacedFeatures.PLACED_CAVE_CHEST, placedFeatureRegistry.getOrThrow(ModPlacedFeatures.PLACED_CAVE_CHEST).value());
        entries.add(ModPlacedFeatures.PLACED_SURFACE_CHEST, placedFeatureRegistry.getOrThrow(ModPlacedFeatures.PLACED_SURFACE_CHEST).value());
        entries.add(ModPlacedFeatures.PLACED_NETHER_CHEST, placedFeatureRegistry.getOrThrow(ModPlacedFeatures.PLACED_NETHER_CHEST).value());
        entries.add(ModPlacedFeatures.CORRUPTION_PLANT, placedFeatureRegistry.getOrThrow(ModPlacedFeatures.CORRUPTION_PLANT).value());
        entries.add(ModPlacedFeatures.CRIMSON_PLANT, placedFeatureRegistry.getOrThrow(ModPlacedFeatures.CRIMSON_PLANT).value());
        entries.add(ModPlacedFeatures.DISK_CORRUPT_SAND, placedFeatureRegistry.getOrThrow(ModPlacedFeatures.DISK_CORRUPT_SAND).value());
        entries.add(ModPlacedFeatures.DISK_CORRUPT_GRAVEL, placedFeatureRegistry.getOrThrow(ModPlacedFeatures.DISK_CORRUPT_GRAVEL).value());
        entries.add(ModPlacedFeatures.DISK_CRIMSON_SAND, placedFeatureRegistry.getOrThrow(ModPlacedFeatures.DISK_CRIMSON_SAND).value());
        entries.add(ModPlacedFeatures.DISK_CRIMSON_GRAVEL, placedFeatureRegistry.getOrThrow(ModPlacedFeatures.DISK_CRIMSON_GRAVEL).value());
        entries.add(ModPlacedFeatures.ORE_DEMONITE_UPPER, placedFeatureRegistry.getOrThrow(ModPlacedFeatures.ORE_DEMONITE_UPPER).value());
        entries.add(ModPlacedFeatures.ORE_DEMONITE_MIDDLE, placedFeatureRegistry.getOrThrow(ModPlacedFeatures.ORE_DEMONITE_MIDDLE).value());
        entries.add(ModPlacedFeatures.ORE_DEMONITE_SMALL, placedFeatureRegistry.getOrThrow(ModPlacedFeatures.ORE_DEMONITE_SMALL).value());
        entries.add(ModPlacedFeatures.ORE_CRIMTANE_UPPER, placedFeatureRegistry.getOrThrow(ModPlacedFeatures.ORE_CRIMTANE_UPPER).value());
        entries.add(ModPlacedFeatures.ORE_CRIMTANE_MIDDLE, placedFeatureRegistry.getOrThrow(ModPlacedFeatures.ORE_CRIMTANE_MIDDLE).value());
        entries.add(ModPlacedFeatures.ORE_CRIMTANE_SMALL, placedFeatureRegistry.getOrThrow(ModPlacedFeatures.ORE_CRIMTANE_SMALL).value());
        entries.add(ModPlacedFeatures.ORE_TIN, placedFeatureRegistry.getOrThrow(ModPlacedFeatures.ORE_TIN).value());
        entries.add(ModPlacedFeatures.ORE_TIN_LARGE, placedFeatureRegistry.getOrThrow(ModPlacedFeatures.ORE_TIN_LARGE).value());
        entries.add(ModPlacedFeatures.ORE_LEAD_UPPER, placedFeatureRegistry.getOrThrow(ModPlacedFeatures.ORE_LEAD_UPPER).value());
        entries.add(ModPlacedFeatures.ORE_LEAD_MIDDLE, placedFeatureRegistry.getOrThrow(ModPlacedFeatures.ORE_LEAD_MIDDLE).value());
        entries.add(ModPlacedFeatures.ORE_LEAD_SMALL, placedFeatureRegistry.getOrThrow(ModPlacedFeatures.ORE_LEAD_SMALL).value());
        entries.add(ModPlacedFeatures.ORE_SILVER, placedFeatureRegistry.getOrThrow(ModPlacedFeatures.ORE_SILVER).value());
        entries.add(ModPlacedFeatures.ORE_SILVER_SMALL, placedFeatureRegistry.getOrThrow(ModPlacedFeatures.ORE_SILVER_SMALL).value());
        entries.add(ModPlacedFeatures.ORE_TUNGSTEN, placedFeatureRegistry.getOrThrow(ModPlacedFeatures.ORE_TUNGSTEN).value());
        entries.add(ModPlacedFeatures.ORE_TUNGSTEN_SMALL, placedFeatureRegistry.getOrThrow(ModPlacedFeatures.ORE_TUNGSTEN_SMALL).value());
        entries.add(ModPlacedFeatures.ORE_PLATINUM, placedFeatureRegistry.getOrThrow(ModPlacedFeatures.ORE_PLATINUM).value());
        entries.add(ModPlacedFeatures.ORE_PLATINUM_LOWER, placedFeatureRegistry.getOrThrow(ModPlacedFeatures.ORE_PLATINUM_LOWER).value());

        // Carvers
        //entries.add(ModCarvers.CORRUPTION_PIT, carverRegistry.getOrThrow(ModCarvers.CORRUPTION_PIT).value());

        // Biomes
        entries.add(ModBiomes.CORRUPTION, biomeRegistry.getOrThrow(ModBiomes.CORRUPTION).value());
        entries.add(ModBiomes.CORRUPTION_DESERT, biomeRegistry.getOrThrow(ModBiomes.CORRUPTION_DESERT).value());
        entries.add(ModBiomes.CRIMSON, biomeRegistry.getOrThrow(ModBiomes.CRIMSON).value());
        entries.add(ModBiomes.CRIMSON_DESERT, biomeRegistry.getOrThrow(ModBiomes.CRIMSON_DESERT).value());
    }

    @Override
    public @NotNull String getName() {
        return "TerraMine";
    }
}
