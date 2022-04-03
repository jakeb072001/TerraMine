package terracraft.common.item.curio.necklace;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import terracraft.TerraCraft;
import terracraft.common.item.curio.TrinketTerrariaItem;

import java.util.UUID;

public class WarriorEmblemItem extends TrinketTerrariaItem {

	@Override
	protected Multimap<Attribute, AttributeModifier> applyModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
		Multimap<Attribute, AttributeModifier> result = super.applyModifiers(stack, slot, entity, uuid);
		AttributeModifier modifier = new AttributeModifier(uuid,
				TerraCraft.id("warrior_emblem_attack_damage").toString(),
				1.5f, AttributeModifier.Operation.ADDITION);
		result.put(Attributes.ATTACK_DAMAGE, modifier);
		return result;
	}
}
