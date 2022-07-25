package terramine.common.world.biome;

import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import terramine.common.init.ModBiomeFeatures;
import terramine.common.init.ModEntities;

public class CorruptionBiome {
    public static final Biome CORRUPTION = createCorruption();

    private static Biome createCorruption() {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        //BiomeDefaultFeatures.monsters(spawnSettings, 100, 25, 100, false);
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntities.EATER_OF_SOULS, 50, 1, 2));

        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder();
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        ModBiomeFeatures.addCorruptionCaveCarver(generationSettings);
        BiomeDefaultFeatures.addDefaultCrystalFormations(generationSettings);
        BiomeDefaultFeatures.addDefaultMonsterRoom(generationSettings);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(generationSettings);
        BiomeDefaultFeatures.addDefaultSprings(generationSettings);
        BiomeDefaultFeatures.addSurfaceFreezing(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        ModBiomeFeatures.addDefaultCorruptOres(generationSettings);
        ModBiomeFeatures.addDefaultCorruptSoftDisks(generationSettings);
        BiomeDefaultFeatures.addWaterTrees(generationSettings);
        BiomeDefaultFeatures.addPlainVegetation(generationSettings);
        BiomeDefaultFeatures.addDefaultMushrooms(generationSettings);
        BiomeDefaultFeatures.addDefaultExtraVegetation(generationSettings);
        BiomeDefaultFeatures.addDefaultSeagrass(generationSettings);

        return (new Biome.BiomeBuilder())
                .precipitation(Biome.Precipitation.RAIN)
                .temperature(0.5F)
                .downfall(0.4F)
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0x9966ff)
                        .waterFogColor(0x9966ff)
                        .fogColor(0x9966ff)
                        .skyColor(0x9966ff) // todo: comment out grass and foliage override to fix pallet crash when biome spread mixin is enables
                        .grassColorOverride(0x9966ff)
                        .foliageColorOverride(0x9966ff)
                        .build())
                .mobSpawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .build();
    }
}
