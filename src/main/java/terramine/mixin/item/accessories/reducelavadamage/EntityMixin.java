package terramine.mixin.item.accessories.reducelavadamage;

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
import terramine.common.init.ModItems;
import terramine.common.misc.AccessoriesHelper;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public abstract DamageSources damageSources();

    @Inject(at = @At("HEAD"), method = "isInvulnerableTo", cancellable = true)
    private void lavaImmunity(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if ((Entity) (Object) this instanceof LivingEntity entity) {
            if (isLavaCharmEquipped(entity)) {
                if (ModComponents.LAVA_IMMUNITY.get(entity).getLavaImmunityTimer() > 0) {
                    if (damageSource.equals(damageSources().lava())) {
                        cir.setReturnValue(true);
                    }
                }
            }
        }
    }

    @WrapWithCondition(method = "lavaHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setSecondsOnFire(I)V"))
    private boolean disableFire(Entity entity, int fireTime) {
        if (entity instanceof Player player) {
            if (isLavaCharmEquipped(player)) {
                return !(ModComponents.LAVA_IMMUNITY.get(player).getLavaImmunityTimer() > 0);
            }
        }
        return true;
    }

    private boolean isLavaCharmEquipped(LivingEntity player) {
        return AccessoriesHelper.isEquipped(ModItems.LAVA_CHARM, player) || AccessoriesHelper.isEquipped(ModItems.MOLTEN_CHARM, player)
                || AccessoriesHelper.isEquipped(ModItems.LAVA_WADERS, player) || AccessoriesHelper.isEquipped(ModItems.TERRASPARK_BOOTS, player);
    }
}
