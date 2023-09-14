package terramine;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import terramine.common.init.ModBiomes;
import terramine.common.init.ModCarvers;
import terramine.common.init.ModFeatures;
import terramine.common.init.ModPlacedFeatures;
import terramine.common.misc.TerraMineRegistryProvider;

public class TerraMineDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(TerraMineRegistryProvider::new);
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