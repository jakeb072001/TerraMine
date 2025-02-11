package terramine.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Unique;
import terramine.common.init.ModBlocks;
import terramine.common.init.ModItems;
import terramine.common.utility.ArmorItemRegister;
import terramine.common.utility.BasicToolSetRegister;
import terramine.common.utility.BlockItemRegister;

import java.util.concurrent.CompletableFuture;

public class ModRecipes extends FabricRecipeProvider {

    public ModRecipes(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
        return new RecipeProvider(provider, recipeOutput) {
            @Override
            public void buildRecipes() {
                ///////////////////////////////////// Shapeless Recipes /////////////////////////////////////

                // Accessories
                createShapeless(RecipeCategory.MISC, ModItems.AVENGER_EMBLEM, ModItems.RANGER_EMBLEM, ModItems.WARRIOR_EMBLEM, ModItems.SORCERER_EMBLEM);
                createShapeless(RecipeCategory.MISC, ModItems.BLUE_HORSESHOE_BALLOON, ModItems.CLOUD_IN_A_BALLOON, ModItems.LUCKY_HORSESHOE);
                // todo: replace with other balloons once made
                createShapeless(RecipeCategory.MISC, ModItems.BUNDLE_OF_BALLOONS, ModItems.CLOUD_IN_A_BALLOON, ModItems.CLOUD_IN_A_BALLOON, ModItems.CLOUD_IN_A_BALLOON);
                createShapeless(RecipeCategory.MISC, ModItems.CELESTIAL_SHELL, ModItems.CELESTIAL_STONE, ModItems.MOON_SHELL);
                createShapeless(RecipeCategory.MISC, ModItems.CELESTIAL_STONE, ModItems.SUN_STONE, ModItems.MOON_STONE);
                createShapeless(RecipeCategory.MISC, ModItems.CELL_PHONE, ModItems.PDA, ModItems.MAGIC_MIRROR);
                createShapeless(RecipeCategory.MISC, ModItems.CHARM_OF_MYTHS, ModItems.BAND_OF_REGENERATION, ModItems.PHILOSOPHERS_STONE);
                createShapeless(RecipeCategory.MISC, ModItems.CLOUD_IN_A_BALLOON, ModItems.SHINY_RED_BALLOON, ModItems.CLOUD_IN_A_BOTTLE);
                createShapeless(RecipeCategory.MISC, ModItems.DIVING_GEAR, ModItems.DIVING_HELMET, ModItems.FLIPPERS);
                createShapeless(RecipeCategory.MISC, ModItems.FAIRY_BOOTS, ModItems.FLOWER_BOOTS, ModItems.SPECTRE_BOOTS);
                createShapeless(RecipeCategory.MISC, ModItems.FIRE_GAUNTLET, ModItems.MECHANICAL_GLOVE, ModItems.MAGMA_STONE);
                createShapeless(RecipeCategory.MISC, ModItems.FISH_FINDER, ModItems.WEATHER_RADIO, ModItems.SEXTANT);
                createShapeless(RecipeCategory.MISC, ModItems.FROSTSPARK_BOOTS, ModItems.LIGHTNING_BOOTS, ModItems.ICE_SKATES);
                createShapeless(RecipeCategory.MISC, ModItems.GOBLIN_TECH, ModItems.METAL_DETECTOR, ModItems.STOPWATCH, ModItems.DPS_METER);
                createShapeless(RecipeCategory.MISC, ModItems.GOLD_WATCH, Items.CLOCK);
                createShapeless(RecipeCategory.MISC, Items.CLOCK, 1, 1, "clock_from_watch", ModItems.GOLD_WATCH);
                createShapeless(RecipeCategory.MISC, ModItems.GPS, ModItems.GOLD_WATCH, ModItems.DEPTH_METER, ModItems.COMPASS);
                createShapeless(RecipeCategory.MISC, ModItems.LAVA_WADERS, 1, 1, Ingredient.of(ModItems.OBSIDIAN_ROSE), Ingredient.of(ModItems.WATER_WALKING_BOOTS, ModItems.OBSIDIAN_WATER_WALKING_BOOTS), Ingredient.of(ModItems.MOLTEN_CHARM, ModItems.LAVA_CHARM));
                createShapeless(RecipeCategory.MISC, ModItems.LIGHTNING_BOOTS, ModItems.SPECTRE_BOOTS, ModItems.ANKLET, ModItems.AGLET);
                createShapeless(RecipeCategory.MISC, ModItems.MAGIC_CUFFS, ModItems.MANA_REGENERATION_BAND, ModItems.SHACKLE);
                createShapeless(RecipeCategory.MISC, ModItems.MAGMA_SKULL, ModItems.OBSIDIAN_SKULL, ModItems.MAGMA_STONE);
                createShapeless(RecipeCategory.MISC, ModItems.MANA_CRYSTAL, 1, 5, ModItems.FALLEN_STAR);
                createShapeless(RecipeCategory.MISC, ModItems.MANA_REGENERATION_BAND, ModItems.BAND_OF_REGENERATION, ModItems.BAND_OF_STARPOWER);
                createShapeless(RecipeCategory.MISC, ModItems.MASTER_NINJA_GEAR, ModItems.TIGER_CLIMBING_GEAR, ModItems.TABI, ModItems.BLACK_BELT);
                createShapeless(RecipeCategory.MISC, ModItems.MECHANICAL_GLOVE, ModItems.POWER_GLOVE, ModItems.AVENGER_EMBLEM);
                createShapeless(RecipeCategory.MISC, ModItems.MOLTEN_CHARM, ModItems.OBSIDIAN_SKULL, ModItems.LAVA_CHARM);
                createShapeless(RecipeCategory.MISC, ModItems.MOLTEN_SKULL_ROSE, 1, 1, Ingredient.of(ModItems.OBSIDIAN_SKULL, ModItems.OBSIDIAN_SKULL_ROSE), Ingredient.of(ModItems.MAGMA_SKULL, ModItems.MAGMA_STONE));
                createShapeless(RecipeCategory.MISC, ModItems.MOON_SHELL, ModItems.MOON_CHARM, ModItems.NEPTUNE_SHELL);
                createShapeless(RecipeCategory.MISC, ModItems.OBSIDIAN_HORSESHOE, ModItems.OBSIDIAN_SKULL, ModItems.LUCKY_HORSESHOE);
                createShapeless(RecipeCategory.MISC, ModItems.OBSIDIAN_SHIELD, ModItems.OBSIDIAN_SKULL, ModItems.COBALT_SHIELD);
                createShapeless(RecipeCategory.MISC, ModItems.OBSIDIAN_SKULL_ROSE, ModItems.OBSIDIAN_SKULL, ModItems.OBSIDIAN_ROSE);
                createShapeless(RecipeCategory.MISC, ModItems.OBSIDIAN_WATER_WALKING_BOOTS, ModItems.OBSIDIAN_SKULL, ModItems.WATER_WALKING_BOOTS);
                createShapeless(RecipeCategory.MISC, ModItems.PDA, ModItems.GPS, ModItems.FISH_FINDER, ModItems.GOBLIN_TECH);
                createShapeless(RecipeCategory.MISC, ModItems.POWER_GLOVE, ModItems.TITAN_GLOVE, ModItems.FERAL_CLAWS);
                createShapeless(RecipeCategory.MISC, ModItems.SPECTRE_BOOTS, ModItems.ROCKET_BOOTS, ModItems.HERMES_BOOTS);
                createShapeless(RecipeCategory.MISC, ModItems.TERRASPARK_BOOTS, ModItems.FROSTSPARK_BOOTS, ModItems.LAVA_WADERS);
                createShapeless(RecipeCategory.MISC, ModItems.TIGER_CLIMBING_GEAR, ModItems.SHOE_SPIKES, ModItems.CLIMBING_CLAWS);

                // Crafting Items
                createShapeless(RecipeCategory.MISC, ModItems.RAW_HELLSTONE_HARDENED, ModItems.RAW_HELLSTONE, Items.OBSIDIAN);

                // Throwables
                createShapeless(RecipeCategory.MISC, ModItems.STICKY_GRENADE, 1, 1, ModItems.GRENADE, ModTags.SLIME_BALLS);
                createShapeless(RecipeCategory.MISC, ModItems.STICKY_BOMB, 1, 1, ModItems.BOMB, ModTags.SLIME_BALLS);
                createShapeless(RecipeCategory.MISC, ModItems.STICKY_DYNAMITE, 1, 1, ModItems.DYNAMITE, ModTags.SLIME_BALLS);
                createShapeless(RecipeCategory.MISC, ModItems.BOUNCY_GRENADE, 1, 1, ModItems.STICKY_GRENADE, ModTags.SLIME_BALLS);
                createShapeless(RecipeCategory.MISC, ModItems.BOUNCY_BOMB, 1, 1, ModItems.STICKY_BOMB, ModTags.SLIME_BALLS);
                createShapeless(RecipeCategory.MISC, ModItems.BOUNCY_DYNAMITE, 1, 1, ModItems.STICKY_DYNAMITE, ModTags.SLIME_BALLS);

                ///////////////////////////////////// Shaped Recipes /////////////////////////////////////

                // Accessories
                createShaped(RecipeCategory.MISC, ModItems.OBSIDIAN_SKULL, new String[]{
                        "XXX",
                        "XXX",
                        "XXX"
                }, 'X', Items.OBSIDIAN);

                // Arrows
                createShaped(RecipeCategory.MISC, ModItems.FLAMING_ARROW, 9, new String[]{
                        "XXX",
                        "XCX",
                        "XXX"
                }, 'X', Items.ARROW, 'C', Items.TORCH);
                createShaped(RecipeCategory.MISC, ModItems.JESTER_ARROW, 9, new String[]{
                        "XXX",
                        "XCX",
                        "XXX"
                }, 'X', Items.ARROW, 'C', ModItems.FALLEN_STAR);
                createShaped(RecipeCategory.MISC, ModItems.UNHOLY_ARROW, 4, new String[]{
                        " X ",
                        "XCX",
                        " X "
                }, 'X', Items.ARROW, 'C', Ingredient.of(ModItems.WORM_TOOTH, ModItems.VERTEBRA));

                // Blocks
                createBlock(ModItems.TIN_INGOT, ModBlocks.TIN_BLOCK);
                createBlock(ModItems.LEAD_INGOT, ModBlocks.LEAD_BLOCK);
                createBlock(ModItems.SILVER_INGOT, ModBlocks.SILVER_BLOCK);
                createBlock(ModItems.TUNGSTEN_INGOT, ModBlocks.TUNGSTEN_BLOCK);
                createBlock(ModItems.PLATINUM_INGOT, ModBlocks.PLATINUM_BLOCK);
                createBlock(ModItems.DEMONITE_INGOT, ModBlocks.DEMONITE_BLOCK);
                createBlock(ModItems.CRIMTANE_INGOT, ModBlocks.CRIMTANE_BLOCK);
                createBlock(ModItems.METEORITE_INGOT, ModBlocks.METEORITE_BLOCK);
                createBlock(ModItems.HELLSTONE_INGOT, ModBlocks.HELLSTONE_BLOCK);
                createBlock(ModItems.RAW_DEMONITE, ModBlocks.RAW_DEMONITE_BLOCK);
                createBlock(ModItems.RAW_CRIMTANE, ModBlocks.RAW_CRIMTANE_BLOCK);
                createBlock(ModItems.RAW_METEORITE, ModBlocks.RAW_METEORITE_BLOCK);
                createBlock(ModItems.RAW_HELLSTONE, ModBlocks.RAW_HELLSTONE_BLOCK);
                createShaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUNPLATE_BLOCK.getItem(), 9, new String[]{
                        "XXX",
                        "XYX",
                        "XXX"
                }, 'X', Items.STONE, 'Y', ModItems.FALLEN_STAR);
                createShaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TINKERER_TABLE.getItem(), new String[]{
                        "A",
                        "B",
                        "C"
                }, 'A', ModTags.ACCESSORY, 'B', Items.BOOK, 'C', Items.CRAFTING_TABLE);

                // Armor Recipes
                createArmor(ModTags.COPPER, ModItems.COPPER_ARMOR);
                createArmor(ModTags.TIN, ModItems.TIN_ARMOR);
                createArmor(ModTags.LEAD, ModItems.LEAD_ARMOR);
                createArmor(ModTags.SILVER, ModItems.SILVER_ARMOR);
                createArmor(ModTags.TUNGSTEN, ModItems.TUNGSTEN_ARMOR);
                createArmor(ModTags.PLATINUM, ModItems.PLATINUM_ARMOR);
                createArmor(ModItems.DEMONITE_INGOT, ModItems.SHADOW_ARMOR);
                createArmor(ModItems.CRIMTANE_INGOT, ModItems.CRIMSON_ARMOR);
                createArmor(ModItems.METEORITE_INGOT, ModItems.METEOR_ARMOR);
                createArmor(ModItems.HELLSTONE_INGOT, ModItems.MOLTEN_ARMOR);

                // Tools
                createTools(ModTags.COPPER, ModItems.COPPER_TOOLS);
                createTools(ModTags.TIN, ModItems.TIN_TOOLS);
                createTools(ModTags.LEAD, ModItems.LEAD_TOOLS);
                createTools(ModTags.SILVER, ModItems.SILVER_TOOLS);
                createTools(ModTags.TUNGSTEN, ModItems.TUNGSTEN_TOOLS);
                createTools(ModTags.PLATINUM, ModItems.PLATINUM_TOOLS);
                createTools(ModItems.DEMONITE_INGOT, ModItems.DEMONITE_TOOLS);
                createTools(ModItems.CRIMTANE_INGOT, ModItems.CRIMTANE_TOOLS);
                createShaped(RecipeCategory.TOOLS, ModItems.MOLTEN_PICKAXE,
                        new String[]{
                                "XXX",
                                " S ",
                                " S "
                        },
                        'X', ModItems.HELLSTONE_INGOT, 'S', Items.STICK
                );
                createShaped(RecipeCategory.TOOLS, ModItems.METEOR_SHAXE, 1, new String[]{
                        "XX ",
                        "XSX",
                        " S "
                }, 'X', ModItems.METEORITE_INGOT, 'S', Items.STICK);
                createShaped(RecipeCategory.TOOLS, ModItems.MOLTEN_SHAXE, 1, new String[]{
                        "XX ",
                        "XSX",
                        " S "
                }, 'X', ModItems.HELLSTONE_INGOT, 'S', Items.STICK);
                createShaped(RecipeCategory.TOOLS, ModItems.MAGIC_MIRROR, new String[]{
                        "IDI",
                        "DGD",
                        "IDI"
                }, 'D', Items.DIAMOND, 'I', Ingredient.of(Items.GOLD_INGOT, ModItems.PLATINUM_INGOT), 'G', Items.GLASS);

                // Weapons
                createShaped(RecipeCategory.COMBAT, ModItems.PHASEBLADE_WHITE, new String[]{
                        "X",
                        "X",
                        "S"
                }, 'X', Items.EMERALD, 'S', ModItems.METEORITE_INGOT);
                createShaped(RecipeCategory.COMBAT, ModItems.PHASEBLADE_GREEN, new String[]{
                        "X",
                        "X",
                        "S"
                }, 'X', Items.DIAMOND, 'S', ModItems.METEORITE_INGOT);
                createShaped(RecipeCategory.COMBAT, ModItems.VOLCANO_SWORD, new String[]{
                        "X",
                        "X",
                        "S"
                }, 'X', ModItems.HELLSTONE_INGOT, 'S', Items.BLAZE_ROD);

                // Ranged
                createShaped(RecipeCategory.COMBAT, ModItems.SPACE_GUN, 1, new String[]{
                        "X  ",
                        "XXX",
                        " X "
                }, 'X', ModItems.METEORITE_INGOT);


                ///////////////////////////////////// Smelting/Cooking /////////////////////////////////////

                // Terraria Ingots
                createSmeltingBlasting(ModItems.TIN_INGOT, ModItems.RAW_TIN, ModBlocks.TIN_ORE.getItem(), ModBlocks.DEEPSLATE_TIN_ORE.getItem(), ModBlocks.CORRUPTED_TIN_ORE.getItem(), ModBlocks.CORRUPTED_DEEPSLATE_TIN_ORE.getItem(), ModBlocks.CRIMSON_TIN_ORE.getItem(), ModBlocks.CRIMSON_DEEPSLATE_TIN_ORE.getItem());
                createSmeltingBlasting(ModBlocks.TIN_BLOCK.getItem(), 6F, 900, ModBlocks.RAW_TIN_BLOCK.getItem());
                createSmeltingBlasting(ModItems.LEAD_INGOT, ModItems.RAW_LEAD, ModBlocks.LEAD_ORE.getItem(), ModBlocks.DEEPSLATE_LEAD_ORE.getItem(), ModBlocks.CORRUPTED_LEAD_ORE.getItem(), ModBlocks.CORRUPTED_DEEPSLATE_LEAD_ORE.getItem(), ModBlocks.CRIMSON_LEAD_ORE.getItem(), ModBlocks.CRIMSON_DEEPSLATE_LEAD_ORE.getItem());
                createSmeltingBlasting(ModBlocks.LEAD_BLOCK.getItem(), 6F, 900, ModBlocks.RAW_LEAD_BLOCK.getItem());
                createSmeltingBlasting(ModItems.SILVER_INGOT, ModItems.RAW_SILVER, ModBlocks.SILVER_ORE.getItem(), ModBlocks.DEEPSLATE_SILVER_ORE.getItem(), ModBlocks.CORRUPTED_SILVER_ORE.getItem(), ModBlocks.CORRUPTED_DEEPSLATE_SILVER_ORE.getItem(), ModBlocks.CRIMSON_SILVER_ORE.getItem(), ModBlocks.CRIMSON_DEEPSLATE_SILVER_ORE.getItem());
                createSmeltingBlasting(ModBlocks.SILVER_BLOCK.getItem(), 6F, 900, ModBlocks.RAW_SILVER_BLOCK.getItem());
                createSmeltingBlasting(ModItems.TUNGSTEN_INGOT, ModItems.RAW_TUNGSTEN, ModBlocks.TUNGSTEN_ORE.getItem(), ModBlocks.DEEPSLATE_TUNGSTEN_ORE.getItem(), ModBlocks.CORRUPTED_TUNGSTEN_ORE.getItem(), ModBlocks.CORRUPTED_DEEPSLATE_TUNGSTEN_ORE.getItem(), ModBlocks.CRIMSON_TUNGSTEN_ORE.getItem(), ModBlocks.CRIMSON_DEEPSLATE_TUNGSTEN_ORE.getItem());
                createSmeltingBlasting(ModBlocks.TUNGSTEN_BLOCK.getItem(), 6F, 900, ModBlocks.RAW_TUNGSTEN_BLOCK.getItem());
                createSmeltingBlasting(ModItems.PLATINUM_INGOT, ModItems.RAW_PLATINUM, ModBlocks.PLATINUM_ORE.getItem(), ModBlocks.DEEPSLATE_PLATINUM_ORE.getItem(), ModBlocks.CORRUPTED_PLATINUM_ORE.getItem(), ModBlocks.CORRUPTED_DEEPSLATE_PLATINUM_ORE.getItem(), ModBlocks.CRIMSON_PLATINUM_ORE.getItem(), ModBlocks.CRIMSON_DEEPSLATE_PLATINUM_ORE.getItem());
                createSmeltingBlasting(ModBlocks.PLATINUM_BLOCK.getItem(), 6F, 900, ModBlocks.RAW_PLATINUM_BLOCK.getItem());
                createSmeltingBlasting(ModItems.DEMONITE_INGOT, ModItems.RAW_DEMONITE, ModBlocks.DEMONITE_ORE.getItem(), ModBlocks.DEEPSLATE_DEMONITE_ORE.getItem());
                createSmeltingBlasting(ModBlocks.DEMONITE_BLOCK.getItem(), 6F, 900, ModBlocks.RAW_DEMONITE_BLOCK.getItem());
                createSmeltingBlasting(ModItems.CRIMTANE_INGOT, ModItems.RAW_CRIMTANE, ModBlocks.CRIMTANE_ORE.getItem(), ModBlocks.DEEPSLATE_CRIMTANE_ORE.getItem());
                createSmeltingBlasting(ModBlocks.CRIMTANE_BLOCK.getItem(), 6F, 900, ModBlocks.RAW_CRIMTANE_BLOCK.getItem());
                createSmeltingBlasting(ModItems.METEORITE_INGOT, ModItems.RAW_METEORITE, ModBlocks.METEORITE_ORE.getItem());
                createSmeltingBlasting(ModBlocks.METEORITE_BLOCK.getItem(), 6F, 900, ModBlocks.RAW_METEORITE_BLOCK.getItem());
                createSmeltingBlasting(ModItems.HELLSTONE_INGOT, ModItems.RAW_HELLSTONE_HARDENED);
                createSmeltingBlasting(ModItems.RAW_HELLSTONE, ModBlocks.HELLSTONE_ORE.getItem());

                // Corruption and Crimson
                createSmeltingBlasting(Items.COPPER_INGOT, ModBlocks.CORRUPTED_COPPER_ORE.getItem(), ModBlocks.CORRUPTED_DEEPSLATE_COPPER_ORE.getItem(), ModBlocks.CRIMSON_COPPER_ORE.getItem(), ModBlocks.CRIMSON_DEEPSLATE_COPPER_ORE.getItem());
                createSmeltingBlasting(Items.IRON_INGOT, ModBlocks.CORRUPTED_IRON_ORE.getItem(), ModBlocks.CORRUPTED_DEEPSLATE_IRON_ORE.getItem(), ModBlocks.CRIMSON_IRON_ORE.getItem(), ModBlocks.CRIMSON_DEEPSLATE_IRON_ORE.getItem());
                createSmeltingBlasting(Items.GOLD_INGOT, ModBlocks.CORRUPTED_GOLD_ORE.getItem(), ModBlocks.CORRUPTED_DEEPSLATE_GOLD_ORE.getItem(), ModBlocks.CRIMSON_GOLD_ORE.getItem(), ModBlocks.CRIMSON_DEEPSLATE_GOLD_ORE.getItem());
                createSmeltingBlasting(Items.EMERALD, ModBlocks.CORRUPTED_EMERALD_ORE.getItem(), ModBlocks.CORRUPTED_DEEPSLATE_EMERALD_ORE.getItem(), ModBlocks.CRIMSON_EMERALD_ORE.getItem(), ModBlocks.CRIMSON_DEEPSLATE_EMERALD_ORE.getItem());
                createSmeltingBlasting(Items.DIAMOND, ModBlocks.CORRUPTED_DIAMOND_ORE.getItem(), ModBlocks.CORRUPTED_DEEPSLATE_DIAMOND_ORE.getItem(), ModBlocks.CRIMSON_DIAMOND_ORE.getItem(), ModBlocks.CRIMSON_DEEPSLATE_DIAMOND_ORE.getItem());
                createSmeltingBlasting(Items.REDSTONE, ModBlocks.CORRUPTED_REDSTONE_ORE.getItem(), ModBlocks.CORRUPTED_DEEPSLATE_REDSTONE_ORE.getItem(), ModBlocks.CRIMSON_REDSTONE_ORE.getItem(), ModBlocks.CRIMSON_DEEPSLATE_REDSTONE_ORE.getItem());
                createSmeltingBlasting(Items.LAPIS_LAZULI, ModBlocks.CORRUPTED_LAPIS_ORE.getItem(), ModBlocks.CORRUPTED_DEEPSLATE_LAPIS_ORE.getItem(), ModBlocks.CRIMSON_LAPIS_ORE.getItem(), ModBlocks.CRIMSON_DEEPSLATE_LAPIS_ORE.getItem());
                createSmeltingBlasting(ModBlocks.CORRUPTED_STONE.getItem(), 0.1F, ModBlocks.CORRUPTED_COBBLESTONE.getItem());
                createSmeltingBlasting(ModBlocks.CORRUPTED_DEEPSLATE.getItem(), 0.1F, ModBlocks.CORRUPTED_COBBLED_DEEPSLATE.getItem());
                createSmeltingBlasting(ModBlocks.CORRUPTED_GLASS.getItem(), 0.1F, ModBlocks.CORRUPTED_SAND.getItem());
                createSmeltingBlasting(ModBlocks.CRIMSON_STONE.getItem(), 0.1F, ModBlocks.CRIMSON_COBBLESTONE.getItem());
                createSmeltingBlasting(ModBlocks.CRIMSON_DEEPSLATE.getItem(), 0.1F, ModBlocks.CRIMSON_COBBLED_DEEPSLATE.getItem());
                createSmeltingBlasting(ModBlocks.CRIMSON_GLASS.getItem(), 0.1F, ModBlocks.CRIMSON_SAND.getItem());

                ///////////////////////////////////// Vanilla Recipes /////////////////////////////////////

                // Copper to Tin
                createShaped(RecipeCategory.TOOLS, Items.BRUSH, new String[]{
                        "F",
                        "X",
                        "S"
                }, 'X', ModItems.TIN_INGOT, 'F', Items.FEATHER, 'S', Items.STICK);
                createShaped(RecipeCategory.TOOLS, Items.SPYGLASS, new String[]{
                        "C",
                        "X",
                        "X"
                }, 'X', ModItems.TIN_INGOT, 'C', Items.AMETHYST_SHARD);
                createShaped(RecipeCategory.TOOLS, Items.LIGHTNING_ROD, new String[]{
                        "X",
                        "X",
                        "X"
                }, 'X', ModItems.TIN_INGOT);
                createShaped(RecipeCategory.BUILDING_BLOCKS, Items.COPPER_DOOR, new String[]{
                        "XX",
                        "XX",
                        "XX"
                }, 'X', ModItems.TIN_INGOT);
                createShaped(RecipeCategory.BUILDING_BLOCKS, Items.COPPER_TRAPDOOR, new String[]{
                        "XX",
                        "XX"
                }, 'X', ModItems.TIN_INGOT);

                // Iron to Lead
                createShapeless(RecipeCategory.MISC, ModItems.LEAD_NUGGET, ModItems.LEAD_INGOT);
                createShaped(RecipeCategory.DECORATIONS, Items.SMITHING_TABLE, new String[]{
                        "XX",
                        "SS",
                        "SS"
                }, 'X', ModItems.LEAD_INGOT, 'S', ItemTags.PLANKS);
                createShaped(RecipeCategory.DECORATIONS, Items.STONECUTTER, new String[]{
                        " X ",
                        "SSS"
                }, 'X', ModItems.LEAD_INGOT, 'S', Items.STONE);
                createShaped(RecipeCategory.DECORATIONS, Items.ANVIL, new String[]{
                        "BBB",
                        " X ",
                        "XXX"
                }, 'X', ModItems.LEAD_INGOT, 'B', ModBlocks.LEAD_BLOCK.getItem());
                createShaped(RecipeCategory.DECORATIONS, Items.BLAST_FURNACE, new String[]{
                        "XXX",
                        "XFX",
                        "SSS"
                }, 'X', ModItems.LEAD_INGOT, 'S', Items.SMOOTH_STONE, 'F', Items.FURNACE);
                createShaped(RecipeCategory.BUILDING_BLOCKS, Items.IRON_BARS, new String[]{
                        "XXX",
                        "XXX"
                }, 'X', ModItems.LEAD_INGOT);
                createShaped(RecipeCategory.BUILDING_BLOCKS, Items.IRON_DOOR, new String[]{
                        "XX",
                        "XX",
                        "XX"
                }, 'X', ModItems.LEAD_INGOT);
                createShaped(RecipeCategory.BUILDING_BLOCKS, Items.IRON_TRAPDOOR, new String[]{
                        "XX",
                        "XX"
                }, 'X', ModItems.LEAD_INGOT);
                createShaped(RecipeCategory.BUILDING_BLOCKS, Items.CHAIN, new String[]{
                        "N",
                        "X",
                        "N"
                }, 'X', ModItems.LEAD_INGOT, 'N', ModItems.LEAD_NUGGET);
                createShaped(RecipeCategory.BUILDING_BLOCKS, Items.LANTERN, new String[]{
                        "XXX",
                        "XTX",
                        "XXX"
                }, 'X', ModItems.LEAD_NUGGET, 'T', Items.TORCH);
                createShaped(RecipeCategory.BUILDING_BLOCKS, Items.SOUL_LANTERN, new String[]{
                        "XXX",
                        "XTX",
                        "XXX"
                }, 'X', ModItems.LEAD_NUGGET, 'T', Items.SOUL_TORCH);
                createShaped(RecipeCategory.BREWING, Items.CAULDRON, new String[]{
                        "X X",
                        "X X",
                        "XXX"
                }, 'X', ModItems.LEAD_INGOT);
                createShaped(RecipeCategory.REDSTONE, Items.HOPPER, new String[]{
                        "X X",
                        "XSX",
                        " X "
                }, 'X', ModItems.LEAD_INGOT, 'S', Items.CHEST);
                createShaped(RecipeCategory.REDSTONE, Items.PISTON, new String[]{
                        "PPP",
                        "CXC",
                        "CRC"
                }, 'X', ModItems.LEAD_INGOT, 'P', ItemTags.PLANKS, 'C', Items.COBBLESTONE, 'R', Items.REDSTONE);
                createShaped(RecipeCategory.REDSTONE, Items.TRIPWIRE_HOOK, new String[]{
                        "X",
                        "S",
                        "P"
                }, 'X', ModItems.LEAD_INGOT, 'S', Items.STICK, 'P', ItemTags.PLANKS);
                createShaped(RecipeCategory.REDSTONE, Items.HEAVY_WEIGHTED_PRESSURE_PLATE, new String[]{
                        "XX"
                }, 'X', ModItems.LEAD_INGOT);
                createShaped(RecipeCategory.REDSTONE, Items.CRAFTER, new String[]{
                        "XXX",
                        "XCX",
                        "RDR"
                }, 'X', ModItems.LEAD_INGOT, 'C', Items.CRAFTING_TABLE, 'R', Items.REDSTONE, 'D', Items.DROPPER);
                createShaped(RecipeCategory.TRANSPORTATION, Items.RAIL, 16, new String[]{
                        "X X",
                        "XSX",
                        "X X"
                }, 'X', ModItems.LEAD_INGOT, 'S', Items.STICK);
                createShaped(RecipeCategory.TRANSPORTATION, Items.DETECTOR_RAIL, 6, new String[]{
                        "X X",
                        "XPX",
                        "XSX"
                }, 'X', ModItems.LEAD_INGOT, 'S', Items.REDSTONE, 'P', Items.STONE_PRESSURE_PLATE);
                createShaped(RecipeCategory.TRANSPORTATION, Items.ACTIVATOR_RAIL, 6, new String[]{
                        "XSX",
                        "XRX",
                        "XSX"
                }, 'X', ModItems.LEAD_INGOT, 'S', Items.STICK, 'R', Items.REDSTONE);
                createShaped(RecipeCategory.TOOLS, Items.COMPASS, new String[]{
                        " X ",
                        "XSX",
                        " X "
                }, 'X', ModItems.LEAD_INGOT, 'S', Items.REDSTONE);
                createShaped(RecipeCategory.TOOLS, Items.BUCKET, new String[]{
                        "X X",
                        " X "
                }, 'X', ModItems.LEAD_INGOT);
                createShaped(RecipeCategory.TOOLS, Items.SHEARS, new String[]{
                        " X",
                        "X "
                }, 'X', ModItems.LEAD_INGOT);
                createShaped(RecipeCategory.TOOLS, Items.FLINT_AND_STEEL, new String[]{
                        "XS"
                }, 'X', ModItems.LEAD_INGOT, 'S', Items.FLINT);
                createShaped(RecipeCategory.TRANSPORTATION, Items.MINECART, new String[]{
                        "X X",
                        "XXX"
                }, 'X', ModItems.LEAD_INGOT);
                createShaped(RecipeCategory.COMBAT, Items.SHIELD, new String[]{
                        "SXS",
                        "SSS",
                        " S "
                }, 'X', ModItems.LEAD_INGOT, 'S', ItemTags.PLANKS);
                createShaped(RecipeCategory.COMBAT, Items.CROSSBOW, new String[]{
                        "SXS",
                        "RTR",
                        " S "
                }, 'X', ModItems.LEAD_INGOT, 'S', Items.STICK, 'R', Items.STRING, 'T', Items.TRIPWIRE_HOOK);

                // Gold to Platinum
                createShapeless(RecipeCategory.MISC, ModItems.PLATINUM_NUGGET, ModItems.PLATINUM_INGOT);

                createShaped(RecipeCategory.FOOD, Items.GOLDEN_CARROT, new String[]{
                        "XXX",
                        "XCX",
                        "XXX"
                }, 'X', ModItems.PLATINUM_NUGGET, 'C', Items.CARROT);
                createShaped(RecipeCategory.FOOD, Items.GLISTERING_MELON_SLICE, new String[]{
                        "XXX",
                        "XCX",
                        "XXX"
                }, 'X', ModItems.PLATINUM_NUGGET, 'C', Items.MELON_SLICE);
                createShaped(RecipeCategory.FOOD, Items.GOLDEN_APPLE, new String[]{
                        "XXX",
                        "XCX",
                        "XXX"
                }, 'X', ModItems.PLATINUM_INGOT, 'C', Items.APPLE);
                createShaped(RecipeCategory.TOOLS, Items.CLOCK, new String[]{
                        " X ",
                        "XRX",
                        " X "
                }, 'X', ModItems.PLATINUM_INGOT, 'R', Items.REDSTONE);
                createShaped(RecipeCategory.TRANSPORTATION, Items.POWERED_RAIL, 6, new String[]{
                        "X X",
                        "XCX",
                        "XRX"
                }, 'X', ModItems.PLATINUM_INGOT, 'C', Items.STICK, 'R', Items.REDSTONE);
                createShaped(RecipeCategory.REDSTONE, Items.LIGHT_WEIGHTED_PRESSURE_PLATE, new String[]{
                        "XX"
                }, 'X', ModItems.PLATINUM_INGOT);
                createShaped(RecipeCategory.REDSTONE, Items.NETHERITE_INGOT, new String[]{
                        "SSS",
                        "SXX",
                        "XX "
                }, 'X', ModItems.PLATINUM_INGOT, 'S', Items.NETHERITE_SCRAP);
            }

            @Unique
            public void createShapeless(RecipeCategory category, Item result, Item... ingredients) {
                createShapeless(category, result, 1, 1, "", ingredients);
            }

            @Unique
            public void createShapeless(RecipeCategory category, Item result, int count, int ingredientCount, Object... ingredients) {
                createShapeless(category, result, count, ingredientCount, "", ingredients);
            }

            @Unique
            public void createShapeless(RecipeCategory category, Item result, int count, int ingredientCount, String customName, Object... ingredients) {
                if (ingredients.length == 0) {
                    throw new IllegalArgumentException("A shapeless recipe must have at least one ingredient.");
                }

                ShapelessRecipeBuilder builder = shapeless(category, result, count);

                // Process each ingredient
                for (Object ingredient : ingredients) {
                    if (ingredient instanceof Item) {
                        builder.requires((Item) ingredient, ingredientCount);
                    } else if (ingredient instanceof TagKey<?>) {
                        builder.requires((TagKey<Item>) ingredient);
                    } else if (ingredient instanceof Ingredient ingredient1) {
                        builder.requires(ingredient1);
                    } else {
                        throw new IllegalArgumentException("Ingredients must be of type Item, Ingredient or TagKey<Item>.");
                    }
                }

                // Automatically add advancement criteria for each ingredient
                for (Object ingredient : ingredients) {
                    if (ingredient instanceof Item) {
                        builder.unlockedBy(
                                getHasName((Item) ingredient),
                                has((Item) ingredient)
                        );
                    } else if (ingredient instanceof TagKey<?>) {
                        builder.unlockedBy(
                                "has_" + ((TagKey<Item>) ingredient).location().getPath(),
                                has((TagKey<Item>) ingredient)
                        );
                    } else if (ingredient instanceof Ingredient ingredient1) {
                        for (Holder<Item> item : ingredient1.items().toList()) {
                            builder.unlockedBy(
                                    getHasName(item.value()),
                                    has(item.value())
                            );
                        }
                    }
                }

                if (!customName.isEmpty()) {
                    builder.save(recipeOutput, customName);
                } else {
                    builder.save(recipeOutput);
                }
            }

            @Unique
            public void createShaped(RecipeCategory category, Item result, String[] pattern, Object... ingredientPairs) {
                createShaped(category, result, 1, pattern, ingredientPairs);
            }

            @Unique
            public void createShaped(RecipeCategory category, Item result, int count, String[] pattern, Object... ingredientPairs) {
                if (pattern.length > 3) {
                    throw new IllegalArgumentException("Pattern can have a maximum of 3 rows.");
                }

                if (ingredientPairs.length % 2 != 0) {
                    throw new IllegalArgumentException("Ingredient pairs must have an even number of arguments (character-item pairs).");
                }

                ShapedRecipeBuilder builder = shaped(category, result, count);

                // Process ingredient pairs
                for (int i = 0; i < ingredientPairs.length; i += 2) {
                    if (!(ingredientPairs[i] instanceof Character)) {
                        throw new IllegalArgumentException("Expected a Character for ingredient key at index " + i);
                    }
                    char key = (Character) ingredientPairs[i];

                    Object ingredient = ingredientPairs[i + 1];
                    if (ingredient instanceof Item item) {
                        builder.define(key, item);
                    } else if (ingredient instanceof TagKey<?>) {
                        builder.define(key, (TagKey<Item>) ingredient);
                    } else if (ingredient instanceof Ingredient ingredient1) {
                        builder.define(key, ingredient1);
                    } else {
                        throw new IllegalArgumentException("Expected an Item or TagKey<Item> for ingredient value at index " + (i + 1));
                    }
                }

                for (int i = 1; i < ingredientPairs.length; i += 2) {
                    Object ingredient = ingredientPairs[i];
                    if (ingredient instanceof Item) {
                        builder.unlockedBy(
                                getHasName((Item) ingredient),
                                has((Item) ingredient)
                        );
                    } else if (ingredient instanceof TagKey<?>) {
                        builder.unlockedBy(
                                "has_" + ((TagKey<Item>) ingredient).location().getPath(),
                                has((TagKey<Item>) ingredient)
                        );
                    } else if (ingredient instanceof Ingredient ingredient1) {
                        for (Holder<Item> item : ingredient1.items().toList()) {
                            builder.unlockedBy(
                                    getHasName(item.value()),
                                    has(item.value())
                            );
                        }
                    }
                }

                // Add the pattern
                for (String row : pattern) {
                    builder.pattern(row);
                }

                builder.save(recipeOutput);
            }

            @Unique
            public void createArmor(Object material, ArmorItemRegister armor) {
                // Helmet (3x3 pattern)
                createShaped(RecipeCategory.COMBAT, armor.HELMET, 1,
                        new String[]{
                                "XXX",
                                "X X"
                        },
                        'X', material
                );

                // Chestplate (3x3 pattern)
                createShaped(RecipeCategory.COMBAT, armor.CHESTPLATE, 1,
                        new String[]{
                                "X X",
                                "XXX",
                                "XXX"
                        },
                        'X', material
                );

                // Leggings (3x3 pattern)
                createShaped(RecipeCategory.COMBAT, armor.LEGGINGS, 1,
                        new String[]{
                                "XXX",
                                "X X",
                                "X X"
                        },
                        'X', material
                );

                // Boots (3x3 pattern)
                createShaped(RecipeCategory.COMBAT, armor.BOOTS, 1,
                        new String[]{
                                "X X",
                                "X X"
                        },
                        'X', material
                );
            }

            @Unique
            public void createTools(Object material, BasicToolSetRegister toolType) {
                createShaped(RecipeCategory.TOOLS, toolType.PICKAXE, 1,
                        new String[]{
                                "XXX",
                                " S ",
                                " S "
                        },
                        'X', material, 'S', Items.STICK
                );

                createShaped(RecipeCategory.COMBAT, toolType.SWORD, 1, new String[]{
                        "X",
                        "X",
                        "S"
                }, 'X', material, 'S', Items.STICK);

                createShaped(RecipeCategory.TOOLS, toolType.AXE, 1,
                        new String[]{
                                "XX",
                                "XS",
                                " S"
                        },
                        'X', material, 'S', Items.STICK
                );

                createShaped(RecipeCategory.TOOLS, toolType.SHOVEL, 1,
                        new String[]{
                                "X",
                                "S",
                                "S"
                        },
                        'X', material, 'S', Items.STICK
                );

                createShaped(RecipeCategory.TOOLS, toolType.HOE, 1,
                        new String[]{
                                "XX",
                                " S",
                                " S"
                        },
                        'X', material, 'S', Items.STICK
                );
            }

            @Unique
            public void createBlock(Item material, BlockItemRegister block) {
                createShaped(RecipeCategory.BUILDING_BLOCKS, block.getItem(), new String[]{
                        "XXX",
                        "XXX",
                        "XXX"
                }, 'X', material);

                createShapeless(RecipeCategory.MISC, material, 9, 1, Ingredient.of(block.getItem()));
            }

            @Unique
            public void createSmeltingBlasting(Item result, Item... ingredients) {
                createSmeltingBlasting(result, 0.7F, 100, ingredients);
            }

            @Unique
            public void createSmeltingBlasting(Item result, float experience, Item... ingredients) {
                createSmeltingBlasting(result, experience, 100, ingredients);
            }
            
            @Unique
            public void createSmeltingBlasting(Item result, float experience, int cookTime, Item... ingredients) {
                if (ingredients.length == 0) {
                    throw new IllegalArgumentException("A shapeless recipe must have at least one ingredient.");
                }

                SimpleCookingRecipeBuilder smelting = SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredients), RecipeCategory.BUILDING_BLOCKS, result, experience, cookTime * 2);
                SimpleCookingRecipeBuilder blasting = SimpleCookingRecipeBuilder.blasting(Ingredient.of(ingredients), RecipeCategory.BUILDING_BLOCKS, result, experience, cookTime);

                for (Item ingredient : ingredients) {
                    smelting.unlockedBy(
                            getHasName(ingredient),
                            has(ingredient)
                    );

                    blasting.unlockedBy(
                            getHasName(ingredient),
                            has(ingredient)
                    );
                }

                smelting.save(recipeOutput, BuiltInRegistries.ITEM.getKey(result.asItem()).getPath() + "_smelting");
                blasting.save(recipeOutput, BuiltInRegistries.ITEM.getKey(result.asItem()).getPath() + "_blasting");
            }
        };
    }

    @Override
    public @NotNull String getName() {
        return "TerraMineRecipes";
    }
}
