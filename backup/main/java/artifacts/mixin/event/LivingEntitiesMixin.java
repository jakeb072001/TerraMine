package terracraft.mixin.event;

import terracraft.common.events.LivingEntityDamagedCallback;
import terracraft.common.events.LivingEntityHurtCallback;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {LivingEntity.class, Player.class})
public abstract class LivingEntitiesMixin extends Entity {

	public LivingEntitiesMixin(EntityType<?> type, Level world) {
		super(type, world);
	}

	@Inject(method = "actuallyHurt", at = @At("HEAD"))
	private void onEntityHurt(DamageSource source, float amount, CallbackInfo info) {
		if (!this.isInvulnerableTo(source)) {
			LivingEntityHurtCallback.EVENT.invoker().hurt((LivingEntity) (Object) this, source, amount);
		}
	}

	@Inject(method = "actuallyHurt", allow = 1, at = @At(value = "JUMP", opcode = Opcodes.IFNE))
	private void onEntityDamaged(DamageSource source, float amount, CallbackInfo info) {
		if (!this.isInvulnerableTo(source)) {
			LivingEntityDamagedCallback.EVENT.invoker().damage((LivingEntity) (Object) this, source, amount);
		}
	}
}
