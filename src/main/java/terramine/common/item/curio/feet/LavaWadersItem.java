package terramine.common.item.curio.feet;

import be.florens.expandability.api.fabric.LivingFluidCollisionCallback;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.material.FluidState;
import terramine.common.events.LivingEntityHurtCallback;
import terramine.common.init.ModComponents;
import terramine.common.init.ModItems;
import terramine.common.item.curio.TrinketTerrariaItem;
import terramine.common.trinkets.TrinketsHelper;

public class LavaWadersItem extends TrinketTerrariaItem {

	public LavaWadersItem() {
		//noinspection UnstableApiUsage
        LivingFluidCollisionCallback.EVENT.register(LavaWadersItem::onFluidCollision);
		LivingEntityHurtCallback.EVENT.register(LavaWadersItem::onLivingHurt);
	}

	@Override
	public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
		ModComponents.SWIM_ABILITIES.maybeGet(entity).ifPresent(swimAbilities -> {
			if (entity.isInWater()) {
				swimAbilities.setWet(true);
			} else if (entity.isOnGround() || (entity instanceof Player player && player.getAbilities().flying)) {
				swimAbilities.setWet(false);
			}
		});

		super.tick(stack, slot, entity);
	}

	private static boolean onFluidCollision(LivingEntity entity, FluidState fluidState) {
		if (TrinketsHelper.isEquipped(ModItems.LAVA_WADERS, entity) && !entity.isCrouching()) {
			if (fluidState.is(FluidTags.LAVA) && !entity.fireImmune() && !EnchantmentHelper.hasFrostWalker(entity)) {
				//entity.hurt(DamageSource.HOT_FLOOR, 1);
			}
			return true;
		}

		return false;
	}

	private static void onLivingHurt(LivingEntity user, DamageSource source, float amount) {
		if (!user.level.isClientSide && amount >= 1 && user instanceof Player player && TrinketsHelper.isEquipped(ModItems.LAVA_WADERS, user)) {
			if (user.isInLava() && !player.getCooldowns().isOnCooldown(ModItems.LAVA_WADERS)) {
				user.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 140, 0, false, true));
				((Player) user).getCooldowns().addCooldown(ModItems.LAVA_WADERS, 450);
			}
		}
	}

	public static boolean canSprintOnWater(LivingEntity entity) {
		return ModComponents.SWIM_ABILITIES.maybeGet(entity)
				.map(swimAbilities -> entity.isSprinting()
						&& entity.fallDistance < 6
						&& !entity.isUsingItem()
						&& !entity.isCrouching()
						&& !swimAbilities.isWet()
						&& !swimAbilities.isSwimming())
				.orElse(false);
	}
}
