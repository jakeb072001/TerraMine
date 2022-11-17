package terramine.mixin.item.accessories.neptuneshell;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terramine.common.init.ModItems;
import terramine.common.init.ModMobEffects;
import terramine.common.trinkets.TrinketsHelper;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    protected boolean jumping;

    @Shadow
    protected abstract float getJumpPower();

    @Shadow
    public abstract double getJumpBoostPower();

    @Inject(at = @At("HEAD"), method = "canBreatheUnderwater", cancellable = true)
    public void doWaterBreathing(CallbackInfoReturnable<Boolean> info) {
        if(isShell()) {
            info.setReturnValue(true);
        }
    }

    @ModifyVariable(method = "causeFallDamage", ordinal = 0, at = @At("HEAD"), argsOnly = true)
    private float reduceFallDistance(float fallDistance) {
        if (isShell() && this.isInWater()) {
            fallDistance = 0;
        }

        return fallDistance;
    }

    @Inject(method = "aiStep", at = @At("HEAD"))
    private void invokeDoubleJump(CallbackInfo info) {
        LivingEntity self = (LivingEntity) (Object) this;
        boolean flying = self instanceof Player player && player.getAbilities().flying;
        if (this.jumping && this.isInWater() && !this.isOnGround() && !this.isPassenger() && !flying && isShell()) {
            double d = (double)this.getJumpPower() + this.getJumpBoostPower();
            Vec3 vec3 = this.getDeltaMovement();
            this.setDeltaMovement(vec3.x, d, vec3.z);
        }
    }

    @Inject(at = @At("RETURN"), method = "getJumpBoostPower", cancellable = true)
    public void addWerewolfJump(CallbackInfoReturnable<Double> info) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (self.hasEffect(ModMobEffects.WEREWOLF)) {
            info.setReturnValue(info.getReturnValue() + 0.1);
        }
    }

    private boolean isShell() {
        LivingEntity self = (LivingEntity) (Object) this;
        return TrinketsHelper.isEquipped(ModItems.NEPTUNE_SHELL, self) || TrinketsHelper.isEquipped(ModItems.MOON_SHELL, self) || TrinketsHelper.isEquipped(ModItems.CELESTIAL_SHELL, self);
    }
}
