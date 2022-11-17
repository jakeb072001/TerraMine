package terramine.mixin.item.accessories.reducelavadamage;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terramine.common.init.ModItems;
import terramine.common.trinkets.TrinketsHelper;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Unique
    private DamageSource damageSource;

    @Inject(method = "hurt", at = @At("HEAD"))
    private void accessDamageSource(DamageSource damageSource, float f, CallbackInfoReturnable<Boolean> cir) {
        this.damageSource = damageSource;
    }

    @ModifyVariable(method = "hurt", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private float reduceLavaDamage(float f) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (TrinketsHelper.isEquipped(ModItems.OBSIDIAN_ROSE, entity) || TrinketsHelper.isEquipped(ModItems.OBSIDIAN_SKULL_ROSE, entity) || TrinketsHelper.isEquipped(ModItems.MOLTEN_SKULL_ROSE, entity)
                || TrinketsHelper.isEquipped(ModItems.LAVA_WADERS, entity) || TrinketsHelper.isEquipped(ModItems.TERRASPARK_BOOTS, entity)) {
            if (damageSource == DamageSource.LAVA) {
                f = 2.0f;
            }
        }
        return f;
    }
}
