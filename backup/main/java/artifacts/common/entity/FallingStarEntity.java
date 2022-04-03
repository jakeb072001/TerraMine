package terracraft.common.entity;

import terracraft.common.init.Items;
import terracraft.common.init.SoundEvents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class FallingStarEntity extends Mob {
    Random rand = new Random();
    float x;
    float z;
    int soundTimer;
    public FallingStarEntity(EntityType<? extends FallingStarEntity> entityType, Level level) {
        super(entityType, level);
        moveControl = new MoveControl(this);
        xpReward = 0;
        setXRot(rand.nextFloat());
        x = ((rand.nextFloat()) - 0.5f) / 4;
        z = ((rand.nextFloat()) - 0.5f) / 4;
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
            playSound(SoundEvents.FALLING_STAR_CRASH,0.5f, 1f);
            ItemEntity starItem = new ItemEntity(level, this.blockPosition().getX(), this.blockPosition().getY(), this.blockPosition().getZ(), Items.FAKE_FALLEN_STAR.getDefaultInstance());
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
        if (this.isOnGround() || this.isInLava() || this.isInWater()) {
            createStarItem();
        }
        if (!level.isClientSide()) {
            spawnEffects();
        }
    }

    private void adjustMotion() {
        Vec3 motion = getDeltaMovement();
        double y = Math.min(-0.2F, motion.y());
        setDeltaMovement(new Vec3(motion.x() + this.x, y, motion.z() + this.z));
    }

    @Environment(EnvType.CLIENT)
    private void spawnEffects() {
        float random = (rand.nextFloat() - 0.5F) * 0.1F;
        Minecraft.getInstance().particleEngine.createParticle(ParticleTypes.FIREWORK, position().x, position().y + 0.5f, position().z, random, -0.2D, random);
        Minecraft.getInstance().particleEngine.createParticle(ParticleTypes.ENCHANTED_HIT, position().x, position().y + 0.5f, position().z, random, -0.2D, random);
        soundTimer += 1;
        if (soundTimer >= (rand.nextInt(7)) + 5) {
            playSound(SoundEvents.FALLING_STAR_FALL,0.5f, 1f);
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
