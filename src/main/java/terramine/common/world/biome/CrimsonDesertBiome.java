package terramine.common.world.biome;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import terramine.common.init.ModEntities;
import terramine.common.init.ModPlacedFeatures;

public class CrimsonDesertBiome {
    public static Biome createCrimsonDesert(HolderGetter<PlacedFeature> holderGetter, HolderGetter<ConfiguredWorldCarver<?>> holderGetter2) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        //BiomeDefaultFeatures.desertSpawns(spawnSettings);
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntities.CRIMERA, 25, 1, 2));

        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(holderGetter, holderGetter2);
        BiomeDefaultFeatures.addFossilDecoration(generationSettings);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        ModPlacedFeatures.addCorruptionCaveCarver(generationSettings);
        BiomeDefaultFeatures.addDefaultCrystalFormations(generationSettings);
        BiomeDefaultFeatures.addDefaultMonsterRoom(generationSettings);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(generationSettings);
        BiomeDefaultFeatures.addDefaultSprings(generationSettings);
        BiomeDefaultFeatures.addSurfaceFreezing(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        ModPlacedFeatures.addDefaultCrimsonOres(generationSettings);
        ModPlacedFeatures.addDefaultCrimsonSoftDisks(generationSettings);
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
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST))
                        .build())
                .mobSpawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .build();
    }
}
