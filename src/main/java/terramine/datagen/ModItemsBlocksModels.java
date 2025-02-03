package terramine.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import terramine.TerraMine;
import terramine.client.render.color.TerrariaDye;
import terramine.common.init.ModBlocks;
import terramine.common.init.ModItems;
import terramine.common.item.armor.TerrariaArmor;
import terramine.common.item.projectiles.throwables.BombItem;
import terramine.common.item.projectiles.throwables.DynamiteItem;
import terramine.common.item.projectiles.throwables.GrenadeItem;

import static terramine.TerraMine.id;

public class ModItemsBlocksModels extends FabricModelProvider {
    public ModItemsBlocksModels(FabricDataOutput output) {
        super(output);
    }

    // todo: add blocks with proper states
    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {

    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
        // Accessories
        for (Item item : ModItems.ACCESSORIES) {
            generateFlatItem(itemModelGenerators, item, ModelTemplates.FLAT_ITEM, "accessories");
        }

        // Armors
        for (Item item : ModItems.ARMORS) {
            if (item instanceof TerrariaArmor terrariaArmor) {
                generateFlatItem(itemModelGenerators, item, ModelTemplates.FLAT_ITEM, "armor/" + terrariaArmor.getTerramineArmorType());
            }
        }

        // Tools
        for (Item item : ModItems.TOOLS) {
            generateFlatItem(itemModelGenerators, item, ModelTemplates.FLAT_HANDHELD_ITEM, "tools");
        }

        // Weapons
        for (Item item : ModItems.WEAPONS) {
            generateFlatItem(itemModelGenerators, item, ModelTemplates.FLAT_HANDHELD_ITEM, "weapons/swords");
        }
        for (Item item : ModItems.BIG_WEAPONS) {
            generateBigSwordItem(itemModelGenerators, item);
        }

        // Arrows
        for (Item item : ModItems.ARROWS) {
            generateFlatItem(itemModelGenerators, item, ModelTemplates.FLAT_ITEM, "weapons/arrows/gui");
        }

        // Magic Weapons
        for (Item item : ModItems.MAGIC_WEAPONS) {
            generateFlatWithHeldModelItem(itemModelGenerators, item, "weapons/magic/gui");
        }

        // Throwables
        for (Item item : ModItems.THROWABLES) {
            if (item instanceof GrenadeItem) {
                generateFlatWithHeldModelItem(itemModelGenerators, item, "weapons/throwables/grenade/gui");
            }
            if (item instanceof BombItem) {
                generateFlatWithHeldModelItem(itemModelGenerators, item, "weapons/throwables/bomb/gui");
            }
            if (item instanceof DynamiteItem) {
                generateFlatWithHeldModelItem(itemModelGenerators, item, "weapons/throwables/dynamite/gui");
            }
        }

        // Shields
        for (Item item : ModItems.SHIELDS) {
            itemModelGenerators.generateBooleanDispatch(item, ItemModelUtils.isUsingItem(), ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item).withSuffix("_blocking")), ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item)));
        }
        generateUmbrella(itemModelGenerators, ModItems.UMBRELLA);

        // Dyes
        ResourceLocation resourceLocation = ModelTemplates.TWO_LAYERED_ITEM.create(id("item/dye"), TextureMapping.layered(id("item/misc/dye"), id("item/misc/dye_overlay")), itemModelGenerators.modelOutput);
        for (Item item : ModItems.DYES) {
            itemModelGenerators.itemModelOutput.accept(item, ItemModelUtils.tintedModel(resourceLocation, ItemModelUtils.constantTint(-1), new TerrariaDye()));
        }

        // Misc
        for (Item item : ModItems.MISC) {
            if (item == ModItems.FAKE_FALLEN_STAR) {
                generateCopyFlatItem(itemModelGenerators, ModItems.FAKE_FALLEN_STAR, ModItems.FALLEN_STAR);
                continue;
            }
            generateFlatItem(itemModelGenerators, item, ModelTemplates.FLAT_ITEM, "misc");
        }
        generateFlatItem(itemModelGenerators, ModItems.MAGIC_MIRROR, ModelTemplates.FLAT_ITEM, "misc");

        // Custom Blocks
        generateBlockItem(itemModelGenerators, ModBlocks.CORRUPTED_SNOW_LAYER.ITEM, "corrupted_snow_height2");
        generateBlockItem(itemModelGenerators, ModBlocks.CRIMSON_SNOW_LAYER.ITEM, "crimson_snow_height2");
        generateBlockItem(itemModelGenerators, ModBlocks.TRAPPED_GOLD_CHEST.ITEM, "gold_chest");
        generateBlockItem(itemModelGenerators, ModBlocks.TRAPPED_FROZEN_CHEST.ITEM, "frozen_chest");
        generateBlockItem(itemModelGenerators, ModBlocks.TRAPPED_IVY_CHEST.ITEM, "ivy_chest");
        generateBlockItem(itemModelGenerators, ModBlocks.TRAPPED_SANDSTONE_CHEST.ITEM, "sandstone_chest");
        generateBlockItem(itemModelGenerators, ModBlocks.INSTANT_TNT.ITEM, "tnt", true);
        generateBlockItem(itemModelGenerators, ModBlocks.REDSTONE_STONE.ITEM, "stone", true);
        generateBlockItem(itemModelGenerators, ModBlocks.REDSTONE_DEEPSLATE.ITEM, "deepslate", true);
        generateFlatItem(itemModelGenerators, ModBlocks.PIGGY_BANK.ITEM, ModelTemplates.FLAT_ITEM, "block");
        generateFlatItem(itemModelGenerators, ModBlocks.SAFE.ITEM, ModelTemplates.FLAT_ITEM, "block");
        generateFlatItem(itemModelGenerators, ModBlocks.VILE_MUSHROOM.ITEM, ModelTemplates.FLAT_ITEM, "block");
        generateFlatItem(itemModelGenerators, ModBlocks.VICIOUS_MUSHROOM.ITEM, ModelTemplates.FLAT_ITEM, "block");

        // no clue how colour is determined, I just copied the colours from vanilla spawn eggs.
        // Spawn Eggs
        itemModelGenerators.generateSpawnEgg(ModItems.MIMIC_SPAWN_EGG, 8409363, 7237230);
        itemModelGenerators.generateSpawnEgg(ModItems.DEMON_EYE_SPAWN_EGG, 16777215, 16711680);
        itemModelGenerators.generateSpawnEgg(ModItems.EATER_OF_SOULS_SPAWN_EGG, 9725844, 9529055);
        itemModelGenerators.generateSpawnEgg(ModItems.DEVOURER_SPAWN_EGG, 9529055, 9725844);
        itemModelGenerators.generateSpawnEgg(ModItems.CRIMERA_SPAWN_EGG, 10236982, 14377823);
    }

    public final void generateFlatItem(ItemModelGenerators itemModelGenerators, Item item, ModelTemplate modelTemplate, String textureLocation) {
        itemModelGenerators.itemModelOutput.accept(item, ItemModelUtils.plainModel(createCustomLocation(itemModelGenerators, item, modelTemplate, textureLocation)));
    }

    // todo: currently just uses the existing model, try to find a way to generate instead, also use for some tools such as the crimtane axe (replace texture to not be stubby aswell)
    public final void generateBigSwordItem(ItemModelGenerators itemModelGenerators, Item item) {
        ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(item);
        itemModelGenerators.itemModelOutput.accept(item, ItemModelUtils.plainModel(resourceLocation.withPrefix("item/")));
    }

    public final void generateCopyFlatItem(ItemModelGenerators itemModelGenerators, Item item, Item copiedItem) {
        ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(copiedItem);
        itemModelGenerators.itemModelOutput.accept(item, ItemModelUtils.plainModel(resourceLocation.withPrefix("item/")));
    }

    public final void generateBlockItem(ItemModelGenerators itemModelGenerators, Item item, String textureLocation) {
        generateBlockItem(itemModelGenerators, item, textureLocation, false);
    }

    public final void generateBlockItem(ItemModelGenerators itemModelGenerators, Item item, String textureLocation, boolean vanilla) {
        ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(vanilla ? "minecraft" : TerraMine.MOD_ID, "block/" + textureLocation);
        itemModelGenerators.itemModelOutput.accept(item, ItemModelUtils.plainModel(resourceLocation));
    }

    public final void generateUmbrella(ItemModelGenerators itemModelGenerators, Item item) {
        ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(item);
        ItemModel.Unbaked unbaked = ItemModelUtils.plainModel(createCustomLocation(itemModelGenerators, item, ModelTemplates.FLAT_ITEM, "weapons"));
        ItemModel.Unbaked unbaked2 = ItemModelUtils.conditional(ItemModelUtils.isUsingItem(), ItemModelUtils.plainModel(resourceLocation.withPrefix("item/").withSuffix("_blocking")), ItemModelUtils.plainModel(resourceLocation.withPrefix("item/").withSuffix("_in_hand")));
        itemModelGenerators.itemModelOutput.accept(item, ItemModelGenerators.createFlatModelDispatch(unbaked, unbaked2));
    }

    public final void generateFlatWithHeldModelItem(ItemModelGenerators itemModelGenerators, Item item, String textureLocation) {
        ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(item);
        ItemModel.Unbaked unbaked = ItemModelUtils.plainModel(createCustomLocation(itemModelGenerators, item, ModelTemplates.FLAT_ITEM, textureLocation));
        ItemModel.Unbaked unbaked2 = ItemModelUtils.plainModel(resourceLocation.withPrefix("item/").withSuffix("_held"));
        itemModelGenerators.itemModelOutput.accept(item, ItemModelGenerators.createFlatModelDispatch(unbaked, unbaked2));
    }

    public final ResourceLocation createCustomLocation(ItemModelGenerators itemModelGenerators, Item item, ModelTemplate modelTemplate, String textureLocation) {
        ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(item);
        return modelTemplate.create(ModelLocationUtils.getModelLocation(item), new TextureMapping().put(TextureSlot.LAYER0, resourceLocation.withPrefix("item/" + textureLocation + "/")), itemModelGenerators.modelOutput);
    }
}
