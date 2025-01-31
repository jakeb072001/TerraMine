package terramine.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;
import terramine.TerraMine;
import terramine.common.init.ModItems;

import java.util.concurrent.CompletableFuture;

public final class ModTags extends FabricTagProvider<Item> {
    // todo: move to another class for block tags and rename this to ModTagsItems or something, need to do for data gen
    // Block Tags
    public static final TagKey<Block> METEORITE_REPLACE_BLOCKS = createBlockTag("meteorite_replace_blocks");
    public static final TagKey<Block> MINEABLE_WITH_SHAXE = createBlockTag("mineable_with_shaxe");
    public static final TagKey<Block> CORRUPTION_MUSHROOM_GROW_BLOCKS = createBlockTag("corruption_mushroom_grow_blocks");
    public static final TagKey<Block> CRIMSON_MUSHROOM_GROW_BLOCKS = createBlockTag("crimson_mushroom_grow_blocks");

    // Item Tags
    public static final TagKey<Item> ACCESSORY = createItemTag("accessory");
    public static final TagKey<Item> REPAIRS_SHADOW_ARMOR = createItemTag("repairs_shadow_armor");
    public static final TagKey<Item> REPAIRS_CRIMSON_ARMOR = createItemTag("repairs_crimson_armor");
    public static final TagKey<Item> REPAIRS_METEOR_ARMOR = createItemTag("repairs_meteor_armor");
    public static final TagKey<Item> REPAIRS_MOLTEN_ARMOR = createItemTag("repairs_molten_armor");

    // Common Item Tags
    public static final TagKey<Item> SLIME_BALLS = createItemTagCommon("slime_balls");

    // Structure
    public static final TagKey<Structure> DUNGEONS = createStructureTag("dungeon");

    // Biomes
    public static final TagKey<Biome> CORRUPTION = createBiomeTag("is_corruption");
    public static final TagKey<Biome> CRIMSON = createBiomeTag("is_crimson");

    public ModTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.ITEM, registriesFuture);
    }

    private static TagKey<Block> createBlockTag(String string) {
        return TagKey.create(Registries.BLOCK, TerraMine.id(string));
    }

    private static TagKey<Item> createItemTag(String string) {
        return TagKey.create(Registries.ITEM, TerraMine.id(string));
    }

    private static TagKey<Item> createItemTagCommon(String string) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", string));
    }

    private static TagKey<Structure> createStructureTag(String string) {
        return TagKey.create(Registries.STRUCTURE, TerraMine.id(string));
    }

    private static TagKey<Biome> createBiomeTag(String string) {
        return TagKey.create(Registries.BIOME, TerraMine.id(string));
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        FabricTagBuilder accessoryBuilder = getOrCreateTagBuilder(ACCESSORY);
        for (Item item : ModItems.ACCESSORIES) {
            accessoryBuilder.add(item);
        }
        accessoryBuilder.add(ModItems.COBALT_SHIELD);
        accessoryBuilder.add(ModItems.OBSIDIAN_SHIELD);
        accessoryBuilder.add(ModItems.SHIELD_OF_CTHULHU);

        getOrCreateTagBuilder(REPAIRS_SHADOW_ARMOR)
                .add(ModItems.DEMONITE_INGOT);
        getOrCreateTagBuilder(REPAIRS_CRIMSON_ARMOR)
                .add(ModItems.CRIMTANE_INGOT);
        getOrCreateTagBuilder(REPAIRS_METEOR_ARMOR)
                .add(ModItems.METEORITE_INGOT);
        getOrCreateTagBuilder(REPAIRS_MOLTEN_ARMOR)
                .add(ModItems.HELLSTONE_INGOT);
    }
}
