package terramine.common.entity.projectiles.throwables;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import terramine.common.init.ModSoundEvents;
import terramine.common.utility.ExplosionConfigurable;

public abstract class ExplosiveThrowableEntity extends ThrowableProjectile {
    public static final EntityDataAccessor<Boolean> STICKY = SynchedEntityData.defineId(ExplosiveThrowableEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> BOUNCY = SynchedEntityData.defineId(ExplosiveThrowableEntity.class, EntityDataSerializers.BOOLEAN);
    protected BlockInteraction explosionType = BlockInteraction.DESTROY;
    private int timer = 0;
    private int fuseTime = 0;
    private float radius = 0;
    private float damage = 0;
    private float yRotStorage = 0;
    private float xRotStorage = 0;

    public ExplosiveThrowableEntity(EntityType<? extends ThrowableProjectile> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public @NotNull Vec3 getLightProbePosition(float f) {
        BlockPos startPos = this.blockPosition();
        Level level = this.level();
        int[] offsets = {-2, -1, 0, 1, 2};

        for (int dx : offsets) {
            for (int dy : offsets) {
                for (int dz : offsets) {
                    BlockPos checkPos = startPos.offset(dx, dy, dz);

                    if (level.getBlockState(checkPos).isAir()) {
                        return new Vec3(checkPos.getX() + 0.5, checkPos.getY() + 0.5, checkPos.getZ() + 0.5);
                    }
                }
            }
        }

        return new Vec3(getX(), getY(), getZ());
    }

    /**
     * @param fuseTime Time in seconds till explosion
     * @param radius Radius for the explosion
     * @param damage How much damage the explosion does
     */
    public void setStats(int fuseTime, float radius, float damage) {
        this.fuseTime = fuseTime;
        this.radius = radius;
        this.damage = damage;
    }

    public void setSticky(boolean isSticky) {
        this.entityData.set(STICKY, isSticky);
    }

    public boolean isSticky() {
        return this.entityData.get(STICKY);
    }

    public void setBouncy(boolean isSticky) {
        this.entityData.set(BOUNCY, isSticky);
    }

    public boolean isBouncy() {
        return this.entityData.get(BOUNCY);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(STICKY, false);
        builder.define(BOUNCY, false);
    }

    @Override
    protected void updateRotation() { // leave blank, makes rotation work
    }

    @Override
    public boolean ignoreExplosion(Explosion explosion) {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        timer ++;

        Vec3 motion = getDeltaMovement();
        double speed = motion.horizontalDistance();

        if (speed > 0.01 || Math.abs(motion.y) > 0.01) {
            float deltaYaw = (float) (Mth.atan2(motion.x, motion.z) * (180 / Math.PI));
            float deltaPitch = (float) (Mth.atan2(motion.y, speed) * (180 / Math.PI));

            yRotStorage += deltaYaw * 0.1f;
            xRotStorage += deltaPitch * 0.1f;
        }

        this.setYRot(yRotStorage);
        this.setXRot(xRotStorage);

        if (isSticky()) {
            if (!this.level().getBlockState(this.blockPosition()).getBlock().equals(Blocks.AIR)) {
                setDeltaMovement(0, 0, 0);
                setNoGravity(true);
            } else {
                setNoGravity(false);
            }
        }

        if (timer >= (fuseTime * 20)) {
            explode();
        }
    }

    protected void explode() {
        if (!this.level().isClientSide) {
            new ExplosionConfigurable((ServerLevel) level(), this, this.position().x(), this.position().y(), this.position().z(), radius, damage, explosionType);
            this.discard();
        }

        level().playSound(null, this.blockPosition(), ModSoundEvents.BOMB, SoundSource.AMBIENT, 1f, 1f);
        level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5, this.getZ(), 0.0, 0.0, 0.0);
        level().addParticle(ParticleTypes.EXPLOSION_EMITTER, this.getX(), this.getY(), this.getZ(), 1.0, 0.0, 0.0);
    }

    @Override
    public void lerpMotion(double d, double e, double f) {
        this.setDeltaMovement(d, e, f);
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult blockHitResult) {
        if (isSticky()) {
            // do nothing
        } else if (isBouncy()) {
            setDeltaMovement(getDeltaMovement().x, 0.2, getDeltaMovement().z);
            if (isInWall()) {
                setDeltaMovement(-(getDeltaMovement().x), 0, -(getDeltaMovement().z));
            }
        } else {
            setDeltaMovement(getDeltaMovement().x / 1.25f, 0, getDeltaMovement().z / 1.25f);
            if (isInWall()) {
                setDeltaMovement(-(getDeltaMovement().x / 1.25f), 0, -(getDeltaMovement().z / 1.25f));
            }
        }
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        timer = compoundTag.getInt("fuse");
        radius = compoundTag.getFloat("radius");
        damage = compoundTag.getFloat("damage");
        yRotStorage = compoundTag.getFloat("yRotStored");
        xRotStorage = compoundTag.getFloat("xRotStored");
        setSticky(compoundTag.getBoolean("isSticky"));
        setBouncy(compoundTag.getBoolean("isBouncy"));
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        compoundTag.putInt("fuse", timer);
        compoundTag.putFloat("radius", radius);
        compoundTag.putFloat("damage", damage);
        compoundTag.putFloat("yRotStored", yRotStorage);
        compoundTag.putFloat("xRotStored", xRotStorage);
        compoundTag.putBoolean("isSticky", isSticky());
        compoundTag.putBoolean("isBouncy", isBouncy());
    }
}
