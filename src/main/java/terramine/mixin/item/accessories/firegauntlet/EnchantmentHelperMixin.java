package terramine.mixin.item.accessories.firegauntlet;

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
	 * Give the player fire aspect II (equals 8 seconds in Forge version)
	 */
	@Inject(method = "getFireAspect", at = @At("RETURN"), cancellable = true)
	private static void giveFireAspect(LivingEntity entity, CallbackInfoReturnable<Integer> info) {
		if (info.getReturnValueI() < 2) {
			if (TrinketsHelper.isEquipped(ModItems.FIRE_GAUNTLET, entity) || TrinketsHelper.isEquipped(ModItems.MAGMA_STONE, entity) || TrinketsHelper.isEquipped(ModItems.MAGMA_SKULL, entity)
					|| TrinketsHelper.isEquipped(ModItems.MOLTEN_SKULL_ROSE, entity)) {
				info.setReturnValue(2);
			}
		}
	}
}
