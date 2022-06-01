package terramine.mixin.item.flippers;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;
import terramine.common.init.ModItems;
import terramine.common.trinkets.TrinketsHelper;
import terramine.extensions.LivingEntityExtensions;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements LivingEntityExtensions {

	@ModifyArg(method = "jumpInLiquid", index = 1, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;add(DDD)Lnet/minecraft/world/phys/Vec3;"))
	private double increaseSwimUpSpeed(double y) {
		return terramine$getIncreasedSwimSpeed(y);
	}

	// This is a big method, so I feel more comfortable with a slice than an ordinal
	// big method, big annotation, big fun
	@ModifyArg(method = "travel", allow = 1,
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;moveRelative(FLnet/minecraft/world/phys/Vec3;)V"),
			slice = @Slice(
					from = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isInWater()Z"),
					to = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isInLava()Z")
			)
	)
	private float increaseSwimSpeed(float speed) {
		return (float) terramine$getIncreasedSwimSpeed(speed);
	}

	@Unique
	@Override
	public double terramine$getIncreasedSwimSpeed(double speed) {
		if (TrinketsHelper.isEquipped(ModItems.FLIPPERS, (LivingEntity) (Object) this) || TrinketsHelper.isEquipped(ModItems.DIVING_GEAR, (LivingEntity) (Object) this)
				|| TrinketsHelper.isEquipped(ModItems.NEPTUNE_SHELL, (LivingEntity) (Object) this)) {
			return speed * 2;
		} else {
			return speed;
		}
	}
}
