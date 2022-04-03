package terracraft.mixin.statuseffect.client;

import terracraft.common.item.curio.TrinketArtifactItem;
import terracraft.common.trinkets.TrinketsHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

	/**
	 * Cancels the night vision fading effect when wearing a trinket that adds night vision as a permanent effect
	 */
	@Inject(method = "getNightVisionScale", at = @At("RETURN"), cancellable = true)
	private static void cancelNightVisionFadeEffect(LivingEntity entity, float tickDelta, CallbackInfoReturnable<Float> info) {
		if (info.getReturnValueF() != 1f) {
			TrinketsHelper.getAllEquipped(entity).stream()
					.map(stack -> Optional.ofNullable(((TrinketArtifactItem) stack.getItem()).getPermanentEffect()))
					.filter(effect -> effect.isPresent() && effect.get().getEffect() == MobEffects.NIGHT_VISION)
					.findAny()
					.ifPresent(effect -> info.setReturnValue(1.0f));
		}
	}
}
