package terramine.mixin.item.accessories.reducelavadamage;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terramine.common.init.ModItems;
import terramine.common.misc.AccessoriesHelper;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Unique
    private DamageSource damageSource;

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "hurt", at = @At("HEAD"))
    private void accessDamageSource(DamageSource damageSource, float f, CallbackInfoReturnable<Boolean> cir) {
        this.damageSource = damageSource;
    }

    @ModifyVariable(method = "hurt", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private float reduceLavaDamage(float f) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (AccessoriesHelper.isEquipped(ModItems.OBSIDIAN_ROSE, entity) || AccessoriesHelper.isEquipped(ModItems.OBSIDIAN_SKULL_ROSE, entity) || AccessoriesHelper.isEquipped(ModItems.MOLTEN_SKULL_ROSE, entity)
                || AccessoriesHelper.isEquipped(ModItems.LAVA_WADERS, entity) || AccessoriesHelper.isEquipped(ModItems.TERRASPARK_BOOTS, entity)) {
            if (damageSource == damageSources().lava()) {
                f = 2.0f;
            }
        }
        return f;
    }
}
