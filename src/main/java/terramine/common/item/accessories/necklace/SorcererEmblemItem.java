package terramine.common.item.accessories.necklace;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import terramine.TerraMine;
import terramine.common.init.ModAttributes;
import terramine.common.item.accessories.TrinketTerrariaItem;

import java.util.UUID;

public class SorcererEmblemItem extends TrinketTerrariaItem {

	@Override // todo: none of the emblems work right now, zero clue as to why though
	protected Multimap<Attribute, AttributeModifier> applyModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
		Multimap<Attribute, AttributeModifier> result = super.applyModifiers(stack, slot, entity, uuid);
		AttributeModifier modifier = new AttributeModifier(uuid, "sorcerer_emblem_magic_attack_damage", 0.15f, AttributeModifier.Operation.MULTIPLY_TOTAL);
		result.put(ModAttributes.MAGIC_ATTACK_DAMAGE, modifier);
		return result;
	}
}
