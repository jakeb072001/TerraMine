package terramine.common.utility;

import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import terramine.common.init.ModBlocks;

import java.util.*;

// todo: add more blacklist items if there are any infinite item loops
// todo: allow tags to work in blacklist, for logs for example
// todo: add a check when adding to result for ingots, if the world has platinum then a clock should give platinum ingots not gold
// todo: add custom uncrafts for items such as netherite equipment that are made in other tables
public class UncraftingHelper {
    public static List<ItemStack> uncraft(ItemStack stack, ServerLevel level) {
        RecipeManager recipeManager = level.recipeAccess();
        List<ItemStack> results = new ArrayList<>();
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
                            Item item = ingredient.items().toList().getFirst().value();
                            if (!blacklistedItems.contains(item)) {
                                results.add(item.getDefaultInstance());
                            }
                        }

                        break;
                    }
                } catch (Exception e) {
                    System.err.println("Skipping invalid recipe: " + recipe + " due to error: " + e.getMessage());
                }
            }
        }

        return results;
    }
}