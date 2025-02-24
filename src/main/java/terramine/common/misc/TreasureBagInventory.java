package terramine.common.misc;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ReloadableServerRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.storage.WorldData;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import terramine.TerraMine;
import terramine.common.init.ModComponents;
import terramine.common.init.ModLootTables;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class TreasureBagInventory implements ImplementedInventory {
    private final ItemStack stack;
    private final NonNullList<ItemStack> items = NonNullList.withSize(9, ItemStack.EMPTY);

    public TreasureBagInventory(ItemStack stack, Player player, ResourceKey<LootTable> lootTable)
    {
        this.stack = stack;
        ItemContainerContents tag = stack.get(DataComponents.CONTAINER);

        if (tag != null) {
            //ContainerHelper.loadAllItems(tag, items);
            tag.copyInto(items);
        } else {
            unpackLootTable(player, lootTable, player.getRandom().nextLong());
        }
    }

    public void unpackLootTable(@Nullable Player player, ResourceKey<LootTable> lootTableLocation, long lootTableSeed) {
        if (lootTableLocation != null && player.level().getServer() != null) {
            LootTable lootTable = player.level().getServer().reloadableRegistries().getLootTable(lootTableLocation);
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.GENERATE_LOOT.trigger((ServerPlayer)player, lootTableLocation);
            }

            LootParams.Builder builder = (new LootParams.Builder((ServerLevel)player.level())).withParameter(LootContextParams.ORIGIN, player.position());
            fill(lootTable, this, builder.create(LootContextParamSets.CHEST), lootTableSeed);

            MinecraftServer server = player.getServer();
            ReloadableServerRegistries.Holder lootRegistries = server.reloadableRegistries();
            WorldData worldData = server.getWorldData();

            boolean isCrimson = ModComponents.EVIL_TYPE.get(worldData).get();
            boolean forceCrimson = TerraMine.CONFIG.worldgen.forceCrimson;
            boolean forceCorruption = TerraMine.CONFIG.worldgen.forceCorruption;

            LootTable corruptionLoot = null;
            LootTable crimsonLoot = null;

            // eye of cthulhu has some different loot in crimson and corruption worlds
            if (lootTableLocation == ModLootTables.EYE_OF_CTHULHU) {
                corruptionLoot = lootRegistries.getLootTable(ModLootTables.EYE_OF_CTHULHU_CORRUPTION);
                crimsonLoot = lootRegistries.getLootTable(ModLootTables.EYE_OF_CTHULHU_CRIMSON);
            }
            // todo: remove once crimson boss is added
            if (lootTableLocation == ModLootTables.EATER_OF_WORLDS) {
                corruptionLoot = lootRegistries.getLootTable(ModLootTables.EATER_OF_WORLDS_CORRUPTION);
                crimsonLoot = lootRegistries.getLootTable(ModLootTables.EATER_OF_WORLDS_CRIMSON);
            }

            if (corruptionLoot != null && crimsonLoot != null) {
                if ((!isCrimson && !forceCrimson) || forceCorruption) {
                    fill(corruptionLoot, this, builder.create(LootContextParamSets.CHEST), lootTableSeed);
                }
                if ((isCrimson && !forceCorruption) || forceCrimson) {
                    fill(crimsonLoot, this, builder.create(LootContextParamSets.CHEST), lootTableSeed);
                }
            }

            setChanged();
        }
    }

    public void fill(LootTable lootTable, Container container, LootParams lootParams, long l) {
        LootContext lootContext = (new LootContext.Builder(lootParams)).withOptionalRandomSeed(l).create(lootTable.randomSequence);
        ObjectArrayList<ItemStack> objectArrayList = getRandomItemsRaw(lootTable, lootParams);
        RandomSource randomSource = lootContext.getRandom();
        List<Integer> list = lootTable.getAvailableSlots(container, randomSource);
        shuffleAndSplitItems(objectArrayList, list.size(), randomSource);
        for (ItemStack itemStack : objectArrayList) {
            if (list.isEmpty()) {
                return;
            }

            if (itemStack.isEmpty()) {
                container.setItem(list.removeLast(), ItemStack.EMPTY);
            } else {
                container.setItem(list.removeLast(), itemStack);
            }
        }
    }

    public ObjectArrayList<ItemStack> getRandomItemsRaw(LootTable lootTable, LootParams lootParams) {
        ObjectArrayList<ItemStack> objectArrayList = new ObjectArrayList<>();
        Objects.requireNonNull(objectArrayList);
        LootContext lootContext = (new LootContext.Builder(lootParams)).create(lootTable.randomSequence);
        LootContext.VisitedEntry<?> visitedEntry = LootContext.createVisitedEntry(lootTable);

        if (lootContext.pushVisitedElement(visitedEntry)) {
            Consumer<ItemStack> consumer2 = LootItemFunction.decorate(lootTable.compositeFunction, objectArrayList::add, lootContext);
            //LootPool[] var5 = lootTable.pools;
            LootPool[] var5 = lootTable.pools.toArray(new LootPool[0]);

            for (LootPool lootPool : var5) {
                addRandomItems(lootPool, consumer2, lootContext);
            }

            lootContext.popVisitedElement(visitedEntry);
        } else {
            TerraMine.LOGGER.warn("Detected infinite loop in loot tables");
        }

        return objectArrayList;
    }

    public void addRandomItems(LootPool lootPool, Consumer<ItemStack> consumer, LootContext lootContext) {
        if (lootPool.compositeCondition.test(lootContext)) {
            RandomSource randomSource = lootContext.getRandom();
            Consumer<ItemStack> consumer2 = LootItemFunction.decorate(lootPool.compositeFunction, consumer, lootContext);
            int i = lootPool.rolls.getInt(lootContext) + Mth.floor(lootPool.bonusRolls.getFloat(lootContext) * lootContext.getLuck());
            //LootPoolEntryContainer[] lootContainer = lootPool.entries;
            LootPoolEntryContainer[] lootContainer = lootPool.entries.toArray(new LootPoolEntryContainer[0]);

            if (i > lootContainer.length) {
                i = lootContainer.length;
            }

            for(int j = 0; j < i; ++j) {
                LootPoolEntryContainer lootPoolEntryContainer = lootContainer[j];
                lootPoolEntryContainer.expand(lootContext, (lootPoolEntryx) -> {
                    int k = lootPoolEntryx.getWeight(lootContext.getLuck());
                    if (k >= randomSource.nextInt(0, 100)) {
                        lootPoolEntryx.createItemStack(consumer2, lootContext);
                    }
                });
            }

        }
    }

    private void shuffleAndSplitItems(ObjectArrayList<ItemStack> objectArrayList, int i, RandomSource randomSource) {
        List<ItemStack> list = Lists.newArrayList();
        Iterator<ItemStack> iterator = objectArrayList.iterator();

        while(iterator.hasNext()) {
            ItemStack itemStack = iterator.next();
            if (itemStack.isEmpty()) {
                iterator.remove();
            } else if (itemStack.getCount() > 1) {
                list.add(itemStack);
                iterator.remove();
            }
        }

        while(i - objectArrayList.size() - list.size() > 0 && !list.isEmpty()) {
            ItemStack itemStack2 = list.remove(Mth.nextInt(randomSource, 0, list.size() - 1));
            if (itemStack2.getCount() > 1 && randomSource.nextBoolean()) {
                list.add(itemStack2);
            } else {
                objectArrayList.add(itemStack2);
            }
        }

        objectArrayList.addAll(list);
        Util.shuffle(objectArrayList, randomSource);
    }

    @Override
    public void stopOpen(@NotNull Player player) {
        if (isEmpty()) {
            stack.shrink(1);
        }
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    public ItemStack getContainerItem() {
        return stack;
    }

    @Override
    public ItemStack removeItem(int slot, int count) {
        ItemStack result = ContainerHelper.removeItem(getItems(), slot, count);

        if (!result.isEmpty()) {
            setChanged();
        }

        return result;
    }

    @Override
    public void setChanged()
    {
        //CompoundTag tag = stack.getOrCreateTagElement("treasure_bag");
        //ContainerHelper.saveAllItems(tag, items);
        stack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(items));
    }
}
