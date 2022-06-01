package terramine.mixin.event;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.common.events.PlayHurtSoundCallback;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	public LivingEntityMixin(EntityType<?> type, Level world) {
		super(type, world);
	}

	@Shadow
	protected abstract float getSoundVolume();

	@Shadow
	protected abstract float getVoicePitch();

	@Inject(method = "playHurtSound", at = @At("HEAD"))
	private void onServerPlayHurtSound(CallbackInfo info) {
		PlayHurtSoundCallback.EVENT.invoker().play((LivingEntity) (Object) this, this.getSoundVolume(), this.getVoicePitch());
	}
}
