package terramine.common.item.armor;

import net.minecraft.Util;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import terramine.datagen.ModTags;

import java.util.EnumMap;

public interface TerrariaArmorMaterials {
    ArmorMaterial VANITY = new ArmorMaterial(-1, Util.make(new EnumMap<>(ArmorType.class), (enumMap) -> {
        enumMap.put(ArmorType.BOOTS, 0);
        enumMap.put(ArmorType.LEGGINGS, 0);
        enumMap.put(ArmorType.CHESTPLATE, 0);
        enumMap.put(ArmorType.HELMET, 0);
        enumMap.put(ArmorType.BODY, 0);
    }), 1, SoundEvents.ARMOR_EQUIP_LEATHER, 0, 0, ItemTags.REPAIRS_LEATHER_ARMOR, TerrariaEquipmentModels.VANITY);
    ArmorMaterial COPPER = new ArmorMaterial(10, Util.make(new EnumMap<>(ArmorType.class), (enumMap) -> {
        enumMap.put(ArmorType.BOOTS, 1);
        enumMap.put(ArmorType.LEGGINGS, 4);
        enumMap.put(ArmorType.CHESTPLATE, 5);
        enumMap.put(ArmorType.HELMET, 2);
        enumMap.put(ArmorType.BODY, 4);
    }), 7, SoundEvents.ARMOR_EQUIP_IRON, 0, 0, ModTags.REPAIRS_COPPER_ARMOR, TerrariaEquipmentModels.COPPER);
    ArmorMaterial TIN = new ArmorMaterial(10, Util.make(new EnumMap<>(ArmorType.class), (enumMap) -> {
        enumMap.put(ArmorType.BOOTS, 1);
        enumMap.put(ArmorType.LEGGINGS, 4);
        enumMap.put(ArmorType.CHESTPLATE, 5);
        enumMap.put(ArmorType.HELMET, 2);
        enumMap.put(ArmorType.BODY, 4);
    }), 7, SoundEvents.ARMOR_EQUIP_IRON, 0, 0, ModTags.REPAIRS_TIN_ARMOR, TerrariaEquipmentModels.TIN);
    ArmorMaterial LEAD = new ArmorMaterial(15, Util.make(new EnumMap<>(ArmorType.class), (enumMap) -> {
        enumMap.put(ArmorType.BOOTS, 2);
        enumMap.put(ArmorType.LEGGINGS, 5);
        enumMap.put(ArmorType.CHESTPLATE, 7);
        enumMap.put(ArmorType.HELMET, 3);
        enumMap.put(ArmorType.BODY, 5);
    }), 9, SoundEvents.ARMOR_EQUIP_IRON, 0, 0, ModTags.REPAIRS_LEAD_ARMOR, TerrariaEquipmentModels.LEAD);
    ArmorMaterial SILVER = new ArmorMaterial(20, Util.make(new EnumMap<>(ArmorType.class), (enumMap) -> {
        enumMap.put(ArmorType.BOOTS, 2);
        enumMap.put(ArmorType.LEGGINGS, 5);
        enumMap.put(ArmorType.CHESTPLATE, 7);
        enumMap.put(ArmorType.HELMET, 3);
        enumMap.put(ArmorType.BODY, 5);
    }), 10, SoundEvents.ARMOR_EQUIP_IRON, 1F, 0, ModTags.REPAIRS_SILVER_ARMOR, TerrariaEquipmentModels.SILVER);
    ArmorMaterial TUNGSTEN = new ArmorMaterial(20, Util.make(new EnumMap<>(ArmorType.class), (enumMap) -> {
        enumMap.put(ArmorType.BOOTS, 2);
        enumMap.put(ArmorType.LEGGINGS, 5);
        enumMap.put(ArmorType.CHESTPLATE, 7);
        enumMap.put(ArmorType.HELMET, 3);
        enumMap.put(ArmorType.BODY, 5);
    }), 10, SoundEvents.ARMOR_EQUIP_IRON, 1F, 0, ModTags.REPAIRS_TUNGSTEN_ARMOR, TerrariaEquipmentModels.TUNGSTEN);
    ArmorMaterial PLATINUM = new ArmorMaterial(10, Util.make(new EnumMap<>(ArmorType.class), (enumMap) -> {
        enumMap.put(ArmorType.BOOTS, 1);
        enumMap.put(ArmorType.LEGGINGS, 3);
        enumMap.put(ArmorType.CHESTPLATE, 5);
        enumMap.put(ArmorType.HELMET, 2);
        enumMap.put(ArmorType.BODY, 7);
    }), 20, SoundEvents.ARMOR_EQUIP_GOLD, 0, 0, ModTags.REPAIRS_PLATINUM_ARMOR, TerrariaEquipmentModels.PLATINUM);
    ArmorMaterial SHADOW = new ArmorMaterial(25, Util.make(new EnumMap<>(ArmorType.class), (enumMap) -> {
        enumMap.put(ArmorType.BOOTS, 2);
        enumMap.put(ArmorType.LEGGINGS, 5);
        enumMap.put(ArmorType.CHESTPLATE, 7);
        enumMap.put(ArmorType.HELMET, 3);
        enumMap.put(ArmorType.BODY, 5);
    }), 12, SoundEvents.ARMOR_EQUIP_IRON, 1.5F, 0, ModTags.REPAIRS_SHADOW_ARMOR, TerrariaEquipmentModels.SHADOW);
    ArmorMaterial ANCIENT_SHADOW = new ArmorMaterial(25, Util.make(new EnumMap<>(ArmorType.class), (enumMap) -> {
        enumMap.put(ArmorType.BOOTS, 2);
        enumMap.put(ArmorType.LEGGINGS, 5);
        enumMap.put(ArmorType.CHESTPLATE, 7);
        enumMap.put(ArmorType.HELMET, 3);
        enumMap.put(ArmorType.BODY, 5);
    }), 12, SoundEvents.ARMOR_EQUIP_IRON, 1.5F, 0F, ModTags.REPAIRS_SHADOW_ARMOR, TerrariaEquipmentModels.ANCIENT_SHADOW);
    ArmorMaterial CRIMSON = new ArmorMaterial(25, Util.make(new EnumMap<>(ArmorType.class), (enumMap) -> {
        enumMap.put(ArmorType.BOOTS, 2);
        enumMap.put(ArmorType.LEGGINGS, 5);
        enumMap.put(ArmorType.CHESTPLATE, 7);
        enumMap.put(ArmorType.HELMET, 3);
        enumMap.put(ArmorType.BODY, 5);
    }), 12, SoundEvents.ARMOR_EQUIP_GOLD, 1.5F, 0F, ModTags.REPAIRS_CRIMSON_ARMOR, TerrariaEquipmentModels.CRIMSON);
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
    }), 20, SoundEvents.ARMOR_EQUIP_NETHERITE, 3.5F, 0.1F, ModTags.REPAIRS_MOLTEN_ARMOR, TerrariaEquipmentModels.MOLTEN);
}
