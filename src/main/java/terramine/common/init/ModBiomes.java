package terramine.common.init;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import terramine.common.world.biome.CorruptionBiome;
import terramine.common.world.biome.CorruptionDesertBiome;
import terramine.common.world.biome.CrimsonBiome;
import terramine.common.world.biome.CrimsonDesertBiome;

import java.util.concurrent.CompletableFuture;

import static terramine.TerraMine.id;

public class ModBiomes extends FabricDynamicRegistryProvider {
    public static final ResourceKey<Biome> CORRUPTION = register(id("corruption"));
    public static final ResourceKey<Biome> CORRUPTION_DESERT = register(id("corruption_desert"));
    public static final ResourceKey<Biome> CRIMSON = register(id("crimson"));
    public static final ResourceKey<Biome> CRIMSON_DESERT = register(id("crimson_desert"));

    public ModBiomes(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    private static ResourceKey<Biome> register(ResourceLocation name) {
        return ResourceKey.create(Registries.BIOME, name);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        entries.add(CORRUPTION, CorruptionBiome.createCorruption(entries));
        entries.add(CORRUPTION_DESERT, CorruptionDesertBiome.createCorruptionDesert(entries));
        entries.add(CRIMSON, CrimsonBiome.createCrimson(entries));
        entries.add(CRIMSON_DESERT, CrimsonDesertBiome.createCrimsonDesert(entries));
    }

    @Override
    public String getName() {
        return "Terramine";
    }
}
