package terracraft.mixin.item.luckyhorseshoe;

import terracraft.common.init.Items;
import terracraft.common.trinkets.TrinketsHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	@Inject(method = "causeFallDamage", cancellable = true, at = @At("HEAD"))
	private void cancelFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> info) {
		if (TrinketsHelper.isEquipped(Items.LUCKY_HORSESHOE, (LivingEntity) (Object) this) || TrinketsHelper.isEquipped(Items.OBSIDIAN_HORSESHOE, (LivingEntity) (Object) this)) {
			info.setReturnValue(false);
		}
	}
}
