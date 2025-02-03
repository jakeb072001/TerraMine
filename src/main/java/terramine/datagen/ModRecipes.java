package terramine.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Unique;
import terramine.common.init.ModBlocks;
import terramine.common.init.ModItems;
import terramine.common.utility.ArmorItemRegister;
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
                createShapeless(RecipeCategory.MISC, Items.CLOCK, ModItems.GOLD_WATCH);
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
                createArmor(ModItems.DEMONITE_INGOT, ModItems.SHADOW_ARMOR);
                createArmor(ModItems.CRIMTANE_INGOT, ModItems.CRIMSON_ARMOR);
                createArmor(ModItems.METEORITE_INGOT, ModItems.METEOR_ARMOR);
                createArmor(ModItems.HELLSTONE_INGOT, ModItems.MOLTEN_ARMOR);

                // Tools
                createTools(ModItems.DEMONITE_INGOT, ModItems.DEMONITE_PICKAXE, ModItems.DEMONITE_AXE, ModItems.DEMONITE_SHOVEL, ModItems.DEMONITE_HOE, ModItems.DEMONITE_SWORD);
                createTools(ModItems.CRIMTANE_INGOT, ModItems.CRIMTANE_PICKAXE, ModItems.CRIMTANE_AXE, ModItems.CRIMTANE_SHOVEL, ModItems.CRIMTANE_HOE, ModItems.CRIMTANE_SWORD);
                createShaped(RecipeCategory.TOOLS, ModItems.MOLTEN_PICKAXE,
                        new String[]{
                                "XXX",
                                " S ",
                                " S "
                        },
                        'X', ModItems.HELLSTONE_INGOT, 'S', Items.STICK
                );
                createMirroredShaped(RecipeCategory.TOOLS, ModItems.METEOR_SHAXE, 1, new String[]{
                        "XX ",
                        "XSX",
                        " S "
                }, 'X', ModItems.METEORITE_INGOT, 'S', Items.STICK);
                createMirroredShaped(RecipeCategory.TOOLS, ModItems.MOLTEN_SHAXE, 1, new String[]{
                        "XX ",
                        "XSX",
                        " S "
                }, 'X', ModItems.HELLSTONE_INGOT, 'S', Items.STICK);
                createShaped(RecipeCategory.TOOLS, ModItems.MAGIC_MIRROR, new String[]{
                        "IDI",
                        "DGD",
                        "IDI"
                }, 'D', Items.DIAMOND, 'I', Items.GOLD_INGOT, 'G', Items.GLASS);

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
                createMirroredShaped(RecipeCategory.COMBAT, ModItems.SPACE_GUN, 1, new String[]{
                        "X  ",
                        "XXX",
                        " X "
                }, 'X', ModItems.METEORITE_INGOT);


                ///////////////////////////////////// Smelting/Cooking /////////////////////////////////////

                // Terraria Ingots
                createSmeltingBlasting(ModItems.DEMONITE_INGOT, ModItems.RAW_DEMONITE, ModBlocks.DEMONITE_ORE.getItem(), ModBlocks.DEEPSLATE_DEMONITE_ORE.getItem());
                createSmeltingBlasting(ModBlocks.DEMONITE_BLOCK.getItem(), 6F, ModBlocks.RAW_DEMONITE_BLOCK.getItem());
                createSmeltingBlasting(ModItems.CRIMTANE_INGOT, ModItems.RAW_CRIMTANE, ModBlocks.CRIMTANE_ORE.getItem(), ModBlocks.DEEPSLATE_CRIMTANE_ORE.getItem());
                createSmeltingBlasting(ModBlocks.CRIMTANE_BLOCK.getItem(), 6F, ModBlocks.RAW_CRIMTANE_BLOCK.getItem());
                createSmeltingBlasting(ModItems.METEORITE_INGOT, ModItems.RAW_METEORITE, ModBlocks.METEORITE_ORE.getItem());
                createSmeltingBlasting(ModBlocks.METEORITE_BLOCK.getItem(), 6F, ModBlocks.RAW_METEORITE_BLOCK.getItem());
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
            }

            @Unique
            public void createShapeless(RecipeCategory category, Item result, Item... ingredients) {
                createShapeless(category, result, 1, 1, ingredients);
            }

            @Unique
            public void createShapeless(RecipeCategory category, Item result, int count, int ingredientCount, Object... ingredients) {
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

                builder.save(recipeOutput);
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
                ShapedRecipeBuilder builderMirrored = shaped(category, result, count);

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
            public void createMirroredShaped(RecipeCategory category, Item result, int count, String[] pattern, Object... ingredientPairs) {
                createShaped(category, result, count, pattern, ingredientPairs);

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

                for (String row : pattern) {
                    builder.pattern(new StringBuilder(row).reverse().toString());
                }

                builder.save(recipeOutput, BuiltInRegistries.ITEM.getKey(result.asItem()).getPath() + "_mirrored");
            }

            @Unique
            public void createArmor(Item material, ArmorItemRegister armor) {
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
            public void createTools(Item material, Item Pickaxe, Item Axe, Item Shovel, Item Hoe, Item Sword) {
                createShaped(RecipeCategory.TOOLS, Pickaxe, 1,
                        new String[]{
                                "XXX",
                                " S ",
                                " S "
                        },
                        'X', material, 'S', Items.STICK
                );

                createMirroredShaped(RecipeCategory.TOOLS, Axe, 1,
                        new String[]{
                                "XX",
                                "XS",
                                " S"
                        },
                        'X', material, 'S', Items.STICK
                );

                createShaped(RecipeCategory.TOOLS, Shovel, 1,
                        new String[]{
                                "X",
                                "S",
                                "S"
                        },
                        'X', material, 'S', Items.STICK
                );

                createMirroredShaped(RecipeCategory.TOOLS, Hoe, 1,
                        new String[]{
                                "XX",
                                " S",
                                " S"
                        },
                        'X', material, 'S', Items.STICK
                );

                createShaped(RecipeCategory.COMBAT, Sword, 1, new String[]{
                        "X",
                        "X",
                        "S"
                }, 'X', material, 'S', Items.STICK);
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
                createSmeltingBlasting(result, 0.7F, ingredients);
            }
            
            @Unique
            public void createSmeltingBlasting(Item result, float experience, Item... ingredients) {
                if (ingredients.length == 0) {
                    throw new IllegalArgumentException("A shapeless recipe must have at least one ingredient.");
                }

                SimpleCookingRecipeBuilder smelting = SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredients), RecipeCategory.BUILDING_BLOCKS, result, experience, 200);
                SimpleCookingRecipeBuilder blasting = SimpleCookingRecipeBuilder.blasting(Ingredient.of(ingredients), RecipeCategory.BUILDING_BLOCKS, result, experience, 100);

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
