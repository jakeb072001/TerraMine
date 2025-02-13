package terramine.common.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.equipment.ArmorType;
import terramine.TerraMine;
import terramine.common.item.CraftingItem;
import terramine.common.item.accessories.AccessoryTerrariaItem;
import terramine.common.item.accessories.ShieldAccessoryLikeItem;
import terramine.common.item.accessories.ShieldOfCthulhuItem;
import terramine.common.item.accessories.WhoopeeCushionItem;
import terramine.common.item.accessories.back.WingsItem;
import terramine.common.item.accessories.belt.*;
import terramine.common.item.accessories.feet.*;
import terramine.common.item.accessories.hands.*;
import terramine.common.item.accessories.head.DivingGear;
import terramine.common.item.accessories.necklace.*;
import terramine.common.item.armor.*;
import terramine.common.item.armor.vanity.FamiliarVanity;
import terramine.common.item.armor.vanity.TopHatVanity;
import terramine.common.item.dye.BasicDye;
import terramine.common.item.equipment.CellPhoneItem;
import terramine.common.item.equipment.MagicMirrorItem;
import terramine.common.item.equipment.TerrariaToolMaterials;
import terramine.common.item.equipment.UmbrellaItem;
import terramine.common.item.equipment.swords.CustomSoundSwordItem;
import terramine.common.item.equipment.swords.VolcanoSwordItem;
import terramine.common.item.equipment.tools.MoltenPickaxeItem;
import terramine.common.item.equipment.tools.TerrariaShaxeItem;
import terramine.common.item.magic.*;
import terramine.common.item.misc.BossSpawnItem;
import terramine.common.item.misc.DemonHeartItem;
import terramine.common.item.misc.EvilSeeds;
import terramine.common.item.misc.TreasureBagItem;
import terramine.common.item.projectiles.arrows.FlamingArrowItem;
import terramine.common.item.projectiles.arrows.JesterArrowItem;
import terramine.common.item.projectiles.arrows.UnholyArrowItem;
import terramine.common.item.projectiles.throwables.BombItem;
import terramine.common.item.projectiles.throwables.DynamiteItem;
import terramine.common.item.projectiles.throwables.GrenadeItem;
import terramine.common.utility.ArmorItemRegister;
import terramine.common.utility.BasicToolSetRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static net.minecraft.world.item.Items.BUCKET;

@SuppressWarnings("unused")
public class ModItems {
	// Lists (for datagen)
	public static List<Item> ACCESSORIES = new ArrayList<>();
	public static List<Item> ARMORS = new ArrayList<>();
	public static List<Item> TOOLS = new ArrayList<>();
	public static List<Item> WEAPONS = new ArrayList<>();
	public static List<Item> BIG_WEAPONS = new ArrayList<>();
	public static List<Item> ARROWS = new ArrayList<>();
	public static List<Item> MAGIC_WEAPONS = new ArrayList<>();
	public static List<Item> THROWABLES = new ArrayList<>();
	public static List<Item> SHIELDS = new ArrayList<>();
	public static List<Item> DYES = new ArrayList<>();
	public static List<Item> MISC = new ArrayList<>();
	public static List<Item> SPAWN_EGGS = new ArrayList<>();

	// Misc
	public static final Item MIMIC_SPAWN_EGG = registerSpawnEgg("mimic_spawn_egg", key -> new SpawnEggItem(ModEntities.MIMIC, new Item.Properties().setId(key)));
	public static final Item DEMON_EYE_SPAWN_EGG = registerSpawnEgg("demon_eye_spawn_egg", key -> new SpawnEggItem(ModEntities.DEMON_EYE, new Item.Properties().setId(key)));
	public static final Item EATER_OF_SOULS_SPAWN_EGG = registerSpawnEgg("eater_of_souls_spawn_egg", key -> new SpawnEggItem(ModEntities.EATER_OF_SOULS, new Item.Properties().setId(key)));
	public static final Item DEVOURER_SPAWN_EGG = registerSpawnEgg("devourer_spawn_egg", key -> new SpawnEggItem(ModEntities.DEVOURER, new Item.Properties().setId(key)));
	public static final Item CRIMERA_SPAWN_EGG = registerSpawnEgg("crimera_spawn_egg", key -> new SpawnEggItem(ModEntities.CRIMERA, new Item.Properties().setId(key)));
	public static final Item SUSPICIOUS_LOOKING_EYE = registerMisc("suspicious_looking_eye", key -> new BossSpawnItem(ModEntities.TEST_BOSS, new Item.Properties().setId(key).stacksTo(16).rarity(Rarity.RARE)));
	public static final Item UMBRELLA = register("umbrella", UmbrellaItem::new);
	public static final Item WHOOPEE_CUSHION = registerAccessory("whoopee_cushion", WhoopeeCushionItem::new);
	public static final Item MAGIC_MIRROR = register("magic_mirror", MagicMirrorItem::new);
	public static final Item SHIMMER_BUCKET = register("shimmer_bucket", key -> new BucketItem(ModFluids.STILL_SHIMMER, new Item.Properties().setId(key).craftRemainder(BUCKET).stacksTo(1)));
	public static final Item COBALT_SHIELD = registerShield("cobalt_shield", key -> new ShieldAccessoryLikeItem(new Item.Properties().setId(key).repairable(Items.DIAMOND).durability(2500).fireResistant().rarity(Rarity.RARE)));
	public static final Item OBSIDIAN_SHIELD = registerShield("obsidian_shield", key -> new ShieldAccessoryLikeItem(new Item.Properties().setId(key).repairable(Items.OBSIDIAN).durability(2500).fireResistant().rarity(Rarity.RARE)));
	public static final Item SHIELD_OF_CTHULHU = registerShield("shield_of_cthulhu", key -> new ShieldOfCthulhuItem(new Item.Properties().setId(key).repairable(Items.ROTTEN_FLESH).durability(2500).fireResistant().rarity(Rarity.RARE)));

	// todo: add many more dyes, need to create a model/item for each one but its just copy paste
	// todo: also add some custom shader dyes, need to add a system to render the dyes first though
	// todo: make dye craft-able, some will also be obtainable from enemies and other things
	// todo: maybe also make work like potions, so only one item is registered and doesn't need a model per item, maybe
	// Dye Items (uses hex)
	public static final Item RED_DYE = registerDye("red_dye", key -> new BasicDye(0xFF0000, key));
	public static final Item GREEN_DYE = registerDye("green_dye", key -> new BasicDye(0x008000, key));
	public static final Item BLUE_DYE = registerDye("blue_dye", key -> new BasicDye(0x0000FF, key));
	public static final Item YELLOW_DYE = registerDye("yellow_dye", key -> new BasicDye(0xFFFF00, key));
	public static final Item ORANGE_DYE = registerDye("orange_dye", key -> new BasicDye(0xFFA500, key));
	public static final Item PURPLE_DYE = registerDye("purple_dye", key -> new BasicDye(0x800080, key));
	public static final Item PINK_DYE = registerDye("pink_dye", key -> new BasicDye(0xFFC0CB, key));
	public static final Item BROWN_DYE = registerDye("brown_dye", key -> new BasicDye(0x964B00, key));
	public static final Item GRAY_DYE = registerDye("gray_dye", key -> new BasicDye(0x808080, key));
	public static final Item BLACK_DYE = registerDye("black_dye", key -> new BasicDye(0x000000, key));

	// Arrows
	// todo: make use just a single arrow item class, need to make all arrows the same entity for this, can be done much later
	public static final Item FLAMING_ARROW = registerArrow("flaming_arrow", key -> new FlamingArrowItem(new Item.Properties().setId(key)));
	public static final Item UNHOLY_ARROW = registerArrow("unholy_arrow", key -> new UnholyArrowItem(new Item.Properties().setId(key)));
	public static final Item JESTER_ARROW = registerArrow("jester_arrow", key -> new JesterArrowItem(new Item.Properties().setId(key)));

	// Treasure Bags
	public static final Item EYE_OF_CTHULHU_TREASURE_BAG = registerMisc("eye_of_cthulhu_treasure_bag", key -> new TreasureBagItem(new Item.Properties().setId(key).stacksTo(1).rarity(Rarity.EPIC).fireResistant(), ModLootTables.EYE_OF_CTHULHU, Component.translatable("terramine.ui.eye_of_cthulhu_treasure_bag")));

	// Crafting Items
	public static final Item LENS = registerMisc("lens", key -> new CraftingItem(new Item.Properties().setId(key), false));
	public static final Item BLACK_LENS = registerMisc("black_lens", key -> new CraftingItem(new Item.Properties().setId(key), false));
	public static final Item ROTTEN_CHUNK = registerMisc("rotten_chunk", key -> new CraftingItem(new Item.Properties().setId(key).stacksTo(64), true));
	public static final Item VERTEBRA = registerMisc("vertebra", key -> new CraftingItem(new Item.Properties().setId(key).stacksTo(64), false));
	public static final Item WORM_TOOTH = registerMisc("worm_tooth", key -> new CraftingItem(new Item.Properties().setId(key).stacksTo(64), false));

	// Ores etc
	public static final Item RAW_TIN = registerMisc("raw_tin", key -> new CraftingItem(new Item.Properties().setId(key), false));
	public static final Item TIN_INGOT = registerMisc("tin_ingot", key -> new CraftingItem(new Item.Properties().setId(key), false));
	public static final Item RAW_LEAD = registerMisc("raw_lead", key -> new CraftingItem(new Item.Properties().setId(key), false));
	public static final Item LEAD_INGOT = registerMisc("lead_ingot", key -> new CraftingItem(new Item.Properties().setId(key), false));
	public static final Item LEAD_NUGGET = registerMisc("lead_nugget", key -> new CraftingItem(new Item.Properties().setId(key), false));
	public static final Item RAW_SILVER = registerMisc("raw_silver", key -> new CraftingItem(new Item.Properties().setId(key), false));
	public static final Item SILVER_INGOT = registerMisc("silver_ingot", key -> new CraftingItem(new Item.Properties().setId(key), false));
	public static final Item RAW_TUNGSTEN = registerMisc("raw_tungsten", key -> new CraftingItem(new Item.Properties().setId(key), false));
	public static final Item TUNGSTEN_INGOT = registerMisc("tungsten_ingot", key -> new CraftingItem(new Item.Properties().setId(key), false));
	public static final Item RAW_PLATINUM = registerMisc("raw_platinum", key -> new CraftingItem(new Item.Properties().setId(key), false));
	public static final Item PLATINUM_INGOT = registerMisc("platinum_ingot", key -> new CraftingItem(new Item.Properties().setId(key), false));
	public static final Item PLATINUM_NUGGET = registerMisc("platinum_nugget", key -> new CraftingItem(new Item.Properties().setId(key), false));
	public static final Item RAW_DEMONITE = registerMisc("raw_demonite", key -> new CraftingItem(new Item.Properties().setId(key), false));
	public static final Item DEMONITE_INGOT = registerMisc("demonite_ingot", key -> new CraftingItem(new Item.Properties().setId(key), true));
	public static final Item RAW_CRIMTANE = registerMisc("raw_crimtane", key -> new CraftingItem(new Item.Properties().setId(key), false));
	public static final Item CRIMTANE_INGOT = registerMisc("crimtane_ingot", key -> new CraftingItem(new Item.Properties().setId(key), false));
	public static final Item RAW_METEORITE = registerMisc("raw_meteorite", key -> new CraftingItem(new Item.Properties().setId(key), false));
	public static final Item METEORITE_INGOT = registerMisc("meteorite_ingot", key -> new CraftingItem(new Item.Properties().setId(key), true));
	public static final Item RAW_HELLSTONE = registerMisc("raw_hellstone", key -> new CraftingItem(new Item.Properties().setId(key).fireResistant(), true));
	public static final Item RAW_HELLSTONE_HARDENED = registerMisc("raw_hellstone_hardened", key -> new CraftingItem(new Item.Properties().setId(key).fireResistant(), true));
	public static final Item HELLSTONE_INGOT = registerMisc("hellstone_ingot", key -> new CraftingItem(new Item.Properties().setId(key).fireResistant(), true));

	// Magic Items
	public static final Item MAGIC_MISSILE_ITEM = registerMagicWeapon("magic_missile", MagicMissileItem::new);
	public static final Item FLAMELASH_ITEM = registerMagicWeapon("flamelash", FlamelashItem::new);
	public static final Item RAINBOW_ROD_ITEM = registerMagicWeapon("rainbow_rod", RainbowRodItem::new);
	public static final Item FAKE_FALLEN_STAR = registerMisc("fake_fallen_star", key -> new CraftingItem(new Item.Properties().setId(key), true));
	public static final Item FALLEN_STAR = registerMisc("fallen_star", key -> new CraftingItem(new Item.Properties().setId(key).stacksTo(64).rarity(Rarity.UNCOMMON), true));
	public static final Item MANA_CRYSTAL = registerMisc("mana_crystal", key -> new ManaCrystalItem(new Item.Properties().setId(key).stacksTo(64).rarity(Rarity.RARE)));
	public static final Item DEMON_HEART = registerMisc("demon_heart", key -> new DemonHeartItem(new Item.Properties().setId(key).stacksTo(16).rarity(Rarity.EPIC)));

	// Seeds
	public static final Item CORRUPT_SEEDS = registerMisc("corrupt_seeds", key -> new EvilSeeds(new Item.Properties().setId(key), ModBlocks.CORRUPTED_GRASS.BLOCK));
	public static final Item CRIMSON_SEEDS = registerMisc("crimson_seeds", key -> new EvilSeeds(new Item.Properties().setId(key), ModBlocks.CRIMSON_GRASS.BLOCK));

	// Informational
	public static final Item GOLD_WATCH = registerAccessory("gold_watch", AccessoryTerrariaItem::new);
	public static final Item DEPTH_METER = registerAccessory("depth_meter", AccessoryTerrariaItem::new);
	public static final Item COMPASS = registerAccessory("compass", AccessoryTerrariaItem::new);
	public static final Item GPS = registerAccessory("gps", AccessoryTerrariaItem::new);
	public static final Item WEATHER_RADIO = registerAccessory("weather_radio", AccessoryTerrariaItem::new);
	public static final Item SEXTANT = registerAccessory("sextant", AccessoryTerrariaItem::new);
	public static final Item FISH_FINDER = registerAccessory("fish_finder", AccessoryTerrariaItem::new);
	public static final Item METAL_DETECTOR = registerAccessory("metal_detector", AccessoryTerrariaItem::new);
	public static final Item STOPWATCH = registerAccessory("stopwatch", AccessoryTerrariaItem::new);
	public static final Item DPS_METER = registerAccessory("dps_meter", AccessoryTerrariaItem::new);
	public static final Item GOBLIN_TECH = registerAccessory("goblin_tech", AccessoryTerrariaItem::new);
	public static final Item PDA = registerAccessory("pda", AccessoryTerrariaItem::new);
	public static final Item CELL_PHONE = registerAccessory("cell_phone", CellPhoneItem::new);

	// Necklace
	public static final Item CROSS_NECKLACE = registerAccessory("cross_necklace", CrossNecklaceItem::new);
	public static final Item PANIC_NECKLACE = registerAccessory("panic_necklace", PanicNecklaceItem::new);
	public static final Item RANGER_EMBLEM = registerAccessory("ranger_emblem", RangerEmblemItem::new);
	public static final Item WARRIOR_EMBLEM = registerAccessory("warrior_emblem", WarriorEmblemItem::new);
	public static final Item SORCERER_EMBLEM = registerAccessory("sorcerer_emblem", SorcererEmblemItem::new);
	public static final Item AVENGER_EMBLEM = registerAccessory("avenger_emblem", AvengerEmblemItem::new);
	public static final Item NEPTUNE_SHELL = registerAccessory("neptune_shell", key -> new CelestialShell(true, false, false, false, key));
	public static final Item MOON_CHARM = registerAccessory("moon_charm", key -> new CelestialShell(false, true, false, false, key));
	public static final Item MOON_SHELL = registerAccessory("moon_shell", key -> new CelestialShell(true, true, false, false, key));
	public static final Item MOON_STONE = registerAccessory("moon_stone", key -> new CelestialShell(false, false, false, true, key));
	public static final Item SUN_STONE = registerAccessory("sun_stone", key -> new CelestialShell(false, false, true, false, key));
	public static final Item CELESTIAL_STONE = registerAccessory("celestial_stone", key -> new CelestialShell(false, false, true, true, key));
	public static final Item CELESTIAL_SHELL = registerAccessory("celestial_shell", key -> new CelestialShell(true, true, true, true, key));

	// Belt
	public static final Item SHACKLE = registerAccessory("shackle", ShackleItem::new);
	public static final Item OBSIDIAN_ROSE = registerAccessory("obsidian_rose", AccessoryTerrariaItem::new);
	public static final Item MAGMA_STONE = registerAccessory("magma_stone", AccessoryTerrariaItem::new);
	public static final Item OBSIDIAN_SKULL = registerAccessory("obsidian_skull", ObsidianSkullItem::new);
	public static final Item MAGMA_SKULL = registerAccessory("magma_skull", ObsidianSkullItem::new);
	public static final Item OBSIDIAN_SKULL_ROSE = registerAccessory("obsidian_skull_rose", ObsidianSkullItem::new);
	public static final Item MOLTEN_SKULL_ROSE = registerAccessory("molten_skull_rose", ObsidianSkullItem::new);
	public static final Item LAVA_CHARM = registerAccessory("lava_charm", AccessoryTerrariaItem::new);
	public static final Item MOLTEN_CHARM = registerAccessory("molten_charm", AccessoryTerrariaItem::new);
	public static final Item LUCKY_HORSESHOE = registerAccessory("lucky_horseshoe", AccessoryTerrariaItem::new);
	public static final Item OBSIDIAN_HORSESHOE = registerAccessory("obsidian_horseshoe", AccessoryTerrariaItem::new);
	public static final Item CLOUD_IN_A_BOTTLE = registerAccessory("cloud_in_a_bottle", CloudInABottleItem::new);
	public static final Item SHINY_RED_BALLOON = registerAccessory("shiny_red_balloon", ShinyRedBalloonItem::new);
	public static final Item CLOUD_IN_A_BALLOON = registerAccessory("cloud_in_a_balloon", CloudInABalloonItem::new);
	public static final Item BUNDLE_OF_BALLOONS = registerAccessory("bundle_of_balloons", BundleOfBalloonsItem::new);
	public static final Item BLUE_HORSESHOE_BALLOON = registerAccessory("blue_horseshoe_balloon", BlueHorseshoeBalloonItem::new);
	public static final Item TOOLBELT = registerAccessory("toolbelt", ToolbeltItem::new);
	public static final Item TOOLBOX = registerAccessory("toolbox", ToolboxItem::new);
	public static final Item EXTENDO_GRIP = registerAccessory("extendo_grip", ExtendoGripItem::new);
	public static final Item ANCIENT_CHISEL = registerAccessory("ancient_chisel", AccessoryTerrariaItem::new);
	public static final Item TREASURE_MAGNET = registerAccessory("treasure_magnet", UniversalAttractorItem::new);

	// Hands
	public static final Item SHOE_SPIKES = registerAccessory("shoe_spikes", AccessoryTerrariaItem::new);
	public static final Item CLIMBING_CLAWS = registerAccessory("climbing_claws", AccessoryTerrariaItem::new);
	public static final Item TIGER_CLIMBING_GEAR = registerAccessory("tiger_climbing_gear", AccessoryTerrariaItem::new);
	public static final Item TABI = registerAccessory("tabi", TabiItem::new);
	public static final Item BLACK_BELT = registerAccessory("black_belt", AccessoryTerrariaItem::new);
	public static final Item MASTER_NINJA_GEAR = registerAccessory("master_ninja_gear", MasterNinjaGearItem::new);
	public static final Item FERAL_CLAWS = registerAccessory("feral_claws", FeralClawsItem::new);
	public static final Item TITAN_GLOVE = registerAccessory("titan_glove", TitanGloveItem::new);
	public static final Item POWER_GLOVE = registerAccessory("power_glove", PowerGloveItem::new);
	public static final Item MECHANICAL_GLOVE = registerAccessory("mechanical_glove", MechanicalGloveItem::new);
	public static final Item FIRE_GAUNTLET = registerAccessory("fire_gauntlet", FireGauntletItem::new);
	public static final Item BAND_OF_REGENERATION = registerAccessory("band_of_regeneration", BandOfRegenerationItem::new);
	public static final Item PHILOSOPHERS_STONE = registerAccessory("philosophers_stone", PhilosophersStoneItem::new);
	public static final Item CHARM_OF_MYTHS = registerAccessory("charm_of_myths", CharmOfMythsItem::new);
	public static final Item BAND_OF_STARPOWER = registerAccessory("band_of_starpower", BandOfStarpowerItem::new);
	public static final Item MANA_REGENERATION_BAND = registerAccessory("mana_regeneration_band", ManaRegenerationBandItem::new);
	public static final Item MAGIC_CUFFS = registerAccessory("magic_cuffs", MagicCuffsItem::new);

	// Head
	public static final Item DIVING_HELMET = registerAccessory("diving_helmet", DivingGear::new);
	public static final Item DIVING_GEAR = registerAccessory("diving_gear", DivingGear::new);

	// Feet
	public static final Item FLIPPERS = registerAccessory("flippers", AccessoryTerrariaItem::new);
	public static final Item AGLET = registerAccessory("aglet", AgletItem::new);
	public static final Item ANKLET = registerAccessory("anklet", AnkletItem::new);
	public static final Item WATER_WALKING_BOOTS = registerAccessory("water_walking_boots", WaterWalkingBootsItem::new);
	public static final Item OBSIDIAN_WATER_WALKING_BOOTS = registerAccessory("obsidian_water_walking_boots", ObsidianWaterWalkingBootsItem::new);
	public static final Item LAVA_WADERS = registerAccessory("lava_waders", LavaWadersItem::new);
	public static final Item ICE_SKATES = registerAccessory("ice_skates", AccessoryTerrariaItem::new);
	public static final Item HERMES_BOOTS = registerAccessory("hermes_boots", AccessoryTerrariaItem::new);
	public static final Item ROCKET_BOOTS = registerAccessory("rocket_boots", RocketBootsItem::new);
	public static final Item SPECTRE_BOOTS = registerAccessory("spectre_boots", SpectreBootsItem::new);
	public static final Item LIGHTNING_BOOTS = registerAccessory("lightning_boots", LightningBootsItem::new);
	public static final Item FROSTSPARK_BOOTS = registerAccessory("frostspark_boots", FrostsparkBootsItem::new);
	public static final Item TERRASPARK_BOOTS = registerAccessory("terraspark_boots", TerrasparkBootsItem::new);
	public static final Item FLOWER_BOOTS = registerAccessory("flower_boots", FlowerBootsItem::new);
	public static final Item FAIRY_BOOTS = registerAccessory("fairy_boots", FairyBootsItem::new);

	// Back
	public static final Item FLEDGLING_WINGS = registerAccessory("fledgling_wings", key -> new WingsItem(0.5D, 0.025D, 20, 7, ModSoundEvents.WINGS_FLAP, key));
	public static final Item ANGEL_WINGS = registerAccessory("angel_wings", key -> new WingsItem(0.5D, 0.05D, 80, 8, ModSoundEvents.WINGS_FLAP, key));
	public static final Item DEMON_WINGS = registerAccessory("demon_wings", key -> new WingsItem(0.5D, 0.05D, 80, 9, ModSoundEvents.WINGS_FLAP, key));
	public static final Item LEAF_WINGS = registerAccessory("leaf_wings", key -> new WingsItem(0.5D, 0.05D, 80, 10, ModSoundEvents.WINGS_FLAP, key));

	// Tools
	public static final BasicToolSetRegister COPPER_TOOLS = new BasicToolSetRegister("copper", TerrariaToolMaterials.COPPER);
	public static final BasicToolSetRegister TIN_TOOLS = new BasicToolSetRegister("tin", TerrariaToolMaterials.TIN);
	public static final BasicToolSetRegister LEAD_TOOLS = new BasicToolSetRegister("lead", TerrariaToolMaterials.LEAD);
	public static final BasicToolSetRegister SILVER_TOOLS = new BasicToolSetRegister("silver", TerrariaToolMaterials.SILVER);
	public static final BasicToolSetRegister TUNGSTEN_TOOLS = new BasicToolSetRegister("tungsten", TerrariaToolMaterials.TUNGSTEN);
	public static final BasicToolSetRegister PLATINUM_TOOLS = new BasicToolSetRegister("platinum", TerrariaToolMaterials.PLATINUM);
	public static final BasicToolSetRegister DEMONITE_TOOLS = new BasicToolSetRegister("demonite", TerrariaToolMaterials.DEMONITE);
	public static final BasicToolSetRegister CRIMTANE_TOOLS = new BasicToolSetRegister("crimtane", TerrariaToolMaterials.CRIMTANE, true);
	public static final Item METEOR_SHAXE = registerTool("meteor_shaxe", key -> new TerrariaShaxeItem(TerrariaToolMaterials.METEOR, 7F, -3.1F, new Item.Properties().setId(key).fireResistant()));
	public static final Item MOLTEN_PICKAXE = registerTool("molten_pickaxe", key -> new MoltenPickaxeItem(TerrariaToolMaterials.MOLTEN, 1F, -2.8F, new Item.Properties().setId(key).fireResistant()));
	public static final Item MOLTEN_SHAXE = registerTool("molten_shaxe", key -> new TerrariaShaxeItem(TerrariaToolMaterials.MOLTEN, true, 7.5F, -3.1F, new Item.Properties().setId(key).fireResistant()));
	// reminder: any pickaxe better than molten needs a true boolean added after the g float

	// Weapons
	public static final Item PHASEBLADE_WHITE = registerBigWeapon("phaseblade_white", key -> new CustomSoundSwordItem(TerrariaToolMaterials.METEOR, 3F, -1F, ModSoundEvents.PHASEBLADE_SWING, new Item.Properties().setId(key)));
	public static final Item PHASEBLADE_GREEN = registerBigWeapon("phaseblade_green", key -> new CustomSoundSwordItem(TerrariaToolMaterials.METEOR, 3F, -1F, ModSoundEvents.PHASEBLADE_SWING, new Item.Properties().setId(key)));
	public static final Item VOLCANO_SWORD = registerBigWeapon("volcano_sword", key -> new VolcanoSwordItem(TerrariaToolMaterials.MOLTEN, 4F, -2.4F, new Item.Properties().setId(key).fireResistant()));

	// Ranged
	public static final Item SPACE_GUN = registerMagicWeapon("space_gun", SpaceGunItem::new);

	// Throwables
	// Grenades
	public static final Item GRENADE = registerThrowable("grenade", key -> new GrenadeItem(new Item.Properties().setId(key), false, false));
	public static final Item STICKY_GRENADE = registerThrowable("sticky_grenade", key -> new GrenadeItem(new Item.Properties().setId(key), true, false));
	public static final Item BOUNCY_GRENADE = registerThrowable("bouncy_grenade", key -> new GrenadeItem(new Item.Properties().setId(key), false, true));
	// Bombs
	public static final Item BOMB = registerThrowable("bomb", key -> new BombItem(new Item.Properties().setId(key), false, false));
	public static final Item STICKY_BOMB = registerThrowable("sticky_bomb", key -> new BombItem(new Item.Properties().setId(key), true, false));
	public static final Item BOUNCY_BOMB = registerThrowable("bouncy_bomb", key -> new BombItem(new Item.Properties().setId(key), false, true));
	// Dynamite
	public static final Item DYNAMITE = registerThrowable("dynamite", key -> new DynamiteItem(new Item.Properties().setId(key), false, false));
	public static final Item STICKY_DYNAMITE = registerThrowable("sticky_dynamite", key -> new DynamiteItem(new Item.Properties().setId(key), true, false));
	public static final Item BOUNCY_DYNAMITE = registerThrowable("bouncy_dynamite", key -> new DynamiteItem(new Item.Properties().setId(key), false, true));

	// Armours
	public static final ArmorItemRegister COPPER_ARMOR = new ArmorItemRegister("copper", "copper", TerrariaArmorMaterials.COPPER);
	public static final ArmorItemRegister TIN_ARMOR = new ArmorItemRegister("tin", "tin", TerrariaArmorMaterials.TIN);
	public static final ArmorItemRegister LEAD_ARMOR = new ArmorItemRegister("lead", "lead", TerrariaArmorMaterials.LEAD);
	public static final ArmorItemRegister SILVER_ARMOR = new ArmorItemRegister("silver", "silver", TerrariaArmorMaterials.SILVER);
	public static final ArmorItemRegister TUNGSTEN_ARMOR = new ArmorItemRegister("tungsten", "tungsten", TerrariaArmorMaterials.TUNGSTEN);
	public static final ArmorItemRegister PLATINUM_ARMOR = new ArmorItemRegister("platinum", "platinum", TerrariaArmorMaterials.PLATINUM);
	public static final ArmorItemRegister SHADOW_ARMOR = new ArmorItemRegister("shadow", "shadow", TerrariaArmorMaterials.SHADOW, ShadowArmor.class);
	public static final ArmorItemRegister ANCIENT_SHADOW_ARMOR = new ArmorItemRegister("ancient_shadow", "shadow", TerrariaArmorMaterials.ANCIENT_SHADOW, ShadowArmor.class);
	public static final ArmorItemRegister CRIMSON_ARMOR = new ArmorItemRegister("crimson", "crimson", TerrariaArmorMaterials.CRIMSON, CrimsonArmor.class);
	public static final ArmorItemRegister METEOR_ARMOR = new ArmorItemRegister("meteor", "meteor", TerrariaArmorMaterials.METEOR, MeteorArmor.class);
	public static final ArmorItemRegister MOLTEN_ARMOR = new ArmorItemRegister("molten", "molten", TerrariaArmorMaterials.MOLTEN, MoltenArmor.class);

	// Vanity Armours
	public static final Item FAMILIAR_WIG = registerArmor("familiar_wig", key -> new FamiliarVanity(TerrariaArmorMaterials.VANITY, ArmorType.HELMET, new Item.Properties().setId(key)));
	public static final Item FAMILIAR_SHIRT = registerArmor("familiar_shirt", key -> new FamiliarVanity(TerrariaArmorMaterials.VANITY, ArmorType.CHESTPLATE, new Item.Properties().setId(key)));
	public static final Item FAMILIAR_PANTS = registerArmor("familiar_pants", key -> new FamiliarVanity(TerrariaArmorMaterials.VANITY, ArmorType.LEGGINGS, new Item.Properties().setId(key)));
	public static final Item FAMILIAR_SHOES = registerArmor("familiar_shoes", key -> new FamiliarVanity(TerrariaArmorMaterials.VANITY, ArmorType.BOOTS, new Item.Properties().setId(key)));
	public static final Item TOP_HAT = registerArmor("top_hat", key -> new TopHatVanity(TerrariaArmorMaterials.VANITY, ArmorType.HELMET, new Item.Properties().setId(key)));
	// todo: make Eye of Cthulhu mask and add it to Treasure Bag loot table (replace familiar wig)

	private static Item registerAccessory(String name, Function<ResourceKey<Item>, Item> itemFactory) {
		Item registeredItem = register(name, itemFactory);
		ACCESSORIES.add(registeredItem);
		return registeredItem;
	}

	private static Item registerArmor(String name, Function<ResourceKey<Item>, Item> itemFactory) {
		Item registeredItem = register(name, itemFactory);
		ARMORS.add(registeredItem);
		return registeredItem;
	}

	private static Item registerTool(String name, Function<ResourceKey<Item>, Item> itemFactory) {
		Item registeredItem = register(name, itemFactory);
		TOOLS.add(registeredItem);
		return registeredItem;
	}

	private static Item registerWeapon(String name, Function<ResourceKey<Item>, Item> itemFactory) {
		Item registeredItem = register(name, itemFactory);
		WEAPONS.add(registeredItem);
		return registeredItem;
	}

	private static Item registerBigWeapon(String name, Function<ResourceKey<Item>, Item> itemFactory) {
		Item registeredItem = register(name, itemFactory);
		BIG_WEAPONS.add(registeredItem);
		return registeredItem;
	}

	private static Item registerArrow(String name, Function<ResourceKey<Item>, Item> itemFactory) {
		Item registeredItem = register(name, itemFactory);
		ARROWS.add(registeredItem);
		return registeredItem;
	}

	private static Item registerMagicWeapon(String name, Function<ResourceKey<Item>, Item> itemFactory) {
		Item registeredItem = register(name, itemFactory);
		MAGIC_WEAPONS.add(registeredItem);
		return registeredItem;
	}

	private static Item registerThrowable(String name, Function<ResourceKey<Item>, Item> itemFactory) {
		Item registeredItem = register(name, itemFactory);
		THROWABLES.add(registeredItem);
		return registeredItem;
	}

	private static Item registerShield(String name, Function<ResourceKey<Item>, Item> itemFactory) {
		Item registeredItem = register(name, itemFactory);
		SHIELDS.add(registeredItem);
		return registeredItem;
	}

	private static Item registerDye(String name, Function<ResourceKey<Item>, Item> itemFactory) {
		Item registeredItem = register(name, itemFactory);
		DYES.add(registeredItem);
		return registeredItem;
	}

	private static Item registerMisc(String name, Function<ResourceKey<Item>, Item> itemFactory) {
		Item registeredItem = register(name, itemFactory);
		MISC.add(registeredItem);
		return registeredItem;
	}

	private static Item registerSpawnEgg(String name, Function<ResourceKey<Item>, Item> itemFactory) {
		Item registeredItem = register(name, itemFactory);
		SPAWN_EGGS.add(registeredItem);
		return registeredItem;
	}

	private static Item register(String name, Function<ResourceKey<Item>, Item> itemFactory) {
		ResourceLocation resourceLocation = TerraMine.id(name);
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, resourceLocation);
		Item item = itemFactory.apply(key);

		return Registry.register(BuiltInRegistries.ITEM, key, item);
	}
}
