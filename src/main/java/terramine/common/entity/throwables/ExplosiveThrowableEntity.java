package terramine.common.entity.throwables;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
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

    public ExplosiveThrowableEntity(EntityType<? extends ThrowableProjectile> entityType, Level level) {
        super(entityType, level);
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
        this.getEntityData().set(STICKY, isSticky);
    }

    public boolean isSticky() {
        return this.getEntityData().get(STICKY);
    }

    public void setBouncy(boolean isSticky) {
        this.getEntityData().set(BOUNCY, isSticky);
    }

    public boolean isBouncy() {
        return this.getEntityData().get(BOUNCY);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(STICKY, false);
        this.entityData.define(BOUNCY, false);
    }

    @Override
    protected void updateRotation() { // leave blank, makes rotation work
    }

    @Override
    public void tick() {
        super.tick();
        timer ++;

        double i = getDeltaMovement().horizontalDistance();
        this.setYRot((float)(Mth.atan2(getDeltaMovement().x, getDeltaMovement().z) * 57.2957763671875));
        this.setXRot((float)(Mth.atan2(getDeltaMovement().y, i) * 57.2957763671875));

        if (timer >= (fuseTime * 20)) {
            explode();
        }
    }

    protected void explode() {
        if (!this.level().isClientSide) {
            new ExplosionConfigurable(level(), this, this.position().x(), this.position().y(), this.position().z(), radius, damage, explosionType);
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
            setDeltaMovement(0, 0, 0);
            setNoGravity(true);
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
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        compoundTag.putInt("fuse", timer);
    }
}
