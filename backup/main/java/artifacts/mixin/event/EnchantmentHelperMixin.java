package terracraft.mixin.event;

import terracraft.common.events.LivingEntityAttackedCallback;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

	@Inject(method = "doPostHurtEffects", at = @At("HEAD"))
	private static void onUserAttacked(LivingEntity entity, Entity attacker, CallbackInfo info) {
		LivingEntityAttackedCallback.EVENT.invoker().attack(entity, attacker, entity.getRandom());
	}
}
