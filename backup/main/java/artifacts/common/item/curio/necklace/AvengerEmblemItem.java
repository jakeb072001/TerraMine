package terracraft.common.item.curio.necklace;

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

public class AvengerEmblemItem extends TrinketArtifactItem {

	@Override
	protected Multimap<Attribute, AttributeModifier> applyModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
		Multimap<Attribute, AttributeModifier> result = super.applyModifiers(stack, slot, entity, uuid);
		AttributeModifier modifier = new AttributeModifier(uuid,
				Artifacts.id("avenger_emblem_attack_damage").toString(),
				1.2f, AttributeModifier.Operation.ADDITION);
		result.put(Attributes.ATTACK_DAMAGE, modifier);
		return result;
	}
}
