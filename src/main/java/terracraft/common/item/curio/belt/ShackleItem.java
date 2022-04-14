package terracraft.common.item.curio.belt;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import terracraft.TerraCraft;
import terracraft.common.item.curio.TrinketTerrariaItem;

import java.util.UUID;

public class ShackleItem extends TrinketTerrariaItem {

	public ShackleItem() {
		canShowTooltip(false);
	}

	@Override
	protected Multimap<Attribute, AttributeModifier> applyModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
		Multimap<Attribute, AttributeModifier> result = super.applyModifiers(stack, slot, entity, uuid);
		AttributeModifier modifier = new AttributeModifier(uuid,
				TerraCraft.id("obsidian_skull_defence").toString(),
				1, AttributeModifier.Operation.ADDITION);
		result.put(Attributes.ARMOR, modifier);
		return result;
	}

	@Override
	protected SoundInfo getEquipSoundInfo() {
		return new SoundInfo(SoundEvents.ARMOR_EQUIP_IRON);
	}
}
