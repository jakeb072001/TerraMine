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

public class PowerGloveItem extends AccessoryTerrariaItem {

	@Override
	protected Multimap<Attribute, AttributeModifier> applyModifiers(ItemStack stack, LivingEntity entity, UUID uuid) {
		Multimap<Attribute, AttributeModifier> result = super.applyModifiers(stack, entity, uuid);
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
	public void curioTick(Player player, ItemStack stack) {
		if (player.isLocalPlayer()) {
			autoSwing();
		}
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
	public SoundInfo getEquipSoundInfo() {
		return new SoundInfo(SoundEvents.ARMOR_EQUIP_NETHERITE);
	}
}
