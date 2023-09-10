package terramine.common.misc;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.*;

// todo: make better, have different lists for each slot type
// probably also don't need some of this other stuff, trinkets used it but i don't think i need some of it, not sure
public class TerrariaInventory extends SimpleContainer {
    private final Map<UUID, AttributeModifier> modifiers = new HashMap<>();
    private final Set<AttributeModifier> persistentModifiers = new HashSet<>();
    private final Set<AttributeModifier> cachedModifiers = new HashSet<>();
    private final Multimap<AttributeModifier.Operation, AttributeModifier> modifiersByOperation = HashMultimap.create();

    public TerrariaInventory(int i) {
        super(i);
    }

    public Collection<AttributeModifier> getModifiersByOperation(AttributeModifier.Operation operation) {
        return this.modifiersByOperation.get(operation);
    }

    public void addModifier(AttributeModifier modifier) {
        this.modifiers.put(modifier.getId(), modifier);
        this.getModifiersByOperation(modifier.getOperation()).add(modifier);
        this.setChanged();
    }

    public void addPersistentModifier(AttributeModifier modifier) {
        this.addModifier(modifier);
        this.persistentModifiers.add(modifier);
    }

    public void removeModifier(UUID uuid) {
        AttributeModifier modifier = this.modifiers.remove(uuid);
        if (modifier != null) {
            this.persistentModifiers.remove(modifier);
            this.getModifiersByOperation(modifier.getOperation()).remove(modifier);
            this.setChanged();
        }
    }

    public void removeCachedModifier(AttributeModifier attributeModifier) {
        this.cachedModifiers.remove(attributeModifier);
    }

    public void clearCachedModifiers() {
        for (AttributeModifier cachedModifier : this.cachedModifiers) {
            this.removeModifier(cachedModifier.getId());
        }
        this.cachedModifiers.clear();
    }

    public void copyFrom(TerrariaInventory other) {
        this.modifiers.clear();
        this.modifiersByOperation.clear();
        this.persistentModifiers.clear();
        other.modifiers.forEach((uuid, modifier) -> this.addModifier(modifier));
        for (AttributeModifier persistentModifier : other.persistentModifiers) {
            this.addPersistentModifier(persistentModifier);
        }
    }

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        if (!this.persistentModifiers.isEmpty()) {
            ListTag listTag = new ListTag();
            for (AttributeModifier entityAttributeModifier : this.persistentModifiers) {
                listTag.add(entityAttributeModifier.save());
            }

            tag.put("PersistentModifiers", listTag);
        }
        if (!this.modifiers.isEmpty()) {
            ListTag listTag = new ListTag();
            this.modifiers.forEach((uuid, modifier) -> {
                if (!this.persistentModifiers.contains(modifier)) {
                    listTag.add(modifier.save());
                }
            });

            tag.put("CachedModifiers", listTag);
        }
        return tag;
    }

    public void fromTag(CompoundTag tag) {
        if (tag.contains("PersistentModifiers", 9)) {
            ListTag listTag = tag.getList("PersistentModifiers", 10);

            for(int i = 0; i < listTag.size(); ++i) {
                AttributeModifier entityAttributeModifier = AttributeModifier.load(listTag.getCompound(i));
                if (entityAttributeModifier != null) {
                    this.addPersistentModifier(entityAttributeModifier);
                }
            }
        }

        if (tag.contains("CachedModifiers", 9)) {
            ListTag listTag = tag.getList("CachedModifiers", 10);

            for (int i = 0; i < listTag.size(); ++i) {
                AttributeModifier entityAttributeModifier = AttributeModifier.load(listTag.getCompound(i));
                if (entityAttributeModifier != null) {
                    this.cachedModifiers.add(entityAttributeModifier);
                    this.addModifier(entityAttributeModifier);
                }
            }
        }
    }

    public CompoundTag getSyncTag() {
        CompoundTag tag = new CompoundTag();
        if (!this.modifiers.isEmpty()) {
            ListTag listTag = new ListTag();
            for (Map.Entry<UUID, AttributeModifier> modifier : this.modifiers.entrySet()) {
                listTag.add(modifier.getValue().save());
            }

            tag.put("Modifiers", listTag);
        }
        return tag;
    }

    public void applySyncTag(CompoundTag tag) {
        this.modifiers.clear();
        this.persistentModifiers.clear();
        this.modifiersByOperation.clear();
        if (tag.contains("Modifiers", 9)) {
            ListTag listTag = tag.getList("Modifiers", 10);

            for (int i = 0; i < listTag.size(); ++i) {
                AttributeModifier entityAttributeModifier = AttributeModifier.load(listTag.getCompound(i));
                if (entityAttributeModifier != null) {
                    this.addModifier(entityAttributeModifier);
                }
            }
        }
        this.setChanged();
    }

    public boolean contains(ItemStack itemStack) {
        for (ItemStack itemStack2 : this.items) {
            if (!itemStack2.isEmpty() && itemStack2.is(itemStack.getItem())) {
                return true;
            }
        }

        return false;
    }
}
