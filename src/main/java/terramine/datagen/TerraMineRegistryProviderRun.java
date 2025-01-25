package terramine.datagen;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import org.jetbrains.annotations.NotNull;
import terramine.common.item.armor.TerrariaEquipmentModels;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class TerraMineRegistryProviderRun extends FabricDynamicRegistryProvider {
    private final FabricDataOutput.PathProvider pathProvider;

    public TerraMineRegistryProviderRun(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);

        this.pathProvider = output.createPathProvider(FabricDataOutput.Target.RESOURCE_PACK, "equipment");
    }

    @Override
    protected void configure(HolderLookup.Provider provider, Entries entries) {
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        Map<ResourceKey<EquipmentAsset>, EquipmentClientInfo> map = new HashMap<>();
        TerrariaEquipmentModels.bootstrap((resourceKey, equipmentClientInfo) -> {
            if (map.putIfAbsent(resourceKey, equipmentClientInfo) != null) {
                throw new IllegalStateException("Tried to register equipment asset twice for id: " + resourceKey);
            }
        });
        Codec<EquipmentClientInfo> var10001 = EquipmentClientInfo.CODEC;
        PackOutput.PathProvider var10002 = this.pathProvider;
        Objects.requireNonNull(var10002);
        return DataProvider.saveAll(cachedOutput, var10001, var10002::json, map);
    }

    @Override
    public @NotNull String getName() {
        return "TerraMine_Run";
    }
}
