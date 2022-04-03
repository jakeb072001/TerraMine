package terracraft.mixin.item.hotfloorimmunity;

import terracraft.common.init.Items;
import terracraft.common.trinkets.TrinketsHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(at = @At("HEAD"), method = "isInvulnerableTo", cancellable = true)
    private void makeOriginInvulnerable(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof LivingEntity entity) {
            if (TrinketsHelper.isEquipped(Items.OBSIDIAN_SKULL, entity) || TrinketsHelper.isEquipped(Items.OBSIDIAN_HORSESHOE, entity) || TrinketsHelper.isEquipped(Items.MAGMA_SKULL, entity) || TrinketsHelper.isEquipped(Items.OBSIDIAN_SKULL_ROSE, entity)
                    || TrinketsHelper.isEquipped(Items.MOLTEN_SKULL_ROSE, entity) || TrinketsHelper.isEquipped(Items.MOLTEN_CHARM, entity) || TrinketsHelper.isEquipped(Items.OBSIDIAN_WATER_WALKING_BOOTS, entity)
                    || TrinketsHelper.isEquipped(Items.AQUA_DASHERS, entity) || TrinketsHelper.isEquipped(Items.TERRASPARK_BOOTS, entity) || entity.getMainHandItem().is(Items.OBSIDIAN_SHIELD)
                    || entity.getOffhandItem().is(Items.OBSIDIAN_SHIELD)) {
                if (!damageSource.equals(DamageSource.OUT_OF_WORLD)) {
                    if (damageSource.equals(DamageSource.HOT_FLOOR)) {
                        cir.setReturnValue(true);
                    }
                }
            }
        }
    }
}
