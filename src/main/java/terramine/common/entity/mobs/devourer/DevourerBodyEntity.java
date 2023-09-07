package terramine.common.entity.mobs.devourer;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import org.jetbrains.annotations.NotNull;
import terramine.common.init.ModSoundEvents;

// todo: better way to save the head entity, use whatever method the DevourerEntity uses to save segments
public class DevourerBodyEntity extends Monster implements Enemy {
    public DevourerEntity head = null;

    public DevourerBodyEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.lookControl = new DevourerBodyLookControl(this);
        this.noPhysics = true;
        this.xpReward = 0;
    }

    protected static class DevourerBodyLookControl extends LookControl {
        public DevourerBodyLookControl(DevourerBodyEntity entity) {
            super(entity);
        }

        @Override
        public void tick() { // leave empty
        }
    }

    @Override
    public void tick() {
        super.tick();

        this.setNoGravity(true);
        this.shareEffects();

        if (this.head != null) {
            this.setHealth(this.head.getHealth());

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

    @SuppressWarnings("ConstantConditions")
    @Override
    public void playerTouch(@NotNull Player player) {
        super.playerTouch(player);

        if (this.isAlive() && this.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
            player.hurt(DamageSource.mobAttack(this), (float) this.getAttribute(Attributes.ATTACK_DAMAGE).getValue());
        }
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float f) {
        if (source != DamageSource.FALL && source != DamageSource.IN_WALL && source != DamageSource.CRAMMING) {
            if (this.head != null) {
                if (f != 0) {
                    this.head.hurt(source, f);
                    return super.hurt(source, 0);
                }
            }
            return super.hurt(source, f);
        }
        return false;
    }

    @Override
    public boolean isInWall() {
        float f = this.getDimensions(this.getPose()).width * 0.8f;
        AABB aABB = AABB.ofSize(this.getEyePosition(), f, 1.0E-6, f);
        return BlockPos.betweenClosedStream(aABB).anyMatch(blockPos -> {
            BlockState blockState = this.level.getBlockState(blockPos);
            return !blockState.isAir() && blockState.isSuffocating(this.level, blockPos) && Shapes.joinIsNotEmpty(blockState.getCollisionShape(this.level, blockPos).move(blockPos.getX(), blockPos.getY(), blockPos.getZ()), Shapes.create(aABB), BooleanOp.AND);
        });
    }

    @Override
    public void lookAt(Entity entity, float f, float g) {
        double h;
        double d = entity.getX() - this.getX();
        double e = entity.getZ() - this.getZ();
        if (entity instanceof LivingEntity livingEntity) {
            h = livingEntity.getEyeY() - this.getEyeY();
        } else {
            h = (entity.getBoundingBox().minY + entity.getBoundingBox().maxY) / 2.0 - this.getEyeY();
        }
        double i = Math.sqrt(d * d + e * e);
        float j = (float)(Mth.atan2(e, d) * 57.2957763671875) - 90.0f;
        float k = (float)((Mth.atan2(h, i) * 57.2957763671875));
        this.setXRot(this.rotlerp(this.getXRot(), k, g));
        this.setYRot(this.rotlerp(this.getYRot(), j, f));
    }

    private void shareEffects() {
        for (MobEffectInstance effect : this.getActiveEffects()) {
            if (this.head != null) {
                this.head.addEffect(effect);
            }
        }
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 25)
                .add(Attributes.ARMOR, 6)
                .add(Attributes.FOLLOW_RANGE, 24)
                .add(Attributes.MOVEMENT_SPEED, 1.5d)
                .add(Attributes.ATTACK_DAMAGE, 1.5d);
    }

    @Override
    public boolean is(@NotNull Entity entity) {
        return this == entity || this.head == entity;
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
