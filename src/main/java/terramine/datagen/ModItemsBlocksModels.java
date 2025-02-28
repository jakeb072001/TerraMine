package terramine.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.color.item.GrassColorSource;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import terramine.TerraMine;
import terramine.client.render.color.TerrariaDye;
import terramine.common.init.ModBlocks;
import terramine.common.init.ModItems;
import terramine.common.item.armor.TerrariaArmor;
import terramine.common.item.projectiles.throwables.BombItem;
import terramine.common.item.projectiles.throwables.DynamiteItem;
import terramine.common.item.projectiles.throwables.GrenadeItem;
import terramine.common.utility.BlockItemRegister;

import static net.minecraft.client.data.models.BlockModelGenerators.createRotatedVariant;
import static net.minecraft.client.data.models.BlockModelGenerators.createSimpleBlock;
import static terramine.TerraMine.id;

// todo: change piggy bank and safe to have their item textures generated here instead of generateItemModels, also probably create a method for creating block model and item model for them.
public class ModItemsBlocksModels extends FabricModelProvider {
    public ModItemsBlocksModels(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
        // Chests
        createChest(blockModelGenerators, ModBlocks.GOLD_CHEST);
        blockModelGenerators.copyModel(ModBlocks.GOLD_CHEST.BLOCK, ModBlocks.TRAPPED_GOLD_CHEST.BLOCK);
        createChest(blockModelGenerators, ModBlocks.FROZEN_CHEST);
        blockModelGenerators.copyModel(ModBlocks.FROZEN_CHEST.BLOCK, ModBlocks.TRAPPED_FROZEN_CHEST.BLOCK);
        createChest(blockModelGenerators, ModBlocks.IVY_CHEST);
        blockModelGenerators.copyModel(ModBlocks.IVY_CHEST.BLOCK, ModBlocks.TRAPPED_IVY_CHEST.BLOCK);
        createChest(blockModelGenerators, ModBlocks.SANDSTONE_CHEST);
        blockModelGenerators.copyModel(ModBlocks.SANDSTONE_CHEST.BLOCK, ModBlocks.TRAPPED_SANDSTONE_CHEST.BLOCK);
        createChest(blockModelGenerators, ModBlocks.WATER_CHEST);
        createChest(blockModelGenerators, ModBlocks.SKYWARE_CHEST);
        createChest(blockModelGenerators, ModBlocks.SHADOW_CHEST);
        blockModelGenerators.blockStateOutput.accept(createSimpleBlock(ModBlocks.PIGGY_BANK.BLOCK, ModelLocationUtils.getModelLocation(ModBlocks.PIGGY_BANK.BLOCK)).with(BlockModelGenerators.createHorizontalFacingDispatch()));
        blockModelGenerators.blockStateOutput.accept(createSimpleBlock(ModBlocks.SAFE.BLOCK, ModelLocationUtils.getModelLocation(ModBlocks.SAFE.BLOCK)).with(BlockModelGenerators.createHorizontalFacingDispatch()));

        // Metals
        for (Block block : ModBlocks.METAL_BLOCKS) {
            createBasicBlock(blockModelGenerators, block);
        }

        // Misc
        copyBlockMirrored(blockModelGenerators, Blocks.STONE, ModBlocks.REDSTONE_STONE);
        copyBlockMirrored(blockModelGenerators, Blocks.DEEPSLATE, ModBlocks.REDSTONE_DEEPSLATE);
        copyBlock(blockModelGenerators, Blocks.TNT, ModBlocks.INSTANT_TNT);
        blockModelGenerators.createCraftingTableLike(ModBlocks.TINKERER_TABLE.BLOCK, Blocks.OAK_PLANKS, TextureMapping::craftingTable);
        blockModelGenerators.registerSimpleItemModel(ModBlocks.TINKERER_TABLE.BLOCK, ModelLocationUtils.getModelLocation(ModBlocks.TINKERER_TABLE.BLOCK));
        blockModelGenerators.createNonTemplateModelBlock(ModBlocks.SHIMMER_BLOCK);

        // Building
        for (Block block : ModBlocks.BUILDING_BLOCKS) {
            createBasicBlock(blockModelGenerators, block);
        }

        // Vegetation
        blockModelGenerators.createPlantWithDefaultItem(ModBlocks.VILE_MUSHROOM.BLOCK, ModBlocks.POTTED_VILE_MUSHROOM.BLOCK, BlockModelGenerators.PlantType.NOT_TINTED);
        blockModelGenerators.createPlantWithDefaultItem(ModBlocks.VICIOUS_MUSHROOM.BLOCK, ModBlocks.POTTED_VICIOUS_MUSHROOM.BLOCK, BlockModelGenerators.PlantType.NOT_TINTED);

        // Corruption
        for (Block block : ModBlocks.CORRUPTION_BLOCKS) {
            createBasicBlock(blockModelGenerators, block);
        }
        createPillar(blockModelGenerators, ModBlocks.CORRUPTED_DEEPSLATE);
        createSandstone(blockModelGenerators, ModBlocks.CORRUPTED_SANDSTONE);
        createGrass(blockModelGenerators, ModBlocks.CORRUPTED_GRASS);
        generateCustomSnowLayer(blockModelGenerators, ModBlocks.CORRUPTED_SNOW_LAYER, ModBlocks.CORRUPTED_SNOW);

        // Crimson
        for (Block block : ModBlocks.CRIMSON_BLOCKS) {
            createBasicBlock(blockModelGenerators, block);
        }
        createPillar(blockModelGenerators, ModBlocks.CRIMSON_DEEPSLATE);
        createSandstone(blockModelGenerators, ModBlocks.CRIMSON_SANDSTONE);
        createGrass(blockModelGenerators, ModBlocks.CRIMSON_GRASS);
        generateCustomSnowLayer(blockModelGenerators, ModBlocks.CRIMSON_SNOW_LAYER, ModBlocks.CRIMSON_SNOW);
    }

    public final void copyBlockMirrored(BlockModelGenerators blockModelGenerators, Block block, BlockItemRegister blockItemRegister) {
        Block moddedBlock = blockItemRegister.BLOCK;
        ResourceLocation resourceLocation = ModelLocationUtils.getModelLocation(block);
        ResourceLocation resourceLocation2 = ModelLocationUtils.getModelLocation(block, "_mirrored");
        blockModelGenerators.blockStateOutput.accept(createRotatedVariant(moddedBlock, resourceLocation, resourceLocation2));
        blockModelGenerators.registerSimpleItemModel(moddedBlock, resourceLocation);
    }

    public final void copyBlock(BlockModelGenerators blockModelGenerators, Block block, BlockItemRegister blockItemRegister) {
        Block moddedBlock = blockItemRegister.BLOCK;
        ResourceLocation resourceLocation = ModelLocationUtils.getModelLocation(block);
        blockModelGenerators.blockStateOutput.accept(createSimpleBlock(moddedBlock, resourceLocation));
        blockModelGenerators.registerSimpleItemModel(moddedBlock, resourceLocation);
    }

    public final void createPillar(BlockModelGenerators blockModelGenerators, BlockItemRegister blockItemRegister) {
        Block block = blockItemRegister.BLOCK;
        blockModelGenerators.blockStateOutput.accept(createRotatedVariant(block, ModelTemplates.CUBE_COLUMN.create(block, TextureMapping.column(block), blockModelGenerators.modelOutput), ModelTemplates.CUBE_COLUMN_MIRRORED.create(block, TextureMapping.column(block), blockModelGenerators.modelOutput)).with(BlockModelGenerators.createRotatedPillar()));
        blockModelGenerators.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
    }

    public final void createBasicBlock(BlockModelGenerators blockModelGenerators, Block block) {
        blockModelGenerators.createTrivialCube(block);
        blockModelGenerators.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
    }

    public final void createSandstone(BlockModelGenerators blockModelGenerators, BlockItemRegister blockItemRegister) {
        Block block = blockItemRegister.BLOCK;
        blockModelGenerators.blockStateOutput.accept(createSimpleBlock(block, ModelTemplates.CUBE_BOTTOM_TOP.create(block, TextureMapping.cubeBottomTop(block), blockModelGenerators.modelOutput)));
        blockModelGenerators.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
    }

    public final void createChest(BlockModelGenerators blockModelGenerators, BlockItemRegister blockItemRegister) {
        Block block = blockItemRegister.BLOCK;
        blockModelGenerators.blockStateOutput.accept(createSimpleBlock(block, ModelLocationUtils.getModelLocation(block)));
    }

    public final void createGrass(BlockModelGenerators blockModelGenerators, BlockItemRegister grassBlock) {
        Block block = grassBlock.BLOCK;
        ResourceLocation resourceLocation = TextureMapping.getBlockTexture(Blocks.DIRT);
        TextureMapping textureMapping = (new TextureMapping()).put(TextureSlot.BOTTOM, resourceLocation).copyForced(TextureSlot.BOTTOM, TextureSlot.PARTICLE).put(TextureSlot.TOP, TextureMapping.getBlockTexture(block, "_top")).put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_snow"));
        Variant variant = Variant.variant().with(VariantProperties.MODEL, ModelTemplates.CUBE_BOTTOM_TOP.createWithSuffix(block, "_snow", textureMapping, blockModelGenerators.modelOutput));
        blockModelGenerators.createGrassLikeBlock(block, ModelLocationUtils.getModelLocation(block), variant);
        blockModelGenerators.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
    }

    public final void generateCustomSnowLayer(BlockModelGenerators blockModelGenerators, BlockItemRegister layerBlock, BlockItemRegister snowBlock) {
        Block block1 = layerBlock.BLOCK;
        Block block2 = snowBlock.BLOCK;
        TextureMapping textureMapping = TextureMapping.cube(block1);
        ResourceLocation resourceLocation = ModelTemplates.CUBE_ALL.create(block2, textureMapping, blockModelGenerators.modelOutput);
        blockModelGenerators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block1).with(PropertyDispatch.property(BlockStateProperties.LAYERS).generate((integer) -> {
            Variant var10000 = Variant.variant();
            VariantProperty<ResourceLocation> var10001 = VariantProperties.MODEL;
            ResourceLocation var2;
            if (integer < 8) {
                int var10003 = integer;
                var2 = ModelLocationUtils.getModelLocation(block1, "_height" + var10003 * 2);
            } else {
                var2 = resourceLocation;
            }

            return var10000.with(var10001, var2);
        })));
        blockModelGenerators.blockStateOutput.accept(createSimpleBlock(block2, resourceLocation));
        blockModelGenerators.registerSimpleItemModel(block1, ModelLocationUtils.getModelLocation(block1, "_height2"));
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
        generateFlatItem(itemModelGenerators, ModItems.SHIMMER_BUCKET, ModelTemplates.FLAT_ITEM, "misc");
        generateFlatWithHeldModelItem(itemModelGenerators, ModItems.CLENTAMINATOR, "tools/clentaminator");

        // Custom Blocks
        generateFlatItem(itemModelGenerators, ModBlocks.PIGGY_BANK.ITEM, ModelTemplates.FLAT_ITEM, "block");
        generateFlatItem(itemModelGenerators, ModBlocks.SAFE.ITEM, ModelTemplates.FLAT_ITEM, "block");

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
