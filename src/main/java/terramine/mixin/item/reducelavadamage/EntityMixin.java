package terramine.mixin.item.reducelavadamage;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terramine.common.init.ModComponents;
import terramine.common.init.ModItems;
import terramine.common.trinkets.TrinketsHelper;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(at = @At("HEAD"), method = "isInvulnerableTo", cancellable = true)
    private void hotFloorImmunity(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof LivingEntity entity) {
            if (TrinketsHelper.isEquipped(ModItems.TERRASPARK_BOOTS, entity) || TrinketsHelper.isEquipped(ModItems.LAVA_WADERS, entity) ||
                    TrinketsHelper.isEquipped(ModItems.MOLTEN_CHARM, entity) || TrinketsHelper.isEquipped(ModItems.LAVA_CHARM, entity)) {
                if (ModComponents.LAVA_IMMUNITY.get((Player)entity).getLavaImmunityTimer() > 0) {
                    if (damageSource.equals(DamageSource.LAVA)) {
                        cir.setReturnValue(true);
                    }
                }
            }
        }
    }

    @WrapWithCondition(method = "lavaHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setSecondsOnFire(I)V"))
    private boolean disableFire(Entity entity, int fireTime) {
        if (entity instanceof Player player) {
            return !(ModComponents.LAVA_IMMUNITY.get(player).getLavaImmunityTimer() > 0);
        }
        return true;
    }
}
