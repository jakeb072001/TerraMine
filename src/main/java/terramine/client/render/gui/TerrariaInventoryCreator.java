package terramine.client.render.gui;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.jetbrains.annotations.NotNull;
import terramine.TerraMine;
import terramine.common.init.ModComponents;
import terramine.common.init.ModScreenHandlerType;
import terramine.common.item.accessories.TrinketTerrariaItem;
import terramine.extensions.PlayerStorages;

public class TerrariaInventoryCreator extends AbstractContainerMenu {
    public static final ResourceLocation EMPTY_ARMOR_SLOT_HELMET = new ResourceLocation("item/empty_armor_slot_helmet");
    public static final ResourceLocation EMPTY_ARMOR_SLOT_CHESTPLATE = new ResourceLocation("item/empty_armor_slot_chestplate");
    public static final ResourceLocation EMPTY_ARMOR_SLOT_LEGGINGS = new ResourceLocation("item/empty_armor_slot_leggings");
    public static final ResourceLocation EMPTY_ARMOR_SLOT_BOOTS = new ResourceLocation("item/empty_armor_slot_boots");
    public static final ResourceLocation EMPTY_ACCESSORY_SLOT = TerraMine.id("gui/slots/accessory");
    public static final ResourceLocation EMPTY_ACCESSORY_VANITY_SLOT = TerraMine.id("gui/slots/accessory_vanity");
    public static final ResourceLocation EMPTY_ACCESSORY_DYE_SLOT = TerraMine.id("gui/slots/accessory_dye");
    static final ResourceLocation[] TEXTURE_EMPTY_SLOTS;
    private static final EquipmentSlot[] SLOT_IDS;
    private final CraftingContainer craftSlots = new CraftingContainer(this, 2, 2);
    private final ResultContainer resultSlots = new ResultContainer();

    public TerrariaInventoryCreator(final Player player) {
        super(ModScreenHandlerType.TERRARIA_CONTAINER, 10);
        Inventory inventory = player.getInventory();
        SimpleContainer terrariaInventory = ((PlayerStorages)player).getTerrariaInventory();

        int i;
        int j;
        for (i = 0; i < 5; ++i) {
            this.addSlot(new Slot(inventory, i, -1000, -1000));
        }

        // Armor
        for(i = 0; i < 4; ++i) {
            final EquipmentSlot equipmentSlot = SLOT_IDS[i];
            this.addSlot(new Slot(inventory, 39 - i, 8, -18 + i * 18) {
                public void set(@NotNull ItemStack itemStack) {
                    ItemStack itemStack2 = this.getItem();
                    super.set(itemStack);
                    player.onEquipItem(equipmentSlot, itemStack2, itemStack);
                }

                public int getMaxStackSize() {
                    return 1;
                }

                public boolean mayPlace(@NotNull ItemStack itemStack) {
                    return equipmentSlot == Mob.getEquipmentSlotForItem(itemStack);
                }

                public boolean mayPickup(@NotNull Player player) {
                    ItemStack itemStack = this.getItem();
                    return (itemStack.isEmpty() || player.isCreative() || !EnchantmentHelper.hasBindingCurse(itemStack)) && super.mayPickup(player);
                }

                public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                    return Pair.of(InventoryMenu.BLOCK_ATLAS, TEXTURE_EMPTY_SLOTS[equipmentSlot.getIndex()]);
                }
            });
        }

        // Inventory
        for(i = 0; i < 3; ++i) {
            for(j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + (i + 1) * 9, 8 + j * 18, 110 + i * 18));
            }
        }

        // Hotbar
        for(i = 0; i < 9; ++i) {
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 168));
        }

        // Shield
        this.addSlot(new Slot(inventory, 40, 62, 54) {
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
            }
        });

        // todo: add all the vanity and dye slots
        // Accessories
        // assets\minecraft\atlases\blocks.json
        for(i = 0; i < 7; ++i) {
            int k = i;
            this.addSlot(new Slot(terrariaInventory, i, 116, -18 + i * 18) {
                public void set(@NotNull ItemStack itemStack) {
                    //ItemStack itemStack2 = this.getItem();
                    super.set(itemStack);
                    //player.onEquipItem(equipmentSlot, itemStack2, itemStack);
                }

                public boolean isActive() {
                    // allows for adding slots during gameplay, for the 2 extra accessory slots players can get
                    return k < (5 + ModComponents.ACCESSORY_SLOTS_ADDER.get(player).get()); // todo: not updated live, only after reloading world
                }

                public int getMaxStackSize() {
                    return 1;
                }

                public boolean mayPlace(@NotNull ItemStack itemStack) {
                    return itemStack.getItem() instanceof TrinketTerrariaItem;
                }

                public boolean mayPickup(@NotNull Player player) {
                    ItemStack itemStack = this.getItem();
                    return !itemStack.isEmpty() && super.mayPickup(player);
                }

                public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                    return Pair.of(InventoryMenu.BLOCK_ATLAS, EMPTY_ACCESSORY_SLOT);
                }
            });
        }
    }

    public boolean stillValid(@NotNull Player player) {
        return true;
    }

    // todo: add accessory slots to quickMoveStack
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int i) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(i);
        if (slot != null && slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            itemStack = itemStack2.copy();
            EquipmentSlot equipmentSlot = Mob.getEquipmentSlotForItem(itemStack);
            if (i == 0) {
                if (!this.moveItemStackTo(itemStack2, 9, 45, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemStack2, itemStack);
            } else if (i >= 1 && i < 5) {
                if (!this.moveItemStackTo(itemStack2, 9, 45, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (i >= 5 && i < 9) {
                if (!this.moveItemStackTo(itemStack2, 9, 45, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (equipmentSlot.getType() == EquipmentSlot.Type.ARMOR && !this.slots.get(8 - equipmentSlot.getIndex()).hasItem()) {
                int j = 8 - equipmentSlot.getIndex();
                if (!this.moveItemStackTo(itemStack2, j, j + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (equipmentSlot == EquipmentSlot.OFFHAND && !this.slots.get(45).hasItem()) {
                if (!this.moveItemStackTo(itemStack2, 45, 46, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (i >= 9 && i < 36) {
                if (!this.moveItemStackTo(itemStack2, 36, 45, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (i >= 36 && i < 45) {
                if (!this.moveItemStackTo(itemStack2, 9, 36, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemStack2, 9, 45, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemStack2);
            if (i == 0) {
                player.drop(itemStack2, false);
            }
        }

        return itemStack;
    }

    public int getSize() {
        return 15; // what do this do?
    }

    static {
        TEXTURE_EMPTY_SLOTS = new ResourceLocation[]{EMPTY_ARMOR_SLOT_BOOTS, EMPTY_ARMOR_SLOT_LEGGINGS, EMPTY_ARMOR_SLOT_CHESTPLATE, EMPTY_ARMOR_SLOT_HELMET};
        SLOT_IDS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    }
}
