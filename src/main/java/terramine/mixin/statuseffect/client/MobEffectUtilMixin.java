package terramine.mixin.statuseffect.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terramine.common.item.accessories.AccessoryTerrariaItem;
import terramine.common.misc.AccessoriesHelper;

@Mixin(MobEffectUtil.class)
public abstract class MobEffectUtilMixin {

	@Inject(method = "formatDuration", at = @At("HEAD"), cancellable = true)
	private static void setFromTerraMineString(MobEffectInstance effect, float multiplier, CallbackInfoReturnable<String> info) {
		LocalPlayer player = Minecraft.getInstance().player;

		if (player != null && effect.isInfiniteDuration()) {
			AccessoriesHelper.getAllEquipped(player).forEach(stack -> {
				MobEffectInstance accessoryEffect = ((AccessoryTerrariaItem) stack.getItem()).getPermanentEffect();

				if (accessoryEffect != null && accessoryEffect.getEffect() == effect.getEffect()) {
					info.setReturnValue(stack.getHoverName().getString());
				}
			});
		}
	}
}
