package terracraft.mixin.item.umbrella;

import terracraft.common.components.SwimAbilityComponent;
import terracraft.common.init.Components;
import terracraft.common.item.UmbrellaItem;
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

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	public LivingEntityMixin(EntityType<?> type, Level world) {
		super(type, world);
	}

	@Shadow
	public abstract boolean hasEffect(MobEffect effect);

	@ModifyVariable(method = "travel", ordinal = 0, name = "d", at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/world/level/Level;getFluidState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/material/FluidState;"))
	private double changeGravity(double gravity) {
		boolean isFalling = !this.isOnGround() && this.getDeltaMovement().y <= 0.0D;
		boolean heldMainHand = UmbrellaItem.getHeldStatusForHand((LivingEntity) (Object) this, InteractionHand.MAIN_HAND) == UmbrellaItem.HeldStatus.HELD_UP;
		boolean heldOffHand = UmbrellaItem.getHeldStatusForHand((LivingEntity) (Object) this, InteractionHand.OFF_HAND) == UmbrellaItem.HeldStatus.HELD_UP;
		boolean isInWater = this.isInWater() && !Components.SWIM_ABILITIES.maybeGet(this).map(SwimAbilityComponent::isSinking).orElse(false);

		if ((heldMainHand || heldOffHand) && isFalling && !isInWater && !this.hasEffect(MobEffects.SLOW_FALLING) ) {
			gravity -= 0.07;
			this.fallDistance = 0;
		}

		return gravity;
	}
}
