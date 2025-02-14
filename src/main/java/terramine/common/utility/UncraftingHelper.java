package terramine.common.utility;

import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.storage.LevelData;
import terramine.common.init.ModBlocks;
import terramine.common.init.ModComponents;
import terramine.common.init.ModItems;

import java.util.*;

import static java.util.stream.Collectors.toList;

// todo: add more blacklist items if there are any infinite item loops
public class UncraftingHelper {
    public static List<ItemStack> uncraft(ItemStack stack, ServerLevel level) {
        RecipeManager recipeManager = level.recipeAccess();
        List<ItemStack> results = new ArrayList<>();

        // Blacklisted tags
        Set<TagKey<Item>> blacklistedTags = new HashSet<>();
        blacklistedTags.add(ItemTags.LOGS);
        blacklistedTags.add(ItemTags.WOOL);
        blacklistedTags.add(ItemTags.WOOL_CARPETS);

        // Blacklisted items
        Set<Item> blacklistedItems = new HashSet<>();
        blacklistedItems.add(Items.RAW_COPPER_BLOCK);
        blacklistedItems.add(Items.RAW_IRON_BLOCK);
        blacklistedItems.add(Items.RAW_GOLD_BLOCK);
        blacklistedItems.add(Items.BONE_BLOCK);
        blacklistedItems.add(Items.COAL_BLOCK);
        blacklistedItems.add(Items.COPPER_BLOCK);
        blacklistedItems.add(Items.IRON_BLOCK);
        blacklistedItems.add(Items.GOLD_BLOCK);
        blacklistedItems.add(Items.LAPIS_BLOCK);
        blacklistedItems.add(Items.REDSTONE_BLOCK);
        blacklistedItems.add(Items.DIAMOND_BLOCK);
        blacklistedItems.add(Items.EMERALD_BLOCK);
        blacklistedItems.add(Items.NETHERITE_BLOCK);
        blacklistedItems.add(ModBlocks.RAW_TIN_BLOCK.ITEM);
        blacklistedItems.add(ModBlocks.RAW_LEAD_BLOCK.ITEM);
        blacklistedItems.add(ModBlocks.RAW_SILVER_BLOCK.ITEM);
        blacklistedItems.add(ModBlocks.RAW_TUNGSTEN_BLOCK.ITEM);
        blacklistedItems.add(ModBlocks.RAW_PLATINUM_BLOCK.ITEM);
        blacklistedItems.add(ModBlocks.RAW_DEMONITE_BLOCK.ITEM);
        blacklistedItems.add(ModBlocks.RAW_CRIMTANE_BLOCK.ITEM);
        blacklistedItems.add(ModBlocks.RAW_METEORITE_BLOCK.ITEM);
        blacklistedItems.add(ModBlocks.RAW_HELLSTONE_BLOCK.ITEM);
        blacklistedItems.add(ModBlocks.TIN_BLOCK.ITEM);
        blacklistedItems.add(ModBlocks.LEAD_BLOCK.ITEM);
        blacklistedItems.add(ModBlocks.SILVER_BLOCK.ITEM);
        blacklistedItems.add(ModBlocks.TUNGSTEN_BLOCK.ITEM);
        blacklistedItems.add(ModBlocks.PLATINUM_BLOCK.ITEM);
        blacklistedItems.add(ModBlocks.DEMONITE_BLOCK.ITEM);
        blacklistedItems.add(ModBlocks.CRIMTANE_BLOCK.ITEM);
        blacklistedItems.add(ModBlocks.METEORITE_BLOCK.ITEM);
        blacklistedItems.add(ModBlocks.HELLSTONE_BLOCK.ITEM);

        for (RecipeHolder<?> recipeHolder : recipeManager.getRecipes()) {
            if (recipeHolder.value() instanceof CraftingRecipe recipe) {
                List<Ingredient> ingredients = recipe.placementInfo().ingredients();

                if (ingredients.isEmpty() || ingredients.size() > 9) {
                    continue;
                }

                int gridSize = (int) Math.sqrt(ingredients.size());
                if (gridSize < 1 || gridSize > 3) {
                    continue;
                }

                NonNullList<ItemStack> grid = NonNullList.withSize(9, ItemStack.EMPTY);
                for (int i = 0; i < ingredients.size(); i++) {
                    if (ingredients.get(i).test(stack)) {
                        grid.set(i, stack.copy());
                    }
                }

                CraftingInput input = CraftingInput.of(gridSize, gridSize, grid);

                try {
                    ItemStack recipeOutput = recipe.assemble(input, level.registryAccess());

                    if (!recipeOutput.isEmpty() && ItemStack.isSameItem(stack, recipeOutput)) {
                        for (Ingredient ingredient : ingredients) {
                            for (int i = 0; i < stack.getCount(); i++) {
                                Item item = ingredient.items().toList().getFirst().value();
                                Item finalItem = item;
                                if (blacklistedItems.contains(item) || blacklistedTags.stream().anyMatch(tag -> finalItem.builtInRegistryHolder().is(tag))) {
                                    continue;
                                }

                                item = replaceOres(item, level.getLevelData());
                                results.add(item.getDefaultInstance());
                            }
                        }

                        break;
                    }
                } catch (Exception e) {
                    System.err.println("Skipping invalid recipe: " + recipe + " due to error: " + e.getMessage());
                }
            } else if (recipeHolder.value() instanceof SmithingRecipe recipe) {
                SmithingRecipeInput input = new SmithingRecipeInput(stack, stack, stack);
                List<Ingredient> ingredients = recipe.placementInfo().ingredients();

                try {
                    ItemStack recipeOutput = recipe.assemble(input, level.registryAccess());

                    if (!recipeOutput.isEmpty() && ItemStack.isSameItem(stack, recipeOutput)) {
                        for (Ingredient ingredient : ingredients) {
                            for (int i = 0; i < stack.getCount(); i++) {
                                Item item = ingredient.items().toList().getFirst().value();
                                results.add(item.getDefaultInstance());
                            }
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Skipping invalid recipe: " + recipe + " due to error: " + e.getMessage());
                }
            }
        }

        return results;
    }

    private static Item replaceOres(Item item, LevelData levelData) {
        if (item == Items.COPPER_INGOT && !ModComponents.ORE_TYPES.get(levelData).getIfCopper()) return ModItems.TIN_INGOT;
        if (item == Items.IRON_INGOT && !ModComponents.ORE_TYPES.get(levelData).getIfIron()) return ModItems.LEAD_INGOT;
        if (item == Items.IRON_NUGGET && !ModComponents.ORE_TYPES.get(levelData).getIfIron()) return ModItems.LEAD_NUGGET;
        if (item == ModItems.SILVER_INGOT && !ModComponents.ORE_TYPES.get(levelData).getIfSilver()) return ModItems.TUNGSTEN_INGOT;
        if (item == Items.GOLD_INGOT && !ModComponents.ORE_TYPES.get(levelData).getIfGold()) return ModItems.PLATINUM_INGOT;
        if (item == Items.GOLD_NUGGET && !ModComponents.ORE_TYPES.get(levelData).getIfGold()) return ModItems.PLATINUM_NUGGET;
        return item;
    }
}