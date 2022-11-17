package terramine.mixin.item.accessories.divinggear;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terramine.common.init.ModItems;
import terramine.common.trinkets.TrinketsHelper;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

	/**
	 * Give the player Respiration 3 (total of 1 minute water breathing)
	 */
	@Inject(method = "getRespiration", at = @At("RETURN"), cancellable = true)
	private static void giveRespiration(LivingEntity entity, CallbackInfoReturnable<Integer> info) {
		if (TrinketsHelper.isEquipped(ModItems.DIVING_HELMET, entity) || TrinketsHelper.isEquipped(ModItems.DIVING_GEAR, entity)) {
			info.setReturnValue(info.getReturnValueI() + 3);
		}
	}
}
