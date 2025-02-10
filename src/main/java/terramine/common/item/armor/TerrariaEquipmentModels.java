package terramine.common.item.armor;

import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.equipment.EquipmentAsset;

import java.util.function.BiConsumer;

import static terramine.TerraMine.id;

public interface TerrariaEquipmentModels {
    ResourceKey<? extends Registry<EquipmentAsset>> ROOT_ID = ResourceKey.createRegistryKey(ResourceLocation.withDefaultNamespace("equipment_asset"));
    ResourceKey<EquipmentAsset> VANITY = createId("vanity");
    ResourceKey<EquipmentAsset> COPPER = createId("copper");
    ResourceKey<EquipmentAsset> TIN = createId("tin");
    ResourceKey<EquipmentAsset> LEAD = createId("lead");
    ResourceKey<EquipmentAsset> SILVER = createId("silver");
    ResourceKey<EquipmentAsset> TUNGSTEN = createId("tungsten");
    ResourceKey<EquipmentAsset> PLATINUM = createId("platinum");
    ResourceKey<EquipmentAsset> SHADOW = createId("shadow");
    ResourceKey<EquipmentAsset> ANCIENT_SHADOW = createId("ancient_shadow");
    ResourceKey<EquipmentAsset> CRIMSON = createId("crimson");
    ResourceKey<EquipmentAsset> METEOR = createId("meteor");
    ResourceKey<EquipmentAsset> MOLTEN = createId("molten");

    static ResourceKey<EquipmentAsset> createId(String string) {
        return ResourceKey.create(ROOT_ID, id(string));
    }

    static void bootstrap(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> biConsumer) {
        biConsumer.accept(VANITY, onlyHumanoid("vanity"));
        biConsumer.accept(COPPER, onlyHumanoid("copper"));
        biConsumer.accept(TIN, onlyHumanoid("tin"));
        biConsumer.accept(LEAD, onlyHumanoid("lead"));
        biConsumer.accept(SILVER, onlyHumanoid("silver"));
        biConsumer.accept(TUNGSTEN, onlyHumanoid("tungsten"));
        biConsumer.accept(PLATINUM, onlyHumanoid("platinum"));
        biConsumer.accept(SHADOW, onlyHumanoid("shadow"));
        biConsumer.accept(ANCIENT_SHADOW, onlyHumanoid("ancient_shadow"));
        biConsumer.accept(CRIMSON, onlyHumanoid("crimson"));
        biConsumer.accept(METEOR, onlyHumanoid("meteor"));
        biConsumer.accept(MOLTEN, onlyHumanoid("molten"));
    }

    private static EquipmentClientInfo onlyHumanoid(String string) {
        return EquipmentClientInfo.builder().addHumanoidLayers(id(string)).build();
    }
}
