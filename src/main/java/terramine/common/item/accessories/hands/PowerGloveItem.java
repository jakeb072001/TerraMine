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

public class PowerGloveItem extends TrinketTerrariaItem {

	@Override
	protected Multimap<Attribute, AttributeModifier> applyModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
		Multimap<Attribute, AttributeModifier> result = super.applyModifiers(stack, slot, entity, uuid);
		AttributeModifier modifier = new AttributeModifier(uuid,
				TerraMine.id("power_glove_attack_speed").toString(),
				0.12, AttributeModifier.Operation.MULTIPLY_TOTAL);
		AttributeModifier modifier2 = new AttributeModifier(uuid,
				TerraMine.id("power_glove_attack_range").toString(),
				3, AttributeModifier.Operation.ADDITION);
		AttributeModifier modifier3 = new AttributeModifier(uuid,
				TerraMine.id("power_glove_range").toString(),
				0.5, AttributeModifier.Operation.ADDITION);
		result.put(Attributes.ATTACK_SPEED, modifier);
		result.put(ReachEntityAttributes.ATTACK_RANGE, modifier2);
		result.put(ReachEntityAttributes.REACH, modifier3);
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
