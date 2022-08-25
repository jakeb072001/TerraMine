package terramine.common.entity.devourer;

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
import net.minecraft.world.entity.Entity;
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
import terramine.common.utility.PosAngData;

import java.util.HashMap;
import java.util.Map;

// todo: body segments need to rotate correctly and better connect to each other
// todo: movement needs to be like Devourer from Terraria, using flying entity AI for testing
// todo: need to add segments to save data somehow, otherwise the devourer creates new segments each world load causing a lot of noise
public class DevourerEntity extends Monster implements Enemy {
    public static final EntityDataAccessor<Boolean> SPAWNED = SynchedEntityData.defineId(DevourerEntity.class, EntityDataSerializers.BOOLEAN);
    public DevourerBodyEntity[] segments;
    public Map<Integer, PosAngData> bodyPositionsForRespawn;
    private LivingEntity target;
    private final int segmentCount;
    public double velX, velY, velZ;
    public double oldVelX, oldVelY, oldVelZ;

    public DevourerEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true;
        this.moveControl = new DevourerMovementController(this);
        this.lookControl = new DevourerLookControl(this);

        this.bodyPositionsForRespawn = new HashMap<>();
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
                double motionY;
                double motionX;
                double motionZ;
                Level level = entity.level;

                if (entity.target != null && !entity.target.isInvisible()) {
                    if (entity.velX > -4 && entity.position().x > entity.target.position().x + entity.target.getBbWidth()) {
                        entity.velX -= 0.08;
                        if (entity.velX > 4) {
                            entity.velX -= 0.04;
                        }
                        else if (entity.velX > 0) {
                            entity.velX -= 0.2;
                        }
                        if (entity.velX < -4) {
                            entity.velX = -4;
                        }
                    } else if (entity.velX < 4 && entity.position().x + 1 < entity.target.position().x) {
                        entity.velX += 0.08;
                        if (entity.velX < -4) {
                            entity.velX += 0.04;
                        }
                        else if (entity.velX < 0) {
                            entity.velX += 0.2;
                        }
                        if (entity.velX > 4) {
                            entity.velX = 4;
                        }
                    }

                    if (entity.velZ > -4 && entity.position().z > entity.target.position().z + entity.target.getBbWidth()) {
                        entity.velZ -= 0.08;
                        if (entity.velZ > 4) {
                            entity.velZ -= 0.04;
                        }
                        else if (entity.velZ > 0f) {
                            entity.velZ -= 0.2;
                        }
                        if (entity.velZ < -4) {
                            entity.velZ = -4;
                        }
                    } else if (entity.velZ < 4f && entity.position().z + 1 < entity.target.position().z) {
                        entity.velZ += 0.08f;
                        if (entity.velZ < -4) {
                            entity.velZ += 0.04;
                        }
                        else if (entity.velZ < 0f) {
                            entity.velZ += 0.2;
                        }
                        if (entity.velZ > 4) {
                            entity.velZ = 4;
                        }
                    }

                    if (entity.velY > -2.5 && entity.position().y > entity.target.position().y + entity.target.getBbHeight()) {
                        entity.velY -= 0.1f;
                        if (entity.velY > 2.5) {
                            entity.velY -= 0.05;
                        } else if (entity.velY > 0f) {
                            entity.velY -= 0.15;
                        }
                    } else if (entity.velY < 2.5 && entity.position().y + 1 < entity.target.position().y) {
                        entity.velY += 0.1f;
                        if (entity.velY < -2.5) {
                            entity.velY += 0.05;
                        }
                        else if (entity.velY < 0) {
                            entity.velY += 0.15;
                        }
                    }
                }

                entity.oldVelX = entity.velX;
                entity.oldVelY = entity.velY;
                entity.oldVelZ = entity.velZ;
                motionX = entity.velX * 0.075f * entity.getAttribute(Attributes.MOVEMENT_SPEED).getValue();
                motionY = entity.velY * 0.075f * entity.getAttribute(Attributes.MOVEMENT_SPEED).getValue();
                motionZ = entity.velZ * 0.075f * entity.getAttribute(Attributes.MOVEMENT_SPEED).getValue();

                entity.setYRot(rotlerp(entity.getYRot(), (float)Math.toDegrees(Math.atan2(entity.velZ, entity.velX)) - 90, 360));
                entity.setXRot((float)(-(Mth.atan2(-entity.velY, Math.sqrt(entity.velX * entity.velX + entity.velZ * entity.velZ)) * 180.0F / (float)Math.PI)));

                entity.setDeltaMovement(motionX, motionY, motionZ);
            } else {
                entity.setDeltaMovement(0, -0.5f, 0);
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
                if (!this.bodyPositionsForRespawn.isEmpty()) {
                    x = this.bodyPositionsForRespawn.get(i).getPos().x();
                    y = this.bodyPositionsForRespawn.get(i).getPos().y();
                    z = this.bodyPositionsForRespawn.get(i).getPos().z();
                    pitch = this.bodyPositionsForRespawn.get(i).getPitch();
                    yaw = this.bodyPositionsForRespawn.get(i).getYaw();
                }

                this.segments[i].moveTo(x, y, z, pitch, yaw);
                this.level.addFreshEntity(this.segments[i]);
            }

            this.bodyPositionsForRespawn.clear();
        }
    }

    // todo: segments can't go straight up or down, need to change that. Segments also don't rotate very well.
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
                diminishY = (float)((double)diminishY + 1.7);
                angle = this.getXRot() / 50.0F;
                angle = Mth.clamp(angle, -1.8F, 1.8F);
                followY += angle / diminishY;
            }

            angle = (leader.getYRot() + 180.0F) * 3.141593F / 180.0F;
            double align = 0.05 + 1.0 / (double)((float)(i + 1)) * 0.5;
            double straightX = (double)(-Mth.sin(angle)) * align;
            double straightZ = (double)Mth.cos(angle) * align;
            Vec3 dif = new Vec3(this.segments[i].getX() - followX, this.segments[i].getY() - followY, this.segments[i].getZ() - followZ);
            dif = dif.normalize();
            dif = dif.add(straightX, 0.0, straightZ);
            dif = dif.normalize();
            double s = distanceForSeg(i);
            double destX = followX + s * dif.x;
            double destY = followY + s * dif.y;
            double destZ = followZ + s * dif.z;
            this.segments[i].absMoveTo(destX, destY, destZ);

            this.segments[i].lookAt(leader, 90f, 360f);
            this.segments[i].getLookControl().setLookAt(leader, 90f, 360f);

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
        float j = (float)(Mth.atan2(e, d) * 90) - 90.0f;
        float k = (float)(-(Mth.atan2(h, i) * 90));
        this.setXRot(Mth.rotlerp(this.getXRot(), k, g));
        this.setYRot(Mth.rotlerp(this.getYRot(), j, f));
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
                .add(Attributes.FOLLOW_RANGE, 24)
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
