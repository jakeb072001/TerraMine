package terracraft.common.init;

import terracraft.Artifacts;
import terracraft.common.world.biome.CorruptionBiome;
import terracraft.common.world.biome.CorruptionDesertBiome;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public class Biomes {
    public static final ResourceKey<Biome> CORRUPTION = register("corruption");
    public static final ResourceKey<Biome> CORRUPTION_DESERT = register("corruption_desert");

    public static void registerAll() {
        register(CORRUPTION, CorruptionBiome.CORRUPTION);
        register(CORRUPTION_DESERT, CorruptionDesertBiome.CORRUPTION_DESERT);
    }

    private static void register(ResourceKey<Biome> key, Biome biome) {
        Registry.register(BuiltinRegistries.BIOME, key, biome);
    }

    private static ResourceKey<Biome> register(String name)
    {
        return ResourceKey.create(Registry.BIOME_REGISTRY, Artifacts.id(name));
    }
}
