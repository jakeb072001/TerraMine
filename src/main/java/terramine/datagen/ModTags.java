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

    // todo: find a way to auto add any accessory
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateTagBuilder(ACCESSORY)
                .add(ModItems.WHOOPEE_CUSHION)
                .add(ModItems.COBALT_SHIELD)
                .add(ModItems.OBSIDIAN_SHIELD)
                .add(ModItems.SHIELD_OF_CTHULHU)
                .add(ModItems.GOLD_WATCH)
                .add(ModItems.DEPTH_METER)
                .add(ModItems.COMPASS)
                .add(ModItems.GPS)
                .add(ModItems.WEATHER_RADIO)
                .add(ModItems.SEXTANT)
                .add(ModItems.FISH_FINDER)
                .add(ModItems.METAL_DETECTOR)
                .add(ModItems.STOPWATCH)
                .add(ModItems.DPS_METER)
                .add(ModItems.GOBLIN_TECH)
                .add(ModItems.PDA)
                .add(ModItems.CELL_PHONE)
                .add(ModItems.CROSS_NECKLACE)
                .add(ModItems.PANIC_NECKLACE)
                .add(ModItems.RANGER_EMBLEM)
                .add(ModItems.WARRIOR_EMBLEM)
                .add(ModItems.SORCERER_EMBLEM)
                .add(ModItems.AVENGER_EMBLEM)
                .add(ModItems.NEPTUNE_SHELL)
                .add(ModItems.MOON_CHARM)
                .add(ModItems.MOON_SHELL)
                .add(ModItems.MOON_STONE)
                .add(ModItems.SUN_STONE)
                .add(ModItems.CELESTIAL_STONE)
                .add(ModItems.CELESTIAL_SHELL)
                .add(ModItems.SHACKLE)
                .add(ModItems.OBSIDIAN_ROSE)
                .add(ModItems.MAGMA_STONE)
                .add(ModItems.OBSIDIAN_SKULL)
                .add(ModItems.MAGMA_SKULL)
                .add(ModItems.OBSIDIAN_SKULL_ROSE)
                .add(ModItems.MOLTEN_SKULL_ROSE)
                .add(ModItems.LAVA_CHARM)
                .add(ModItems.MOLTEN_CHARM)
                .add(ModItems.LUCKY_HORSESHOE)
                .add(ModItems.OBSIDIAN_HORSESHOE)
                .add(ModItems.CLOUD_IN_A_BOTTLE)
                .add(ModItems.SHINY_RED_BALLOON)
                .add(ModItems.CLOUD_IN_A_BALLOON)
                .add(ModItems.BUNDLE_OF_BALLOONS)
                .add(ModItems.BLUE_HORSESHOE_BALLOON)
                .add(ModItems.TOOLBELT)
                .add(ModItems.TOOLBOX)
                .add(ModItems.EXTENDO_GRIP)
                .add(ModItems.ANCIENT_CHISEL)
                .add(ModItems.TREASURE_MAGNET)
                .add(ModItems.SHOE_SPIKES)
                .add(ModItems.CLIMBING_CLAWS)
                .add(ModItems.TIGER_CLIMBING_GEAR)
                .add(ModItems.TABI)
                .add(ModItems.BLACK_BELT)
                .add(ModItems.MASTER_NINJA_GEAR)
                .add(ModItems.FERAL_CLAWS)
                .add(ModItems.TITAN_GLOVE)
                .add(ModItems.POWER_GLOVE)
                .add(ModItems.MECHANICAL_GLOVE)
                .add(ModItems.FIRE_GAUNTLET)
                .add(ModItems.BAND_OF_REGENERATION)
                .add(ModItems.PHILOSOPHERS_STONE)
                .add(ModItems.CHARM_OF_MYTHS)
                .add(ModItems.BAND_OF_STARPOWER)
                .add(ModItems.MANA_REGENERATION_BAND)
                .add(ModItems.MAGIC_CUFFS)
                .add(ModItems.DIVING_HELMET)
                .add(ModItems.DIVING_GEAR)
                .add(ModItems.FLIPPERS)
                .add(ModItems.AGLET)
                .add(ModItems.ANKLET)
                .add(ModItems.WATER_WALKING_BOOTS)
                .add(ModItems.OBSIDIAN_WATER_WALKING_BOOTS)
                .add(ModItems.LAVA_WADERS)
                .add(ModItems.ICE_SKATES)
                .add(ModItems.HERMES_BOOTS)
                .add(ModItems.ROCKET_BOOTS)
                .add(ModItems.SPECTRE_BOOTS)
                .add(ModItems.LIGHTNING_BOOTS)
                .add(ModItems.FROSTSPARK_BOOTS)
                .add(ModItems.TERRASPARK_BOOTS)
                .add(ModItems.FLOWER_BOOTS)
                .add(ModItems.FAIRY_BOOTS)
                .add(ModItems.FLEDGLING_WINGS)
                .add(ModItems.ANGEL_WINGS)
                .add(ModItems.DEMON_WINGS)
                .add(ModItems.LEAF_WINGS);

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
