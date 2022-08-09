package terramine.common.item.curio.hands;

import dev.emi.trinkets.api.SlotReference;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import terramine.common.events.LivingEntityPotionEffectCallback;
import terramine.common.init.ModItems;
import terramine.common.item.curio.TrinketTerrariaItem;
import terramine.common.trinkets.TrinketsHelper;
import terramine.extensions.MobEffectInstanceExtensions;

import javax.annotation.Nullable;

public class PhilosophersStoneItem extends TrinketTerrariaItem {

	public PhilosophersStoneItem() {
        LivingEntityPotionEffectCallback.EVENT.register(PhilosophersStoneItem::onPotionStart);
	}

	/** Removes all beneficial effects when trinket is removed */
	@Override
	public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
		for (MobEffectInstance effectInstance : entity.getActiveEffects()) {
			if (effectInstance.getEffect().isBeneficial()) {
				entity.removeEffect(effectInstance.getEffect());
			}
		}
	}

	/** Called when the potion effects start to apply this effect */
	private static void onPotionStart(LivingEntity living, MobEffectInstance newEffect, MobEffectInstance oldEffect, @Nullable Entity source) {
		if (newEffect.getEffect().isBeneficial() && !newEffect.isNoCounter()) {
			if (TrinketsHelper.isEquipped(ModItems.PHILOSOPHERS_STONE, living) && !TrinketsHelper.isEquipped(ModItems.CHARM_OF_MYTHS, living)) {
				int duration = (int) (newEffect.getDuration() * 1.25f);
				((MobEffectInstanceExtensions) newEffect).terramine$setDuration(duration);
			}
		}
	}
}
