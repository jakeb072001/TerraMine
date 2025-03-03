package terramine.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import terramine.TerraMine;
import terramine.common.init.ModBlocks;
import terramine.common.init.ModEntities;
import terramine.common.init.ModItems;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static terramine.TerraMine.id;

public class ModAdvancements extends FabricAdvancementProvider {

    public ModAdvancements(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer) {
        AdvancementHolder rootAdvancement = Advancement.Builder.advancement()
                .display(
                        ModItems.HERMES_BOOTS, // The display icon
                        Component.translatable("terramine.advancements.root.title"), // The title
                        Component.translatable("terramine.advancements.root.description"), // The description
                        id("textures/block/corrupted_stone.png"), // Background image used
                        AdvancementType.TASK, // Options: TASK, CHALLENGE, GOAL
                        false, // Show toast top right
                        false, // Announce to chat
                        false // Hidden in the advancement tab
                )
                // The first string used in criterion is the name referenced by other advancements when they want to have 'requirements'
                .addCriterion("tick", PlayerTrigger.TriggerInstance.tick())
                .save(consumer, TerraMine.MOD_ID + ":root");

        AdvancementHolder accessoriseAdvancement = Advancement.Builder.advancement().parent(rootAdvancement)
                .display(
                        ModItems.AGLET,
                        Component.translatable("terramine.advancements.accessorise.title"),
                        Component.translatable("terramine.advancements.accessorise.description"),
                        null, // children to parent advancements don't need a background set
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("find_accessory", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(provider.lookupOrThrow(Registries.ITEM), ModTags.ACCESSORY)))
                .save(consumer, TerraMine.MOD_ID + ":accessorise");

        AdvancementHolder bootsOfTheHeroAdvancement = Advancement.Builder.advancement().parent(accessoriseAdvancement)
                .display(
                        ModItems.TERRASPARK_BOOTS,
                        Component.translatable("terramine.advancements.boots_of_the_hero.title"),
                        Component.translatable("terramine.advancements.boots_of_the_hero.description"),
                        null, // children to parent advancements don't need a background set
                        AdvancementType.CHALLENGE,
                        true,
                        true,
                        false
                )
                .addCriterion("find_accessory", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.TERRASPARK_BOOTS))
                .save(consumer, TerraMine.MOD_ID + ":boots_of_the_hero");

        AdvancementHolder starPowerAdvancement = Advancement.Builder.advancement().parent(rootAdvancement)
                .display(
                        ModItems.MANA_CRYSTAL,
                        Component.translatable("terramine.advancements.star_power.title"),
                        Component.translatable("terramine.advancements.star_power.description"),
                        null, // children to parent advancements don't need a background set
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("consume_mana_crystal", ConsumeItemTrigger.TriggerInstance.usedItem(BuiltInRegistries.ITEM, ModItems.MANA_CRYSTAL))
                .save(consumer, TerraMine.MOD_ID + ":star_power");

        AdvancementHolder prismancerAdvancement = Advancement.Builder.advancement().parent(starPowerAdvancement)
                .display(
                        ModItems.RAINBOW_ROD_ITEM,
                        Component.translatable("terramine.advancements.prismancer.title"),
                        Component.translatable("terramine.advancements.prismancer.description"),
                        null, // children to parent advancements don't need a background set
                        AdvancementType.CHALLENGE,
                        true,
                        true,
                        true
                )
                .addCriterion("find_accessory", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RAINBOW_ROD_ITEM))
                .save(consumer, TerraMine.MOD_ID + ":prismancer");

        AdvancementHolder iAmLootAdvancement = Advancement.Builder.advancement().parent(rootAdvancement)
                .display(
                        ModBlocks.GOLD_CHEST.ITEM,
                        Component.translatable("terramine.advancements.i_am_loot.title"),
                        Component.translatable("terramine.advancements.i_am_loot.description"),
                        null, // children to parent advancements don't need a background set
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("gold_chest", CriteriaTriggers.ANY_BLOCK_USE.createCriterion(new AnyBlockInteractionTrigger.TriggerInstance(Optional.empty(), Optional.of(ContextAwarePredicate.create(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.GOLD_CHEST.BLOCK).build())))))
                .addCriterion("trapped_gold_chest", CriteriaTriggers.ANY_BLOCK_USE.createCriterion(new AnyBlockInteractionTrigger.TriggerInstance(Optional.empty(), Optional.of(ContextAwarePredicate.create(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.TRAPPED_GOLD_CHEST.BLOCK).build())))))
                .requirements(AdvancementRequirements.Strategy.OR)
                .save(consumer, TerraMine.MOD_ID + ":i_am_loot");

        AdvancementHolder deadMenTellNoTalesAdvancement = Advancement.Builder.advancement().parent(rootAdvancement)
                .display(
                        ModBlocks.TRAPPED_GOLD_CHEST.ITEM,
                        Component.translatable("terramine.advancements.dead_men_tell_no_tales.title"),
                        Component.translatable("terramine.advancements.dead_men_tell_no_tales.description"),
                        null, // children to parent advancements don't need a background set
                        AdvancementType.TASK,
                        true,
                        true,
                        true
                )
                .addCriterion("trapped_gold_chest", CriteriaTriggers.ANY_BLOCK_USE.createCriterion(new AnyBlockInteractionTrigger.TriggerInstance(Optional.empty(), Optional.of(ContextAwarePredicate.create(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.TRAPPED_GOLD_CHEST.BLOCK).build())))))
                .addCriterion("trapped_frozen_chest", CriteriaTriggers.ANY_BLOCK_USE.createCriterion(new AnyBlockInteractionTrigger.TriggerInstance(Optional.empty(), Optional.of(ContextAwarePredicate.create(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.TRAPPED_FROZEN_CHEST.BLOCK).build())))))
                .addCriterion("trapped_ivy_chest", CriteriaTriggers.ANY_BLOCK_USE.createCriterion(new AnyBlockInteractionTrigger.TriggerInstance(Optional.empty(), Optional.of(ContextAwarePredicate.create(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.TRAPPED_IVY_CHEST.BLOCK).build())))))
                .addCriterion("trapped_sandstone_chest", CriteriaTriggers.ANY_BLOCK_USE.createCriterion(new AnyBlockInteractionTrigger.TriggerInstance(Optional.empty(), Optional.of(ContextAwarePredicate.create(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.TRAPPED_SANDSTONE_CHEST.BLOCK).build())))))
                .requirements(AdvancementRequirements.Strategy.OR)
                .save(consumer, TerraMine.MOD_ID + ":dead_men_tell_no_tales");

        AdvancementHolder chestSlayerAdvancement = Advancement.Builder.advancement().parent(iAmLootAdvancement)
                .display(
                        ModItems.MIMIC_SPAWN_EGG,
                        Component.translatable("terramine.advancements.chest_slayer.title"),
                        Component.translatable("terramine.advancements.chest_slayer.description"),
                        null, // children to parent advancements don't need a background set
                        AdvancementType.TASK,
                        true,
                        true,
                        true
                )
                .addCriterion("kill_mimic", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(BuiltInRegistries.ENTITY_TYPE, ModEntities.MIMIC)))
                .save(consumer, TerraMine.MOD_ID + ":chest_slayer");

        AdvancementHolder dungeonCrawlerAdvancement = Advancement.Builder.advancement().parent(rootAdvancement)
                .display(
                        ModBlocks.PURPLE_BRICKS.ITEM,
                        Component.translatable("terramine.advancements.dungeon_crawler.title"),
                        Component.translatable("terramine.advancements.dungeon_crawler.description"),
                        null, // children to parent advancements don't need a background set
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("location", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.location().setStructures(provider.lookupOrThrow(Registries.STRUCTURE).getOrThrow(ModTags.DUNGEONS))))
                .save(consumer, TerraMine.MOD_ID + ":dungeon_crawler");

        AdvancementHolder corruptionAdvancement = Advancement.Builder.advancement().parent(rootAdvancement)
                .display(
                        ModBlocks.CORRUPTED_GRASS.ITEM,
                        Component.translatable("terramine.advancements.the_corruption.title"),
                        Component.translatable("terramine.advancements.the_corruption.description"),
                        null, // children to parent advancements don't need a background set
                        AdvancementType.TASK,
                        true,
                        true,
                        true
                )
                .addCriterion("location", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.location().setBiomes(provider.lookupOrThrow(Registries.BIOME).getOrThrow(ModTags.CORRUPTION))))
                .save(consumer, TerraMine.MOD_ID + ":the_corruption");

        AdvancementHolder crimsonAdvancement = Advancement.Builder.advancement().parent(rootAdvancement)
                .display(
                        ModBlocks.CRIMSON_GRASS.ITEM,
                        Component.translatable("terramine.advancements.the_crimson.title"),
                        Component.translatable("terramine.advancements.the_crimson.description"),
                        null, // children to parent advancements don't need a background set
                        AdvancementType.TASK,
                        true,
                        true,
                        true
                )
                .addCriterion("location", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.location().setBiomes(provider.lookupOrThrow(Registries.BIOME).getOrThrow(ModTags.CRIMSON))))
                .save(consumer, TerraMine.MOD_ID + ":the_crimson");
    }
}
