package terramine.common.utility;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import terramine.TerraMine;
import terramine.common.item.armor.ShadowArmor;

import java.util.List;
import java.util.function.Function;

public class ArmorItemRegister {
    public final Item HELMET;
    public final Item CHESTPLATE;
    public final Item LEGGINGS;
    public final Item BOOTS;

    public ArmorItemRegister(String registerName, String armorType, ArmorMaterial armorMaterial, List<Item> list) {
        HELMET = register(registerName + "_helmet", key -> new ShadowArmor(armorType, armorMaterial, ArmorType.HELMET, new Item.Properties().setId(key)), list);
        CHESTPLATE = register(registerName + "_chestplate", key -> new ShadowArmor(armorType, armorMaterial, ArmorType.CHESTPLATE, new Item.Properties().setId(key)), list);
        LEGGINGS = register(registerName + "_leggings", key -> new ShadowArmor(armorType, armorMaterial, ArmorType.LEGGINGS, new Item.Properties().setId(key)), list);
        BOOTS = register(registerName + "_boots", key -> new ShadowArmor(armorType, armorMaterial, ArmorType.BOOTS, new Item.Properties().setId(key)), list);
    }

    public Item getHelmet() {
        return HELMET;
    }

    public Item getChestplate() {
        return CHESTPLATE;
    }

    public Item getLeggings() {
        return LEGGINGS;
    }

    public Item getBoots() {
        return BOOTS;
    }

    public List<Item> getSet() {
        return List.of(HELMET, CHESTPLATE, LEGGINGS, BOOTS);
    }

    private static Item register(String name, Function<ResourceKey<Item>, Item> itemFactory, List<Item> list) {
        ResourceLocation resourceLocation = TerraMine.id(name);
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, resourceLocation);
        Item item = itemFactory.apply(key);
        Item registeredItem = Registry.register(BuiltInRegistries.ITEM, key, item);
        list.add(registeredItem);

        return registeredItem;
    }
}
