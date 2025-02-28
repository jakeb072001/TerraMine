package terramine.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import terramine.TerraMine;

public class TerraMineDataGenerator implements DataGeneratorEntrypoint {
    // todo: https://wiki.fabricmc.net/tutorial:datagen_loot

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModTags::new); // todo: have a better way of doing tags (also do the rest of the tags), like have a list of accessories created in ModItems when their registered and use a for loop to add them to the accessories tag
        pack.addProvider(ModAdvancements::new); // todo: make helper methods so its a bit cleaner and easier to work with
        pack.addProvider(ModRecipes::new);
        pack.addProvider(ModItemsBlocksModels::new);
        pack.addProvider(TerraMineRegistryProvider::new);
        pack.addProvider(TerraMineRegistryProviderRun::new);
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