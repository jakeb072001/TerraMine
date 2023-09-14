package terramine.common.init;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import terramine.common.world.biome.CorruptionBiome;
import terramine.common.world.biome.CorruptionDesertBiome;
import terramine.common.world.biome.CrimsonBiome;
import terramine.common.world.biome.CrimsonDesertBiome;

import static terramine.TerraMine.id;

public class ModBiomes {
    public static final ResourceKey<Biome> CORRUPTION = register(id("corruption"));
    public static final ResourceKey<Biome> CORRUPTION_DESERT = register(id("corruption_desert"));
    public static final ResourceKey<Biome> CRIMSON = register(id("crimson"));
    public static final ResourceKey<Biome> CRIMSON_DESERT = register(id("crimson_desert"));

    private static ResourceKey<Biome> register(ResourceLocation name) {
        return ResourceKey.create(Registries.BIOME, name);
    }

    public static void bootstrap(BootstapContext<Biome> context) {
        HolderGetter<PlacedFeature> holderGetter = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> holderGetter2 = context.lookup(Registries.CONFIGURED_CARVER);
        context.register(CORRUPTION, CorruptionBiome.createCorruption(holderGetter, holderGetter2));
        context.register(CORRUPTION_DESERT, CorruptionDesertBiome.createCorruptionDesert(holderGetter, holderGetter2));
        context.register(CRIMSON, CrimsonBiome.createCrimson(holderGetter, holderGetter2));
        context.register(CRIMSON_DESERT, CrimsonDesertBiome.createCrimsonDesert(holderGetter, holderGetter2));
    }
}
