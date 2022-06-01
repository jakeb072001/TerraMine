package terramine.common.item.curio.belt;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import terramine.common.events.LivingEntityHurtCallback;
import terramine.common.init.ModItems;
import terramine.common.item.curio.TrinketTerrariaItem;
import terramine.common.trinkets.TrinketsHelper;

public class LavaCharmItem extends TrinketTerrariaItem {

    public LavaCharmItem() {
		LivingEntityHurtCallback.EVENT.register(LavaCharmItem::onLivingHurt);
    }

	private static void onLivingHurt(LivingEntity user, DamageSource source, float amount) {
		if (!user.level.isClientSide && amount >= 1 && user.isInLava() && user instanceof Player player && !player.getCooldowns().isOnCooldown(ModItems.LAVA_CHARM)
				&& TrinketsHelper.isEquipped(ModItems.LAVA_CHARM, user)) {
			user.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 140, 0, false, true));
			((Player) user).getCooldowns().addCooldown(ModItems.LAVA_CHARM, 450);
		}
	}

	@Override
	protected SoundInfo getEquipSoundInfo() {
		return new SoundInfo(SoundEvents.ARMOR_EQUIP_IRON);
	}
}
