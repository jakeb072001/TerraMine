package terramine.mixin.item.armors.molten;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terramine.common.utility.equipmentchecks.ArmorSetCheck;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(at = @At("HEAD"), method = "isInvulnerableTo", cancellable = true)
    private void fireImmunity(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if ((Entity) (Object) this instanceof LivingEntity livingEntity) {
            if (ArmorSetCheck.isSetEquipped(livingEntity, "molten_armor")) {
                if (damageSource.equals(DamageSource.ON_FIRE) || damageSource.equals(DamageSource.IN_FIRE)) {
                    cir.setReturnValue(true);
                }
            }
        }
    }
}
