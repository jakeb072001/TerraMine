package terracraft.mixin.item.neptuneshell;

import io.github.apace100.origins.power.OriginsPowerTypes;
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
import terracraft.common.init.ModComponents;
import terracraft.common.init.ModItems;
import terracraft.common.trinkets.TrinketsHelper;

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
        if(TrinketsHelper.isEquipped(ModItems.NEPTUNE_SHELL, (LivingEntity) (Object) this)) {
            info.setReturnValue(true);
        }
    }

    @ModifyVariable(method = "causeFallDamage", ordinal = 0, at = @At("HEAD"))
    private float reduceFallDistance(float fallDistance) {
        if (TrinketsHelper.isEquipped(ModItems.NEPTUNE_SHELL, (LivingEntity) (Object) this) && this.isInWater()) {
            fallDistance = 0;
        }

        return fallDistance;
    }

    @Inject(method = "aiStep", at = @At("HEAD"))
    private void invokeDoubleJump(CallbackInfo info) {
        LivingEntity self = (LivingEntity) (Object) this;
        boolean flying = self instanceof Player player && player.getAbilities().flying;
        if (this.jumping && this.isInWater() && !this.isOnGround() && !this.isPassenger() && !flying
                && TrinketsHelper.isEquipped(ModItems.NEPTUNE_SHELL, self)) {
            double d = (double)this.getJumpPower() + this.getJumpBoostPower();
            Vec3 vec3 = this.getDeltaMovement();
            this.setDeltaMovement(vec3.x, d, vec3.z);
        }
    }
}
