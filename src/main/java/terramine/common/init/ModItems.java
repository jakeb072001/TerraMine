package terramine.common.init;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.world.item.*;
import terramine.TerraMine;
import terramine.common.item.*;
import terramine.common.item.curio.ShieldTrinketItem;
import terramine.common.item.curio.TrinketTerrariaItem;
import terramine.common.item.curio.WhoopeeCushionItem;
import terramine.common.item.curio.belt.*;
import terramine.common.item.curio.feet.*;
import terramine.common.item.curio.hands.*;
import terramine.common.item.curio.necklace.*;
import terramine.common.item.magic.*;

@SuppressWarnings("unused")
public class ModItems {

	// Misc
	public static final Item MIMIC_SPAWN_EGG = register("mimic_spawn_egg", new SpawnEggItem(ModEntities.MIMIC, 0x805113, 0x212121, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final Item DEMON_EYE_SPAWN_EGG = register("demon_eye_spawn_egg", new SpawnEggItem(ModEntities.DEMON_EYE, 0xffffff, 0xff0000, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final Item UMBRELLA = register("umbrella", new UmbrellaItem());
	public static final Item WHOOPEE_CUSHION = register("whoopee_cushion", new WhoopeeCushionItem());
	public static final Item MAGIC_MIRROR = register("magic_mirror", new MagicMirrorItem());
	public static final Item COBALT_SHIELD = register("cobalt_shield", new ShieldTrinketItem(new FabricItemSettings().maxDamage(2500).fireproof().rarity(Rarity.RARE).group(TerraMine.ITEM_GROUP), Items.DIAMOND));
	public static final Item OBSIDIAN_SHIELD = register("obsidian_shield", new ShieldTrinketItem(new FabricItemSettings().maxDamage(2500).fireproof().rarity(Rarity.RARE).group(TerraMine.ITEM_GROUP), Items.OBSIDIAN));

	// Crafting Items
	public static final Item LENS = register("lens", new CraftingItem(new FabricItemSettings().group(CreativeModeTab.TAB_MISC)));
	public static final Item BLACK_LENS = register("black_lens", new CraftingItem(new FabricItemSettings().group(CreativeModeTab.TAB_MISC)));

	// Ores etc
	public static final Item RAW_DEMONITE = register("raw_demonite", new CraftingItem(new FabricItemSettings().group(CreativeModeTab.TAB_MATERIALS)));
	public static final Item DEMONITE_INGOT = register("demonite_ingot", new CraftingItem(new FabricItemSettings().group(CreativeModeTab.TAB_MATERIALS)));

	// Magic Items
	public static final Item MAGIC_MISSILE_ITEM = register("magic_missile", new MagicMissileItem());
	public static final Item FLAMELASH_ITEM = register("flamelash", new FlamelashItem());
	public static final Item RAINBOW_ROD_ITEM = register("rainbow_rod", new RainbowRodItem());
	public static final Item FAKE_FALLEN_STAR = register("fake_fallen_star", new FallenStarItem(new FabricItemSettings()));
	public static final Item FALLEN_STAR = register("fallen_star", new FallenStarItem(new FabricItemSettings().stacksTo(64).rarity(Rarity.UNCOMMON).tab(CreativeModeTab.TAB_MISC)));
	public static final Item MANA_CRYSTAL = register("mana_crystal", new ManaCrystalItem(new FabricItemSettings().stacksTo(64).rarity(Rarity.RARE).tab(CreativeModeTab.TAB_MISC)));

	// Informational
	public static final Item GOLD_WATCH = register("gold_watch", new TrinketTerrariaItem());
	public static final Item DEPTH_METER = register("depth_meter", new TrinketTerrariaItem());
	public static final Item COMPASS = register("compass", new TrinketTerrariaItem());
	public static final Item GPS = register("gps", new TrinketTerrariaItem());
	public static final Item WEATHER_RADIO = register("weather_radio", new TrinketTerrariaItem());
	public static final Item SEXTANT = register("sextant", new TrinketTerrariaItem());
	public static final Item FISH_FINDER = register("fish_finder", new TrinketTerrariaItem());
	public static final Item METAL_DETECTOR = register("metal_detector", new TrinketTerrariaItem());
	public static final Item STOPWATCH = register("stopwatch", new TrinketTerrariaItem());
	public static final Item DPS_METER = register("dps_meter", new TrinketTerrariaItem());
	public static final Item GOBLIN_TECH = register("goblin_tech", new TrinketTerrariaItem());
	public static final Item PDA = register("pda", new TrinketTerrariaItem());
	public static final Item CELL_PHONE = register("cell_phone", new CellPhoneItem());

	// Necklace
	public static final Item CROSS_NECKLACE = register("cross_necklace", new CrossNecklaceItem());
	public static final Item PANIC_NECKLACE = register("panic_necklace", new PanicNecklaceItem());
	public static final Item RANGER_EMBLEM = register("ranger_emblem", new TrinketTerrariaItem());
	public static final Item WARRIOR_EMBLEM = register("warrior_emblem", new WarriorEmblemItem());
	public static final Item SORCERER_EMBLEM = register("sorcerer_emblem", new TrinketTerrariaItem());
	public static final Item AVENGER_EMBLEM = register("avenger_emblem", new AvengerEmblemItem());
	public static final Item NEPTUNE_SHELL = register("neptune_shell", new CelestialShell(true, false, false, false));
	public static final Item MOON_CHARM = register("moon_charm", new CelestialShell(false, true, false, false));
	public static final Item MOON_SHELL = register("moon_shell", new CelestialShell(true, true, false, false));
	public static final Item MOON_STONE = register("moon_stone", new CelestialShell(false, false, false, true));
	public static final Item SUN_STONE = register("sun_stone", new CelestialShell(false, false, true, false));
	public static final Item CELESTIAL_STONE = register("celestial_stone", new CelestialShell(false, false, true, true));
	public static final Item CELESTIAL_SHELL = register("celestial_shell", new CelestialShell(true, true, true, true));

	// Belt
	public static final Item SHACKLE = register("shackle", new ShackleItem());
	public static final Item OBSIDIAN_ROSE = register("obsidian_rose", new TrinketTerrariaItem());
	public static final Item MAGMA_STONE = register("magma_stone", new TrinketTerrariaItem());
	public static final Item OBSIDIAN_SKULL = register("obsidian_skull", new ObsidianSkullItem());
	public static final Item MAGMA_SKULL = register("magma_skull", new ObsidianSkullItem());
	public static final Item OBSIDIAN_SKULL_ROSE = register("obsidian_skull_rose", new ObsidianSkullItem());
	public static final Item MOLTEN_SKULL_ROSE = register("molten_skull_rose", new ObsidianSkullItem());
	public static final Item LAVA_CHARM = register("lava_charm", new TrinketTerrariaItem());
	public static final Item MOLTEN_CHARM = register("molten_charm", new TrinketTerrariaItem());
	public static final Item LUCKY_HORSESHOE = register("lucky_horseshoe", new TrinketTerrariaItem());
	public static final Item OBSIDIAN_HORSESHOE = register("obsidian_horseshoe", new TrinketTerrariaItem());
	public static final Item CLOUD_IN_A_BOTTLE = register("cloud_in_a_bottle", new CloudInABottleItem());
	public static final Item SHINY_RED_BALLOON = register("shiny_red_balloon", new ShinyRedBalloonItem());
	public static final Item CLOUD_IN_A_BALLOON = register("cloud_in_a_balloon", new CloudInABalloonItem());
	public static final Item BUNDLE_OF_BALLOONS = register("bundle_of_balloons", new BundleOfBalloonsItem());
	public static final Item BLUE_HORSESHOE_BALLOON = register("blue_horseshoe_balloon", new BlueHorseshoeBalloonItem());
	public static final Item TOOLBELT = register("toolbelt", new ToolbeltItem());
	public static final Item TOOLBOX = register("toolbox", new ToolboxItem());
	public static final Item EXTENDO_GRIP = register("extendo_grip", new ExtendoGripItem());
	public static final Item ANCIENT_CHISEL = register("ancient_chisel", new TrinketTerrariaItem());
	public static final Item TREASURE_MAGNET = register("treasure_magnet", new UniversalAttractorItem());

	// Hands
	public static final Item SHOE_SPIKES = register("shoe_spikes", new TrinketTerrariaItem());
	public static final Item CLIMBING_CLAWS = register("climbing_claws", new TrinketTerrariaItem());
	public static final Item TIGER_CLIMBING_GEAR = register("tiger_climbing_gear", new TrinketTerrariaItem());
	public static final Item TABI = register("tabi", new TabiItem());
	public static final Item BLACK_BELT = register("black_belt", new TrinketTerrariaItem());
	public static final Item MASTER_NINJA_GEAR = register("master_ninja_gear", new MasterNinjaGearItem());
	public static final Item FERAL_CLAWS = register("feral_claws", new FeralClawsItem());
	public static final Item TITAN_GLOVE = register("titan_glove", new TitanGloveItem());
	public static final Item POWER_GLOVE = register("power_glove", new PowerGloveItem());
	public static final Item MECHANICAL_GLOVE = register("mechanical_glove", new MechanicalGloveItem());
	public static final Item FIRE_GAUNTLET = register("fire_gauntlet", new FireGauntletItem());
	public static final Item BAND_OF_REGENERATION = register("band_of_regeneration", new BandOfRegenerationItem());
	public static final Item BAND_OF_STARPOWER = register("band_of_starpower", new BandOfStarpowerItem());
	public static final Item MANA_REGENERATION_BAND = register("mana_regeneration_band", new ManaRegenerationBandItem());
	public static final Item MAGIC_CUFFS = register("magic_cuffs", new MagicCuffsItem());

	// Head
	public static final Item DIVING_HELMET = register("diving_helmet", new TrinketTerrariaItem());
	public static final Item DIVING_GEAR = register("diving_gear", new TrinketTerrariaItem());

	// Feet
	public static final Item FLIPPERS = register("flippers", new TrinketTerrariaItem());
	public static final Item AGLET = register("aglet", new AgletItem());
	public static final Item ANKLET = register("anklet", new AnkletItem());
	public static final Item WATER_WALKING_BOOTS = register("water_walking_boots", new WaterWalkingBootsItem());
	public static final Item OBSIDIAN_WATER_WALKING_BOOTS = register("obsidian_water_walking_boots", new ObsidianWaterWalkingBootsItem());
	public static final Item LAVA_WADERS = register("lava_waders", new LavaWadersItem());
	public static final Item ICE_SKATES = register("ice_skates", new TrinketTerrariaItem());
	public static final Item RUNNING_SHOES = register("hermes_boots", new RunningShoesItem());
	public static final Item ROCKET_BOOTS = register("rocket_boots", new RocketBootsItem());
	public static final Item SPECTRE_BOOTS = register("spectre_boots", new SpectreBootsItem());
	public static final Item LIGHTNING_BOOTS = register("lightning_boots", new LightningBootsItem());
	public static final Item FROSTSPARK_BOOTS = register("frostspark_boots", new FrostsparkBootsItem());
	public static final Item TERRASPARK_BOOTS = register("terraspark_boots", new TerrasparkBootsItem());
	public static final Item FLOWER_BOOTS = register("flower_boots", new FlowerBootsItem());
	public static final Item FAIRY_BOOTS = register("fairy_boots", new FairyBootsItem());

	// Blocks
	public static final Item GOLD_CHEST = register("gold_chest", new BlockItem(ModBlocks.GOLD_CHEST, new FabricItemSettings().group(CreativeModeTab.TAB_DECORATIONS)));
	public static final Item TRAPPED_GOLD_CHEST = register("trapped_gold_chest", new BlockItem(ModBlocks.TRAPPED_GOLD_CHEST, new FabricItemSettings().group(CreativeModeTab.TAB_DECORATIONS)));
	public static final Item FROZEN_CHEST = register("frozen_chest", new BlockItem(ModBlocks.FROZEN_CHEST, new FabricItemSettings().group(CreativeModeTab.TAB_DECORATIONS)));
	public static final Item TRAPPED_FROZEN_CHEST = register("trapped_frozen_chest", new BlockItem(ModBlocks.TRAPPED_FROZEN_CHEST, new FabricItemSettings().group(CreativeModeTab.TAB_DECORATIONS)));
	public static final Item IVY_CHEST = register("ivy_chest", new BlockItem(ModBlocks.IVY_CHEST, new FabricItemSettings().group(CreativeModeTab.TAB_DECORATIONS)));
	public static final Item TRAPPED_IVY_CHEST = register("trapped_ivy_chest", new BlockItem(ModBlocks.TRAPPED_IVY_CHEST, new FabricItemSettings().group(CreativeModeTab.TAB_DECORATIONS)));
	public static final Item SANDSTONE_CHEST = register("sandstone_chest", new BlockItem(ModBlocks.SANDSTONE_CHEST, new FabricItemSettings().group(CreativeModeTab.TAB_DECORATIONS)));
	public static final Item TRAPPED_SANDSTONE_CHEST = register("trapped_sandstone_chest", new BlockItem(ModBlocks.TRAPPED_SANDSTONE_CHEST, new FabricItemSettings().group(CreativeModeTab.TAB_DECORATIONS)));
	public static final Item WATER_CHEST = register("water_chest", new BlockItem(ModBlocks.WATER_CHEST, new FabricItemSettings().group(CreativeModeTab.TAB_DECORATIONS)));
	public static final Item SKYWARE_CHEST = register("skyware_chest", new BlockItem(ModBlocks.SKYWARE_CHEST, new FabricItemSettings().group(CreativeModeTab.TAB_DECORATIONS)));
	public static final Item SHADOW_CHEST = register("shadow_chest", new BlockItem(ModBlocks.SHADOW_CHEST, new FabricItemSettings().group(CreativeModeTab.TAB_DECORATIONS)));
	public static final Item PIGGY_BANK = register("piggy_bank", new BlockItem(ModBlocks.PIGGY_BANK, new FabricItemSettings().group(CreativeModeTab.TAB_DECORATIONS)));
	public static final Item SAFE = register("safe", new BlockItem(ModBlocks.SAFE, new FabricItemSettings().group(CreativeModeTab.TAB_DECORATIONS)));
	public static final Item REDSTONE_STONE = register("redstone_stone", new BlockItem(ModBlocks.REDSTONE_STONE, new FabricItemSettings().group(CreativeModeTab.TAB_REDSTONE)));
	public static final Item REDSTONE_DEEPSLATE = register("redstone_deepslate", new BlockItem(ModBlocks.REDSTONE_DEEPSLATE, new FabricItemSettings().group(CreativeModeTab.TAB_REDSTONE)));
	public static final Item INSTANT_TNT = register("instant_tnt", new BlockItem(ModBlocks.INSTANT_TNT, new FabricItemSettings().group(CreativeModeTab.TAB_REDSTONE)));
	public static final Item TINKERER_TABLE = register("tinkerer_workshop", new BlockItem(ModBlocks.TINKERER_TABLE, new FabricItemSettings().group(CreativeModeTab.TAB_DECORATIONS)));
	public static final Item BLUE_BRICKS = register("blue_brick", new BlockItem(ModBlocks.BLUE_BRICKS, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CRACKED_BLUE_BRICKS = register("cracked_blue_brick", new BlockItem(ModBlocks.CRACKED_BLUE_BRICKS, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item FANCY_BLUE_BRICKS = register("fancy_blue_brick", new BlockItem(ModBlocks.FANCY_BLUE_BRICKS, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item GREEN_BRICKS = register("green_brick", new BlockItem(ModBlocks.GREEN_BRICKS, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CRACKED_GREEN_BRICKS = register("cracked_green_brick", new BlockItem(ModBlocks.CRACKED_GREEN_BRICKS, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item FANCY_GREEN_BRICKS = register("fancy_green_brick", new BlockItem(ModBlocks.FANCY_GREEN_BRICKS, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item PURPLE_BRICKS = register("purple_brick", new BlockItem(ModBlocks.PURPLE_BRICKS, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CRACKED_PURPLE_BRICKS = register("cracked_purple_brick", new BlockItem(ModBlocks.CRACKED_PURPLE_BRICKS, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item FANCY_PURPLE_BRICKS = register("fancy_purple_brick", new BlockItem(ModBlocks.FANCY_PURPLE_BRICKS, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CORRUPTED_GRASS_BLOCK = register("corrupted_grass", new BlockItem(ModBlocks.CORRUPTED_GRASS, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CORRUPTED_GRAVEL = register("corrupted_gravel", new BlockItem(ModBlocks.CORRUPTED_GRAVEL, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CORRUPTED_SAND = register("corrupted_sand", new BlockItem(ModBlocks.CORRUPTED_SAND, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CORRUPTED_GLASS = register("corrupted_glass", new BlockItem(ModBlocks.CORRUPTED_GLASS, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CORRUPTED_SANDSTONE = register("corrupted_sandstone", new BlockItem(ModBlocks.CORRUPTED_SANDSTONE, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CORRUPTED_ANDESITE = register("corrupted_andesite", new BlockItem(ModBlocks.CORRUPTED_ANDESITE, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CORRUPTED_DIORITE = register("corrupted_diorite", new BlockItem(ModBlocks.CORRUPTED_DIORITE, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CORRUPTED_GRANITE = register("corrupted_granite", new BlockItem(ModBlocks.CORRUPTED_GRANITE, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CORRUPTED_STONE = register("corrupted_stone", new BlockItem(ModBlocks.CORRUPTED_STONE, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CORRUPTED_DEEPSLATE = register("corrupted_deepslate", new BlockItem(ModBlocks.CORRUPTED_DEEPSLATE, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CORRUPTED_COBBLESTONE = register("corrupted_cobblestone", new BlockItem(ModBlocks.CORRUPTED_COBBLESTONE, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CORRUPTED_COBBLED_DEEPSLATE = register("corrupted_cobbled_deepslate", new BlockItem(ModBlocks.CORRUPTED_COBBLED_DEEPSLATE, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CORRUPTED_COAL_ORE = register("corrupted_coal_ore", new BlockItem(ModBlocks.CORRUPTED_COAL_ORE, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CORRUPTED_IRON_ORE = register("corrupted_iron_ore", new BlockItem(ModBlocks.CORRUPTED_IRON_ORE, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item DEMONITE_ORE = register("demonite_ore", new BlockItem(ModBlocks.DEMONITE_ORE, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CORRUPTED_COPPER_ORE = register("corrupted_copper_ore", new BlockItem(ModBlocks.CORRUPTED_COPPER_ORE, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CORRUPTED_GOLD_ORE = register("corrupted_gold_ore", new BlockItem(ModBlocks.CORRUPTED_GOLD_ORE, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CORRUPTED_LAPIS_ORE = register("corrupted_lapis_ore", new BlockItem(ModBlocks.CORRUPTED_LAPIS_ORE, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CORRUPTED_REDSTONE_ORE = register("corrupted_redstone_ore", new BlockItem(ModBlocks.CORRUPTED_REDSTONE_ORE, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CORRUPTED_DIAMOND_ORE = register("corrupted_diamond_ore", new BlockItem(ModBlocks.CORRUPTED_DIAMOND_ORE, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CORRUPTED_EMERALD_ORE = register("corrupted_emerald_ore", new BlockItem(ModBlocks.CORRUPTED_EMERALD_ORE, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CORRUPTED_DEEPSLATE_COAL_ORE = register("corrupted_deepslate_coal_ore", new BlockItem(ModBlocks.CORRUPTED_DEEPSLATE_COAL_ORE, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CORRUPTED_DEEPSLATE_IRON_ORE = register("corrupted_deepslate_iron_ore", new BlockItem(ModBlocks.CORRUPTED_DEEPSLATE_IRON_ORE, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item DEEPSLATE_DEMONITE_ORE = register("deepslate_demonite_ore", new BlockItem(ModBlocks.DEEPSLATE_DEMONITE_ORE, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CORRUPTED_DEEPSLATE_COPPER_ORE = register("corrupted_deepslate_copper_ore", new BlockItem(ModBlocks.CORRUPTED_DEEPSLATE_COPPER_ORE, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CORRUPTED_DEEPSLATE_GOLD_ORE = register("corrupted_deepslate_gold_ore", new BlockItem(ModBlocks.CORRUPTED_DEEPSLATE_GOLD_ORE, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CORRUPTED_DEEPSLATE_LAPIS_ORE = register("corrupted_deepslate_lapis_ore", new BlockItem(ModBlocks.CORRUPTED_DEEPSLATE_LAPIS_ORE, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CORRUPTED_DEEPSLATE_REDSTONE_ORE = register("corrupted_deepslate_redstone_ore", new BlockItem(ModBlocks.CORRUPTED_DEEPSLATE_REDSTONE_ORE, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CORRUPTED_DEEPSLATE_DIAMOND_ORE = register("corrupted_deepslate_diamond_ore", new BlockItem(ModBlocks.CORRUPTED_DEEPSLATE_DIAMOND_ORE, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CORRUPTED_DEEPSLATE_EMERALD_ORE = register("corrupted_deepslate_emerald_ore", new BlockItem(ModBlocks.CORRUPTED_DEEPSLATE_EMERALD_ORE, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CORRUPTED_SNOW_LAYER = register("corrupted_snow_layer", new BlockItem(ModBlocks.CORRUPTED_SNOW_LAYER, new FabricItemSettings().group(CreativeModeTab.TAB_DECORATIONS)));
	public static final Item CORRUPTED_SNOW_BLOCK = register("corrupted_snow", new BlockItem(ModBlocks.CORRUPTED_SNOW, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CORRUPTED_ICE = register("corrupted_ice", new BlockItem(ModBlocks.CORRUPTED_ICE, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CORRUPTED_PACKED_ICE = register("corrupted_packed_ice", new BlockItem(ModBlocks.CORRUPTED_PACKED_ICE, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));
	public static final Item CORRUPTED_BLUE_ICE = register("corrupted_blue_ice", new BlockItem(ModBlocks.CORRUPTED_BLUE_ICE, new FabricItemSettings().group(CreativeModeTab.TAB_BUILDING_BLOCKS)));

	private static Item register(String name, Item item) {
		return Registry.register(Registry.ITEM, TerraMine.id(name), item);
	}
}
