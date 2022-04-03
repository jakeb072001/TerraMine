package terracraft.mixin.item.superstitioushat;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terracraft.common.init.ModItems;
import terracraft.common.trinkets.TrinketsHelper;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

	@Inject(method = "getMobLooting", at = @At("RETURN"), cancellable = true)
	private static void increaseLooting(LivingEntity entity, CallbackInfoReturnable<Integer> info) {
		// Add 1 level of knockback with a minimum of 2
		if (TrinketsHelper.isEquipped(ModItems.SUPERSTITIOUS_HAT, entity)) {
			info.setReturnValue(info.getReturnValueI() + 1);
		}
	}
}
