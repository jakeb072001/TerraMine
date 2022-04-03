package terracraft.common.item.curio.hands;

import terracraft.Artifacts;
import terracraft.common.item.curio.TrinketArtifactItem;
import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;

import java.util.UUID;

public class TitanGloveItem extends TrinketArtifactItem {
    @Override
	protected Multimap<Attribute, AttributeModifier> applyModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
		Multimap<Attribute, AttributeModifier> result = super.applyModifiers(stack, slot, entity, uuid);
		AttributeModifier modifier = new AttributeModifier(uuid,
				Artifacts.id("titan_glove_attack_range").toString(),
				3, AttributeModifier.Operation.ADDITION);
		AttributeModifier modifier2 = new AttributeModifier(uuid,
				Artifacts.id("titan_glove_range").toString(),
				0.5, AttributeModifier.Operation.ADDITION);
		result.put(ReachEntityAttributes.ATTACK_RANGE, modifier);
		result.put(ReachEntityAttributes.REACH, modifier2);
		return result;
	}

	@Override
	protected SoundInfo getEquipSoundInfo() {
		return new SoundInfo(SoundEvents.ARMOR_EQUIP_NETHERITE);
	}
}
