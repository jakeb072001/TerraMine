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

public class MoltenCharmItem extends TrinketTerrariaItem {

    public MoltenCharmItem() {
		LivingEntityHurtCallback.EVENT.register(MoltenCharmItem::onLivingHurt);
    }

	private static void onLivingHurt(LivingEntity user, DamageSource source, float amount) {
		if (!user.level.isClientSide && amount >= 1 && user instanceof Player player && TrinketsHelper.isEquipped(ModItems.MOLTEN_CHARM, user)) {
			if (user.isInLava() && !player.getCooldowns().isOnCooldown(ModItems.MOLTEN_CHARM)) {
				user.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 140, 0, false, true));
				((Player) user).getCooldowns().addCooldown(ModItems.MOLTEN_CHARM, 450);
			}
		}
	}

	@Override
	protected SoundInfo getEquipSoundInfo() {
		return new SoundInfo(SoundEvents.ARMOR_EQUIP_IRON);
	}
}
