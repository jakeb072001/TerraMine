package terramine.common.item.accessories.belt;

import com.google.common.collect.Multimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import terramine.TerraMine;
import terramine.common.item.accessories.AccessoryTerrariaItem;

import java.util.UUID;

public class ToolboxItem extends AccessoryTerrariaItem {
	@Override
	protected Multimap<Attribute, AttributeModifier> applyModifiers(ItemStack stack, LivingEntity entity, UUID uuid) {
		Multimap<Attribute, AttributeModifier> result = super.applyModifiers(stack, entity, uuid);
		AttributeModifier modifier = new AttributeModifier(uuid,
				TerraMine.id("toolbox_range").toString(),
				1, AttributeModifier.Operation.ADDITION);
		result.put(ReachEntityAttributes.REACH, modifier);
		return result;
	}
}
