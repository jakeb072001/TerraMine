package terramine.common.item.accessories.hands;

import dev.emi.trinkets.api.SlotReference;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import terramine.common.events.LivingEntityPotionEffectCallback;
import terramine.common.init.ModItems;
import terramine.common.item.accessories.TrinketTerrariaItem;
import terramine.common.trinkets.TrinketsHelper;
import terramine.extensions.MobEffectInstanceExtensions;

import javax.annotation.Nullable;

public class CharmOfMythsItem extends TrinketTerrariaItem {
	private int timer;

	public CharmOfMythsItem() {
        LivingEntityPotionEffectCallback.EVENT.register(CharmOfMythsItem::onPotionStart);
	}

	@Override
	public void curioTick(LivingEntity player, ItemStack stack) {
		if (player != null) {
			timer += 1;
			if (timer >= 50) {
				player.heal(0.5f);
				timer = 0;
			}
		}
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
			if (TrinketsHelper.isEquipped(ModItems.CHARM_OF_MYTHS, living)) {
				int duration = (int) (newEffect.getDuration() * 1.25f);
				((MobEffectInstanceExtensions) newEffect).terramine$setDuration(duration);
			}
		}
	}
}
