package terramine.common.misc;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TreasureBagInventory implements ImplementedInventory {
    private final ItemStack stack;
    private final NonNullList<ItemStack> items = NonNullList.withSize(9, ItemStack.EMPTY);

    public TreasureBagInventory(ItemStack stack, Player player, ResourceLocation lootTable)
    {
        this.stack = stack;
        CompoundTag tag = stack.getTagElement("treasure_bag");

        if (tag != null) {
            ContainerHelper.loadAllItems(tag, items);
        } else {
            unpackLootTable(player, lootTable, player.getRandom().nextLong());
        }
    }

    // todo: right now, items aren't getting stacked, which means the bag gets filled up with 1 type of item most times
    public void unpackLootTable(@Nullable Player player, ResourceLocation lootTableLocation, long lootTableSeed) {
        if (lootTableLocation != null && player.level().getServer() != null) {
            LootTable lootTable = player.level().getServer().getLootData().getLootTable(lootTableLocation);
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.GENERATE_LOOT.trigger((ServerPlayer)player, lootTableLocation);
            }

            LootParams.Builder builder = (new LootParams.Builder((ServerLevel)player.level())).withParameter(LootContextParams.ORIGIN, player.position());
            lootTable.fill(this, builder.create(LootContextParamSets.CHEST), lootTableSeed);
            setChanged();
        }
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
        CompoundTag tag = stack.getOrCreateTagElement("treasure_bag");
        ContainerHelper.saveAllItems(tag, items);
    }
}
