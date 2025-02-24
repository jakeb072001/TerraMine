package terramine.mixin.item.accessories.umbrella;

import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import terramine.common.item.equipment.UmbrellaItem;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	public LivingEntityMixin(EntityType<?> type, Level world) {
		super(type, world);
	}

	@Shadow
	public abstract boolean hasEffect(Holder<MobEffect> effect);

	@SuppressWarnings("UnreachableCode")
	@ModifyVariable(method = "travelInAir", ordinal = 0, name = "d", at = @At("STORE"))
	private double changeGravity(double gravity) {
		boolean isFalling = !this.onGround() && this.getDeltaMovement().y <= 0.0D;
		boolean heldMainHand = UmbrellaItem.getHeldStatusForHand((LivingEntity) (Object) this, InteractionHand.MAIN_HAND) == UmbrellaItem.HeldStatus.HELD_UP;
		boolean heldOffHand = UmbrellaItem.getHeldStatusForHand((LivingEntity) (Object) this, InteractionHand.OFF_HAND) == UmbrellaItem.HeldStatus.HELD_UP;

		if ((heldMainHand || heldOffHand) && isFalling && !this.hasEffect(MobEffects.SLOW_FALLING)) {
			gravity = gravity / 1.5;
			this.fallDistance = 0;
		}

		return gravity;
	}
}
