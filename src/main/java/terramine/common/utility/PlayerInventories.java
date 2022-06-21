package terramine.common.utility;

import net.fabricmc.fabric.api.transfer.v1.storage.base.SidedStorageBlockEntity;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import terramine.common.entity.block.ChestEntity;
import terramine.common.entity.block.PiggyBankEntity;
import terramine.extensions.PlayerStorages;

public class PlayerInventories extends SimpleContainer implements SidedStorageBlockEntity {

    private ChestEntity activeBlockEntity;
    private final Player player;

    public PlayerInventories(Player player) {
        super();
        this.player = player;
    }

    public void setActiveBlockEntity(ChestEntity blockEntity) {
        this.activeBlockEntity = blockEntity;
    }

    @Override
    public int getContainerSize() {
        return 40;
    }

    @Override
    public ItemStack getItem(int slot) {
        if (activeBlockEntity instanceof PiggyBankEntity)
            return ((PlayerStorages) player).getPiggyBankInventory().getItem(slot);
        return ((PlayerStorages) player).getSafeInventory().getItem(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        if (activeBlockEntity instanceof PiggyBankEntity)
            return ((PlayerStorages) player).getPiggyBankInventory().removeItem(slot, amount);
        return ((PlayerStorages) player).getSafeInventory().removeItem(slot, amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        if (activeBlockEntity instanceof PiggyBankEntity)
            return ((PlayerStorages) player).getPiggyBankInventory().removeItemNoUpdate(slot);
        return ((PlayerStorages) player).getSafeInventory().removeItemNoUpdate(slot);
    }

    @Override
    public void setItem(int slot, @NotNull ItemStack stack) {
        if (activeBlockEntity instanceof PiggyBankEntity)
            ((PlayerStorages) player).getPiggyBankInventory().setItem(slot, stack);
        ((PlayerStorages) player).getSafeInventory().setItem(slot, stack);
    }

    @Override
    public void setChanged() {
        if (activeBlockEntity instanceof PiggyBankEntity)
            ((PlayerStorages) player).getPiggyBankInventory().setChanged();
        ((PlayerStorages) player).getSafeInventory().setChanged();
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }

    @Override
    public boolean isEmpty() {
        if (activeBlockEntity instanceof PiggyBankEntity)
            return ((PlayerStorages) player).getPiggyBankInventory().isEmpty();
        return ((PlayerStorages) player).getSafeInventory().isEmpty();
    }

    @Override
    public void clearContent() {
        ((PlayerStorages) player).getPiggyBankInventory().clearContent();
        ((PlayerStorages) player).getSafeInventory().clearContent();
        this.setChanged();
    }

    public void startOpen(@NotNull Player player) {
        if (this.activeBlockEntity != null) {
            this.activeBlockEntity.startOpen(player);
        }
    }

    @Override
    public void stopOpen(@NotNull Player player) {
        if (this.activeBlockEntity != null) {
            this.activeBlockEntity.startOpen(player);
        }
        this.activeBlockEntity = null;
        ((PlayerStorages) player).getPiggyBankInventory().stopOpen(player);
        ((PlayerStorages) player).getSafeInventory().stopOpen(player);
    }
}
