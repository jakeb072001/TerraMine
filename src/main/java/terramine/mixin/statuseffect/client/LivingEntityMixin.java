package terramine.mixin.statuseffect.client;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.common.item.accessories.AccessoryTerrariaItem;
import terramine.common.misc.AccessoriesHelper;

// todo: remove for 1.20 and later, has infinite duration now
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	/**
	 * Show the effect as permanent, which normally only happens if the duration is >= 32767
	 * Doing it here makes sure it is set to permanent everytime the server sent an update packet
	 */
	@Inject(method = "forceAddEffect", at = @At("HEAD"))
	private void showStatusEffectPermanent(MobEffectInstance effect, Entity __, CallbackInfo info) {
		//noinspection ConstantConditions
		if ((Object) this instanceof Player player) {

			AccessoriesHelper.getAllEquipped(player).forEach(stack -> {
				if (stack.getItem() instanceof AccessoryTerrariaItem accessoryItem) {
					MobEffectInstance accessoriesPermEffect = accessoryItem.getPermanentEffect();

					if (accessoriesPermEffect != null && accessoriesPermEffect.getEffect() == effect.getEffect()) {
						effect.setNoCounter(true);
					}
				}
			});
		}
	}
}
