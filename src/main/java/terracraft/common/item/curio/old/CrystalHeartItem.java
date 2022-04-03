package terracraft.common.item.curio.old;

import dev.emi.trinkets.api.SlotReference;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import terracraft.common.item.curio.TrinketTerrariaItem;
import terracraft.common.trinkets.TrinketsHelper;

import java.util.UUID;

public class CrystalHeartItem extends TrinketTerrariaItem {

	private static final AttributeModifier HEALTH_BONUS = new AttributeModifier(UUID.fromString("99fa0537-90b9-481a-bc76-4650987faba3"),
			"terracraft:crystal_heart_health_bonus", 10, AttributeModifier.Operation.ADDITION);

    @Override
	public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
		if (!entity.level.isClientSide() && TrinketsHelper.areEffectsEnabled(stack)) {
			AttributeInstance health = entity.getAttribute(Attributes.MAX_HEALTH);
			if (health != null && !health.hasModifier(HEALTH_BONUS)) {
				health.addPermanentModifier(HEALTH_BONUS);
			}
		}
	}

	@Override
	public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
		if (!entity.level.isClientSide()) {
			AttributeInstance health = entity.getAttribute(Attributes.MAX_HEALTH);
			if (health != null && health.hasModifier(HEALTH_BONUS)) {
				health.removeModifier(HEALTH_BONUS);
				if (entity.getHealth() > entity.getMaxHealth()) {
					entity.setHealth(entity.getMaxHealth());
				}
			}
		}
	}

	@Override
	public SoundInfo getEquipSoundInfo() {
		return new SoundInfo(SoundEvents.ARMOR_EQUIP_DIAMOND);
	}
}
