package terracraft.common.item.curio.belt;

import terracraft.common.events.LivingEntityHurtCallback;
import terracraft.common.init.Items;
import terracraft.common.item.curio.TrinketArtifactItem;
import terracraft.common.trinkets.TrinketsHelper;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class LavaCharmItem extends TrinketArtifactItem {

    public LavaCharmItem() {
		LivingEntityHurtCallback.EVENT.register(LavaCharmItem::onLivingHurt);
    }

	private static void onLivingHurt(LivingEntity user, DamageSource source, float amount) {
		if (!user.level.isClientSide && amount >= 1 && user.isInLava() && user instanceof Player player && !player.getCooldowns().isOnCooldown(Items.LAVA_CHARM)
				&& TrinketsHelper.isEquipped(Items.LAVA_CHARM, user)) {
			user.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 140, 0, false, true));
			((Player) user).getCooldowns().addCooldown(Items.LAVA_CHARM, 450);
		}
	}

	@Override
	protected SoundInfo getEquipSoundInfo() {
		return new SoundInfo(SoundEvents.ARMOR_EQUIP_IRON);
	}
}
