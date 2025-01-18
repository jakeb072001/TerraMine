package terramine.common.item.armor;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.equipment.EquipmentModel;

import java.util.function.BiConsumer;

import static terramine.TerraMine.id;

public interface TerrariaEquipmentModels {
    ResourceLocation VANITY = id("vanity");
    ResourceLocation SHADOW = id("shadow");
    ResourceLocation ANCIENT_SHADOW = id("ancient_shadow");
    ResourceLocation CRIMSON = id("crimson");
    ResourceLocation METEOR = id("meteor");
    ResourceLocation MOLTEN = id("molten");

    static void bootstrap(BiConsumer<ResourceLocation, EquipmentModel> biConsumer) {
        biConsumer.accept(VANITY, onlyHumanoid("vanity"));
        biConsumer.accept(SHADOW, onlyHumanoid("shadow"));
        biConsumer.accept(ANCIENT_SHADOW, onlyHumanoid("ancient_shadow"));
        biConsumer.accept(CRIMSON, onlyHumanoid("crimson"));
        biConsumer.accept(METEOR, onlyHumanoid("meteor"));
        biConsumer.accept(MOLTEN, onlyHumanoid("molten"));
    }

    private static EquipmentModel onlyHumanoid(String string) {
        return EquipmentModel.builder().addHumanoidLayers(id(string)).build();
    }
}
