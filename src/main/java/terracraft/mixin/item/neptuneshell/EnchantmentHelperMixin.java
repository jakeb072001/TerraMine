package terracraft.mixin.item.neptuneshell;

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

    @Inject(method = "hasAquaAffinity", at = @At("HEAD"), cancellable = true)
    private static void normalWaterMining(LivingEntity livingEntity, CallbackInfoReturnable<Boolean> info) {
        if (TrinketsHelper.isEquipped(ModItems.NEPTUNE_SHELL, livingEntity)) {
            info.setReturnValue(true);
        }
    }
}
