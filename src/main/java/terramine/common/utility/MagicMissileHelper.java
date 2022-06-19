package terramine.common.utility;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import terramine.common.init.ModItems;
import terramine.common.init.ModSoundEvents;
import terramine.common.trinkets.TrinketsHelper;

public class MagicMissileHelper extends AbstractArrow {

    private final RandomSource rand = RandomSource.create();
    private Item wandItem;
    private float damageIncrease;
    private float speed;
    private float damage;
    private int timer;
    private boolean canBeInWater;
    private boolean canBeInLava;
    private boolean canIgnite = false;
    private boolean limitedTime = false;

    public MagicMissileHelper(EntityType<? extends MagicMissileHelper> entityType, Level level) {
        super(entityType, level);
        this.setNoGravity(true);
        setKnockback(7);
        this.pickup = AbstractArrow.Pickup.DISALLOWED;
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

    public SoundSource getSoundSource() {
        return SoundSource.PLAYERS;
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
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
            new ExplosionConfigurable(this.level, this.getOwner() != null ? this.getOwner() : this, DamageSource.playerAttack((Player) this.getOwner()).setMagic(), null, this.position().x(), this.position().y(), this.position().z(), 1F, damage / 1.5f, true, false, Explosion.BlockInteraction.NONE);
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
        createParticles();
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

    public void createParticles() {
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
        canBeInWater = compound.getBoolean("canBeInWater");
        canBeInLava = compound.getBoolean("canBeInLava");
        canIgnite = compound.getBoolean("canIgnite");
        limitedTime = compound.getBoolean("limitedTime");
    }
}
