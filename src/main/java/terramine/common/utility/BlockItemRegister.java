package terramine.common.utility;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;
import terramine.TerraMine;
import terramine.common.init.ModBlocks;

import java.util.function.Function;

public class BlockItemRegister {
    public final Block BLOCK;
    public final Item ITEM;

    public BlockItemRegister(String registerName, Function<ResourceKey<Block>, Block> blockFactory) {
        this(registerName, blockFactory, true);
    }

    public BlockItemRegister(String registerName, Function<ResourceKey<Block>, Block> blockFactory, boolean addToList) {
        BLOCK = registerBlock(registerName, blockFactory, addToList);
        ITEM = registerItem(BLOCK, new Item.Properties(), addToList);
    }

    public BlockItemRegister(String registerName, Function<ResourceKey<Block>, Block> blockFactory, Item.Properties itemProperties) {
        this(registerName, blockFactory, itemProperties, true);
    }

    public BlockItemRegister(String registerName, Function<ResourceKey<Block>, Block> blockFactory, Item.Properties itemProperties, boolean addToList) {
        BLOCK = registerBlock(registerName, blockFactory, addToList);
        ITEM = registerItem(BLOCK, itemProperties, addToList);
    }

    public BlockItemRegister(String registerName, Function<ResourceKey<Block>, Block> blockFactory, float compostable) {
        BLOCK = registerBlock(registerName, blockFactory, false);
        ITEM = registerPlant(BLOCK, compostable);
    }

    public Block getBlock() {
        return BLOCK;
    }

    public Item getItem() {
        return ITEM;
    }

    private static Block registerBlock(String name, Function<ResourceKey<Block>, Block> blockFactory, boolean addToList) {
        ResourceLocation resourceLocation = TerraMine.id(name);
        ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, resourceLocation);
        Block block = blockFactory.apply(key);

        return Registry.register(BuiltInRegistries.BLOCK, key, block);
    }

    private static Item registerItem(Block block, Item.Properties properties, boolean addToList) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, block.builtInRegistryHolder().key().location());
        BlockItem blockItem = new BlockItem(block, properties.setId(key).useBlockDescriptionPrefix());
        Item registeredItem = Registry.register(BuiltInRegistries.ITEM, key, blockItem);
        if (addToList) {
            ModBlocks.BLOCK_ITEMS.add(registeredItem);
        }

        return registeredItem;
    }

    private static Item registerPlant(Block block, float chance) {
        Item item = registerItem(block, new Item.Properties(), false);
        ModBlocks.BLOCK_PLANTS.add(item);
        ComposterBlock.COMPOSTABLES.put(item, chance);

        return item;
    }
}
