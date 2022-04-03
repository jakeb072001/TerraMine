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

public class MoltenCharmItem extends TrinketArtifactItem {

    public MoltenCharmItem() {
		LivingEntityHurtCallback.EVENT.register(MoltenCharmItem::onLivingHurt);
    }

	private static void onLivingHurt(LivingEntity user, DamageSource source, float amount) {
		if (!user.level.isClientSide && amount >= 1 && user instanceof Player player && TrinketsHelper.isEquipped(Items.MOLTEN_CHARM, user)) {
			if (user.isInLava() && !player.getCooldowns().isOnCooldown(Items.MOLTEN_CHARM)) {
				user.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 140, 0, false, true));
				((Player) user).getCooldowns().addCooldown(Items.MOLTEN_CHARM, 450);
			}
		}
	}

	@Override
	protected SoundInfo getEquipSoundInfo() {
		return new SoundInfo(SoundEvents.ARMOR_EQUIP_IRON);
	}
}
