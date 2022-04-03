package terracraft.common.item.curio.necklace;

import terracraft.common.events.LivingEntityHurtCallback;
import terracraft.common.init.Items;
import terracraft.common.item.curio.TrinketArtifactItem;
import terracraft.common.trinkets.TrinketsHelper;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class PanicNecklaceItem extends TrinketArtifactItem {

	public PanicNecklaceItem() {
        LivingEntityHurtCallback.EVENT.register(PanicNecklaceItem::applyEffects);
	}

	private static void applyEffects(LivingEntity user, DamageSource source, float amount) {
		if (!user.level.isClientSide && amount >= 1 && TrinketsHelper.isEquipped(Items.PANIC_NECKLACE, user)) {
			user.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 160, 0, false, false));
		}
	}

	@Override
	protected SoundInfo getEquipSoundInfo() {
		return new SoundInfo(SoundEvents.ARMOR_EQUIP_DIAMOND);
	}
}
