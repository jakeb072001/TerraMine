package terramine.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.equipment.EquipmentModel;
import org.jetbrains.annotations.NotNull;
import terramine.common.item.armor.TerrariaEquipmentModels;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class TerraMineRegistryProviderRun extends FabricDynamicRegistryProvider {
    private final FabricDataOutput.PathProvider pathProvider;

    public TerraMineRegistryProviderRun(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);

        // todo: update location to just equipment for 1.21.4 update
        this.pathProvider = output.createPathProvider(FabricDataOutput.Target.RESOURCE_PACK, "models/equipment");
    }

    @Override
    protected void configure(HolderLookup.Provider provider, Entries entries) {
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        Map<ResourceLocation, EquipmentModel> map = new HashMap<>();
        TerrariaEquipmentModels.bootstrap((resourceLocation, equipmentModel) -> {
            if (map.putIfAbsent(resourceLocation, equipmentModel) != null) {
                throw new IllegalStateException("Tried to register equipment model twice for id: " + resourceLocation);
            }
        });
        return DataProvider.saveAll(cachedOutput, EquipmentModel.CODEC, this.pathProvider, map);
    }

    @Override
    public @NotNull String getName() {
        return "TerraMine_Run";
    }
}
