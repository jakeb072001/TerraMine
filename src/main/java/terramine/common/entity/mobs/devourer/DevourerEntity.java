package terramine.common.entity.mobs.devourer;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import org.jetbrains.annotations.NotNull;
import terramine.common.init.ModEntities;
import terramine.common.init.ModLootTables;
import terramine.common.init.ModSoundEvents;

// todo: movement needs to be like Devourer from Terraria, using flying entity AI for testing
// probably don't use gravity and just curve downwards through code when having left the ground
// todo: need to add segments to save data somehow, otherwise the devourer creates new segments each world load causing a lot of noise
public class DevourerEntity extends Monster implements Enemy {
    public static final EntityDataAccessor<Boolean> SPAWNED = SynchedEntityData.defineId(DevourerEntity.class, EntityDataSerializers.BOOLEAN);
    public DevourerBodyEntity[] segments;
    private LivingEntity target;
    private final int segmentCount;
    private double sinValue = 0;
    private final double sinIncrement = 0.05; // adjust to control how fast the worm moves up and down
    public double velX, velY, velZ;

    public DevourerEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true;
        this.moveControl = new DevourerMovementController(this);
        this.lookControl = new DevourerLookControl(this);
        segmentCount = random.nextInt(10, 15);
    }

    protected static class DevourerLookControl extends LookControl {
        public DevourerLookControl(DevourerEntity entity) {
            super(entity);
        }

        @Override
        public void tick() { // leave empty
        }
    }

    protected static class DevourerMovementController extends MoveControl {
        private final DevourerEntity entity;

        public DevourerMovementController(DevourerEntity entity) {
            super(entity);
            this.entity = entity;
        }

        @SuppressWarnings("ConstantConditions")
        @Override
        public void tick() {
            if (entity.isAlive()) {
                entity.target = entity.level.getNearestPlayer(entity.getX(), entity.getY(), entity.getZ(), entity.getAttribute(Attributes.FOLLOW_RANGE).getValue(), true);
                entity.setNoGravity(true);

                if (entity.target != null && !entity.target.isInvisible()) {
                    double speed = entity.getAttribute(Attributes.MOVEMENT_SPEED).getValue() * 0.50;
                    double playerX = entity.target.getX();
                    double playerY = entity.target.getY();
                    double playerZ = entity.target.getZ();
                    double dx = playerX - entity.getX();
                    double dy = playerY - entity.getY();
                    double dz = playerZ - entity.getZ();
                    double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

                    // move towards the player in a sine wave pattern
                    entity.sinValue += entity.sinIncrement;
                    double newY;
                    if (playerY >= entity.getY()) {
                        newY = entity.getY() + (0.3);
                    } else {
                        newY = entity.getY() - (0.3);
                    }
                    newY = newY + (Math.sin(entity.sinValue));
                    double newSpeedX = dx / distance * speed;
                    double newSpeedZ = dz / distance * speed;

                    entity.setYRot(rotlerp(entity.getYRot(), (float)Math.toDegrees(Math.atan2(newSpeedZ, newSpeedX)) - 90, 360));
                    entity.setXRot((float)(-(Mth.atan2(-(newY - entity.getY()), Math.sqrt(newSpeedX * newSpeedX + newSpeedZ * newSpeedZ)) * 180.0F / (float)Math.PI)));

                    entity.setDeltaMovement(newSpeedX, newY - entity.getY(), newSpeedZ);
                } else {
                    entity.sinValue = 0;
                    entity.setDeltaMovement(0, -0.15, 0);
                }
            }
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SPAWNED, false);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void tick() {
        super.tick();

        for(int i = 0; i < this.level.players().size(); ++i) {
            double dist = this.level.players().get(i).position().distanceTo(this.position());
            if (dist < this.getAttribute(Attributes.FOLLOW_RANGE).getValue()) {
                if (!this.level.players().get(i).isCreative() && !this.level.players().get(i).isSpectator()) {
                    target = this.level.players().get(i);
                } else {
                    target = null;
                }
            }
        }

        if (!this.isSpawned()) {
            this.segments = new DevourerBodyEntity[segmentCount];

            for(int i = 0; i < segmentCount; ++i) {
                if (i == segmentCount - 1) {
                    this.segments[i] = new DevourerTailEntity(ModEntities.DEVOURER_TAIL, level);
                } else {
                    this.segments[i] = new DevourerBodyEntity(ModEntities.DEVOURER_BODY, level);
                }
                this.segments[i].head = this;
            }
        }

        if (this.segments != null) {
            this.tickSegments();
            this.shareEffects();

            if (!this.level.isClientSide()) {
                this.serverTick();
            }
        }

        if (!this.level.isClientSide() && !this.isSpawned()) {
            this.setSpawned(true);
        }
    }

    private void serverTick() {
        if (!this.isSpawned()) {
            for(int i = 0; i < this.segmentCount; ++i) {
                BlockPos offsetPos = this.blockPosition().relative(this.getMotionDirection().getOpposite(), i);
                double x = offsetPos.getX() - 0.5d;
                double y = offsetPos.getY() - 0.5d;
                double z = offsetPos.getZ() - 0.5d;
                float pitch = this.getXRot();
                float yaw = this.getYHeadRot();

                this.segments[i].moveTo(x, y, z, pitch, yaw);
                this.level.addFreshEntity(this.segments[i]);
            }
        }
    }

    private void tickSegments() {
        float diminishY = 1.0F;

        for(int i = 0; i < segmentCount; ++i) {
            if (this.segments[i] == null) {
                return;
            }

            Monster leader = i == 0 ? this : this.segments[i - 1];
            double followX = leader.getX();
            double followY = leader.getY();
            double followZ = leader.getZ();
            float pitchThreshold = 10.0F;
            float angle;
            if (this.getXRot() > pitchThreshold || this.getXRot() < -pitchThreshold) {
                diminishY = (float)(diminishY + 1.7);
                angle = this.getXRot() / 50.0F;
                angle = Mth.clamp(angle, -1.8F, 1.8F);
                followY += angle / diminishY;
            }

            angle = (leader.getYRot() + 180.0F) * 3.141593F / 180.0F;
            double align = 0.05 + 1.0 / (i + 1) * 0.5;
            double straightX = -Mth.sin(angle) * align;
            double straightZ = Mth.cos(angle) * align;
            double straightY = Mth.atan2(-this.velY, Math.sqrt(this.velX * this.velX + this.velZ * this.velZ)); // todo: improve slightly so further away segments don't snap into place as much
            Vec3 dif = new Vec3(this.segments[i].getX() - followX, this.segments[i].getY() - followY, this.segments[i].getZ() - followZ);
            dif = dif.normalize();
            dif = dif.add(straightX, straightY, straightZ);
            dif = dif.normalize();
            double s = distanceForSeg(i);
            double destX = followX + s * dif.x;
            double destY = followY + s * dif.y;
            double destZ = followZ + s * dif.z;
            this.segments[i].absMoveTo(destX, destY, destZ);
            this.segments[i].lookAt(leader, 90f, 360f);

            MobEffectInstance highlightEffect = new MobEffectInstance(MobEffects.GLOWING, 20, 0);
            if (this.isInWall() || this.segments[i].isInWall()) {
                this.addEffect(highlightEffect);
            } else {
                if (this.hasEffect(highlightEffect.getEffect())) {
                    this.removeEffect(highlightEffect.getEffect());
                    this.segments[i].removeEffect(highlightEffect.getEffect());
                }
            }
        }
    }

    private float distanceForSeg(int i) {
        float dis = 0.8F;

        for(int o = 0; o < i; ++o) {
            dis -= 0.04F;
        }

        if (i == 0) {
            dis = 1f;
        }

        return dis;
    }

    private void shareEffects() {
        for (MobEffectInstance effect : this.getActiveEffects()) {
            for (int i = 0; i < segmentCount; ++i) {
                this.segments[i].addEffect(effect);
            }
        }
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
            if (this.segments != null) {
                for (DevourerBodyEntity seg : this.segments) {
                    if (seg != null) {
                        seg.hurt(source, 0);
                        seg.setRemainingFireTicks(this.getRemainingFireTicks());
                    }
                }
            }
            return super.hurt(source, f);
        } else {
            return false;
        }
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 25)
                .add(Attributes.ARMOR, 2)
                .add(Attributes.FOLLOW_RANGE, 32)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1)
                .add(Attributes.MOVEMENT_SPEED, 1.5d)
                .add(Attributes.ATTACK_DAMAGE, 3);
    }

    public void setSpawned(boolean b) {
        this.getEntityData().set(SPAWNED, b);
    }

    public boolean isSpawned() {
        return this.getEntityData().get(SPAWNED);
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.DEMON_EYE_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return ModSoundEvents.DEMON_EYE_HURT;
    }

    @Override
    protected ResourceLocation getDefaultLootTable() {
        return ModLootTables.DEVOURER;
    }
}
