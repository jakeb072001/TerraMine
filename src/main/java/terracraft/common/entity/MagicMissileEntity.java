package terracraft.common.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import terracraft.TerraCraft;
import terracraft.common.init.ModItems;
import terracraft.common.init.ModSoundEvents;
import terracraft.common.trinkets.TrinketsHelper;
import terracraft.common.utility.ExplosionConfigurable;
import terracraft.common.utility.Utilities;

import java.util.Random;

public class MagicMissileEntity extends AbstractArrow {

    public static final ResourceLocation MAGIC_MISSILE_PARTICLE_ID = TerraCraft.id("magic_missile_particle");
    private final Random rand = new Random();
    private Item wandItem;
    private final ContainerData dataAccess;
    private float damageIncrease;
    private float speed;
    private float damage;
    private int timer;
    private int changeParticleType;
    private boolean canBeInWater;
    private boolean canBeInLava;
    private boolean canIgnite = false;
    private boolean limitedTime = false;

    public MagicMissileEntity(EntityType<? extends MagicMissileEntity> entityType, Level level) {
        super(entityType, level);
        this.setNoGravity(true);
        setKnockback(7);
        this.pickup = AbstractArrow.Pickup.DISALLOWED;
        this.dataAccess = new ContainerData(){
            @Override
            public int get(int i) {
                return MagicMissileEntity.this.changeParticleType;
            }

            @Override
            public void set(int i, int j) {
                MagicMissileEntity.this.changeParticleType = j;
            }

            @Override
            public int getCount() {
                return 1;
            }
        };
    }

    private void trinketCheck() { // replace with AttributeModifier later
        if (TrinketsHelper.isEquipped(ModItems.SORCERER_EMBLEM, (LivingEntity) this.getOwner()) && TrinketsHelper.isEquipped(ModItems.AVENGER_EMBLEM, (LivingEntity) this.getOwner())) {
            damageIncrease = 1.27f;
        } else if (TrinketsHelper.isEquipped(ModItems.SORCERER_EMBLEM, (LivingEntity) this.getOwner())) {
            damageIncrease = 1.15f;
        } else if (TrinketsHelper.isEquipped(ModItems.AVENGER_EMBLEM, (LivingEntity) this.getOwner())) {
            damageIncrease = 1.12f;
        } else {
            damageIncrease = 1f;
        }
    }

    public void setCooldownItem(Item wandItem) {
        this.wandItem = wandItem;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
    public void setDamage(float damage) {
        this.damage = damage;
    }

    public void liquidCollision(boolean water, boolean lava) {
        this.canBeInWater = water;
        this.canBeInLava = lava;
    }
    public void canIgnite(boolean canIgnite) {
        this.canIgnite = canIgnite;
    }
    public void limitedTime(boolean limitedTime) {
        this.limitedTime = limitedTime;
    }

    public void setParticleType(int changeParticleType) {
        this.changeParticleType = changeParticleType;
    }

    @Environment(EnvType.CLIENT)
    private static void sendParticlePacket() {
        ClientPlayNetworking.send(MAGIC_MISSILE_PARTICLE_ID, PacketByteBufs.empty());
    }

    public SoundSource getSoundSource() {
        return SoundSource.PLAYERS;
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        //super.onHitEntity(entityHitResult);
        if (entityHitResult != null && entityHitResult.getEntity() != this.getOwner()) {
            entityHitResult.getEntity().hurt(DamageSource.indirectMagic(entityHitResult.getEntity(), this.getOwner()), damage * damageIncrease);
            if (canIgnite) {
                entityHitResult.getEntity().setSecondsOnFire(rand.nextInt(4) + 4);
            }
            this.explode();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        //super.onHitBlock(blockHitResult);
        this.explode();
        this.inGround = false;
    }

    @Override
    public void playerTouch(Player player) {
    }

    private void explode()
    {
        if(!this.level.isClientSide)
        {
            new ExplosionConfigurable(this.level, this.getOwner() != null ? this.getOwner() : this, DamageSource.playerAttack((Player) this.getOwner()).setMagic(), null, this.position().x(), this.position().y(), this.position().z(), 1F, damage / 1.5f, false, Explosion.BlockInteraction.NONE);
            playSound(ModSoundEvents.FALLING_STAR_CRASH,0.25f, 1.7f);
            this.kill();
        }
    }

    @Override
    public void tick()
    {
        super.tick();
        adjustMotion();
        trinketCheck();
        particleController();
        if (this.isAlive() && this.getOwner() != null) {
            ((Player) this.getOwner()).getCooldowns().addCooldown(wandItem, 10);
            if (!this.getOwner().isAlive()) {
                this.explode();
            }
        }
        if (!canBeInWater && this.isInWater()) {
            this.explode();
        }
        if (!canBeInLava && this.isInLava()) {
            this.explode();
        }
        if (limitedTime) {
            timer++;
            if (timer >= 1200) {
                this.explode();
            }
        }
    }

    private void adjustMotion() {
        if (this.getOwner() != null) {
            Utilities.rotateTowardsMotion(this, 0.4F);
            BlockHitResult result = Utilities.rayTraceBlocks(this.getOwner(), 16D, false);
            EntityHitResult result2 = Utilities.rayTraceEntity(this.getOwner(), this, 16D);
            Vec3 end;
            if (result2 != null) {
                end = result2.getLocation().add(0, result2.getEntity().getEyeHeight(), 0);
            } else {
                end = result != null ? result.getLocation() : this.getOwner().getViewVector(1.0F).add(this.getOwner().position().x(), this.getOwner().position().y(), this.getOwner().position().z());
            }
            if (true) { // will be replaced with something else later, will make the projectile stop moving if at the HitResult point to stop the projectile rapidly moving bug.
                this.fire(this.position().x(), this.position().y(), this.position().z(), new Vec3(end.x - this.position().x(), end.y - this.position().y(), end.z - this.position().z()).normalize(), this.speed, 0F);
            }
        } else {
            this.explode();
        }
    }

    public void fire(double x, double y, double z, Vec3 direction, double speed, float inaccuracy)
    {
        this.setPos(x, y, z);
        direction = direction.normalize();
        Vec3 motion = direction;
        if(inaccuracy != 0F) motion = Utilities.sampleSphereCap(direction, inaccuracy);
        motion = motion.scale(speed);
        this.setDeltaMovement(motion.x, motion.y, motion.z);
        Utilities.rotateTowardsMotion(this, 1F);
    }

    private void particleController() {
        if (changeParticleType == 0) {
            spawnMagicEffects();
            trailEffect();
        } else if (changeParticleType == 1) {
            spawnFireEffects();
            trailEffect();
        } else {
            spawnRainbowEffects();
            trailEffect();
        }
    }

    private void spawnMagicEffects() {
        float random = (rand.nextFloat() - 0.5F) * 0.1F;
        this.level.addParticle(ParticleTypes.ENCHANTED_HIT, position().x(), position().y(), position().z(), random, -0.2D, random);
        this.level.addParticle(TerraCraft.BLUE_POOF, position().x(), position().y(), position().z(), random, -0.2D, random);
    }
    private void spawnFireEffects() {
        float random = (rand.nextFloat() - 0.5F) * 0.1F;
        this.level.addParticle(ParticleTypes.FLAME, position().x(), position().y(), position().z(), random, -0.2D, random);
        this.level.addParticle(ParticleTypes.LAVA, position().x(), position().y(), position().z(), random, -0.2D, random);
    }
    private void spawnRainbowEffects() {
        float random = (rand.nextFloat() - 0.5F) * 0.1F;
        this.level.addParticle(ParticleTypes.FLAME, position().x(), position().y(), position().z(), random, -0.2D, random);
        this.level.addParticle(TerraCraft.GREEN_SPARK, position().x(), position().y(), position().z(), random, -0.2D, random);
        this.level.addParticle(TerraCraft.BLUE_POOF, position().x(), position().y(), position().z(), random, -0.2D, random);
    }

    private void trailEffect() {
        //nothing yet...
    }

    @Override
    protected ItemStack getPickupItem() {
        return null;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putFloat("speed", speed);
        compound.putFloat("damage", damage);
        compound.putInt("changeParticleType", changeParticleType);
        compound.putBoolean("canBeInWater", canBeInWater);
        compound.putBoolean("canBeInLava", canBeInLava);
        compound.putBoolean("canIgnite", canIgnite);
        compound.putBoolean("limitedTime", limitedTime);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        speed = compound.getFloat("speed");
        damage = compound.getFloat("damage");
        changeParticleType = compound.getInt("changeParticleType");
        canBeInWater = compound.getBoolean("canBeInWater");
        canBeInLava = compound.getBoolean("canBeInLava");
        canIgnite = compound.getBoolean("canIgnite");
        limitedTime = compound.getBoolean("limitedTime");
    }
}
