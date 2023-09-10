package terramine.common.world.biome;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import terramine.common.init.ModBiomeFeatures;
import terramine.common.init.ModEntities;

public class CrimsonDesertBiome {
    public static Biome createCrimsonDesert(FabricDynamicRegistryProvider.Entries entries) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        //BiomeDefaultFeatures.desertSpawns(spawnSettings);
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntities.CRIMERA, 25, 0, 2));

        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(entries.placedFeatures(), entries.configuredCarvers());
        BiomeDefaultFeatures.addFossilDecoration(generationSettings);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        ModBiomeFeatures.addCorruptionCaveCarver(generationSettings);
        BiomeDefaultFeatures.addDefaultCrystalFormations(generationSettings);
        BiomeDefaultFeatures.addDefaultMonsterRoom(generationSettings);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(generationSettings);
        BiomeDefaultFeatures.addDefaultSprings(generationSettings);
        BiomeDefaultFeatures.addSurfaceFreezing(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        ModBiomeFeatures.addDefaultCrimsonOres(generationSettings);
        ModBiomeFeatures.addDefaultCrimsonSoftDisks(generationSettings);
        BiomeDefaultFeatures.addWaterTrees(generationSettings);
        BiomeDefaultFeatures.addDesertVegetation(generationSettings);
        BiomeDefaultFeatures.addDefaultMushrooms(generationSettings);
        BiomeDefaultFeatures.addDesertExtraVegetation(generationSettings);
        BiomeDefaultFeatures.addDesertExtraDecoration(generationSettings);

        return (new Biome.BiomeBuilder())
                .hasPrecipitation(false)
                .temperature(2F)
                .downfall(0F)
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0xdc143c)
                        .waterFogColor(0xdc143c)
                        .fogColor(0xdc143c)
                        .skyColor(0xdc143c)
                        .grassColorOverride(0xdc143c)
                        .foliageColorOverride(0xdc143c)
                        .build())
                .mobSpawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .build();
    }
}
