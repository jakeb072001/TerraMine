package terramine.mixin.player;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(Inventory.class)
public abstract class InventoryMixin implements Container {
    @Shadow
    @Final
    @Mutable
    private List<NonNullList<ItemStack>> compartments;

    // Accessories
    private NonNullList<ItemStack> accessories;
    private NonNullList<ItemStack> accessoriesVanity;
    private NonNullList<ItemStack> accessoriesDye;

    // Vanity Armor
    private NonNullList<ItemStack> armorVanity;
    private NonNullList<ItemStack> armorDye;

    @Inject(method = "<init>*", at = @At("RETURN"))
    private void initSlots(Player playerEntity, CallbackInfo info) {
        this.accessories = NonNullList.withSize(7, ItemStack.EMPTY);
        this.accessoriesVanity = NonNullList.withSize(7, ItemStack.EMPTY);
        this.accessoriesDye = NonNullList.withSize(7, ItemStack.EMPTY);
        this.armorVanity = NonNullList.withSize(4, ItemStack.EMPTY);
        this.armorDye = NonNullList.withSize(4, ItemStack.EMPTY);
        this.compartments = new ArrayList<>(compartments);
        this.compartments.add(accessories);
        this.compartments.add(accessoriesVanity);
        this.compartments.add(accessoriesDye);
        this.compartments.add(armorVanity);
        this.compartments.add(armorDye);
        this.compartments = ImmutableList.copyOf(this.compartments);
    }

    @Inject(method = "save", at = @At("TAIL"))
    public void saveSlots(ListTag listTag, CallbackInfoReturnable<ListTag> cir) {
        int i;
        CompoundTag compoundTag;
        for(i = 0; i < this.accessories.size(); ++i) {
            if (!this.accessories.get(i).isEmpty()) {
                compoundTag = new CompoundTag();
                compoundTag.putByte("Slot", (byte)(i + 115));
                this.accessories.get(i).save(compoundTag);
                listTag.add(compoundTag);
            }
        }
        for(i = 0; i < this.accessoriesVanity.size(); ++i) {
            if (!this.accessoriesVanity.get(i).isEmpty()) {
                compoundTag = new CompoundTag();
                compoundTag.putByte("Slot", (byte)(i + 122));
                this.accessoriesVanity.get(i).save(compoundTag);
                listTag.add(compoundTag);
            }
        }
        for(i = 0; i < this.accessoriesDye.size(); ++i) {
            if (!this.accessoriesDye.get(i).isEmpty()) {
                compoundTag = new CompoundTag();
                compoundTag.putByte("Slot", (byte)(i + 129));
                this.accessoriesDye.get(i).save(compoundTag);
                listTag.add(compoundTag);
            }
        }
        for(i = 0; i < this.armorVanity.size(); ++i) {
            if (!this.armorVanity.get(i).isEmpty()) {
                compoundTag = new CompoundTag();
                compoundTag.putByte("Slot", (byte)(i + 136));
                this.armorVanity.get(i).save(compoundTag);
                listTag.add(compoundTag);
            }
        }
        for(i = 0; i < this.armorDye.size(); ++i) {
            if (!this.armorDye.get(i).isEmpty()) {
                compoundTag = new CompoundTag();
                compoundTag.putByte("Slot", (byte)(i + 140));
                this.armorDye.get(i).save(compoundTag);
                listTag.add(compoundTag);
            }
        }
    }

    @Inject(method = "load", at = @At("TAIL"))
    public void loadSlots(ListTag listTag, CallbackInfo ci) {
        this.accessories.clear();
        this.accessoriesVanity.clear();
        this.accessoriesDye.clear();
        this.armorVanity.clear();
        this.armorDye.clear();

        for(int i = 0; i < listTag.size(); ++i) {
            CompoundTag compoundTag = listTag.getCompound(i);
            int j = compoundTag.getByte("Slot") & 255;
            ItemStack itemStack = ItemStack.of(compoundTag);
            if (!itemStack.isEmpty()) {
                if (j >= 115 && j < this.accessories.size() + 115) {
                    this.accessories.set(j - 115, itemStack);
                } else if (j >= 122 && j < this.accessoriesVanity.size() + 122) {
                    this.accessoriesVanity.set(j - 122, itemStack);
                } else if (j >= 129 && j < this.accessoriesDye.size() + 129) {
                    this.accessoriesDye.set(j - 129, itemStack);
                } else if (j >= 136 && j < this.armorVanity.size() + 136) {
                    this.armorVanity.set(j - 136, itemStack);
                } else if (j >= 140 && j < this.armorDye.size() + 140) {
                    this.armorDye.set(j - 140, itemStack);
                }
            }
        }
    }

    @Inject(method = "getContainerSize", at = @At("HEAD"), cancellable = true)
    public void getSlotSize(CallbackInfoReturnable<Integer> info) {
        int size = 0;
        for (NonNullList<ItemStack> list : compartments) {
            size += list.size();
        }
        info.setReturnValue(size);
    }

    @Inject(method = "isEmpty", at = @At("HEAD"), cancellable = true)
    public void isSlotEmpty(CallbackInfoReturnable<Boolean> info) {
        if (!this.accessories.isEmpty() || !this.accessoriesVanity.isEmpty() || !this.accessoriesDye.isEmpty()
                || !this.armorVanity.isEmpty() || !this.armorDye.isEmpty()) {
            info.setReturnValue(false);
        }
    }
}
