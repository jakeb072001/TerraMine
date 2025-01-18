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

import java.util.function.Function;

public class BlockItemRegister {
    public final Block BLOCK;
    public final Item ITEM;

    public BlockItemRegister(String registerName, Function<ResourceKey<Block>, Block> blockFactory) {
        BLOCK = registerBlock(registerName, blockFactory);
        ITEM = registerItem(BLOCK, new Item.Properties());
    }

    public BlockItemRegister(String registerName, Function<ResourceKey<Block>, Block> blockFactory, Item.Properties itemProperties) {
        BLOCK = registerBlock(registerName, blockFactory);
        ITEM = registerItem(BLOCK, itemProperties);
    }

    public BlockItemRegister(String registerName, Function<ResourceKey<Block>, Block> blockFactory, float compostable) {
        BLOCK = registerBlock(registerName, blockFactory);
        ITEM = registerPlant(BLOCK, compostable);
    }

    public Block getBlock() {
        return BLOCK;
    }

    public Item getItem() {
        return ITEM;
    }

    private static Block registerBlock(String name, Function<ResourceKey<Block>, Block> blockFactory) {
        ResourceLocation resourceLocation = TerraMine.id(name);
        ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, resourceLocation);
        Block block = blockFactory.apply(key);

        return Registry.register(BuiltInRegistries.BLOCK, key, block);
    }

    private static Item registerItem(Block block, Item.Properties properties) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, block.builtInRegistryHolder().key().location());
        BlockItem blockItem = new BlockItem(block, properties.setId(key).useBlockDescriptionPrefix());

        return Registry.register(BuiltInRegistries.ITEM, key, blockItem);
    }

    private static Item registerPlant(Block block, float chance) {
        Item item = registerItem(block, new Item.Properties());
        ComposterBlock.COMPOSTABLES.put(item, chance);

        return item;
    }
}
