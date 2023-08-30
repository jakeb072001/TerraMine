package terramine.common.item.accessories.hands;

import com.google.common.collect.Multimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import terramine.TerraMine;
import terramine.common.item.accessories.AccessoryTerrariaItem;

import java.util.UUID;

import static terramine.common.utility.Utilities.autoSwing;

public class MechanicalGloveItem extends AccessoryTerrariaItem {

	@Override
	protected Multimap<Attribute, AttributeModifier> applyModifiers(ItemStack stack, LivingEntity entity, UUID uuid) {
		Multimap<Attribute, AttributeModifier> result = super.applyModifiers(stack, entity, uuid);
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
	public void curioTick(Player player, ItemStack stack) {
		autoSwing();
	}

	@Override
	public boolean isBothHands() {
		return true;
	}

	@Override
	public boolean isGlove() {
		return true;
	}

	@Override
	protected SoundInfo getEquipSoundInfo() {
		return new SoundInfo(SoundEvents.ARMOR_EQUIP_NETHERITE);
	}
}
