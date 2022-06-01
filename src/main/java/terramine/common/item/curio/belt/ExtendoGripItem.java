package terramine.common.item.curio.belt;

import com.google.common.collect.Multimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import terramine.TerraMine;
import terramine.common.item.curio.TrinketTerrariaItem;

import java.util.UUID;

public class ExtendoGripItem extends TrinketTerrariaItem {
	@Override
	protected Multimap<Attribute, AttributeModifier> applyModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
		Multimap<Attribute, AttributeModifier> result = super.applyModifiers(stack, slot, entity, uuid);
		AttributeModifier modifier = new AttributeModifier(uuid,
				TerraMine.id("extendo_grip_range").toString(),
				3, AttributeModifier.Operation.ADDITION);
		result.put(ReachEntityAttributes.REACH, modifier);
		return result;
	}
}
