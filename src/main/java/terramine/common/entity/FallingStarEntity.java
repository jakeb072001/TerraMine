package terramine.common.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import terramine.common.init.ModItems;
import terramine.common.init.ModSoundEvents;
import terramine.common.utility.ExplosionConfigurable;

public class FallingStarEntity extends Mob {
    private final float xSpeed, zSpeed;
    private int soundTimer;

    public FallingStarEntity(EntityType<? extends FallingStarEntity> entityType, Level level) {
        super(entityType, level);
        moveControl = new MoveControl(this);
        xpReward = 0;
        setXRot(getRandom().nextFloat());
        xSpeed = ((getRandom().nextFloat()) - 0.5f) / 4;
        zSpeed = ((getRandom().nextFloat()) - 0.5f) / 4;
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8);
    }

    public SoundSource getSoundSource() {
        return SoundSource.BLOCKS;
    }

    public void createStarItem() {
        if (!level.isClientSide()) { // checks if the world is not client
            level.broadcastEntityEvent(this, (byte)3); // particle?
            level.playSound(null, this.blockPosition(), ModSoundEvents.FALLING_STAR_CRASH, SoundSource.AMBIENT, 2f, 1f);
            ItemEntity starItem = new ItemEntity(level, this.blockPosition().getX(), this.blockPosition().getY(), this.blockPosition().getZ(), ModItems.FAKE_FALLEN_STAR.getDefaultInstance());
            starItem.setUnlimitedLifetime();
            level.addFreshEntity(starItem);
            this.discard();
        }
    }

    @Override
    public boolean isSunBurnTick() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        adjustMotion();
        resetFallDistance();
        spawnEffects();
        if (this.isOnGround() || this.isInLava() || this.isInWater()) {
            new ExplosionConfigurable(level, this, this.position().x(), this.position().y(), this.position().z(), 1F, 4f, Explosion.BlockInteraction.NONE);
            createStarItem();
        }
    }

    private void adjustMotion() {
        Vec3 motion = getDeltaMovement();
        double y = Math.min(-0.2F, motion.y());
        setDeltaMovement(new Vec3(motion.x() + xSpeed, y, motion.z() + zSpeed));
    }

    private void spawnEffects() {
        if (level != null) {
            Vec3 motion = getDeltaMovement();
            level.addParticle(ParticleTypes.FIREWORK, position().x, position().y + 0.5f, position().z, motion.x, motion.y, motion.z);
            level.addParticle(ParticleTypes.ENCHANTED_HIT, position().x, position().y + 0.5f, position().z, motion.x, motion.y, motion.z);
        }
        soundTimer += 1;
        if (soundTimer >= (getRandom().nextInt(7)) + 5) {
            level.playSound(null, this.blockPosition(), ModSoundEvents.FALLING_STAR_FALL, SoundSource.AMBIENT, 0.5f, 1f);
            soundTimer = 0;
        }
    }

    @Override
    public void setPos(double x, double y, double z) {
        int chunkX = (int) Math.floor(this.getX() / 16.0D);
        int chunkZ = (int) Math.floor(this.getZ() / 16.0D);
        int newChunkX = (int) Math.floor(x / 16.0D);
        int newChunkZ = (int) Math.floor(z / 16.0D);
        if (chunkX != newChunkX || chunkZ != newChunkZ) {
            if (!level.hasChunk(newChunkX, newChunkZ)) {
                this.remove(RemovalReason.DISCARDED);
                return;
            }
        }
        super.setPos(x, y, z);
    }
}
