package terramine.common.init;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import static terramine.TerraMine.id;

public class ModItemGroups {
    public static final CreativeModeTab ITEM_GROUP_EQUIPMENT = registerCreativeTab("terramine_equipment", FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.DEMONITE_SWORD))
            .title(Component.translatable("itemGroup.terramine.terramine_equipment"))
            .build());
    public static final CreativeModeTab ITEM_GROUP_ARMOR = registerCreativeTab("terramine_armor", FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.SHADOW_ARMOR.getHelmet()))
            .title(Component.translatable("itemGroup.terramine.terramine_armor"))
            .build());
    public static final CreativeModeTab ITEM_GROUP_ACCESSORIES = registerCreativeTab("terramine_accessories", FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.TERRASPARK_BOOTS))
            .title(Component.translatable("itemGroup.terramine.terramine_accessories"))
            .build());
    public static final CreativeModeTab ITEM_GROUP_BLOCKS = registerCreativeTab("terramine_blocks", FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModBlocks.RAW_DEMONITE_BLOCK.ITEM))
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
            for (Item armorPiece : ModItems.SHADOW_ARMOR.getSet()) {
                entries.accept(armorPiece);
            }
            for (Item armorPiece : ModItems.ANCIENT_SHADOW_ARMOR.getSet()) {
                entries.accept(armorPiece);
            }
            for (Item armorPiece : ModItems.CRIMSON_ARMOR.getSet()) {
                entries.accept(armorPiece);
            }
            for (Item armorPiece : ModItems.METEOR_ARMOR.getSet()) {
                entries.accept(armorPiece);
            }
            for (Item armorPiece : ModItems.MOLTEN_ARMOR.getSet()) {
                entries.accept(armorPiece);
            }
            entries.accept(ModItems.FAMILIAR_WIG);
            entries.accept(ModItems.FAMILIAR_SHIRT);
            entries.accept(ModItems.FAMILIAR_PANTS);
            entries.accept(ModItems.FAMILIAR_SHOES);
            //entries.accept(ModItems.TOP_HAT);
        });

        ItemGroupEvents.modifyEntriesEvent(createKey("terramine_accessories")).register(id("third_phase"), entries -> {
            entries.accept(ModItems.COBALT_SHIELD);
            entries.accept(ModItems.OBSIDIAN_SHIELD);
            entries.accept(ModItems.SHIELD_OF_CTHULHU);
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
            entries.accept(ModBlocks.GOLD_CHEST.ITEM);
            entries.accept(ModBlocks.TRAPPED_GOLD_CHEST.ITEM);
            entries.accept(ModBlocks.FROZEN_CHEST.ITEM);
            entries.accept(ModBlocks.TRAPPED_FROZEN_CHEST.ITEM);
            entries.accept(ModBlocks.IVY_CHEST.ITEM);
            entries.accept(ModBlocks.TRAPPED_IVY_CHEST.ITEM);
            entries.accept(ModBlocks.SANDSTONE_CHEST.ITEM);
            entries.accept(ModBlocks.TRAPPED_SANDSTONE_CHEST.ITEM);
            entries.accept(ModBlocks.WATER_CHEST.ITEM);
            entries.accept(ModBlocks.SKYWARE_CHEST.ITEM);
            entries.accept(ModBlocks.SHADOW_CHEST.ITEM);
            entries.accept(ModBlocks.PIGGY_BANK.ITEM);
            entries.accept(ModBlocks.SAFE.ITEM);
            entries.accept(ModBlocks.METEORITE_ORE.ITEM);
            entries.accept(ModBlocks.RAW_METEORITE_BLOCK.ITEM);
            entries.accept(ModBlocks.METEORITE_BLOCK.ITEM);
            entries.accept(ModBlocks.DEMONITE_ORE.ITEM);
            entries.accept(ModBlocks.DEEPSLATE_DEMONITE_ORE.ITEM);
            entries.accept(ModBlocks.RAW_DEMONITE_BLOCK.ITEM);
            entries.accept(ModBlocks.DEMONITE_BLOCK.ITEM);
            entries.accept(ModBlocks.CRIMTANE_ORE.ITEM);
            entries.accept(ModBlocks.DEEPSLATE_CRIMTANE_ORE.ITEM);
            entries.accept(ModBlocks.RAW_CRIMTANE_BLOCK.ITEM);
            entries.accept(ModBlocks.CRIMTANE_BLOCK.ITEM);
            entries.accept(ModBlocks.HELLSTONE_ORE.ITEM);
            entries.accept(ModBlocks.RAW_HELLSTONE_BLOCK.ITEM);
            entries.accept(ModBlocks.HELLSTONE_BLOCK.ITEM);
            entries.accept(ModBlocks.TINKERER_TABLE.ITEM);
            entries.accept(ModBlocks.SUNPLATE_BLOCK.ITEM);
            entries.accept(ModBlocks.CLOUD.ITEM);
            entries.accept(ModBlocks.RAIN_CLOUD.ITEM);
            entries.accept(ModBlocks.BLUE_BRICKS.ITEM);
            entries.accept(ModBlocks.CRACKED_BLUE_BRICKS.ITEM);
            entries.accept(ModBlocks.FANCY_BLUE_BRICKS.ITEM);
            entries.accept(ModBlocks.GREEN_BRICKS.ITEM);
            entries.accept(ModBlocks.CRACKED_GREEN_BRICKS.ITEM);
            entries.accept(ModBlocks.FANCY_GREEN_BRICKS.ITEM);
            entries.accept(ModBlocks.PURPLE_BRICKS.ITEM);
            entries.accept(ModBlocks.CRACKED_PURPLE_BRICKS.ITEM);
            entries.accept(ModBlocks.FANCY_PURPLE_BRICKS.ITEM);
            entries.accept(ModBlocks.CORRUPTED_GRASS.ITEM);
            entries.accept(ModBlocks.CORRUPTED_GRAVEL.ITEM);
            entries.accept(ModBlocks.CORRUPTED_SAND.ITEM);
            entries.accept(ModBlocks.CORRUPTED_GLASS.ITEM);
            entries.accept(ModBlocks.CORRUPTED_SANDSTONE.ITEM);
            entries.accept(ModBlocks.CORRUPTED_ANDESITE.ITEM);
            entries.accept(ModBlocks.CORRUPTED_DIORITE.ITEM);
            entries.accept(ModBlocks.CORRUPTED_GRANITE.ITEM);
            entries.accept(ModBlocks.CORRUPTED_STONE.ITEM);
            entries.accept(ModBlocks.CORRUPTED_DEEPSLATE.ITEM);
            entries.accept(ModBlocks.CORRUPTED_COBBLESTONE.ITEM);
            entries.accept(ModBlocks.CORRUPTED_COBBLED_DEEPSLATE.ITEM);
            entries.accept(ModBlocks.CORRUPTED_COAL_ORE.ITEM);
            entries.accept(ModBlocks.CORRUPTED_IRON_ORE.ITEM);
            entries.accept(ModBlocks.CORRUPTED_COPPER_ORE.ITEM);
            entries.accept(ModBlocks.CORRUPTED_GOLD_ORE.ITEM);
            entries.accept(ModBlocks.CORRUPTED_LAPIS_ORE.ITEM);
            entries.accept(ModBlocks.CORRUPTED_REDSTONE_ORE.ITEM);
            entries.accept(ModBlocks.CORRUPTED_DIAMOND_ORE.ITEM);
            entries.accept(ModBlocks.CORRUPTED_EMERALD_ORE.ITEM);
            entries.accept(ModBlocks.CORRUPTED_DEEPSLATE_COAL_ORE.ITEM);
            entries.accept(ModBlocks.CORRUPTED_DEEPSLATE_IRON_ORE.ITEM);
            entries.accept(ModBlocks.CORRUPTED_DEEPSLATE_COPPER_ORE.ITEM);
            entries.accept(ModBlocks.CORRUPTED_DEEPSLATE_GOLD_ORE.ITEM);
            entries.accept(ModBlocks.CORRUPTED_DEEPSLATE_LAPIS_ORE.ITEM);
            entries.accept(ModBlocks.CORRUPTED_DEEPSLATE_REDSTONE_ORE.ITEM);
            entries.accept(ModBlocks.CORRUPTED_DEEPSLATE_DIAMOND_ORE.ITEM);
            entries.accept(ModBlocks.CORRUPTED_DEEPSLATE_EMERALD_ORE.ITEM);
            entries.accept(ModBlocks.CORRUPTED_SNOW_LAYER.ITEM);
            entries.accept(ModBlocks.CORRUPTED_SNOW.ITEM);
            entries.accept(ModBlocks.CORRUPTED_ICE.ITEM);
            entries.accept(ModBlocks.CORRUPTED_PACKED_ICE.ITEM);
            entries.accept(ModBlocks.CORRUPTED_BLUE_ICE.ITEM);
            entries.accept(ModBlocks.CRIMSON_GRASS.ITEM);
            entries.accept(ModBlocks.CRIMSON_GRAVEL.ITEM);
            entries.accept(ModBlocks.CRIMSON_SAND.ITEM);
            entries.accept(ModBlocks.CRIMSON_GLASS.ITEM);
            entries.accept(ModBlocks.CRIMSON_SANDSTONE.ITEM);
            entries.accept(ModBlocks.CRIMSON_ANDESITE.ITEM);
            entries.accept(ModBlocks.CRIMSON_DIORITE.ITEM);
            entries.accept(ModBlocks.CRIMSON_GRANITE.ITEM);
            entries.accept(ModBlocks.CRIMSON_STONE.ITEM);
            entries.accept(ModBlocks.CRIMSON_DEEPSLATE.ITEM);
            entries.accept(ModBlocks.CRIMSON_COBBLESTONE.ITEM);
            entries.accept(ModBlocks.CRIMSON_COBBLED_DEEPSLATE.ITEM);
            entries.accept(ModBlocks.CRIMSON_COAL_ORE.ITEM);
            entries.accept(ModBlocks.CRIMSON_IRON_ORE.ITEM);
            entries.accept(ModBlocks.CRIMSON_COPPER_ORE.ITEM);
            entries.accept(ModBlocks.CRIMSON_GOLD_ORE.ITEM);
            entries.accept(ModBlocks.CRIMSON_LAPIS_ORE.ITEM);
            entries.accept(ModBlocks.CRIMSON_REDSTONE_ORE.ITEM);
            entries.accept(ModBlocks.CRIMSON_DIAMOND_ORE.ITEM);
            entries.accept(ModBlocks.CRIMSON_EMERALD_ORE.ITEM);
            entries.accept(ModBlocks.CRIMSON_DEEPSLATE_COAL_ORE.ITEM);
            entries.accept(ModBlocks.CRIMSON_DEEPSLATE_IRON_ORE.ITEM);
            entries.accept(ModBlocks.CRIMSON_DEEPSLATE_COPPER_ORE.ITEM);
            entries.accept(ModBlocks.CRIMSON_DEEPSLATE_GOLD_ORE.ITEM);
            entries.accept(ModBlocks.CRIMSON_DEEPSLATE_LAPIS_ORE.ITEM);
            entries.accept(ModBlocks.CRIMSON_DEEPSLATE_REDSTONE_ORE.ITEM);
            entries.accept(ModBlocks.CRIMSON_DEEPSLATE_DIAMOND_ORE.ITEM);
            entries.accept(ModBlocks.CRIMSON_DEEPSLATE_EMERALD_ORE.ITEM);
            entries.accept(ModBlocks.CRIMSON_SNOW_LAYER.ITEM);
            entries.accept(ModBlocks.CRIMSON_SNOW.ITEM);
            entries.accept(ModBlocks.CRIMSON_ICE.ITEM);
            entries.accept(ModBlocks.CRIMSON_PACKED_ICE.ITEM);
            entries.accept(ModBlocks.CRIMSON_BLUE_ICE.ITEM);
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
            entries.accept(ModItems.FLAMING_ARROW);
            entries.accept(ModItems.UNHOLY_ARROW);
            entries.accept(ModItems.JESTER_ARROW);
        });

        ItemGroupEvents.modifyEntriesEvent(createKey("terramine_stuff")).register(id("sixth_phase"), entries -> {
            entries.accept(ModItems.LENS);
            entries.accept(ModItems.BLACK_LENS);
            entries.accept(ModItems.ROTTEN_CHUNK);
            entries.accept(ModItems.VERTEBRA);
            entries.accept(ModBlocks.VILE_MUSHROOM.ITEM);
            entries.accept(ModBlocks.VICIOUS_MUSHROOM.ITEM);
            entries.accept(ModItems.CORRUPT_SEEDS);
            entries.accept(ModItems.CRIMSON_SEEDS);
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
            entries.accept(ModItems.SUSPICIOUS_LOOKING_EYE);
            entries.accept(ModItems.EYE_OF_CTHULHU_TREASURE_BAG);
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

        // todo: unsure if this works, test
        ItemGroupEvents.MODIFY_ENTRIES_ALL.addPhaseOrdering(id("first_phase"), id("second_phase"));
        ItemGroupEvents.MODIFY_ENTRIES_ALL.addPhaseOrdering(id("second_phase"), id("third_phase"));
        ItemGroupEvents.MODIFY_ENTRIES_ALL.addPhaseOrdering(id("third_phase"), id("fourth_phase"));
        ItemGroupEvents.MODIFY_ENTRIES_ALL.addPhaseOrdering(id("fourth_phase"), id("fifth_phase"));
        ItemGroupEvents.MODIFY_ENTRIES_ALL.addPhaseOrdering(id("fifth_phase"), id("sixth_phase"));
        ItemGroupEvents.MODIFY_ENTRIES_ALL.addPhaseOrdering(id("sixth_phase"), id("seventh_phase"));
    }

    private static CreativeModeTab registerCreativeTab(String string, CreativeModeTab tab) {
        return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, id(string), tab);
    }

    private static ResourceKey<CreativeModeTab> createKey(String string) {
        return ResourceKey.create(Registries.CREATIVE_MODE_TAB, id(string));
    }
}
