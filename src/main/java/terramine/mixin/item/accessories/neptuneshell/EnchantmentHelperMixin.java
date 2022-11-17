package terramine.mixin.item.accessories.neptuneshell;

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

    @Inject(method = "hasAquaAffinity", at = @At("HEAD"), cancellable = true)
    private static void normalWaterMining(LivingEntity livingEntity, CallbackInfoReturnable<Boolean> info) {
        if (TrinketsHelper.isEquipped(ModItems.NEPTUNE_SHELL, livingEntity) || TrinketsHelper.isEquipped(ModItems.MOON_SHELL, livingEntity)
                || TrinketsHelper.isEquipped(ModItems.CELESTIAL_SHELL, livingEntity)) {
            info.setReturnValue(true);
        }
    }
}
