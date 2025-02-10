package terramine.common.utility;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import terramine.TerraMine;
import terramine.common.init.ModItems;
import terramine.common.item.armor.ShadowArmor;
import terramine.common.item.equipment.tools.TerrariaPickaxeItem;

import java.util.List;
import java.util.function.Function;

public class BasicToolSetRegister {
    public final Item PICKAXE;
    public final Item SWORD;
    public final Item AXE;
    public final Item SHOVEL;
    public final Item HOE;

    public BasicToolSetRegister(String registerName, ToolMaterial toolMaterial) {
        this(registerName, toolMaterial, false);
    }

    public BasicToolSetRegister(String registerName, ToolMaterial toolMaterial, boolean isBigSword) {
        PICKAXE = register(registerName + "_pickaxe", key -> new TerrariaPickaxeItem(toolMaterial, 1F, -2.8F, new Item.Properties().setId(key)), ModItems.TOOLS);
        SWORD = register(registerName + "_sword", key -> new SwordItem(toolMaterial, 3F, -2.4F, new Item.Properties().setId(key)), isBigSword ? ModItems.BIG_WEAPONS : ModItems.WEAPONS);
        AXE = register(registerName + "_axe", key -> new AxeItem(toolMaterial, 6F, -3.1F, new Item.Properties().setId(key)), ModItems.TOOLS);
        SHOVEL = register(registerName + "_shovel", key -> new ShovelItem(toolMaterial, 1.5F, -3F, new Item.Properties().setId(key)), ModItems.TOOLS);
        HOE = register(registerName + "_hoe", key -> new HoeItem(toolMaterial, -2F, -1F, new Item.Properties().setId(key)), ModItems.TOOLS);
    }

    public List<Item> getSet() {
        return List.of(PICKAXE, SWORD, AXE, SHOVEL, HOE);
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
