package terramine.mixin.item.accessories.luckyhorseshoe;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terramine.common.init.ModItems;
import terramine.common.misc.AccessoriesHelper;
import terramine.common.utility.equipmentchecks.WingsEquippedCheck;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	@Inject(method = "causeFallDamage", cancellable = true, at = @At("HEAD"))
	private void cancelFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> info) {
		if (AccessoriesHelper.isEquipped(ModItems.LUCKY_HORSESHOE, (LivingEntity) (Object) this) || AccessoriesHelper.isEquipped(ModItems.OBSIDIAN_HORSESHOE, (LivingEntity) (Object) this)
				|| AccessoriesHelper.isEquipped(ModItems.BLUE_HORSESHOE_BALLOON, (LivingEntity) (Object) this) || WingsEquippedCheck.isEquipped((LivingEntity) (Object) this)) {
			info.setReturnValue(false);
		}
	}
}
