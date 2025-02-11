package terramine.common.init;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import terramine.common.block.*;
import terramine.common.block.chests.*;
import terramine.common.block.plants.EvilMushroom;
import terramine.common.utility.BlockItemRegister;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {
    public static List<Item> BLOCK_ITEMS = new ArrayList<>();
    public static List<Item> BLOCK_PLANTS = new ArrayList<>();

    // Chests
    public static final BlockItemRegister GOLD_CHEST = new BlockItemRegister("gold_chest", key -> new GoldChestBlock(Properties.of().setId(key).mapColor(MapColor.GOLD).strength(3.0f, 6.0f).sound(SoundType.METAL).requiresCorrectToolForDrops(), false, () -> ModBlockEntityType.GOLD_CHEST));
    public static final BlockItemRegister TRAPPED_GOLD_CHEST = new BlockItemRegister("trapped_gold_chest", key -> new GoldChestBlock(Properties.of().setId(key).mapColor(MapColor.GOLD).strength(3.0f, 6.0f).sound(SoundType.METAL).requiresCorrectToolForDrops(), true, () -> ModBlockEntityType.GOLD_CHEST));
    public static final BlockItemRegister FROZEN_CHEST = new BlockItemRegister("frozen_chest", key -> new FrozenChestBlock(Properties.of().setId(key).mapColor(MapColor.ICE).strength(2.0f, 5.0f).sound(SoundType.METAL).friction(0.98f).requiresCorrectToolForDrops(), false, () -> ModBlockEntityType.FROZEN_CHEST));
    public static final BlockItemRegister TRAPPED_FROZEN_CHEST = new BlockItemRegister("trapped_frozen_chest", key -> new FrozenChestBlock(Properties.of().setId(key).mapColor(MapColor.ICE).strength(2.0f, 5.0f).sound(SoundType.METAL).friction(0.98f).requiresCorrectToolForDrops(), true, () -> ModBlockEntityType.FROZEN_CHEST));
    public static final BlockItemRegister IVY_CHEST = new BlockItemRegister("ivy_chest", key -> new IvyChestBlock(Properties.of().setId(key).mapColor(MapColor.WOOD).strength(2.5f).sound(SoundType.WOOD), false, () -> ModBlockEntityType.IVY_CHEST));
    public static final BlockItemRegister TRAPPED_IVY_CHEST = new BlockItemRegister("trapped_ivy_chest", key -> new IvyChestBlock(Properties.of().setId(key).mapColor(MapColor.WOOD).strength(2.5f).sound(SoundType.WOOD), true, () -> ModBlockEntityType.IVY_CHEST));
    public static final BlockItemRegister SANDSTONE_CHEST = new BlockItemRegister("sandstone_chest", key -> new SandstoneChestBlock(Properties.of().setId(key).mapColor(MapColor.SAND).strength(3.0f).sound(SoundType.STONE).requiresCorrectToolForDrops(), false, () -> ModBlockEntityType.SANDSTONE_CHEST));
    public static final BlockItemRegister TRAPPED_SANDSTONE_CHEST = new BlockItemRegister("trapped_sandstone_chest", key -> new SandstoneChestBlock(Properties.of().setId(key).mapColor(MapColor.SAND).strength(3.0f).sound(SoundType.STONE).requiresCorrectToolForDrops(), true, () -> ModBlockEntityType.SANDSTONE_CHEST));
    public static final BlockItemRegister WATER_CHEST = new BlockItemRegister("water_chest", key -> new WaterChestBlock(Properties.of().setId(key).mapColor(MapColor.WATER).strength(3.0f, 6.0f).sound(SoundType.METAL).requiresCorrectToolForDrops(), false, () -> ModBlockEntityType.WATER_CHEST));
    public static final BlockItemRegister SKYWARE_CHEST = new BlockItemRegister("skyware_chest", key -> new SkywareChestBlock(Properties.of().setId(key).mapColor(MapColor.LAPIS).strength(3.0f, 6.0f).sound(SoundType.METAL).requiresCorrectToolForDrops(), false, () -> ModBlockEntityType.SKYWARE_CHEST));
    public static final BlockItemRegister SHADOW_CHEST = new BlockItemRegister("shadow_chest", key -> new ShadowChestBlock(Properties.of().setId(key).mapColor(MapColor.COLOR_PURPLE).strength(3.0f, 6.0f).sound(SoundType.METAL).requiresCorrectToolForDrops(), false, () -> ModBlockEntityType.SHADOW_CHEST));
    public static final BlockItemRegister PIGGY_BANK = new BlockItemRegister("piggy_bank", key -> new PiggyBankBlock(Properties.ofFullCopy(Blocks.TERRACOTTA).setId(key), () -> ModBlockEntityType.PIGGY_BANK));
    public static final BlockItemRegister SAFE = new BlockItemRegister("safe", key -> new SafeBlock(Properties.ofFullCopy(Blocks.IRON_BLOCK).setId(key), () -> ModBlockEntityType.SAFE));

    // Metals
    public static final BlockItemRegister TIN_ORE = new BlockItemRegister("tin_ore", key -> new Block(Properties.ofFullCopy(Blocks.COPPER_ORE).setId(key)));
    public static final BlockItemRegister DEEPSLATE_TIN_ORE = new BlockItemRegister("deepslate_tin_ore", key -> new Block(Properties.ofFullCopy(Blocks.DEEPSLATE_COPPER_ORE).setId(key)));
    public static final BlockItemRegister RAW_TIN_BLOCK = new BlockItemRegister("raw_tin_block", key -> new Block(Properties.ofFullCopy(Blocks.RAW_COPPER_BLOCK).setId(key)));
    public static final BlockItemRegister TIN_BLOCK = new BlockItemRegister("tin_block", key -> new Block(Properties.ofFullCopy(Blocks.COPPER_BLOCK).setId(key)));
    public static final BlockItemRegister LEAD_ORE = new BlockItemRegister("lead_ore", key -> new Block(Properties.ofFullCopy(Blocks.IRON_ORE).setId(key)));
    public static final BlockItemRegister DEEPSLATE_LEAD_ORE = new BlockItemRegister("deepslate_lead_ore", key -> new Block(Properties.ofFullCopy(Blocks.DEEPSLATE_IRON_ORE).setId(key)));
    public static final BlockItemRegister RAW_LEAD_BLOCK = new BlockItemRegister("raw_lead_block", key -> new Block(Properties.ofFullCopy(Blocks.RAW_IRON_BLOCK).setId(key)));
    public static final BlockItemRegister LEAD_BLOCK = new BlockItemRegister("lead_block", key -> new Block(Properties.ofFullCopy(Blocks.IRON_BLOCK).setId(key)));
    public static final BlockItemRegister SILVER_ORE = new BlockItemRegister("silver_ore", key -> new Block(Properties.ofFullCopy(Blocks.GOLD_ORE).setId(key)));
    public static final BlockItemRegister DEEPSLATE_SILVER_ORE = new BlockItemRegister("deepslate_silver_ore", key -> new Block(Properties.ofFullCopy(Blocks.DEEPSLATE_GOLD_ORE).setId(key)));
    public static final BlockItemRegister RAW_SILVER_BLOCK = new BlockItemRegister("raw_silver_block", key -> new Block(Properties.ofFullCopy(Blocks.RAW_GOLD_BLOCK).setId(key)));
    public static final BlockItemRegister SILVER_BLOCK = new BlockItemRegister("silver_block", key -> new Block(Properties.ofFullCopy(Blocks.GOLD_BLOCK).setId(key)));
    public static final BlockItemRegister TUNGSTEN_ORE = new BlockItemRegister("tungsten_ore", key -> new Block(Properties.ofFullCopy(Blocks.GOLD_ORE).setId(key)));
    public static final BlockItemRegister DEEPSLATE_TUNGSTEN_ORE = new BlockItemRegister("deepslate_tungsten_ore", key -> new Block(Properties.ofFullCopy(Blocks.DEEPSLATE_GOLD_ORE).setId(key)));
    public static final BlockItemRegister RAW_TUNGSTEN_BLOCK = new BlockItemRegister("raw_tungsten_block", key -> new Block(Properties.ofFullCopy(Blocks.RAW_GOLD_BLOCK).setId(key)));
    public static final BlockItemRegister TUNGSTEN_BLOCK = new BlockItemRegister("tungsten_block", key -> new Block(Properties.ofFullCopy(Blocks.GOLD_BLOCK).setId(key)));
    public static final BlockItemRegister PLATINUM_ORE = new BlockItemRegister("platinum_ore", key -> new Block(Properties.ofFullCopy(Blocks.GOLD_ORE).setId(key)));
    public static final BlockItemRegister DEEPSLATE_PLATINUM_ORE = new BlockItemRegister("deepslate_platinum_ore", key -> new Block(Properties.ofFullCopy(Blocks.DEEPSLATE_GOLD_ORE).setId(key)));
    public static final BlockItemRegister RAW_PLATINUM_BLOCK = new BlockItemRegister("raw_platinum_block", key -> new Block(Properties.ofFullCopy(Blocks.RAW_GOLD_BLOCK).setId(key)));
    public static final BlockItemRegister PLATINUM_BLOCK = new BlockItemRegister("platinum_block", key -> new Block(Properties.ofFullCopy(Blocks.GOLD_BLOCK).setId(key)));
    public static final BlockItemRegister DEMONITE_ORE = new BlockItemRegister("demonite_ore", key -> new Block(Properties.ofFullCopy(Blocks.IRON_ORE).setId(key)));
    public static final BlockItemRegister DEEPSLATE_DEMONITE_ORE = new BlockItemRegister("deepslate_demonite_ore", key -> new Block(Properties.ofFullCopy(Blocks.DEEPSLATE_IRON_ORE).setId(key)));
    public static final BlockItemRegister RAW_DEMONITE_BLOCK = new BlockItemRegister("raw_demonite_block", key -> new Block(Properties.ofFullCopy(Blocks.RAW_IRON_BLOCK).setId(key)));
    public static final BlockItemRegister DEMONITE_BLOCK = new BlockItemRegister("demonite_block", key -> new Block(Properties.ofFullCopy(Blocks.IRON_BLOCK).setId(key)));
    public static final BlockItemRegister CRIMTANE_ORE = new BlockItemRegister("crimtane_ore", key -> new Block(Properties.ofFullCopy(Blocks.IRON_ORE).setId(key)));
    public static final BlockItemRegister DEEPSLATE_CRIMTANE_ORE = new BlockItemRegister("deepslate_crimtane_ore", key -> new Block(Properties.ofFullCopy(Blocks.DEEPSLATE_IRON_ORE).setId(key)));
    public static final BlockItemRegister RAW_CRIMTANE_BLOCK = new BlockItemRegister("raw_crimtane_block", key -> new Block(Properties.ofFullCopy(Blocks.RAW_IRON_BLOCK).setId(key)));
    public static final BlockItemRegister CRIMTANE_BLOCK = new BlockItemRegister("crimtane_block", key -> new Block(Properties.ofFullCopy(Blocks.IRON_BLOCK).setId(key)));
    public static final BlockItemRegister METEORITE_ORE = new BlockItemRegister("meteorite_ore", key -> new HotFloorBlock(Properties.ofFullCopy(Blocks.MAGMA_BLOCK).setId(key).strength(5f)));
    public static final BlockItemRegister RAW_METEORITE_BLOCK = new BlockItemRegister("raw_meteorite_block", key -> new Block(Properties.ofFullCopy(Blocks.MAGMA_BLOCK).setId(key).strength(8f)));
    public static final BlockItemRegister METEORITE_BLOCK = new BlockItemRegister("meteorite_block", key -> new Block(Properties.ofFullCopy(Blocks.RAW_IRON_BLOCK).setId(key)));
    public static final BlockItemRegister HELLSTONE_ORE = new BlockItemRegister("hellstone_ore", key -> new HellstoneBlock(Properties.ofFullCopy(Blocks.MAGMA_BLOCK).setId(key).sound(SoundType.NETHER_GOLD_ORE).strength(7f)), new Item.Properties().fireResistant());
    public static final BlockItemRegister RAW_HELLSTONE_BLOCK = new BlockItemRegister("raw_hellstone_block", key -> new HotFloorBlock(Properties.ofFullCopy(Blocks.MAGMA_BLOCK).setId(key).destroyTime(10f).explosionResistance(1200f), false), new Item.Properties().fireResistant());
    public static final BlockItemRegister HELLSTONE_BLOCK = new BlockItemRegister("hellstone_block", key -> new Block(Properties.ofFullCopy(Blocks.NETHERITE_BLOCK).setId(key)), new Item.Properties().fireResistant());

    // Misc
    public static final BlockItemRegister REDSTONE_STONE = new BlockItemRegister("redstone_stone", key -> new RedStoneStoneBlock(Properties.ofFullCopy(Blocks.STONE).setId(key).strength(1.5f, 1200.0f)));
    public static final BlockItemRegister REDSTONE_DEEPSLATE = new BlockItemRegister("redstone_deepslate", key -> new RedStoneDeepslateBlock(Properties.ofFullCopy(Blocks.DEEPSLATE).setId(key).strength(3.0f, 1200.0f)));
    public static final BlockItemRegister INSTANT_TNT = new BlockItemRegister("instant_tnt", key -> new InstantTNTBlock(Properties.ofFullCopy(Blocks.TNT).setId(key)));
    public static final BlockItemRegister TINKERER_TABLE = new BlockItemRegister("tinkerer_workshop", key -> new Block(Properties.ofFullCopy(Blocks.CRAFTING_TABLE).setId(key)));

    // Building
    public static final BlockItemRegister SUNPLATE_BLOCK = new BlockItemRegister("sunplate_block", key -> new Block(Properties.ofFullCopy(Blocks.GOLD_BLOCK).setId(key)));
    public static final BlockItemRegister CLOUD = new BlockItemRegister("cloud", key -> new Block(Properties.of().setId(key).mapColor(MapColor.SNOW).strength(0.2f).sound(SoundType.SNOW).noOcclusion()));
    public static final BlockItemRegister RAIN_CLOUD = new BlockItemRegister("rain_cloud", key -> new RainCloudBlock(Properties.of().setId(key).mapColor(MapColor.COLOR_LIGHT_BLUE).strength(0.2f).sound(SoundType.SNOW).noOcclusion()));
    public static final BlockItemRegister BLUE_BRICKS = new BlockItemRegister("blue_brick", key -> new DungeonBlock(Properties.of().setId(key).mapColor(MapColor.COLOR_BLUE).requiresCorrectToolForDrops().strength(1.5f, 1200.0F)));
    public static final BlockItemRegister CRACKED_BLUE_BRICKS = new BlockItemRegister("cracked_blue_brick", key -> new DungeonBlock(Properties.of().setId(key).mapColor(MapColor.COLOR_BLUE).requiresCorrectToolForDrops().strength(1.5f, 1200.0F)));
    public static final BlockItemRegister FANCY_BLUE_BRICKS = new BlockItemRegister("fancy_blue_brick", key -> new DungeonBlock(Properties.of().setId(key).mapColor(MapColor.COLOR_BLUE).requiresCorrectToolForDrops().strength(1.5f, 1200.0F)));
    public static final BlockItemRegister GREEN_BRICKS = new BlockItemRegister("green_brick", key -> new DungeonBlock(Properties.of().setId(key).requiresCorrectToolForDrops().mapColor(MapColor.COLOR_GREEN).strength(1.5f, 1200.0F)));
    public static final BlockItemRegister CRACKED_GREEN_BRICKS = new BlockItemRegister("cracked_green_brick", key -> new DungeonBlock(Properties.of().setId(key).mapColor(MapColor.COLOR_GREEN).requiresCorrectToolForDrops().strength(1.5f, 1200.0F)));
    public static final BlockItemRegister FANCY_GREEN_BRICKS = new BlockItemRegister("fancy_green_brick", key -> new DungeonBlock(Properties.of().setId(key).mapColor(MapColor.COLOR_GREEN).requiresCorrectToolForDrops().strength(1.5f, 1200.0F)));
    public static final BlockItemRegister PURPLE_BRICKS = new BlockItemRegister("purple_brick", key -> new DungeonBlock(Properties.of().setId(key).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(1.5f, 1200.0F)));
    public static final BlockItemRegister CRACKED_PURPLE_BRICKS = new BlockItemRegister("cracked_purple_brick", key -> new DungeonBlock(Properties.of().setId(key).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(1.5f, 1200.0F)));
    public static final BlockItemRegister FANCY_PURPLE_BRICKS = new BlockItemRegister("fancy_purple_brick", key -> new DungeonBlock(Properties.of().setId(key).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(1.5f, 1200.0F)));

    // Vegetation
    public static final BlockItemRegister VILE_MUSHROOM = new BlockItemRegister("vile_mushroom", key -> new EvilMushroom(true, Properties.of().setId(key).mapColor(MapColor.COLOR_PURPLE).pushReaction(PushReaction.DESTROY).noCollission().instabreak().sound(SoundType.GRASS).lightLevel(blockState -> 1).hasPostProcess(ModBlocks::always)), 0.65f);
    public static final BlockItemRegister POTTED_VILE_MUSHROOM = new BlockItemRegister("potted_vile_mushroom", key -> new FlowerPotBlock(VILE_MUSHROOM.BLOCK, BlockBehaviour.Properties.of().setId(key).mapColor(MapColor.COLOR_PURPLE).pushReaction(PushReaction.DESTROY).instabreak().noOcclusion()), false);
    public static final BlockItemRegister VICIOUS_MUSHROOM = new BlockItemRegister("vicious_mushroom", key -> new EvilMushroom(false, Properties.of().setId(key).mapColor(MapColor.COLOR_RED).pushReaction(PushReaction.DESTROY).noCollission().instabreak().sound(SoundType.GRASS).lightLevel(blockState -> 1).hasPostProcess(ModBlocks::always)), 0.65f);
    public static final BlockItemRegister POTTED_VICIOUS_MUSHROOM = new BlockItemRegister("potted_vicious_mushroom", key -> new FlowerPotBlock(VICIOUS_MUSHROOM.BLOCK, BlockBehaviour.Properties.of().setId(key).mapColor(MapColor.COLOR_RED).pushReaction(PushReaction.DESTROY).instabreak().noOcclusion()), false);

    // todo: maybe find a better way to add evil version of each block, maybe have a component that stores which evil type the block is if its even evil, then in the block renderer for the texture change the namespace to terramine and append _corruption or something to the texture
    //  this way i can just mixin to the blocks i want and add that component, then create the texture. for block spread just use vanilla blocks but check for the component.
    // Corruption
    public static final BlockItemRegister CORRUPTED_GRASS = new BlockItemRegister("corrupted_grass", key -> new CorruptedGrass(Properties.ofFullCopy(Blocks.GRASS_BLOCK).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_GRAVEL = new BlockItemRegister("corrupted_gravel", key -> new CorruptedFallingBlock(Properties.ofFullCopy(Blocks.GRAVEL).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_SAND = new BlockItemRegister("corrupted_sand", key -> new CorruptedFallingBlock(Properties.ofFullCopy(Blocks.SAND).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_GLASS = new BlockItemRegister("corrupted_glass", key -> new CorruptedTransparentBlock(Properties.ofFullCopy(Blocks.GLASS).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_SANDSTONE = new BlockItemRegister("corrupted_sandstone", key -> new CorruptedBlock(Properties.ofFullCopy(Blocks.SANDSTONE).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_ANDESITE = new BlockItemRegister("corrupted_andesite", key -> new CorruptedBlock(Properties.ofFullCopy(Blocks.ANDESITE).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_DIORITE = new BlockItemRegister("corrupted_diorite", key -> new CorruptedBlock(Properties.ofFullCopy(Blocks.DIORITE).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_GRANITE = new BlockItemRegister("corrupted_granite", key -> new CorruptedBlock(Properties.ofFullCopy(Blocks.GRANITE).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_STONE = new BlockItemRegister("corrupted_stone", key -> new CorruptedBlock(Properties.ofFullCopy(Blocks.STONE).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_DEEPSLATE = new BlockItemRegister("corrupted_deepslate", key -> new CorruptedRotatableBlock(Properties.ofFullCopy(Blocks.DEEPSLATE).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_COBBLESTONE = new BlockItemRegister("corrupted_cobblestone", key -> new CorruptedBlock(Properties.ofFullCopy(Blocks.COBBLESTONE).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_COBBLED_DEEPSLATE = new BlockItemRegister("corrupted_cobbled_deepslate", key -> new CorruptedBlock(Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_COAL_ORE = new BlockItemRegister("corrupted_coal_ore", key -> new CorruptedBlock(Properties.ofFullCopy(Blocks.COAL_ORE).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_IRON_ORE = new BlockItemRegister("corrupted_iron_ore", key -> new CorruptedBlock(Properties.ofFullCopy(Blocks.IRON_ORE).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_COPPER_ORE = new BlockItemRegister("corrupted_copper_ore", key -> new CorruptedBlock(Properties.ofFullCopy(Blocks.COPPER_ORE).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_GOLD_ORE = new BlockItemRegister("corrupted_gold_ore", key -> new CorruptedBlock(Properties.ofFullCopy(Blocks.GOLD_ORE).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_LAPIS_ORE = new BlockItemRegister("corrupted_lapis_ore", key -> new CorruptedBlock(Properties.ofFullCopy(Blocks.LAPIS_ORE).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_REDSTONE_ORE = new BlockItemRegister("corrupted_redstone_ore", key -> new CorruptedRedstoneOreBlock(Properties.ofFullCopy(Blocks.REDSTONE_ORE).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_DIAMOND_ORE = new BlockItemRegister("corrupted_diamond_ore", key -> new CorruptedBlock(Properties.ofFullCopy(Blocks.DIAMOND_ORE).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_EMERALD_ORE = new BlockItemRegister("corrupted_emerald_ore", key -> new CorruptedBlock(Properties.ofFullCopy(Blocks.EMERALD_ORE).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_TIN_ORE = new BlockItemRegister("corrupted_tin_ore", key -> new CorruptedBlock(Properties.ofFullCopy(TIN_ORE.BLOCK).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_LEAD_ORE = new BlockItemRegister("corrupted_lead_ore", key -> new CorruptedBlock(Properties.ofFullCopy(LEAD_ORE.BLOCK).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_SILVER_ORE = new BlockItemRegister("corrupted_silver_ore", key -> new CorruptedBlock(Properties.ofFullCopy(SILVER_ORE.BLOCK).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_TUNGSTEN_ORE = new BlockItemRegister("corrupted_tungsten_ore", key -> new CorruptedBlock(Properties.ofFullCopy(TUNGSTEN_ORE.BLOCK).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_PLATINUM_ORE = new BlockItemRegister("corrupted_platinum_ore", key -> new CorruptedBlock(Properties.ofFullCopy(PLATINUM_ORE.BLOCK).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_DEEPSLATE_COAL_ORE = new BlockItemRegister("corrupted_deepslate_coal_ore", key -> new CorruptedBlock(Properties.ofFullCopy(Blocks.DEEPSLATE_COAL_ORE).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_DEEPSLATE_IRON_ORE = new BlockItemRegister("corrupted_deepslate_iron_ore", key -> new CorruptedBlock(Properties.ofFullCopy(Blocks.DEEPSLATE_IRON_ORE).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_DEEPSLATE_COPPER_ORE = new BlockItemRegister("corrupted_deepslate_copper_ore", key -> new CorruptedBlock(Properties.ofFullCopy(Blocks.DEEPSLATE_COPPER_ORE).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_DEEPSLATE_GOLD_ORE = new BlockItemRegister("corrupted_deepslate_gold_ore", key -> new CorruptedBlock(Properties.ofFullCopy(Blocks.DEEPSLATE_GOLD_ORE).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_DEEPSLATE_LAPIS_ORE = new BlockItemRegister("corrupted_deepslate_lapis_ore", key -> new CorruptedBlock(Properties.ofFullCopy(Blocks.DEEPSLATE_LAPIS_ORE).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_DEEPSLATE_REDSTONE_ORE = new BlockItemRegister("corrupted_deepslate_redstone_ore", key -> new CorruptedRedstoneOreBlock(Properties.ofFullCopy(Blocks.DEEPSLATE_REDSTONE_ORE).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_DEEPSLATE_DIAMOND_ORE = new BlockItemRegister("corrupted_deepslate_diamond_ore", key -> new CorruptedBlock(Properties.ofFullCopy(Blocks.DEEPSLATE_DIAMOND_ORE).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_DEEPSLATE_EMERALD_ORE = new BlockItemRegister("corrupted_deepslate_emerald_ore", key -> new CorruptedBlock(Properties.ofFullCopy(Blocks.DEEPSLATE_EMERALD_ORE).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_DEEPSLATE_TIN_ORE = new BlockItemRegister("corrupted_deepslate_tin_ore", key -> new CorruptedBlock(Properties.ofFullCopy(DEEPSLATE_TIN_ORE.BLOCK).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_DEEPSLATE_LEAD_ORE = new BlockItemRegister("corrupted_deepslate_lead_ore", key -> new CorruptedBlock(Properties.ofFullCopy(DEEPSLATE_LEAD_ORE.BLOCK).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_DEEPSLATE_SILVER_ORE = new BlockItemRegister("corrupted_deepslate_silver_ore", key -> new CorruptedBlock(Properties.ofFullCopy(DEEPSLATE_SILVER_ORE.BLOCK).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_DEEPSLATE_TUNGSTEN_ORE = new BlockItemRegister("corrupted_deepslate_tungsten_ore", key -> new CorruptedBlock(Properties.ofFullCopy(DEEPSLATE_TUNGSTEN_ORE.BLOCK).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_DEEPSLATE_PLATINUM_ORE = new BlockItemRegister("corrupted_deepslate_platinum_ore", key -> new CorruptedBlock(Properties.ofFullCopy(DEEPSLATE_PLATINUM_ORE.BLOCK).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_SNOW_LAYER = new BlockItemRegister("corrupted_snow_layer", key -> new CorruptedSnowLayer(Properties.ofFullCopy(Blocks.SNOW).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_SNOW = new BlockItemRegister("corrupted_snow", key -> new CorruptedBlock(Properties.ofFullCopy(Blocks.SNOW_BLOCK).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_ICE = new BlockItemRegister("corrupted_ice", key -> new CorruptedBlock(Properties.ofFullCopy(Blocks.ICE).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_PACKED_ICE = new BlockItemRegister("corrupted_packed_ice", key -> new CorruptedBlock(Properties.ofFullCopy(Blocks.PACKED_ICE).setId(key).randomTicks()));
    public static final BlockItemRegister CORRUPTED_BLUE_ICE = new BlockItemRegister("corrupted_blue_ice", key -> new CorruptedBlock(Properties.ofFullCopy(Blocks.BLUE_ICE).setId(key).randomTicks()));

    // Crimson
    public static final BlockItemRegister CRIMSON_GRASS = new BlockItemRegister("crimson_grass", key -> new CrimsonGrass(Properties.ofFullCopy(Blocks.GRASS_BLOCK).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_GRAVEL = new BlockItemRegister("crimson_gravel", key -> new CrimsonFallingBlock(Properties.ofFullCopy(Blocks.GRAVEL).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_SAND = new BlockItemRegister("crimson_sand", key -> new CrimsonFallingBlock(Properties.ofFullCopy(Blocks.SAND).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_GLASS = new BlockItemRegister("crimson_glass", key -> new CrimsonTransparentBlock(Properties.ofFullCopy(Blocks.GLASS).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_SANDSTONE = new BlockItemRegister("crimson_sandstone", key -> new CrimsonBlock(Properties.ofFullCopy(Blocks.SANDSTONE).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_ANDESITE = new BlockItemRegister("crimson_andesite", key -> new CrimsonBlock(Properties.ofFullCopy(Blocks.ANDESITE).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_DIORITE = new BlockItemRegister("crimson_diorite", key -> new CrimsonBlock(Properties.ofFullCopy(Blocks.DIORITE).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_GRANITE = new BlockItemRegister("crimson_granite", key -> new CrimsonBlock(Properties.ofFullCopy(Blocks.GRANITE).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_STONE = new BlockItemRegister("crimson_stone", key -> new CrimsonBlock(Properties.ofFullCopy(Blocks.STONE).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_DEEPSLATE = new BlockItemRegister("crimson_deepslate", key -> new CrimsonRotatableBlock(Properties.ofFullCopy(Blocks.DEEPSLATE).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_COBBLESTONE = new BlockItemRegister("crimson_cobblestone", key -> new CrimsonBlock(Properties.ofFullCopy(Blocks.COBBLESTONE).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_COBBLED_DEEPSLATE = new BlockItemRegister("crimson_cobbled_deepslate", key -> new CrimsonBlock(Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_COAL_ORE = new BlockItemRegister("crimson_coal_ore", key -> new CrimsonBlock(Properties.ofFullCopy(Blocks.COAL_ORE).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_IRON_ORE = new BlockItemRegister("crimson_iron_ore", key -> new CrimsonBlock(Properties.ofFullCopy(Blocks.IRON_ORE).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_COPPER_ORE = new BlockItemRegister("crimson_copper_ore", key -> new CrimsonBlock(Properties.ofFullCopy(Blocks.COPPER_ORE).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_GOLD_ORE = new BlockItemRegister("crimson_gold_ore", key -> new CrimsonBlock(Properties.ofFullCopy(Blocks.GOLD_ORE).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_LAPIS_ORE = new BlockItemRegister("crimson_lapis_ore", key -> new CrimsonBlock(Properties.ofFullCopy(Blocks.LAPIS_ORE).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_REDSTONE_ORE = new BlockItemRegister("crimson_redstone_ore", key -> new CrimsonRedstoneOreBlock(Properties.ofFullCopy(Blocks.REDSTONE_ORE).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_DIAMOND_ORE = new BlockItemRegister("crimson_diamond_ore", key -> new CrimsonBlock(Properties.ofFullCopy(Blocks.DIAMOND_ORE).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_EMERALD_ORE = new BlockItemRegister("crimson_emerald_ore", key -> new CrimsonBlock(Properties.ofFullCopy(Blocks.EMERALD_ORE).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_TIN_ORE = new BlockItemRegister("crimson_tin_ore", key -> new CrimsonBlock(Properties.ofFullCopy(TIN_ORE.BLOCK).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_LEAD_ORE = new BlockItemRegister("crimson_lead_ore", key -> new CrimsonBlock(Properties.ofFullCopy(LEAD_ORE.BLOCK).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_SILVER_ORE = new BlockItemRegister("crimson_silver_ore", key -> new CrimsonBlock(Properties.ofFullCopy(SILVER_ORE.BLOCK).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_TUNGSTEN_ORE = new BlockItemRegister("crimson_tungsten_ore", key -> new CrimsonBlock(Properties.ofFullCopy(TUNGSTEN_ORE.BLOCK).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_PLATINUM_ORE = new BlockItemRegister("crimson_platinum_ore", key -> new CrimsonBlock(Properties.ofFullCopy(PLATINUM_ORE.BLOCK).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_DEEPSLATE_COAL_ORE = new BlockItemRegister("crimson_deepslate_coal_ore", key -> new CrimsonBlock(Properties.ofFullCopy(Blocks.DEEPSLATE_COAL_ORE).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_DEEPSLATE_IRON_ORE = new BlockItemRegister("crimson_deepslate_iron_ore", key -> new CrimsonBlock(Properties.ofFullCopy(Blocks.DEEPSLATE_IRON_ORE).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_DEEPSLATE_COPPER_ORE = new BlockItemRegister("crimson_deepslate_copper_ore", key -> new CrimsonBlock(Properties.ofFullCopy(Blocks.DEEPSLATE_COPPER_ORE).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_DEEPSLATE_GOLD_ORE = new BlockItemRegister("crimson_deepslate_gold_ore", key -> new CrimsonBlock(Properties.ofFullCopy(Blocks.DEEPSLATE_GOLD_ORE).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_DEEPSLATE_LAPIS_ORE = new BlockItemRegister("crimson_deepslate_lapis_ore", key -> new CrimsonBlock(Properties.ofFullCopy(Blocks.DEEPSLATE_LAPIS_ORE).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_DEEPSLATE_REDSTONE_ORE = new BlockItemRegister("crimson_deepslate_redstone_ore", key -> new CrimsonRedstoneOreBlock(Properties.ofFullCopy(Blocks.DEEPSLATE_REDSTONE_ORE).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_DEEPSLATE_DIAMOND_ORE = new BlockItemRegister("crimson_deepslate_diamond_ore", key -> new CrimsonBlock(Properties.ofFullCopy(Blocks.DEEPSLATE_DIAMOND_ORE).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_DEEPSLATE_EMERALD_ORE = new BlockItemRegister("crimson_deepslate_emerald_ore", key -> new CrimsonBlock(Properties.ofFullCopy(Blocks.DEEPSLATE_EMERALD_ORE).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_DEEPSLATE_TIN_ORE = new BlockItemRegister("crimson_deepslate_tin_ore", key -> new CrimsonBlock(Properties.ofFullCopy(DEEPSLATE_TIN_ORE.BLOCK).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_DEEPSLATE_LEAD_ORE = new BlockItemRegister("crimson_deepslate_lead_ore", key -> new CrimsonBlock(Properties.ofFullCopy(DEEPSLATE_LEAD_ORE.BLOCK).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_DEEPSLATE_SILVER_ORE = new BlockItemRegister("crimson_deepslate_silver_ore", key -> new CrimsonBlock(Properties.ofFullCopy(DEEPSLATE_SILVER_ORE.BLOCK).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_DEEPSLATE_TUNGSTEN_ORE = new BlockItemRegister("crimson_deepslate_tungsten_ore", key -> new CrimsonBlock(Properties.ofFullCopy(DEEPSLATE_TUNGSTEN_ORE.BLOCK).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_DEEPSLATE_PLATINUM_ORE = new BlockItemRegister("crimson_deepslate_platinum_ore", key -> new CrimsonBlock(Properties.ofFullCopy(DEEPSLATE_PLATINUM_ORE.BLOCK).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_SNOW_LAYER = new BlockItemRegister("crimson_snow_layer", key -> new CrimsonSnowLayer(Properties.ofFullCopy(Blocks.SNOW).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_SNOW = new BlockItemRegister("crimson_snow", key -> new CrimsonBlock(Properties.ofFullCopy(Blocks.SNOW_BLOCK).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_ICE = new BlockItemRegister("crimson_ice", key -> new CrimsonBlock(Properties.ofFullCopy(Blocks.ICE).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_PACKED_ICE = new BlockItemRegister("crimson_packed_ice", key -> new CrimsonBlock(Properties.ofFullCopy(Blocks.PACKED_ICE).setId(key).randomTicks()));
    public static final BlockItemRegister CRIMSON_BLUE_ICE = new BlockItemRegister("crimson_blue_ice", key -> new CrimsonBlock(Properties.ofFullCopy(Blocks.BLUE_ICE).setId(key).randomTicks()));

    private static boolean always(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return true;
    }
}
