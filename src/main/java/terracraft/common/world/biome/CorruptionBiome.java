package terracraft.common.world.biome;

import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import terracraft.common.init.ModBiomeFeatures;

public class CorruptionBiome {
    public static final Biome CORRUPTION = createCorruption();

    private static Biome createCorruption() {
        // We specify what entities spawn and what features generate in the biome.
        // Aside from some structures, trees, rocks, plants and
        //   custom entities, these are mostly the same for each biome.
        // Vanilla configured features for biomes are defined in DefaultBiomeFeatures.

        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.monsters(spawnSettings, 100, 25, 100, false);
        //spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.CORRUPTION_ENEMIES, 4, 2, 3));

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
                .biomeCategory(Biome.BiomeCategory.PLAINS)
                .temperature(0.5F)
                .downfall(0.4F)
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0x9966ff)
                        .waterFogColor(0x9966ff)
                        .fogColor(0x9966ff)
                        .skyColor(0x9966ff) // comment out grass and foliage override for now to fix pallet crash
                        //.grassColorOverride(0x9966ff)
                        //.foliageColorOverride(0x9966ff)
                        .build())
                .mobSpawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .build();
    }
}
