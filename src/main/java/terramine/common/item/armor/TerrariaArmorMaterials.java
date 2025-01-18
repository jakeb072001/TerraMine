package terramine.common.item.armor;

import net.minecraft.Util;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import terramine.datagen.ModTags;

import java.util.EnumMap;

public interface TerrariaArmorMaterials {

    // todo: make -1 durability not break
    ArmorMaterial VANITY = new ArmorMaterial(-1, Util.make(new EnumMap<>(ArmorType.class), (enumMap) -> {
        enumMap.put(ArmorType.BOOTS, 0);
        enumMap.put(ArmorType.LEGGINGS, 0);
        enumMap.put(ArmorType.CHESTPLATE, 0);
        enumMap.put(ArmorType.HELMET, 0);
        enumMap.put(ArmorType.BODY, 0);
    }), 1, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, ItemTags.REPAIRS_LEATHER_ARMOR, TerrariaEquipmentModels.VANITY);
    ArmorMaterial SHADOW = new ArmorMaterial(20, Util.make(new EnumMap<>(ArmorType.class), (enumMap) -> {
        enumMap.put(ArmorType.BOOTS, 2);
        enumMap.put(ArmorType.LEGGINGS, 5);
        enumMap.put(ArmorType.CHESTPLATE, 7);
        enumMap.put(ArmorType.HELMET, 3);
        enumMap.put(ArmorType.BODY, 5);
    }), 10, SoundEvents.ARMOR_EQUIP_IRON, 1F, 0, ModTags.REPAIRS_SHADOW_ARMOR, TerrariaEquipmentModels.SHADOW);
    ArmorMaterial ANCIENT_SHADOW = new ArmorMaterial(20, Util.make(new EnumMap<>(ArmorType.class), (enumMap) -> {
        enumMap.put(ArmorType.BOOTS, 2);
        enumMap.put(ArmorType.LEGGINGS, 5);
        enumMap.put(ArmorType.CHESTPLATE, 7);
        enumMap.put(ArmorType.HELMET, 3);
        enumMap.put(ArmorType.BODY, 5);
    }), 10, SoundEvents.ARMOR_EQUIP_IRON, 1F, 0F, ModTags.REPAIRS_SHADOW_ARMOR, TerrariaEquipmentModels.ANCIENT_SHADOW);
    ArmorMaterial CRIMSON = new ArmorMaterial(20, Util.make(new EnumMap<>(ArmorType.class), (enumMap) -> {
        enumMap.put(ArmorType.BOOTS, 2);
        enumMap.put(ArmorType.LEGGINGS, 5);
        enumMap.put(ArmorType.CHESTPLATE, 7);
        enumMap.put(ArmorType.HELMET, 3);
        enumMap.put(ArmorType.BODY, 5);
    }), 10, SoundEvents.ARMOR_EQUIP_GOLD, 1F, 0F, ModTags.REPAIRS_CRIMSON_ARMOR, TerrariaEquipmentModels.CRIMSON);
    ArmorMaterial METEOR = new ArmorMaterial(35, Util.make(new EnumMap<>(ArmorType.class), (enumMap) -> {
        enumMap.put(ArmorType.BOOTS, 3);
        enumMap.put(ArmorType.LEGGINGS, 6);
        enumMap.put(ArmorType.CHESTPLATE, 8);
        enumMap.put(ArmorType.HELMET, 3);
        enumMap.put(ArmorType.BODY, 6);
    }), 15, SoundEvents.ARMOR_EQUIP_NETHERITE, 2.0F, 0.1F, ModTags.REPAIRS_METEOR_ARMOR, TerrariaEquipmentModels.METEOR);
    ArmorMaterial MOLTEN = new ArmorMaterial(40, Util.make(new EnumMap<>(ArmorType.class), (enumMap) -> {
        enumMap.put(ArmorType.BOOTS, 4);
        enumMap.put(ArmorType.LEGGINGS, 8);
        enumMap.put(ArmorType.CHESTPLATE, 8);
        enumMap.put(ArmorType.HELMET, 4);
        enumMap.put(ArmorType.BODY, 7);
    }), 20, SoundEvents.ARMOR_EQUIP_NETHERITE, 3.0F, 0.1F, ModTags.REPAIRS_MOLTEN_ARMOR, TerrariaEquipmentModels.MOLTEN);
}
