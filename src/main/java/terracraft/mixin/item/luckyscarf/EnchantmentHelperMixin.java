package terracraft.mixin.item.luckyscarf;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terracraft.common.init.ModItems;
import terracraft.common.trinkets.TrinketsHelper;
import terracraft.mixin.accessors.ItemStackAccessor;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

	@Inject(method = "getItemEnchantmentLevel", at = @At("RETURN"), cancellable = true)
	private static void increaseFortune(Enchantment enchantment, ItemStack stack, CallbackInfoReturnable<Integer> info) {
		//noinspection ConstantConditions
		Entity holder = ((ItemStackAccessor) (Object) stack).getEntityRepresentation();
		if (holder instanceof LivingEntity livingEntity && enchantment == Enchantments.BLOCK_FORTUNE && TrinketsHelper.isEquipped(ModItems.LUCKY_SCARF, livingEntity)) {
			info.setReturnValue(info.getReturnValueI() + 1);
		}
	}
}
