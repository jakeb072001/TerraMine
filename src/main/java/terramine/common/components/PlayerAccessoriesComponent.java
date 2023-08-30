package terramine.common.components;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import terramine.common.misc.TerrariaInventory;
import terramine.extensions.accessories.AccessoriesComponent;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PlayerAccessoriesComponent implements AccessoriesComponent, AutoSyncedComponent {
    public Map<String, Map<String, TerrariaInventory>> inventory = new HashMap<>();
    public int size;
    public Player player;

    private boolean syncing;

    public PlayerAccessoriesComponent(Player player) {
        this.player = player;
    }

    public Player getEntity() {
        return this.player;
    }

    @Override
    public Map<String, Map<String, TerrariaInventory>> getInventory() {
        return inventory;
    }

    @Override
    public void addTemporaryModifiers(Multimap<String, AttributeModifier> modifiers) {
        for (Map.Entry<String, Collection<AttributeModifier>> entry : modifiers.asMap().entrySet()) {
            String[] keys = entry.getKey().split("/");
            String group = keys[0];
            String slot = keys[1];
            for (AttributeModifier modifier : entry.getValue()) {
                Map<String, TerrariaInventory> groupInv = this.inventory.get(group);
                if (groupInv != null) {
                    TerrariaInventory inv = groupInv.get(slot);
                    if (inv != null) {
                        inv.addModifier(modifier);
                    }
                }
            }
        }
    }

    @Override
    public void removeModifiers(Multimap<String, AttributeModifier> modifiers) {
        for (Map.Entry<String, Collection<AttributeModifier>> entry : modifiers.asMap().entrySet()) {
            String[] keys = entry.getKey().split("/");
            String group = keys[0];
            String slot = keys[1];
            for (AttributeModifier modifier : entry.getValue()) {
                Map<String, TerrariaInventory> groupInv = this.inventory.get(group);
                if (groupInv != null) {
                    TerrariaInventory inv = groupInv.get(slot);
                    if (inv != null) {
                        inv.removeModifier(modifier.getId());
                    }
                }
            }
        }
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        NonNullList<ItemStack> dropped = NonNullList.create();
        for (String groupKey : tag.getAllKeys()) {
            CompoundTag groupTag = tag.getCompound(groupKey);
            if (groupTag != null) {
                Map<String, TerrariaInventory> groupSlots = this.inventory.get(groupKey);
                if (groupSlots != null) {
                    for (String slotKey : groupTag.getAllKeys()) {
                        CompoundTag slotTag = groupTag.getCompound(slotKey);
                        ListTag list = slotTag.getList("Items", NbtType.COMPOUND);
                        TerrariaInventory inv = groupSlots.get(slotKey);

                        if (inv != null) {
                            inv.fromTag(slotTag.getCompound("Metadata"));
                        }

                        for (int i = 0; i < list.size(); i++) {
                            CompoundTag c = list.getCompound(i);
                            ItemStack stack = ItemStack.of(c);
                            if (inv != null && i < inv.getContainerSize()) {
                                inv.setItem(i, stack);
                            } else {
                                dropped.add(stack);
                            }
                        }
                    }
                } else {
                    for (String slotKey : groupTag.getAllKeys()) {
                        CompoundTag slotTag = groupTag.getCompound(slotKey);
                        ListTag list = slotTag.getList("Items", NbtType.COMPOUND);
                        for (int i = 0; i < list.size(); i++) {
                            CompoundTag c = list.getCompound(i);
                            dropped.add(ItemStack.of(c));
                        }
                    }
                }
            }
        }
        for (ItemStack itemStack : dropped) {
            this.player.spawnAtLocation(itemStack);
        }
        Multimap<String, AttributeModifier> slotMap = HashMultimap.create();
        //this.forEach((ref, stack) -> {
        //    if (!stack.isEmpty()) {
        //        UUID uuid = SlotAttributes.getUuid(ref);
        //        Accessories trinket = TrinketsApi.getTrinket(stack.getItem());
        //        Multimap<Attribute, AttributeModifier> map = trinket.getModifiers(stack, player, uuid);
        //        for (Attribute entityAttribute : map.keySet()) {
        //            if (entityAttribute instanceof SlotAttributes.SlotEntityAttribute slotEntityAttribute) {
        //                slotMap.putAll(slotEntityAttribute.slot, map.get(entityAttribute));
        //            }
        //        }
        //    }
        //});
        for (Map.Entry<String, Map<String, TerrariaInventory>> groupEntry : this.getInventory().entrySet()) {
            for (Map.Entry<String, TerrariaInventory> slotEntry : groupEntry.getValue().entrySet()) {
                String group = groupEntry.getKey();
                String slot = slotEntry.getKey();
                String key = group + "/" + slot;
                Collection<AttributeModifier> modifiers = slotMap.get(key);
                TerrariaInventory inventory = slotEntry.getValue();
                for (AttributeModifier modifier : modifiers) {
                    inventory.removeCachedModifier(modifier);
                }
                inventory.clearCachedModifiers();
            }
        }
    }

    @Override
    public void applySyncPacket(FriendlyByteBuf buf) {
        CompoundTag tag = buf.readNbt();

        if (tag != null) {

            for (String groupKey : tag.getAllKeys()) {
                CompoundTag groupTag = tag.getCompound(groupKey);

                if (groupTag != null) {
                    Map<String, TerrariaInventory> groupSlots = this.inventory.get(groupKey);

                    if (groupSlots != null) {

                        for (String slotKey : groupTag.getAllKeys()) {
                            CompoundTag slotTag = groupTag.getCompound(slotKey);
                            ListTag list = slotTag.getList("Items", NbtType.COMPOUND);
                            TerrariaInventory inv = groupSlots.get(slotKey);

                            if (inv != null) {
                                inv.applySyncTag(slotTag.getCompound("Metadata"));
                            }

                            for (int i = 0; i < list.size(); i++) {
                                CompoundTag c = list.getCompound(i);
                                ItemStack stack = ItemStack.of(c);
                                if (inv != null && i < inv.getContainerSize()) {
                                    inv.setItem(i, stack);
                                }
                            }
                        }
                    }
                }
            }

            //((TrinketPlayerScreenHandler) player.playerScreenHandler).trinkets$updateTrinketSlots(false);
        }
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        for (Map.Entry<String, Map<String, TerrariaInventory>> group : this.getInventory().entrySet()) {
            CompoundTag groupTag = new CompoundTag();
            for (Map.Entry<String, TerrariaInventory> slot : group.getValue().entrySet()) {
                CompoundTag slotTag = new CompoundTag();
                ListTag list = new ListTag();
                TerrariaInventory inv = slot.getValue();
                for (int i = 0; i < inv.getContainerSize(); i++) {
                    CompoundTag c = inv.getItem(i).save(new CompoundTag());
                    list.add(c);
                }
                slotTag.put("Metadata", this.syncing ? inv.getSyncTag() : inv.toTag());
                slotTag.put("Items", list);
                groupTag.put(slot.getKey(), slotTag);
            }
            tag.put(group.getKey(), groupTag);
        }
    }

    @Override
    public void writeSyncPacket(FriendlyByteBuf buf, ServerPlayer recipient) {
        this.syncing = true;
        CompoundTag tag = new CompoundTag();
        this.writeToNbt(tag);
        this.syncing = false;
        buf.writeNbt(tag);
    }
}
