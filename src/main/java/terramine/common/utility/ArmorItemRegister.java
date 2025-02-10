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
import terramine.common.init.ModItems;
import terramine.common.item.armor.TerrariaArmor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Function;

public class ArmorItemRegister {
    public final Item HELMET;
    public final Item CHESTPLATE;
    public final Item LEGGINGS;
    public final Item BOOTS;

    public ArmorItemRegister(String registerName, String armorType, ArmorMaterial armorMaterial) {
        this(registerName, armorType, armorMaterial, TerrariaArmor.class, false);
    }

    public <T extends TerrariaArmor> ArmorItemRegister(String registerName, String armorType, ArmorMaterial armorMaterial, Class<T> armorItemClass) {
        this(registerName, armorType, armorMaterial, armorItemClass, true);
    }

    public <T extends TerrariaArmor> ArmorItemRegister(String registerName, String armorType, ArmorMaterial armorMaterial, Class<T> armorItemClass, boolean hasTooltip) {
        HELMET = register(registerName + "_helmet", key -> createArmorInstance(armorItemClass, armorType, armorMaterial, ArmorType.HELMET, key, hasTooltip), ModItems.ARMORS);
        CHESTPLATE = register(registerName + "_chestplate", key -> createArmorInstance(armorItemClass, armorType, armorMaterial, ArmorType.CHESTPLATE, key, hasTooltip), ModItems.ARMORS);
        LEGGINGS = register(registerName + "_leggings", key -> createArmorInstance(armorItemClass, armorType, armorMaterial, ArmorType.LEGGINGS, key, hasTooltip), ModItems.ARMORS);
        BOOTS = register(registerName + "_boots", key -> createArmorInstance(armorItemClass, armorType, armorMaterial, ArmorType.BOOTS, key, hasTooltip), ModItems.ARMORS);
    }

    private <T extends TerrariaArmor> T createArmorInstance(Class<T> armorClass, String armorType, ArmorMaterial armorMaterial, ArmorType slot, ResourceKey<Item> key, boolean extraFlag) {
        try {
            Constructor<T> constructor;
            if (hasBooleanConstructor(armorClass)) {
                constructor = armorClass.getConstructor(String.class, ArmorMaterial.class, ArmorType.class, Item.Properties.class, boolean.class);
                return constructor.newInstance(armorType, armorMaterial, slot, new Item.Properties().setId(key), extraFlag);
            } else {
                constructor = armorClass.getConstructor(String.class, ArmorMaterial.class, ArmorType.class, Item.Properties.class);
                return constructor.newInstance(armorType, armorMaterial, slot, new Item.Properties().setId(key));
            }
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException("Failed to create armor instance for " + armorClass.getSimpleName(), e);
        }
    }

    private <T extends TerrariaArmor> boolean hasBooleanConstructor(Class<T> armorClass) {
        for (Constructor<?> constructor : armorClass.getConstructors()) {
            Class<?>[] params = constructor.getParameterTypes();
            if (params.length == 5 && params[4] == boolean.class) {
                return true;
            }
        }
        return false;
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
