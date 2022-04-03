package terracraft.common.item.curio.old;

import terracraft.Artifacts;
import terracraft.common.item.curio.TrinketArtifactItem;
import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class SteadfastSpikesItem extends TrinketArtifactItem {

    @Override
	protected Multimap<Attribute, AttributeModifier> applyModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
		Multimap<Attribute, AttributeModifier> modifiers = super.applyModifiers(stack, slot, entity, uuid);
		AttributeModifier modifier = new AttributeModifier(uuid,
				Artifacts.id("steadfast_spikes_knockback_resistance").toString(),
				1, AttributeModifier.Operation.ADDITION);
		modifiers.put(Attributes.KNOCKBACK_RESISTANCE, modifier);
		return modifiers;
	}
}
