package terramine.common.utility;

import net.fabricmc.fabric.api.transfer.v1.storage.base.SidedStorageBlockEntity;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import terramine.common.entity.block.ChestEntity;

public class PlayerInventories extends SimpleContainer implements SidedStorageBlockEntity {

    private ChestEntity blockEntity;
    private final SimpleContainer inventory;

    public PlayerInventories(SimpleContainer inventory, ChestEntity blockEntity) {
        super();
        this.inventory = inventory;
        this.blockEntity = blockEntity;
    }

    @Override
    public int getContainerSize() {
        return 40;
    }

    @Override
    public ItemStack getItem(int slot) {
        return inventory.getItem(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return inventory.removeItem(slot, amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return inventory.removeItemNoUpdate(slot);
    }

    @Override
    public void setItem(int slot, @NotNull ItemStack stack) {
        inventory.setItem(slot, stack);
    }

    @Override
    public void setChanged() {
        inventory.setChanged();
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    @Override
    public void clearContent() {
        inventory.clearContent();
        this.setChanged();
    }

    public void startOpen(@NotNull Player player) {
        if (this.blockEntity != null) {
            this.blockEntity.startOpen(player);
        }
    }

    @Override
    public void stopOpen(@NotNull Player player) {
        if (this.blockEntity != null) {
            this.blockEntity.startOpen(player);
        }
        this.blockEntity = null;
        inventory.stopOpen(player);
    }
}
