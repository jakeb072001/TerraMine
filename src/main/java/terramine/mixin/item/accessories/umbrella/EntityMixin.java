package terramine.mixin.item.accessories.umbrella;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terramine.common.item.equipment.UmbrellaItem;

@Mixin(Entity.class)
public abstract class EntityMixin {

	@Inject(method = "isInRain", at = @At("RETURN"), cancellable = true)
	private void umbrellaBlocksRain(CallbackInfoReturnable<Boolean> info) {
		//noinspection ConstantConditions
		if (info.getReturnValueZ() && (Object) this instanceof LivingEntity entity && UmbrellaItem.isHeldUpInEitherHand(entity)) {
			info.setReturnValue(false);
		}
	}
}
