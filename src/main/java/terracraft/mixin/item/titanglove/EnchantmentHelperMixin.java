package terracraft.mixin.item.titanglove;

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

	@Inject(method = "getKnockbackBonus", at = @At("RETURN"), cancellable = true)
	private static void increaseKnockback(LivingEntity entity, CallbackInfoReturnable<Integer> info) {
		// Add 1 level of knockback with a minimum of 2
		if (TrinketsHelper.isEquipped(ModItems.POCKET_PISTON, entity) || TrinketsHelper.isEquipped(ModItems.TITAN_GLOVE, entity)
				|| TrinketsHelper.isEquipped(ModItems.POWER_GLOVE, entity) || TrinketsHelper.isEquipped(ModItems.MECHANICAL_GLOVE, entity)
				|| TrinketsHelper.isEquipped(ModItems.FIRE_GAUNTLET, entity)) {
			info.setReturnValue(Math.max(info.getReturnValueI() + 1, 2));
		}
	}
}
