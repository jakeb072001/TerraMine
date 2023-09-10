package terramine.mixin.event.client;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.common.events.PlayHurtSoundCallback;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	@Shadow
	protected abstract float getSoundVolume();

	@Shadow
	public abstract float getVoicePitch();

	@Inject(method = "handleEntityEvent", at = @At(value = "RETURN", target = "Lnet/minecraft/world/entity/LivingEntity;getHurtSound(Lnet/minecraft/world/damagesource/DamageSource;)Lnet/minecraft/sounds/SoundEvent;"), cancellable = true)
	private void onClientPlayHurtSound(CallbackInfo info) {
		if (((LivingEntity) (Object) this) instanceof Player player) {
			PlayHurtSoundCallback.EVENT.invoker().play(player, this.getSoundVolume(), this.getVoicePitch());
		}

		info.cancel();
	}
}
