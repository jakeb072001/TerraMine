package terramine.common.init;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import terramine.TerraMine;

import static terramine.TerraMine.id;

public class ModItemGroups {
    public static final CreativeModeTab ITEM_GROUP_EQUIPMENT = registerCreativeTab("terramine_equipment", FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.DEMONITE_SWORD))
            .title(Component.translatable("itemGroup.terramine.terramine_equipment"))
            .build());
    public static final CreativeModeTab ITEM_GROUP_ARMOR = registerCreativeTab("terramine_armor", FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.SHADOW_HELMET))
            .title(Component.translatable("itemGroup.terramine.terramine_armor"))
            .build());
    public static final CreativeModeTab ITEM_GROUP_ACCESSORIES = registerCreativeTab("terramine_accessories", FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.TERRASPARK_BOOTS))
            .title(Component.translatable("itemGroup.terramine.terramine_accessories"))
            .build());
    public static final CreativeModeTab ITEM_GROUP_BLOCKS = registerCreativeTab("terramine_blocks", FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.RAW_DEMONITE_BLOCK))
            .title(Component.translatable("itemGroup.terramine.terramine_blocks"))
            .build());
    public static final CreativeModeTab ITEM_GROUP_THROWABLES = registerCreativeTab("terramine_throwables", FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.DYNAMITE))
            .title(Component.translatable("itemGroup.terramine.terramine_throwables"))
            .build());
    public static final CreativeModeTab ITEM_GROUP_STUFF = registerCreativeTab("terramine_stuff", FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.RAW_DEMONITE))
            .title(Component.translatable("itemGroup.terramine.terramine_stuff"))
            .build());
    public static final CreativeModeTab ITEM_GROUP_DYES = registerCreativeTab("terramine_dyes", FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.BLUE_DYE))
            .title(Component.translatable("itemGroup.terramine.terramine_dyes"))
            .build());

    public static void registerItemGroups() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.SPAWN_EGGS).register(entries -> {
            entries.accept(ModItems.MIMIC_SPAWN_EGG);
            entries.accept(ModItems.DEMON_EYE_SPAWN_EGG);
            entries.accept(ModItems.EATER_OF_SOULS_SPAWN_EGG);
            entries.accept(ModItems.DEVOURER_SPAWN_EGG);
            entries.accept(ModItems.CRIMERA_SPAWN_EGG);
        });

        ItemGroupEvents.modifyEntriesEvent(createKey("terramine_equipment")).register(id("first_phase"), entries -> {
            entries.accept(ModItems.UMBRELLA);
            entries.accept(ModItems.MAGIC_MIRROR);
            entries.accept(ModItems.MAGIC_MISSILE_ITEM);
            entries.accept(ModItems.FLAMELASH_ITEM);
            entries.accept(ModItems.RAINBOW_ROD_ITEM);
            entries.accept(ModItems.SPACE_GUN);
            entries.accept(ModItems.DEMONITE_PICKAXE);
            entries.accept(ModItems.DEMONITE_AXE);
            entries.accept(ModItems.DEMONITE_SHOVEL);
            entries.accept(ModItems.DEMONITE_HOE);
            entries.accept(ModItems.CRIMTANE_PICKAXE);
            entries.accept(ModItems.CRIMTANE_AXE);
            entries.accept(ModItems.CRIMTANE_SHOVEL);
            entries.accept(ModItems.CRIMTANE_HOE);
            entries.accept(ModItems.METEOR_SHAXE);
            entries.accept(ModItems.MOLTEN_PICKAXE);
            entries.accept(ModItems.MOLTEN_SHAXE);
            entries.accept(ModItems.DEMONITE_SWORD);
            entries.accept(ModItems.CRIMTANE_SWORD);
            entries.accept(ModItems.PHASEBLADE_WHITE);
            entries.accept(ModItems.PHASEBLADE_GREEN);
            entries.accept(ModItems.VOLCANO_SWORD);
        });

        ItemGroupEvents.modifyEntriesEvent(createKey("terramine_armor")).register(id("second_phase"), entries -> {
            entries.accept(ModItems.SHADOW_HELMET);
            entries.accept(ModItems.SHADOW_CHESTPLATE);
            entries.accept(ModItems.SHADOW_LEGGINGS);
            entries.accept(ModItems.SHADOW_BOOTS);
            entries.accept(ModItems.ANCIENT_SHADOW_HELMET);
            entries.accept(ModItems.ANCIENT_SHADOW_CHESTPLATE);
            entries.accept(ModItems.ANCIENT_SHADOW_LEGGINGS);
            entries.accept(ModItems.ANCIENT_SHADOW_BOOTS);
            entries.accept(ModItems.CRIMSON_HELMET);
            entries.accept(ModItems.CRIMSON_CHESTPLATE);
            entries.accept(ModItems.CRIMSON_LEGGINGS);
            entries.accept(ModItems.CRIMSON_BOOTS);
            entries.accept(ModItems.METEOR_HELMET);
            entries.accept(ModItems.METEOR_CHESTPLATE);
            entries.accept(ModItems.METEOR_LEGGINGS);
            entries.accept(ModItems.METEOR_BOOTS);
            entries.accept(ModItems.MOLTEN_HELMET);
            entries.accept(ModItems.MOLTEN_CHESTPLATE);
            entries.accept(ModItems.MOLTEN_LEGGINGS);
            entries.accept(ModItems.MOLTEN_BOOTS);
            entries.accept(ModItems.FAMILIAR_WIG);
            entries.accept(ModItems.FAMILIAR_SHIRT);
            entries.accept(ModItems.FAMILIAR_PANTS);
            entries.accept(ModItems.FAMILIAR_SHOES);
            entries.accept(ModItems.TOP_HAT);
        });

        ItemGroupEvents.modifyEntriesEvent(createKey("terramine_accessories")).register(id("third_phase"), entries -> {
            entries.accept(ModItems.COBALT_SHIELD);
            entries.accept(ModItems.OBSIDIAN_SHIELD);
            entries.accept(ModItems.GOLD_WATCH);
            entries.accept(ModItems.DEPTH_METER);
            entries.accept(ModItems.COMPASS);
            entries.accept(ModItems.GPS);
            entries.accept(ModItems.WEATHER_RADIO);
            entries.accept(ModItems.SEXTANT);
            entries.accept(ModItems.FISH_FINDER);
            entries.accept(ModItems.METAL_DETECTOR);
            entries.accept(ModItems.STOPWATCH);
            entries.accept(ModItems.DPS_METER);
            entries.accept(ModItems.GOBLIN_TECH);
            entries.accept(ModItems.PDA);
            entries.accept(ModItems.CELL_PHONE);
            entries.accept(ModItems.CROSS_NECKLACE);
            entries.accept(ModItems.PANIC_NECKLACE);
            entries.accept(ModItems.RANGER_EMBLEM);
            entries.accept(ModItems.WARRIOR_EMBLEM);
            entries.accept(ModItems.SORCERER_EMBLEM);
            entries.accept(ModItems.AVENGER_EMBLEM);
            entries.accept(ModItems.NEPTUNE_SHELL);
            entries.accept(ModItems.MOON_CHARM);
            entries.accept(ModItems.MOON_SHELL);
            entries.accept(ModItems.MOON_STONE);
            entries.accept(ModItems.SUN_STONE);
            entries.accept(ModItems.CELESTIAL_STONE);
            entries.accept(ModItems.CELESTIAL_SHELL);
            entries.accept(ModItems.SHACKLE);
            entries.accept(ModItems.OBSIDIAN_ROSE);
            entries.accept(ModItems.MAGMA_STONE);
            entries.accept(ModItems.OBSIDIAN_SKULL);
            entries.accept(ModItems.MAGMA_SKULL);
            entries.accept(ModItems.OBSIDIAN_SKULL_ROSE);
            entries.accept(ModItems.MOLTEN_SKULL_ROSE);
            entries.accept(ModItems.LAVA_CHARM);
            entries.accept(ModItems.MOLTEN_CHARM);
            entries.accept(ModItems.LUCKY_HORSESHOE);
            entries.accept(ModItems.OBSIDIAN_HORSESHOE);
            entries.accept(ModItems.CLOUD_IN_A_BOTTLE);
            entries.accept(ModItems.SHINY_RED_BALLOON);
            entries.accept(ModItems.CLOUD_IN_A_BALLOON);
            entries.accept(ModItems.BUNDLE_OF_BALLOONS);
            entries.accept(ModItems.BLUE_HORSESHOE_BALLOON);
            entries.accept(ModItems.TOOLBELT);
            entries.accept(ModItems.TOOLBOX);
            entries.accept(ModItems.EXTENDO_GRIP);
            entries.accept(ModItems.ANCIENT_CHISEL);
            entries.accept(ModItems.TREASURE_MAGNET);
            entries.accept(ModItems.SHOE_SPIKES);
            entries.accept(ModItems.CLIMBING_CLAWS);
            entries.accept(ModItems.TIGER_CLIMBING_GEAR);
            entries.accept(ModItems.TABI);
            entries.accept(ModItems.BLACK_BELT);
            entries.accept(ModItems.MASTER_NINJA_GEAR);
            entries.accept(ModItems.FERAL_CLAWS);
            entries.accept(ModItems.TITAN_GLOVE);
            entries.accept(ModItems.POWER_GLOVE);
            entries.accept(ModItems.MECHANICAL_GLOVE);
            entries.accept(ModItems.FIRE_GAUNTLET);
            entries.accept(ModItems.BAND_OF_REGENERATION);
            entries.accept(ModItems.PHILOSOPHERS_STONE);
            entries.accept(ModItems.CHARM_OF_MYTHS);
            entries.accept(ModItems.BAND_OF_STARPOWER);
            entries.accept(ModItems.MANA_REGENERATION_BAND);
            entries.accept(ModItems.MAGIC_CUFFS);
            entries.accept(ModItems.DIVING_HELMET);
            entries.accept(ModItems.DIVING_GEAR);
            entries.accept(ModItems.FLIPPERS);
            entries.accept(ModItems.AGLET);
            entries.accept(ModItems.ANKLET);
            entries.accept(ModItems.WATER_WALKING_BOOTS);
            entries.accept(ModItems.OBSIDIAN_WATER_WALKING_BOOTS);
            entries.accept(ModItems.LAVA_WADERS);
            entries.accept(ModItems.ICE_SKATES);
            entries.accept(ModItems.HERMES_BOOTS);
            entries.accept(ModItems.ROCKET_BOOTS);
            entries.accept(ModItems.SPECTRE_BOOTS);
            entries.accept(ModItems.LIGHTNING_BOOTS);
            entries.accept(ModItems.FROSTSPARK_BOOTS);
            entries.accept(ModItems.TERRASPARK_BOOTS);
            entries.accept(ModItems.FLOWER_BOOTS);
            entries.accept(ModItems.FAIRY_BOOTS);
            entries.accept(ModItems.FLEDGLING_WINGS);
            entries.accept(ModItems.ANGEL_WINGS);
            entries.accept(ModItems.DEMON_WINGS);
            entries.accept(ModItems.LEAF_WINGS);
        });

        ItemGroupEvents.modifyEntriesEvent(createKey("terramine_blocks")).register(id("fourth_phase"), entries -> {
            entries.accept(ModItems.GOLD_CHEST);
            entries.accept(ModItems.TRAPPED_GOLD_CHEST);
            entries.accept(ModItems.FROZEN_CHEST);
            entries.accept(ModItems.TRAPPED_FROZEN_CHEST);
            entries.accept(ModItems.IVY_CHEST);
            entries.accept(ModItems.TRAPPED_IVY_CHEST);
            entries.accept(ModItems.SANDSTONE_CHEST);
            entries.accept(ModItems.TRAPPED_SANDSTONE_CHEST);
            entries.accept(ModItems.WATER_CHEST);
            entries.accept(ModItems.SKYWARE_CHEST);
            entries.accept(ModItems.SHADOW_CHEST);
            entries.accept(ModItems.PIGGY_BANK);
            entries.accept(ModItems.SAFE);
            entries.accept(ModItems.METEORITE_ORE);
            entries.accept(ModItems.RAW_METEORITE_BLOCK);
            entries.accept(ModItems.METEORITE_BLOCK);
            entries.accept(ModItems.DEMONITE_ORE);
            entries.accept(ModItems.DEEPSLATE_DEMONITE_ORE);
            entries.accept(ModItems.RAW_DEMONITE_BLOCK);
            entries.accept(ModItems.DEMONITE_BLOCK);
            entries.accept(ModItems.CRIMTANE_ORE);
            entries.accept(ModItems.DEEPSLATE_CRIMTANE_ORE);
            entries.accept(ModItems.RAW_CRIMTANE_BLOCK);
            entries.accept(ModItems.CRIMTANE_BLOCK);
            entries.accept(ModItems.HELLSTONE_ORE);
            entries.accept(ModItems.RAW_HELLSTONE_BLOCK);
            entries.accept(ModItems.HELLSTONE_BLOCK);
            entries.accept(ModItems.TINKERER_TABLE);
            entries.accept(ModItems.SUNPLATE_BLOCK);
            entries.accept(ModItems.CLOUD);
            entries.accept(ModItems.RAIN_CLOUD);
            entries.accept(ModItems.BLUE_BRICKS);
            entries.accept(ModItems.CRACKED_BLUE_BRICKS);
            entries.accept(ModItems.FANCY_BLUE_BRICKS);
            entries.accept(ModItems.GREEN_BRICKS);
            entries.accept(ModItems.CRACKED_GREEN_BRICKS);
            entries.accept(ModItems.FANCY_GREEN_BRICKS);
            entries.accept(ModItems.PURPLE_BRICKS);
            entries.accept(ModItems.CRACKED_PURPLE_BRICKS);
            entries.accept(ModItems.FANCY_PURPLE_BRICKS);
            entries.accept(ModItems.CORRUPTED_GRASS_BLOCK);
            entries.accept(ModItems.CORRUPTED_GRAVEL);
            entries.accept(ModItems.CORRUPTED_SAND);
            entries.accept(ModItems.CORRUPTED_GLASS);
            entries.accept(ModItems.CORRUPTED_SANDSTONE);
            entries.accept(ModItems.CORRUPTED_ANDESITE);
            entries.accept(ModItems.CORRUPTED_DIORITE);
            entries.accept(ModItems.CORRUPTED_GRANITE);
            entries.accept(ModItems.CORRUPTED_STONE);
            entries.accept(ModItems.CORRUPTED_DEEPSLATE);
            entries.accept(ModItems.CORRUPTED_COBBLESTONE);
            entries.accept(ModItems.CORRUPTED_COBBLED_DEEPSLATE);
            entries.accept(ModItems.CORRUPTED_COAL_ORE);
            entries.accept(ModItems.CORRUPTED_IRON_ORE);
            entries.accept(ModItems.CORRUPTED_COPPER_ORE);
            entries.accept(ModItems.CORRUPTED_GOLD_ORE);
            entries.accept(ModItems.CORRUPTED_LAPIS_ORE);
            entries.accept(ModItems.CORRUPTED_REDSTONE_ORE);
            entries.accept(ModItems.CORRUPTED_DIAMOND_ORE);
            entries.accept(ModItems.CORRUPTED_EMERALD_ORE);
            entries.accept(ModItems.CORRUPTED_DEEPSLATE_COAL_ORE);
            entries.accept(ModItems.CORRUPTED_DEEPSLATE_IRON_ORE);
            entries.accept(ModItems.CORRUPTED_DEEPSLATE_COPPER_ORE);
            entries.accept(ModItems.CORRUPTED_DEEPSLATE_GOLD_ORE);
            entries.accept(ModItems.CORRUPTED_DEEPSLATE_LAPIS_ORE);
            entries.accept(ModItems.CORRUPTED_DEEPSLATE_REDSTONE_ORE);
            entries.accept(ModItems.CORRUPTED_DEEPSLATE_DIAMOND_ORE);
            entries.accept(ModItems.CORRUPTED_DEEPSLATE_EMERALD_ORE);
            entries.accept(ModItems.CORRUPTED_SNOW_LAYER);
            entries.accept(ModItems.CORRUPTED_SNOW_BLOCK);
            entries.accept(ModItems.CORRUPTED_ICE);
            entries.accept(ModItems.CORRUPTED_PACKED_ICE);
            entries.accept(ModItems.CORRUPTED_BLUE_ICE);
            entries.accept(ModItems.CRIMSON_GRASS_BLOCK);
            entries.accept(ModItems.CRIMSON_GRAVEL);
            entries.accept(ModItems.CRIMSON_SAND);
            entries.accept(ModItems.CRIMSON_GLASS);
            entries.accept(ModItems.CRIMSON_SANDSTONE);
            entries.accept(ModItems.CRIMSON_ANDESITE);
            entries.accept(ModItems.CRIMSON_DIORITE);
            entries.accept(ModItems.CRIMSON_GRANITE);
            entries.accept(ModItems.CRIMSON_STONE);
            entries.accept(ModItems.CRIMSON_DEEPSLATE);
            entries.accept(ModItems.CRIMSON_COBBLESTONE);
            entries.accept(ModItems.CRIMSON_COBBLED_DEEPSLATE);
            entries.accept(ModItems.CRIMSON_COAL_ORE);
            entries.accept(ModItems.CRIMSON_IRON_ORE);
            entries.accept(ModItems.CRIMSON_COPPER_ORE);
            entries.accept(ModItems.CRIMSON_GOLD_ORE);
            entries.accept(ModItems.CRIMSON_LAPIS_ORE);
            entries.accept(ModItems.CRIMSON_REDSTONE_ORE);
            entries.accept(ModItems.CRIMSON_DIAMOND_ORE);
            entries.accept(ModItems.CRIMSON_EMERALD_ORE);
            entries.accept(ModItems.CRIMSON_DEEPSLATE_COAL_ORE);
            entries.accept(ModItems.CRIMSON_DEEPSLATE_IRON_ORE);
            entries.accept(ModItems.CRIMSON_DEEPSLATE_COPPER_ORE);
            entries.accept(ModItems.CRIMSON_DEEPSLATE_GOLD_ORE);
            entries.accept(ModItems.CRIMSON_DEEPSLATE_LAPIS_ORE);
            entries.accept(ModItems.CRIMSON_DEEPSLATE_REDSTONE_ORE);
            entries.accept(ModItems.CRIMSON_DEEPSLATE_DIAMOND_ORE);
            entries.accept(ModItems.CRIMSON_DEEPSLATE_EMERALD_ORE);
            entries.accept(ModItems.CRIMSON_SNOW_LAYER);
            entries.accept(ModItems.CRIMSON_SNOW_BLOCK);
            entries.accept(ModItems.CRIMSON_ICE);
            entries.accept(ModItems.CRIMSON_PACKED_ICE);
            entries.accept(ModItems.CRIMSON_BLUE_ICE);
        });

        ItemGroupEvents.modifyEntriesEvent(createKey("terramine_throwables")).register(id("fifth_phase"), entries -> {
            entries.accept(ModItems.GRENADE);
            entries.accept(ModItems.STICKY_GRENADE);
            entries.accept(ModItems.BOUNCY_GRENADE);
            entries.accept(ModItems.BOMB);
            entries.accept(ModItems.STICKY_BOMB);
            entries.accept(ModItems.BOUNCY_BOMB);
            entries.accept(ModItems.DYNAMITE);
            entries.accept(ModItems.STICKY_DYNAMITE);
            entries.accept(ModItems.BOUNCY_DYNAMITE);
        });

        ItemGroupEvents.modifyEntriesEvent(createKey("terramine_stuff")).register(id("sixth_phase"), entries -> {
            entries.accept(ModItems.LENS);
            entries.accept(ModItems.BLACK_LENS);
            entries.accept(ModItems.ROTTEN_CHUNK);
            entries.accept(ModItems.VERTEBRA);
            entries.accept(ModItems.RAW_METEORITE);
            entries.accept(ModItems.METEORITE_INGOT);
            entries.accept(ModItems.RAW_DEMONITE);
            entries.accept(ModItems.DEMONITE_INGOT);
            entries.accept(ModItems.RAW_CRIMTANE);
            entries.accept(ModItems.CRIMTANE_INGOT);
            entries.accept(ModItems.RAW_HELLSTONE);
            entries.accept(ModItems.RAW_HELLSTONE_HARDENED);
            entries.accept(ModItems.HELLSTONE_INGOT);
            entries.accept(ModItems.FALLEN_STAR);
            entries.accept(ModItems.MANA_CRYSTAL);
            entries.accept(ModItems.DEMON_HEART);
            entries.accept(ModItems.VILE_MUSHROOM);
            entries.accept(ModItems.VICIOUS_MUSHROOM);
        });

        ItemGroupEvents.modifyEntriesEvent(createKey("terramine_dyes")).register(id("seventh_phase"), entries -> {
            entries.accept(ModItems.RED_DYE);
            entries.accept(ModItems.GREEN_DYE);
            entries.accept(ModItems.BLUE_DYE);
            entries.accept(ModItems.YELLOW_DYE);
            entries.accept(ModItems.ORANGE_DYE);
            entries.accept(ModItems.PURPLE_DYE);
            entries.accept(ModItems.PINK_DYE);
            entries.accept(ModItems.BROWN_DYE);
            entries.accept(ModItems.GRAY_DYE);
            entries.accept(ModItems.BLACK_DYE);
        });
    }

    private static CreativeModeTab registerCreativeTab(String string, CreativeModeTab tab) {
        return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, id(string), tab);
    }

    private static ResourceKey<CreativeModeTab> createKey(String string) {
        return ResourceKey.create(Registries.CREATIVE_MODE_TAB, id(string));
    }
}
