package terramine.extensions;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public interface Accessories {
    default void tick(ItemStack stack, Player player) {
    }

    default void onEquip(ItemStack stack, Player player) {
    }

    default void onUnequip(ItemStack stack, Player player) {
    }

    default Multimap<Attribute, AttributeModifier> getModifiers(ItemStack stack, Player player, UUID uuid) {
        Multimap<Attribute, AttributeModifier> map = Multimaps.newMultimap(Maps.newLinkedHashMap(), ArrayList::new);

        if (stack.hasTag() && stack.getTag().contains("AccessoriesAttributeModifiers", 9)) {
            ListTag list = stack.getTag().getList("AccessoriesAttributeModifiers", 10);

            for (int i = 0; i < list.size(); i++) {
                CompoundTag tag = list.getCompound(i);

                Optional<Attribute> optional = BuiltInRegistries.ATTRIBUTE
                        .getOptional(ResourceLocation.tryParse(tag.getString("AttributeName")));

                if (optional.isPresent()) {
                    AttributeModifier entityAttributeModifier = AttributeModifier.load(tag);

                    if (entityAttributeModifier != null
                            && entityAttributeModifier.getId().getLeastSignificantBits() != 0L
                            && entityAttributeModifier.getId().getMostSignificantBits() != 0L) {
                        map.put(optional.get(), entityAttributeModifier);
                    }
                }
            }
        }
        return map;
    }
}
