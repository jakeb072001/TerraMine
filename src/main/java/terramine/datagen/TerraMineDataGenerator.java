package terramine.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import terramine.TerraMine;

public class TerraMineDataGenerator implements DataGeneratorEntrypoint {
    // todo:
    // https://wiki.fabricmc.net/tutorial:datagen_loot
    // https://wiki.fabricmc.net/tutorial:datagen_model
    // https://wiki.fabricmc.net/tutorial:datagen_tags
    // https://wiki.fabricmc.net/tutorial:datagen_recipe

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(TerraMineRegistryProvider::new);
        pack.addProvider(TerraMineRegistryProviderRun::new);
        pack.addProvider(ModTags::new);
        pack.addProvider(ModAdvancements::new);
        pack.addProvider(ModRecipes::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(Registries.CONFIGURED_FEATURE, ModFeatures::bootstrap);
        registryBuilder.add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
        registryBuilder.add(Registries.CONFIGURED_CARVER, ModCarvers::bootstrap);
        registryBuilder.add(Registries.BIOME, ModBiomes::bootstrap);
    }

    @Override
    public String getEffectiveModId() {
        return TerraMine.MOD_ID;
    }
}