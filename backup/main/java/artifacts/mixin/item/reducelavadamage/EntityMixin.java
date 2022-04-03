package terracraft.mixin.item.reducelavadamage;

import terracraft.common.init.Items;
import terracraft.common.trinkets.TrinketsHelper;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.*;

import java.util.Random;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow @Final
    protected Random random;
    @Shadow public abstract boolean fireImmune();
    @Shadow public abstract void setSecondsOnFire(int i);
    @Shadow public abstract boolean hurt(DamageSource damageSource, float f);
    @Shadow public abstract void playSound(SoundEvent soundEvent, float f, float g);

    /**
     * @reason Reduces lava damage, couldn't get working with inject
     * @author Jakeb
     **/
    @Overwrite
    public void lavaHurt() {  // the method signature must match exactly!
        if ((Object) this instanceof LivingEntity entity) {
            if (!this.fireImmune()) {
                this.setSecondsOnFire(15);
                if (TrinketsHelper.isEquipped(Items.OBSIDIAN_ROSE, entity) || TrinketsHelper.isEquipped(Items.OBSIDIAN_SKULL_ROSE, entity) || TrinketsHelper.isEquipped(Items.MOLTEN_SKULL_ROSE, entity)
                        || TrinketsHelper.isEquipped(Items.AQUA_DASHERS, entity) || TrinketsHelper.isEquipped(Items.TERRASPARK_BOOTS, entity)) {
                    if (this.hurt(DamageSource.LAVA, 2.0F)) {
                        this.playSound(SoundEvents.GENERIC_BURN, 0.4F, 1.0F + this.random.nextFloat() * 0.2F);
                    }
                } else {
                    if (this.hurt(DamageSource.LAVA, 4.0F)) {
                        this.playSound(SoundEvents.GENERIC_BURN, 0.4F, 2.0F + this.random.nextFloat() * 0.4F);
                    }
                }
            }
        } else {
            if (!this.fireImmune()) {
                this.setSecondsOnFire(15);
                if (this.hurt(DamageSource.LAVA, 4.0F)) {
                    this.playSound(SoundEvents.GENERIC_BURN, 0.4F, 2.0F + this.random.nextFloat() * 0.4F);
                }
            }
        }
    }
}
