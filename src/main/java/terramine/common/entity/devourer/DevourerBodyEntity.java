package terramine.common.entity.devourer;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import terramine.common.init.ModSoundEvents;

public class DevourerBodyEntity extends Monster implements Enemy {
    public DevourerEntity head = null;
    private int tunnelTransitionTicks = 0;

    public DevourerBodyEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true;
        this.xpReward = 0;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        if (this.head != null) {
            compoundTag.putUUID("head", this.head.getUUID());
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        for (DevourerEntity entity : level.getEntitiesOfClass(DevourerEntity.class, new AABB(getX() - 100, getY() - 100, getZ() - 100, getX() + 100, getY() + 100, getZ() + 100))) {
            if (entity.getUUID() == compoundTag.getUUID("head")) {
                this.head = entity;
                break;
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.tunnelTransitionTicks < 20) {
            ++this.tunnelTransitionTicks;
        }

        if (this.head != null) {
            if (this.head.isRemoved() && this.head.getRemovalReason() != null) {
                this.setRemoved(this.head.getRemovalReason());
            }

            if (!this.isDeadOrDying() && this.head.isDeadOrDying()) {
                this.kill();
            }
        } else {
            this.kill();
        }
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float f) {
        if (source != DamageSource.FALL && source != DamageSource.IN_WALL && source != DamageSource.CRAMMING) {
            if (this.head != null && f != 0) {
                this.head.hurt(source, f);
            }
            return super.hurt(source, f);
        } else {
            return false;
        }
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 25)
                .add(Attributes.ARMOR, 6)
                .add(Attributes.FOLLOW_RANGE, 24)
                .add(Attributes.MOVEMENT_SPEED, 1)
                .add(Attributes.ATTACK_DAMAGE, 1.5d);
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.DEMON_EYE_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return ModSoundEvents.DEMON_EYE_HURT;
    }
}
