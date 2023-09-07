package terramine.mixin.event;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import terramine.common.events.LivingEntityPotionEffectCallback;
import terramine.common.events.PlayHurtSoundCallback;

import javax.annotation.Nullable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	public LivingEntityMixin(EntityType<?> type, Level world) {
		super(type, world);
	}

	@Shadow
	protected abstract float getSoundVolume();

	@Shadow
	public abstract float getVoicePitch();

	@Inject(method = "playHurtSound", at = @At("HEAD"))
	private void onServerPlayHurtSound(CallbackInfo info) {
		if (((LivingEntity) (Object) this) instanceof Player player) {
			PlayHurtSoundCallback.EVENT.invoker().play(player, this.getSoundVolume(), this.getVoicePitch());
		}
	}

	// from porting-lib, didn't want to import the whole thing so just doing it this way for now
	@Inject(
			method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z",
			at = @At(
					value = "INVOKE",
					target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;",
					shift = At.Shift.BY,
					by = 3,
					remap = false
			),
			locals = LocalCapture.CAPTURE_FAILHARD
	)
	public void onAddEffect(MobEffectInstance newEffect, @Nullable Entity source, CallbackInfoReturnable<Boolean> cir, MobEffectInstance oldEffect) {
		LivingEntityPotionEffectCallback.EVENT.invoker().onPotionAdded((LivingEntity) (Object) this, newEffect, oldEffect, source);
	}
}
