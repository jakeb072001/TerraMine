package terramine.common.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;
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

import java.util.function.Function;

@SuppressWarnings("unused")
public class ModItems {

	// Misc
	public static final Item MIMIC_SPAWN_EGG = register("mimic_spawn_egg", key -> new SpawnEggItem(ModEntities.MIMIC, 0x805113, 0x212121, new Item.Properties().setId(key)));
	public static final Item DEMON_EYE_SPAWN_EGG = register("demon_eye_spawn_egg", key -> new SpawnEggItem(ModEntities.DEMON_EYE, 0xffffff, 0xff0000, new Item.Properties().setId(key)));
	public static final Item EATER_OF_SOULS_SPAWN_EGG = register("eater_of_souls_spawn_egg", key -> new SpawnEggItem(ModEntities.EATER_OF_SOULS, 0x735c5f, 0x999190, new Item.Properties().setId(key)));
	public static final Item DEVOURER_SPAWN_EGG = register("devourer_spawn_egg", key -> new SpawnEggItem(ModEntities.DEVOURER, 0x999190, 0x735c5f, new Item.Properties().setId(key)));
	public static final Item CRIMERA_SPAWN_EGG = register("crimera_spawn_egg", key -> new SpawnEggItem(ModEntities.CRIMERA, 0x72261f, 0xac524d, new Item.Properties().setId(key)));
	public static final Item SUSPICIOUS_LOOKING_EYE = register("suspicious_looking_eye", key -> new BossSpawnItem(ModEntities.TEST_BOSS, new Item.Properties().setId(key).stacksTo(16).rarity(Rarity.RARE)));
	public static final Item UMBRELLA = register("umbrella", UmbrellaItem::new);
	public static final Item WHOOPEE_CUSHION = register("whoopee_cushion", WhoopeeCushionItem::new);
	public static final Item MAGIC_MIRROR = register("magic_mirror", MagicMirrorItem::new);
	public static final Item COBALT_SHIELD = register("cobalt_shield", key -> new ShieldAccessoryLikeItem(new Item.Properties().setId(key).repairable(Items.DIAMOND).durability(2500).fireResistant().rarity(Rarity.RARE)));
	public static final Item OBSIDIAN_SHIELD = register("obsidian_shield", key -> new ShieldAccessoryLikeItem(new Item.Properties().setId(key).repairable(Items.OBSIDIAN).durability(2500).fireResistant().rarity(Rarity.RARE)));
	public static final Item SHIELD_OF_CTHULHU = register("shield_of_cthulhu", key -> new ShieldOfCthulhuItem(new Item.Properties().setId(key).repairable(Items.ROTTEN_FLESH).durability(2500).fireResistant().rarity(Rarity.RARE)));
	public static final Item CORRUPT_SEEDS = register("corrupt_seeds", key -> new EvilSeeds(new Item.Properties().setId(key), ModBlocks.CORRUPTED_GRASS.BLOCK));
	public static final Item CRIMSON_SEEDS = register("crimson_seeds", key -> new EvilSeeds(new Item.Properties().setId(key), ModBlocks.CRIMSON_GRASS.BLOCK));

	// todo: add many more dyes, need to create a model/item for each one but its just copy paste
	// todo: also add some custom shader dyes, need to add a system to render the dyes first though
	// todo: make dye craft-able, some will also be obtainable from enemies and other things
	// todo: maybe also make work like potions, so only one item is registered and doesn't need a model per item, maybe
	// Dye Items (uses hex)
	public static final Item RED_DYE = register("red_dye", key -> new BasicDye(0xFF0000, key));
	public static final Item GREEN_DYE = register("green_dye", key -> new BasicDye(0x008000, key));
	public static final Item BLUE_DYE = register("blue_dye", key -> new BasicDye(0x0000FF, key));
	public static final Item YELLOW_DYE = register("yellow_dye", key -> new BasicDye(0xFFFF00, key));
	public static final Item ORANGE_DYE = register("orange_dye", key -> new BasicDye(0xFFA500, key));
	public static final Item PURPLE_DYE = register("purple_dye", key -> new BasicDye(0x800080, key));
	public static final Item PINK_DYE = register("pink_dye", key -> new BasicDye(0xFFC0CB, key));
	public static final Item BROWN_DYE = register("brown_dye", key -> new BasicDye(0x964B00, key));
	public static final Item GRAY_DYE = register("gray_dye", key -> new BasicDye(0x808080, key));
	public static final Item BLACK_DYE = register("black_dye", key -> new BasicDye(0x000000, key));

	// Arrows
	// todo: make use just a single arrow item class, need to make all arrows the same entity for this, can be done much later
	public static final Item FLAMING_ARROW = register("flaming_arrow", key -> new FlamingArrowItem(new Item.Properties().setId(key)));
	public static final Item UNHOLY_ARROW = register("unholy_arrow", key -> new UnholyArrowItem(new Item.Properties().setId(key)));
	public static final Item JESTER_ARROW = register("jester_arrow", key -> new JesterArrowItem(new Item.Properties().setId(key)));

	// Treasure Bags
	public static final Item EYE_OF_CTHULHU_TREASURE_BAG = register("eye_of_cthulhu_treasure_bag", key -> new TreasureBagItem(new Item.Properties().setId(key).stacksTo(1).rarity(Rarity.EPIC).fireResistant(), ModLootTables.EYE_OF_CTHULHU, Component.translatable("terramine.ui.eye_of_cthulhu_treasure_bag")));

	// Crafting Items
	public static final Item LENS = register("lens", key -> new CraftingItem(new Item.Properties().setId(key), false));
	public static final Item BLACK_LENS = register("black_lens", key -> new CraftingItem(new Item.Properties().setId(key), false));
	public static final Item ROTTEN_CHUNK = register("rotten_chunk", key -> new CraftingItem(new Item.Properties().setId(key).stacksTo(64), true));
	public static final Item VERTEBRA = register("vertebra", key -> new CraftingItem(new Item.Properties().setId(key).stacksTo(64), false));
	public static final Item WORM_TOOTH = register("worm_tooth", key -> new CraftingItem(new Item.Properties().setId(key).stacksTo(64), false));

	// Ores etc
	public static final Item RAW_METEORITE = register("raw_meteorite", key -> new CraftingItem(new Item.Properties().setId(key), false));
	public static final Item METEORITE_INGOT = register("meteorite_ingot", key -> new CraftingItem(new Item.Properties().setId(key), true));
	public static final Item RAW_DEMONITE = register("raw_demonite", key -> new CraftingItem(new Item.Properties().setId(key), false));
	public static final Item DEMONITE_INGOT = register("demonite_ingot", key -> new CraftingItem(new Item.Properties().setId(key), true));
	public static final Item RAW_CRIMTANE = register("raw_crimtane", key -> new CraftingItem(new Item.Properties().setId(key), false));
	public static final Item CRIMTANE_INGOT = register("crimtane_ingot", key -> new CraftingItem(new Item.Properties().setId(key), false));
	public static final Item RAW_HELLSTONE = register("raw_hellstone", key -> new CraftingItem(new Item.Properties().setId(key).fireResistant(), true));
	public static final Item RAW_HELLSTONE_HARDENED = register("raw_hellstone_hardened", key -> new CraftingItem(new Item.Properties().setId(key).fireResistant(), true));
	public static final Item HELLSTONE_INGOT = register("hellstone_ingot", key -> new CraftingItem(new Item.Properties().setId(key).fireResistant(), true));

	// Magic Items
	public static final Item MAGIC_MISSILE_ITEM = register("magic_missile", MagicMissileItem::new);
	public static final Item FLAMELASH_ITEM = register("flamelash", FlamelashItem::new);
	public static final Item RAINBOW_ROD_ITEM = register("rainbow_rod", RainbowRodItem::new);
	public static final Item FAKE_FALLEN_STAR = register("fake_fallen_star", key -> new CraftingItem(new Item.Properties().setId(key), true));
	public static final Item FALLEN_STAR = register("fallen_star", key -> new CraftingItem(new Item.Properties().setId(key).stacksTo(64).rarity(Rarity.UNCOMMON), true));
	public static final Item MANA_CRYSTAL = register("mana_crystal", key -> new ManaCrystalItem(new Item.Properties().setId(key).stacksTo(64).rarity(Rarity.RARE)));
	public static final Item DEMON_HEART = register("demon_heart", key -> new DemonHeartItem(new Item.Properties().setId(key).stacksTo(16).rarity(Rarity.EPIC)));

	// Informational
	public static final Item GOLD_WATCH = register("gold_watch", AccessoryTerrariaItem::new);
	public static final Item DEPTH_METER = register("depth_meter", AccessoryTerrariaItem::new);
	public static final Item COMPASS = register("compass", AccessoryTerrariaItem::new);
	public static final Item GPS = register("gps", AccessoryTerrariaItem::new);
	public static final Item WEATHER_RADIO = register("weather_radio", AccessoryTerrariaItem::new);
	public static final Item SEXTANT = register("sextant", AccessoryTerrariaItem::new);
	public static final Item FISH_FINDER = register("fish_finder", AccessoryTerrariaItem::new);
	public static final Item METAL_DETECTOR = register("metal_detector", AccessoryTerrariaItem::new);
	public static final Item STOPWATCH = register("stopwatch", AccessoryTerrariaItem::new);
	public static final Item DPS_METER = register("dps_meter", AccessoryTerrariaItem::new);
	public static final Item GOBLIN_TECH = register("goblin_tech", AccessoryTerrariaItem::new);
	public static final Item PDA = register("pda", AccessoryTerrariaItem::new);
	public static final Item CELL_PHONE = register("cell_phone", CellPhoneItem::new);

	// Necklace
	public static final Item CROSS_NECKLACE = register("cross_necklace", CrossNecklaceItem::new);
	public static final Item PANIC_NECKLACE = register("panic_necklace", PanicNecklaceItem::new);
	public static final Item RANGER_EMBLEM = register("ranger_emblem", RangerEmblemItem::new);
	public static final Item WARRIOR_EMBLEM = register("warrior_emblem", WarriorEmblemItem::new);
	public static final Item SORCERER_EMBLEM = register("sorcerer_emblem", SorcererEmblemItem::new);
	public static final Item AVENGER_EMBLEM = register("avenger_emblem", AvengerEmblemItem::new);
	public static final Item NEPTUNE_SHELL = register("neptune_shell", key -> new CelestialShell(true, false, false, false, key));
	public static final Item MOON_CHARM = register("moon_charm", key -> new CelestialShell(false, true, false, false, key));
	public static final Item MOON_SHELL = register("moon_shell", key -> new CelestialShell(true, true, false, false, key));
	public static final Item MOON_STONE = register("moon_stone", key -> new CelestialShell(false, false, false, true, key));
	public static final Item SUN_STONE = register("sun_stone", key -> new CelestialShell(false, false, true, false, key));
	public static final Item CELESTIAL_STONE = register("celestial_stone", key -> new CelestialShell(false, false, true, true, key));
	public static final Item CELESTIAL_SHELL = register("celestial_shell", key -> new CelestialShell(true, true, true, true, key));

	// Belt
	public static final Item SHACKLE = register("shackle", ShackleItem::new);
	public static final Item OBSIDIAN_ROSE = register("obsidian_rose", AccessoryTerrariaItem::new);
	public static final Item MAGMA_STONE = register("magma_stone", AccessoryTerrariaItem::new);
	public static final Item OBSIDIAN_SKULL = register("obsidian_skull", ObsidianSkullItem::new);
	public static final Item MAGMA_SKULL = register("magma_skull", ObsidianSkullItem::new);
	public static final Item OBSIDIAN_SKULL_ROSE = register("obsidian_skull_rose", ObsidianSkullItem::new);
	public static final Item MOLTEN_SKULL_ROSE = register("molten_skull_rose", ObsidianSkullItem::new);
	public static final Item LAVA_CHARM = register("lava_charm", AccessoryTerrariaItem::new);
	public static final Item MOLTEN_CHARM = register("molten_charm", AccessoryTerrariaItem::new);
	public static final Item LUCKY_HORSESHOE = register("lucky_horseshoe", AccessoryTerrariaItem::new);
	public static final Item OBSIDIAN_HORSESHOE = register("obsidian_horseshoe", AccessoryTerrariaItem::new);
	public static final Item CLOUD_IN_A_BOTTLE = register("cloud_in_a_bottle", CloudInABottleItem::new);
	public static final Item SHINY_RED_BALLOON = register("shiny_red_balloon", ShinyRedBalloonItem::new);
	public static final Item CLOUD_IN_A_BALLOON = register("cloud_in_a_balloon", CloudInABalloonItem::new);
	public static final Item BUNDLE_OF_BALLOONS = register("bundle_of_balloons", BundleOfBalloonsItem::new);
	public static final Item BLUE_HORSESHOE_BALLOON = register("blue_horseshoe_balloon", BlueHorseshoeBalloonItem::new);
	public static final Item TOOLBELT = register("toolbelt", ToolbeltItem::new);
	public static final Item TOOLBOX = register("toolbox", ToolboxItem::new);
	public static final Item EXTENDO_GRIP = register("extendo_grip", ExtendoGripItem::new);
	public static final Item ANCIENT_CHISEL = register("ancient_chisel", AccessoryTerrariaItem::new);
	public static final Item TREASURE_MAGNET = register("treasure_magnet", UniversalAttractorItem::new);

	// Hands
	public static final Item SHOE_SPIKES = register("shoe_spikes", AccessoryTerrariaItem::new);
	public static final Item CLIMBING_CLAWS = register("climbing_claws", AccessoryTerrariaItem::new);
	public static final Item TIGER_CLIMBING_GEAR = register("tiger_climbing_gear", AccessoryTerrariaItem::new);
	public static final Item TABI = register("tabi", TabiItem::new);
	public static final Item BLACK_BELT = register("black_belt", AccessoryTerrariaItem::new);
	public static final Item MASTER_NINJA_GEAR = register("master_ninja_gear", MasterNinjaGearItem::new);
	public static final Item FERAL_CLAWS = register("feral_claws", FeralClawsItem::new);
	public static final Item TITAN_GLOVE = register("titan_glove", TitanGloveItem::new);
	public static final Item POWER_GLOVE = register("power_glove", PowerGloveItem::new);
	public static final Item MECHANICAL_GLOVE = register("mechanical_glove", MechanicalGloveItem::new);
	public static final Item FIRE_GAUNTLET = register("fire_gauntlet", FireGauntletItem::new);
	public static final Item BAND_OF_REGENERATION = register("band_of_regeneration", BandOfRegenerationItem::new);
	public static final Item PHILOSOPHERS_STONE = register("philosophers_stone", PhilosophersStoneItem::new);
	public static final Item CHARM_OF_MYTHS = register("charm_of_myths", CharmOfMythsItem::new);
	public static final Item BAND_OF_STARPOWER = register("band_of_starpower", BandOfStarpowerItem::new);
	public static final Item MANA_REGENERATION_BAND = register("mana_regeneration_band", ManaRegenerationBandItem::new);
	public static final Item MAGIC_CUFFS = register("magic_cuffs", MagicCuffsItem::new);

	// Head
	public static final Item DIVING_HELMET = register("diving_helmet", DivingGear::new);
	public static final Item DIVING_GEAR = register("diving_gear", DivingGear::new);

	// Feet
	public static final Item FLIPPERS = register("flippers", AccessoryTerrariaItem::new);
	public static final Item AGLET = register("aglet", AgletItem::new);
	public static final Item ANKLET = register("anklet", AnkletItem::new);
	public static final Item WATER_WALKING_BOOTS = register("water_walking_boots", WaterWalkingBootsItem::new);
	public static final Item OBSIDIAN_WATER_WALKING_BOOTS = register("obsidian_water_walking_boots", ObsidianWaterWalkingBootsItem::new);
	public static final Item LAVA_WADERS = register("lava_waders", LavaWadersItem::new);
	public static final Item ICE_SKATES = register("ice_skates", AccessoryTerrariaItem::new);
	public static final Item HERMES_BOOTS = register("hermes_boots", AccessoryTerrariaItem::new);
	public static final Item ROCKET_BOOTS = register("rocket_boots", RocketBootsItem::new);
	public static final Item SPECTRE_BOOTS = register("spectre_boots", SpectreBootsItem::new);
	public static final Item LIGHTNING_BOOTS = register("lightning_boots", LightningBootsItem::new);
	public static final Item FROSTSPARK_BOOTS = register("frostspark_boots", FrostsparkBootsItem::new);
	public static final Item TERRASPARK_BOOTS = register("terraspark_boots", TerrasparkBootsItem::new);
	public static final Item FLOWER_BOOTS = register("flower_boots", FlowerBootsItem::new);
	public static final Item FAIRY_BOOTS = register("fairy_boots", FairyBootsItem::new);

	// Back
	public static final Item FLEDGLING_WINGS = register("fledgling_wings", key -> new WingsItem(0.5D, 0.025D, 20, 7, ModSoundEvents.WINGS_FLAP, key));
	public static final Item ANGEL_WINGS = register("angel_wings", key -> new WingsItem(0.5D, 0.05D, 80, 8, ModSoundEvents.WINGS_FLAP, key));
	public static final Item DEMON_WINGS = register("demon_wings", key -> new WingsItem(0.5D, 0.05D, 80, 9, ModSoundEvents.WINGS_FLAP, key));
	public static final Item LEAF_WINGS = register("leaf_wings", key -> new WingsItem(0.5D, 0.05D, 80, 10, ModSoundEvents.WINGS_FLAP, key));

	// Tools
	public static final Item DEMONITE_PICKAXE = register("demonite_pickaxe", key -> new PickaxeItem(TerrariaToolMaterials.DEMONITE, 1F, -2.8F, new Item.Properties().setId(key)));
	public static final Item DEMONITE_AXE = register("demonite_axe", key -> new AxeItem(TerrariaToolMaterials.DEMONITE, 6F, -3.1F, new Item.Properties().setId(key)));
	public static final Item DEMONITE_SHOVEL = register("demonite_shovel", key -> new ShovelItem(TerrariaToolMaterials.DEMONITE, 1.5F, -3F, new Item.Properties().setId(key)));
	public static final Item DEMONITE_HOE = register("demonite_hoe", key -> new HoeItem(TerrariaToolMaterials.DEMONITE, -2F, -1F, new Item.Properties().setId(key)));
	public static final Item CRIMTANE_PICKAXE = register("crimtane_pickaxe", key -> new PickaxeItem(TerrariaToolMaterials.CRIMTANE, 1F, -2.8F, new Item.Properties().setId(key)));
	public static final Item CRIMTANE_AXE = register("crimtane_axe", key -> new AxeItem(TerrariaToolMaterials.CRIMTANE, 6.5F, -3.1F, new Item.Properties().setId(key)));
	public static final Item CRIMTANE_SHOVEL = register("crimtane_shovel", key -> new ShovelItem(TerrariaToolMaterials.CRIMTANE, 1.5F, -3F, new Item.Properties().setId(key)));
	public static final Item CRIMTANE_HOE = register("crimtane_hoe", key -> new HoeItem(TerrariaToolMaterials.CRIMTANE, -2, -1F, new Item.Properties().setId(key)));
	public static final Item METEOR_SHAXE = register("meteor_shaxe", key -> new TerrariaShaxeItem(TerrariaToolMaterials.METEOR, 7F, -3.1F, new Item.Properties().setId(key)));
	public static final Item MOLTEN_PICKAXE = register("molten_pickaxe", key -> new MoltenPickaxeItem(TerrariaToolMaterials.MOLTEN, 1F, -2.8F, new Item.Properties().setId(key).fireResistant()));
	public static final Item MOLTEN_SHAXE = register("molten_shaxe", key -> new TerrariaShaxeItem(TerrariaToolMaterials.MOLTEN, true, 7.5F, -3.1F, new Item.Properties().setId(key).fireResistant()));

	// Weapons
	public static final Item DEMONITE_SWORD = register("demonite_sword", key -> new SwordItem(TerrariaToolMaterials.DEMONITE, 3F, -2.4F, new Item.Properties().setId(key)));
	public static final Item CRIMTANE_SWORD = register("crimtane_sword", key -> new SwordItem(TerrariaToolMaterials.CRIMTANE, 3F, -2.4F, new Item.Properties().setId(key)));
	public static final Item PHASEBLADE_WHITE = register("phaseblade_white", key -> new CustomSoundSwordItem(TerrariaToolMaterials.METEOR, 3F, -1F, ModSoundEvents.PHASEBLADE_SWING, new Item.Properties().setId(key)));
	public static final Item PHASEBLADE_GREEN = register("phaseblade_green", key -> new CustomSoundSwordItem(TerrariaToolMaterials.METEOR, 3F, -1F, ModSoundEvents.PHASEBLADE_SWING, new Item.Properties().setId(key)));
	public static final Item VOLCANO_SWORD = register("volcano_sword", key -> new VolcanoSwordItem(TerrariaToolMaterials.MOLTEN, 4F, -2.4F, new Item.Properties().setId(key).fireResistant()));

	// Ranged
	public static final Item SPACE_GUN = register("space_gun", SpaceGunItem::new);

	// Throwables
	// Grenades
	public static final Item GRENADE = register("grenade", key -> new GrenadeItem(new Item.Properties().setId(key), false, false));
	public static final Item STICKY_GRENADE = register("sticky_grenade", key -> new GrenadeItem(new Item.Properties().setId(key), true, false));
	public static final Item BOUNCY_GRENADE = register("bouncy_grenade", key -> new GrenadeItem(new Item.Properties().setId(key), false, true));
	// Bombs
	public static final Item BOMB = register("bomb", key -> new BombItem(new Item.Properties().setId(key), false, false));
	public static final Item STICKY_BOMB = register("sticky_bomb", key -> new BombItem(new Item.Properties().setId(key), true, false));
	public static final Item BOUNCY_BOMB = register("bouncy_bomb", key -> new BombItem(new Item.Properties().setId(key), false, true));
	// Dynamite
	public static final Item DYNAMITE = register("dynamite", key -> new DynamiteItem(new Item.Properties().setId(key), false, false));
	public static final Item STICKY_DYNAMITE = register("sticky_dynamite", key -> new DynamiteItem(new Item.Properties().setId(key), true, false));
	public static final Item BOUNCY_DYNAMITE = register("bouncy_dynamite", key -> new DynamiteItem(new Item.Properties().setId(key), false, true));

	// Armours
	// todo: make another register method that registers a full set of armor instead of registering per piece, don't know the best way to do this since the item is different (ShadowArmor, CrimsonArmor)
	public static final ArmorItemRegister SHADOW_ARMOR = new ArmorItemRegister("shadow", "shadow_armor", TerrariaArmorMaterials.SHADOW);
	public static final ArmorItemRegister ANCIENT_SHADOW_ARMOR = new ArmorItemRegister("ancient_shadow", "shadow_armor", TerrariaArmorMaterials.ANCIENT_SHADOW);
	public static final ArmorItemRegister CRIMSON_ARMOR = new ArmorItemRegister("crimson", "crimson_armor", TerrariaArmorMaterials.CRIMSON);
	public static final ArmorItemRegister METEOR_ARMOR = new ArmorItemRegister("meteor", "meteor_armor", TerrariaArmorMaterials.METEOR);
	public static final ArmorItemRegister MOLTEN_ARMOR = new ArmorItemRegister("molten", "molten_armor", TerrariaArmorMaterials.MOLTEN);

	// Vanity Armours
	public static final Item FAMILIAR_WIG = register("familiar_wig", key -> new FamiliarVanity("familiar_wig", TerrariaArmorMaterials.VANITY, ArmorType.HELMET, new Item.Properties().setId(key)));
	public static final Item FAMILIAR_SHIRT = register("familiar_shirt", key -> new FamiliarVanity("familiar_shirt", TerrariaArmorMaterials.VANITY, ArmorType.CHESTPLATE, new Item.Properties().setId(key)));
	public static final Item FAMILIAR_PANTS = register("familiar_pants", key -> new FamiliarVanity("familiar_pants", TerrariaArmorMaterials.VANITY, ArmorType.LEGGINGS, new Item.Properties().setId(key)));
	public static final Item FAMILIAR_SHOES = register("familiar_shoes", key -> new FamiliarVanity("familiar_shoes", TerrariaArmorMaterials.VANITY, ArmorType.BOOTS, new Item.Properties().setId(key)));
	//public static final Item TOP_HAT = register("top_hat", key -> new TopHatVanity("top_hat", TerrariaArmorMaterials.VANITY, ArmorType.HELMET, new FabricItemSettings()));
	// todo: make Eye of Cthulhu mask and add it to Treasure Bag loot table (replace familiar wig)

	private static Item register(String name, Function<ResourceKey<Item>, Item> itemFactory) {
		ResourceLocation resourceLocation = TerraMine.id(name);
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, resourceLocation);
		Item item = itemFactory.apply(key);

		return Registry.register(BuiltInRegistries.ITEM, key, item);
	}
}
