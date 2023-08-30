package terramine.common.item.accessories.hands;

import com.google.common.collect.Multimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import terramine.TerraMine;
import terramine.common.item.accessories.AccessoryTerrariaItem;

import java.util.UUID;

public class TitanGloveItem extends AccessoryTerrariaItem {
    @Override
	protected Multimap<Attribute, AttributeModifier> applyModifiers(ItemStack stack, LivingEntity entity, UUID uuid) {
		Multimap<Attribute, AttributeModifier> result = super.applyModifiers(stack, entity, uuid);
		AttributeModifier modifier = new AttributeModifier(uuid,
				TerraMine.id("titan_glove_attack_range").toString(),
				3, AttributeModifier.Operation.ADDITION);
		AttributeModifier modifier2 = new AttributeModifier(uuid,
				TerraMine.id("titan_glove_range").toString(),
				0.5, AttributeModifier.Operation.ADDITION);
		result.put(ReachEntityAttributes.ATTACK_RANGE, modifier);
		result.put(ReachEntityAttributes.REACH, modifier2);
		return result;
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
