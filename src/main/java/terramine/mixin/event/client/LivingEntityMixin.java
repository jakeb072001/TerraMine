package terramine.mixin.event.client;

import net.minecraft.world.entity.LivingEntity;
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

	@Inject(method = "handleEntityEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getHurtSound(Lnet/minecraft/world/damagesource/DamageSource;)Lnet/minecraft/sounds/SoundEvent;"))
	private void onClientPlayHurtSound(byte status, CallbackInfo info) {
		PlayHurtSoundCallback.EVENT.invoker().play((LivingEntity) (Object) this, this.getSoundVolume(), this.getVoicePitch());
	}
}
