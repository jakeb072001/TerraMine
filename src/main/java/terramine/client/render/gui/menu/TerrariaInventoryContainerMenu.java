package terramine.client.render.gui.menu;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.jetbrains.annotations.NotNull;
import terramine.TerraMine;
import terramine.common.init.ModComponents;
import terramine.common.init.ModScreenHandlerType;
import terramine.common.item.accessories.AccessoryTerrariaItem;
import terramine.common.item.accessories.ShieldAccessoryLikeItem;
import terramine.common.item.dye.BasicDye;
import terramine.common.misc.TerrariaInventory;
import terramine.common.network.ServerPacketHandler;
import terramine.common.network.types.ItemNetworkType;
import terramine.extensions.PlayerStorages;

import java.util.Map;

public class TerrariaInventoryContainerMenu extends AbstractContainerMenu {
    public static final ResourceLocation EMPTY_ARMOR_SLOT_HELMET = ResourceLocation.withDefaultNamespace("container/slot/helmet");
    public static final ResourceLocation EMPTY_ARMOR_SLOT_CHESTPLATE = ResourceLocation.withDefaultNamespace("container/slot/chestplate");
    public static final ResourceLocation EMPTY_ARMOR_SLOT_LEGGINGS = ResourceLocation.withDefaultNamespace("container/slot/leggings");
    public static final ResourceLocation EMPTY_ARMOR_SLOT_BOOTS = ResourceLocation.withDefaultNamespace("container/slot/boots");
    public static final ResourceLocation EMPTY_ACCESSORY_SLOT = TerraMine.id("slots/accessory");
    public static final ResourceLocation EMPTY_ACCESSORY_VANITY_SLOT = TerraMine.id("slots/accessory_vanity");
    public static final ResourceLocation EMPTY_ACCESSORY_DYE_SLOT = TerraMine.id("slots/accessory_dye");
    private static final Map<EquipmentSlot, ResourceLocation> TEXTURE_EMPTY_SLOTS;
    private static final EquipmentSlot[] SLOT_IDS;

    public TerrariaInventoryContainerMenu(final Player player) {
        super(ModScreenHandlerType.TERRARIA_CONTAINER, 10);
        addSlots(player);
    }

    private void addSlots(Player player) {
        Inventory inventory = player.getInventory();
        TerrariaInventory terrariaInventory = ((PlayerStorages)player).getTerrariaInventory();

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
                    return equipmentSlot == player.getEquipmentSlotForItem(itemStack);
                }

                public boolean mayPickup(@NotNull Player player) {
                    ItemStack itemStack = this.getItem();
                    return (itemStack.isEmpty() || player.isCreative() || !EnchantmentHelper.has(itemStack, EnchantmentEffectComponents.PREVENT_ARMOR_CHANGE)) && super.mayPickup(player);
                }

                public ResourceLocation getNoItemIcon() {
                    return TEXTURE_EMPTY_SLOTS.get(equipmentSlot);
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
            public ResourceLocation getNoItemIcon() {
                return InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD;
            }
        });

        // Accessories
        createAccessorySlots(player, terrariaInventory, EMPTY_ACCESSORY_SLOT, 0);
        createAccessorySlots(player, terrariaInventory, EMPTY_ACCESSORY_VANITY_SLOT, 1);
        createAccessorySlots(player, terrariaInventory, EMPTY_ACCESSORY_DYE_SLOT, 2);

        // Shield Vanity
        this.addSlot(new Slot(terrariaInventory, 21, 80, 54) {
            public void set(@NotNull ItemStack itemStack) {
                super.set(itemStack);
                updatePacket(player, terrariaInventory, 21);
            }

            public boolean mayPlace(@NotNull ItemStack itemStack) {
                return itemStack.getItem() instanceof ShieldItem || itemStack.getItem() instanceof ShieldAccessoryLikeItem;
            }

            public ResourceLocation getNoItemIcon() {
                return InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD;
            }
        });
        this.addSlot(new Slot(terrariaInventory, 22, 98, 54) {
            public void set(@NotNull ItemStack itemStack) {
                super.set(itemStack);
                updatePacket(player, terrariaInventory, 22);
            }

            public boolean mayPlace(@NotNull ItemStack itemStack) {
                return itemStack.getItem() instanceof BasicDye;
            }

            public int getMaxStackSize() {
                return 1;
            }

            public ResourceLocation getNoItemIcon() {
                return EMPTY_ACCESSORY_DYE_SLOT;
            }
        });

        // Armor Vanity
        createExtraArmorSlots(player, terrariaInventory, 26, false);
        createExtraArmorSlots(player, terrariaInventory, 30, true);
    }

    private void createAccessorySlots(Player player, TerrariaInventory terrariaInventory, ResourceLocation texture, int accessoryType) {
        for(int i = 0; i < 7; ++i) {
            int finalI = i;
            this.addSlot(new Slot(terrariaInventory, finalI + (accessoryType * 7), 116 + (accessoryType * 18), -18 + finalI * 18) {
                public void set(@NotNull ItemStack itemStack) {
                    ItemStack itemStack2 = this.getItem();
                    super.set(itemStack);
                    onEquipAccessory(player, itemStack2, itemStack);
                    updatePacket(player, terrariaInventory, finalI + (accessoryType * 7));
                }

                public boolean isActive() {
                    // allows for adding slots during gameplay, for the 2 extra accessory slots players can get
                    return finalI < (5 + ModComponents.ACCESSORY_SLOTS_ADDER.get(player).get());
                }

                public int getMaxStackSize() {
                    return 1;
                }

                public boolean mayPlace(@NotNull ItemStack itemStack) {
                    if (accessoryType == 2) {
                        return itemStack.getItem() instanceof BasicDye;
                    }
                    return itemStack.getItem() instanceof AccessoryTerrariaItem;
                }

                public boolean mayPickup(@NotNull Player player) {
                    ItemStack itemStack = this.getItem();
                    return !itemStack.isEmpty() && super.mayPickup(player);
                }

                public ResourceLocation getNoItemIcon() {
                    return texture;
                }
            });
        }
    }

    private void createExtraArmorSlots(Player player, TerrariaInventory terrariaInventory, int slotValue, boolean isDye) {
        for(int i = 0; i < 4; ++i) {
            final EquipmentSlot equipmentSlot = SLOT_IDS[i];
            int finalI = i;
            this.addSlot(new Slot(terrariaInventory, slotValue - finalI, 8 + ((isDye ? 2 : 1) * 18), -18 + finalI * 18) {
                public void set(@NotNull ItemStack itemStack) {
                    ItemStack itemStack2 = this.getItem();
                    super.set(itemStack);
                    player.onEquipItem(equipmentSlot, itemStack2, itemStack);
                    updatePacket(player, terrariaInventory, slotValue - finalI);
                }

                public int getMaxStackSize() {
                    return 1;
                }

                public boolean mayPlace(@NotNull ItemStack itemStack) {
                    if (isDye) {
                        return itemStack.getItem() instanceof BasicDye;
                    }
                    return equipmentSlot == player.getEquipmentSlotForItem(itemStack);
                }

                public boolean mayPickup(@NotNull Player player) {
                    ItemStack itemStack = this.getItem();
                    return !itemStack.isEmpty() && super.mayPickup(player);
                }

                public ResourceLocation getNoItemIcon() {
                    // assets\minecraft\atlases\blocks.json
                    if (isDye) {
                        return EMPTY_ACCESSORY_DYE_SLOT;
                    }
                    return TEXTURE_EMPTY_SLOTS.get(equipmentSlot);
                }
            });
        }
    }

    private void onEquipAccessory(Player player, ItemStack itemStack, ItemStack itemStack2) {
        boolean bl = itemStack2.isEmpty() && itemStack.isEmpty();
        if (!bl && !ItemStack.isSameItem(itemStack, itemStack2)) {
            if (itemStack2.getItem() instanceof AccessoryTerrariaItem accessory) {
                AccessoryTerrariaItem.SoundInfo sound = accessory.getEquipSoundInfo();
                player.playSound(sound.soundEvent(), sound.volume(), sound.pitch());
            }
        }
    }

    private void updatePacket(Player player, TerrariaInventory terrariaInventory, int slot) {
        for (Player otherPlayer : player.level().players()) {
            if (otherPlayer instanceof ServerPlayer serverPlayer) {
                ServerPlayNetworking.send(serverPlayer, new ItemNetworkType(terrariaInventory.getItem(slot), slot, player.getUUID(), ServerPacketHandler.UPDATE_INVENTORY_PACKET_ID));
            }
        }
    }

    public boolean stillValid(@NotNull Player player) {
        return true;
    }

    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int i) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(i);
        if (slot != null && slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            itemStack = itemStack2.copy();
            EquipmentSlot equipmentSlot = player.getEquipmentSlotForItem(itemStack);
            int extraSlots = ModComponents.ACCESSORY_SLOTS_ADDER.get(player).get();
            if (i == 0) {
                if (!this.moveItemStackTo(itemStack2, 9, 45, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemStack2, itemStack);
            } else if (i >= 45 && i < 76) {
                if (!this.moveItemStackTo(itemStack2, 9, 45, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemStack2.getItem() instanceof AccessoryTerrariaItem) {
                if (!this.moveItemStackTo(itemStack2, 45, 51 + extraSlots, false)) {
                    if (!this.moveItemStackTo(itemStack2, 53, 58 + extraSlots, false)) {
                        if (!this.moveItemStackTo(itemStack2, 67, 68, false)) {
                            if (!this.moveItemStackTo(itemStack2, 9, 45, false)) {
                                return ItemStack.EMPTY;
                            }
                        }
                    }
                }
            //} else if (itemStack2.getItem() instanceof BasicDye) {
            //    if (!this.moveItemStackTo(itemStack2, 59, 63 + extraSlots, false)) {
            //        if (!this.moveItemStackTo(itemStack2, 64, 65, false)) {
            //            if (!this.moveItemStackTo(itemStack2, 72, 76, false)) {
            //                return ItemStack.EMPTY;
            //            }
            //        }
            //    }
            } else if (itemStack2.getItem() instanceof ShieldItem) {
                if (!this.moveItemStackTo(itemStack2, 67, 68, false)) {
                    if (!this.moveItemStackTo(itemStack2, 9, 45, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (i >= 1 && i < 5) {
                if (!this.moveItemStackTo(itemStack2, 9, 45, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (i >= 5 && i < 9) {
                if (!this.moveItemStackTo(itemStack2, 9, 45, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (equipmentSlot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR && !this.slots.get(8 - equipmentSlot.getIndex()).hasItem()) {
                int j = 8 - equipmentSlot.getIndex();
                if (!this.moveItemStackTo(itemStack2, j, j + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (equipmentSlot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR && !this.slots.get(72 - equipmentSlot.getIndex()).hasItem()) {
                int j = 72 - equipmentSlot.getIndex();
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
        TEXTURE_EMPTY_SLOTS = Map.of(EquipmentSlot.FEET, EMPTY_ARMOR_SLOT_BOOTS, EquipmentSlot.LEGS, EMPTY_ARMOR_SLOT_LEGGINGS, EquipmentSlot.CHEST, EMPTY_ARMOR_SLOT_CHESTPLATE, EquipmentSlot.HEAD, EMPTY_ARMOR_SLOT_HELMET);
        SLOT_IDS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    }
}
