package terracraft.common.item.curio.necklace;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import terracraft.common.events.LivingEntityHurtCallback;
import terracraft.common.init.ModItems;
import terracraft.common.item.curio.TrinketTerrariaItem;
import terracraft.common.trinkets.TrinketsHelper;

public class PanicNecklaceItem extends TrinketTerrariaItem {

	public PanicNecklaceItem() {
        LivingEntityHurtCallback.EVENT.register(PanicNecklaceItem::applyEffects);
	}

	private static void applyEffects(LivingEntity user, DamageSource source, float amount) {
		if (!user.level.isClientSide && amount >= 1 && TrinketsHelper.isEquipped(ModItems.PANIC_NECKLACE, user)) {
			user.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 160, 0, false, false));
		}
	}

	@Override
	protected SoundInfo getEquipSoundInfo() {
		return new SoundInfo(SoundEvents.ARMOR_EQUIP_DIAMOND);
	}
}
