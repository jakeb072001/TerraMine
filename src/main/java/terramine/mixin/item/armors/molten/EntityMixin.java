package terramine.mixin.item.armors.molten;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terramine.common.init.ModComponents;
import terramine.common.utility.equipmentchecks.ArmorSetCheck;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public abstract DamageSources damageSources();

    @Inject(at = @At("HEAD"), method = "isInvulnerableToBase", cancellable = true)
    private void fireImmunity(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if ((Entity) (Object) this instanceof LivingEntity livingEntity) {
            if (ArmorSetCheck.isSetEquipped(livingEntity, "molten")) {
                if (damageSource.equals(damageSources().onFire()) || damageSource.equals(damageSources().inFire()) || damageSource.equals(damageSources().lava())) {
                    cir.setReturnValue(true);
                }
            }
        }
    }

    @WrapWithCondition(method = "lavaHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;igniteForSeconds(F)V"))
    private boolean disableFire(Entity entity, float f) {
        if ((Entity) (Object) this instanceof LivingEntity livingEntity) {
            return !ArmorSetCheck.isSetEquipped(livingEntity, "molten");
        }
        return true;
    }
}
