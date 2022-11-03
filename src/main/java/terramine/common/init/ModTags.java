package terramine.common.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import terramine.TerraMine;

public final class ModTags {
    // Block Tags
    public static final TagKey<Block> METEORITE_REPLACE_BLOCKS = createBlockTag("meteorite_replace_blocks");
    public static final TagKey<Block> CORRUPTION_MUSHROOM_GROW_BLOCKS = createBlockTag("corruption_mushroom_grow_blocks");
    public static final TagKey<Block> CRIMSON_MUSHROOM_GROW_BLOCKS = createBlockTag("crimson_mushroom_grow_blocks");

    private static TagKey<Block> createBlockTag(String string) {
        return TagKey.create(Registry.BLOCK_REGISTRY, TerraMine.id(string));
    }
}
