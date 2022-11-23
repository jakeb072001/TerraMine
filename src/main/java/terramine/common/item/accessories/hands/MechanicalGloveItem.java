package terramine.common.item.accessories.hands;

import com.google.common.collect.Multimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import terramine.TerraMine;
import terramine.common.item.accessories.TrinketTerrariaItem;

import java.util.UUID;

import static terramine.common.utility.Utilities.autoSwing;

public class MechanicalGloveItem extends TrinketTerrariaItem {

	@Override
	protected Multimap<Attribute, AttributeModifier> applyModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
		Multimap<Attribute, AttributeModifier> result = super.applyModifiers(stack, slot, entity, uuid);
		AttributeModifier attackModifier = new AttributeModifier(uuid,
				TerraMine.id("mechanical_glove_attack").toString(),
				0.12, AttributeModifier.Operation.MULTIPLY_TOTAL);
		AttributeModifier reachModifier = new AttributeModifier(uuid,
				TerraMine.id("mechanical_glove_range").toString(),
				0.5, AttributeModifier.Operation.ADDITION);
		AttributeModifier attackRangeModifier = new AttributeModifier(uuid,
				TerraMine.id("mechanical_glove_attack_range").toString(),
				3, AttributeModifier.Operation.ADDITION);
		result.put(Attributes.ATTACK_SPEED, attackModifier);
		result.put(Attributes.ATTACK_DAMAGE, attackModifier);
		result.put(ReachEntityAttributes.REACH, reachModifier);
		result.put(ReachEntityAttributes.ATTACK_RANGE, attackRangeModifier);
		return result;
	}

	@Override
	public void curioTick(LivingEntity livingEntity, ItemStack stack) {
		autoSwing();
	}

	@Override
	protected SoundInfo getEquipSoundInfo() {
		return new SoundInfo(SoundEvents.ARMOR_EQUIP_NETHERITE);
	}
}
