package terramine.common.world.biome;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terramine.TerraMine;
import terramine.common.components.SyncedBooleanComponent;
import terramine.common.init.ModBiomes;
import terramine.common.init.ModComponents;

import java.util.function.Consumer;

public class BiomeAdder extends Region {

    public BiomeAdder(ResourceLocation name, RegionType type, int weight) {
        super(name, type, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper)
    {
        if (TerraMine.CONFIG.worldgen.evilBiomeEnabled) {
            if (!TerraMine.CONFIG.worldgen.forceCorruption && !TerraMine.CONFIG.worldgen.forceCrimson) {
                if (!ModComponents.EVIL_TYPE.get(SyncedBooleanComponent.getLevelData()).get()) {
                    addCorruptionBiomes(mapper);
                } else {
                    addCrimsonBiomes(mapper);
                }
            } else if (TerraMine.CONFIG.worldgen.forceCorruption) {
                addCorruptionBiomes(mapper);
            }
        }
    }

    private void addCorruptionBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        this.addModifiedVanillaOverworldBiomes(mapper, builder -> {
            builder.replaceBiome(Biomes.PLAINS, ModBiomes.CORRUPTION);
            builder.replaceBiome(Biomes.DESERT, ModBiomes.CORRUPTION_DESERT);
        });
    }

    private void addCrimsonBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        this.addModifiedVanillaOverworldBiomes(mapper, builder -> {
            builder.replaceBiome(Biomes.PLAINS, ModBiomes.CRIMSON);
            builder.replaceBiome(Biomes.DESERT, ModBiomes.CRIMSON_DESERT);
        });
    }
}
