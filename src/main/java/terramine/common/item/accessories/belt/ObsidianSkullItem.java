package terramine.common.item.accessories.belt;

import com.google.common.collect.Multimap;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import terramine.TerraMine;
import terramine.common.item.accessories.AccessoryTerrariaItem;

import java.util.UUID;

public class ObsidianSkullItem extends AccessoryTerrariaItem {

	@Override
	protected Multimap<Attribute, AttributeModifier> applyModifiers(ItemStack stack, LivingEntity entity, UUID uuid) {
		Multimap<Attribute, AttributeModifier> result = super.applyModifiers(stack, entity, uuid);
		AttributeModifier modifier = new AttributeModifier(uuid,
				TerraMine.id("obsidian_skull_defence").toString(),
				1, AttributeModifier.Operation.ADDITION);
		result.put(Attributes.ARMOR, modifier);
		return result;
	}

	@Override
	protected SoundInfo getEquipSoundInfo() {
		return new SoundInfo(SoundEvents.ARMOR_EQUIP_IRON);
	}
}
