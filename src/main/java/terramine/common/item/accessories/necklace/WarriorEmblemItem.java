package terramine.common.item.accessories.necklace;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import terramine.common.item.accessories.AccessoryTerrariaItem;

import java.util.UUID;

public class WarriorEmblemItem extends AccessoryTerrariaItem {

	@Override
	protected Multimap<Attribute, AttributeModifier> applyModifiers(ItemStack stack, LivingEntity entity, UUID uuid) {
		Multimap<Attribute, AttributeModifier> result = super.applyModifiers(stack, entity, uuid);
		AttributeModifier modifier = new AttributeModifier(uuid, "warrior_emblem_attack_damage", 0.15f, AttributeModifier.Operation.MULTIPLY_TOTAL);
		result.put(Attributes.ATTACK_DAMAGE, modifier);
		return result;
	}
}
