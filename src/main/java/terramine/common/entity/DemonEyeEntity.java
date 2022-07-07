package terramine.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import terramine.common.init.ModLootTables;
import terramine.common.init.ModSoundEvents;

public class DemonEyeEntity extends Monster implements Enemy {
    public static final EntityDataAccessor<Integer> typed_data = SynchedEntityData.defineId(DemonEyeEntity.class, EntityDataSerializers.INT);
    public boolean spawnedBlood = false;
    public boolean bounce;
    public boolean doOnce = false;
    public double velX, velY, velZ;
    public double oldVelX, oldVelY, oldVelZ;

    public DemonEyeEntity(EntityType<? extends DemonEyeEntity> entityType, Level worldIn) {
        super(entityType, worldIn);
        moveControl = new DemonEyeMovementController(this);
        setEyeType(random.nextInt(6));
    }

    protected BodyRotationControl createBodyControl() {
        return new DemonEyeRotationControl(this);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(typed_data, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("eyeType", getEyeType());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setEyeType(tag.getInt("eyeType"));
    }

    @SuppressWarnings("ConstantConditions")
    private void setEyeType(int eyeType) {
        this.entityData.set(typed_data, eyeType);
        switch (eyeType) {
            case 0 -> {
                this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(10);
                this.getAttribute(Attributes.ARMOR).setBaseValue(2);
                this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.2);
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(1.5);
            }
            case 1 -> {
                this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(15);
                this.getAttribute(Attributes.ARMOR).setBaseValue(4);
                this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.3);
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(1.5);
            }
            case 2 -> {
                this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20);
                this.getAttribute(Attributes.ARMOR).setBaseValue(2);
                this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.2);
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(1.5);
            }
            case 3 -> {
                this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(15);
                this.getAttribute(Attributes.ARMOR).setBaseValue(0);
                this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.2);
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(2);
            }
            case 4 -> {
                this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(15);
                this.getAttribute(Attributes.ARMOR).setBaseValue(4);
                this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.2);
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(1);
            }
            case 5 -> {
                this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(15);
                this.getAttribute(Attributes.ARMOR).setBaseValue(2);
                this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.15);
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(1);
            }
        }
    }

    private int getEyeType() {
        return this.entityData.get(typed_data);
    }

    protected static class DemonEyeMovementController extends MoveControl {
        private final DemonEyeEntity demonEye;

        public DemonEyeMovementController(DemonEyeEntity demonEye) {
            super(demonEye);
            this.demonEye = demonEye;
        }

        @Override
        public void tick() {
            if (demonEye.isAlive()) {
                if (demonEye.horizontalCollision) { // todo: apply opposite force so that the demon eye actually bounces (right now it kinda bounces when hitting a wall with speed but eventually will get to 0 speed and will not bounce)
                    demonEye.setYRot(demonEye.getYRot() + 180.0F);
                    demonEye.velX = -demonEye.velX;
                    demonEye.velZ = -demonEye.velZ;
                }

                boolean isDay = demonEye.level.isDay();

                double motionY;
                double motionX;
                double motionZ;
                demonEye.setNoGravity(true);
                demonEye.fallDistance = 0;
                Level world = demonEye.level;
                Player target = null;

                for(int i = 0; i < demonEye.level.players().size(); ++i) {
                    double dist = demonEye.level.players().get(i).position().distanceTo(demonEye.position());
                    if (dist < demonEye.getAttribute(Attributes.FOLLOW_RANGE).getValue()) {
                        if (!demonEye.level.players().get(i).isCreative()) {
                            if (!demonEye.level.players().get(i).isSpectator()) {
                                target = demonEye.level.players().get(i);
                            }
                        }
                    }
                }

                if (world.getBlockState(new BlockPos(demonEye.position().x(), demonEye.position().y() - 1, demonEye.position().z())).getBlock().defaultBlockState() == Blocks.WATER.defaultBlockState()) {
                    if (demonEye.velY < 0) {
                        demonEye.velY = 3;
                    }
                }

                if (!demonEye.isSpectator()) {
                    if (world.getBlockState(new BlockPos(demonEye.position().x, demonEye.position().y - 0.5f, demonEye.position().z)).getMaterial().isSolid()) {
                        demonEye.velY = 2.0f;
                    }
                }

                if (demonEye.bounce) {
                    if (!demonEye.onGround) {
                        demonEye.velX = demonEye.oldVelX * -0.5;
                        if (demonEye.velX > 0 && demonEye.velX < 2) {
                            demonEye.velX = 2;
                        }
                        if (demonEye.velX < 0 && demonEye.velX > -2) {
                            demonEye.velX = -2;
                        }
                        demonEye.velZ = demonEye.oldVelZ * -0.5;
                        if (demonEye.velZ > 0 && demonEye.velZ < 2) {
                            demonEye.velZ = 2;
                        }
                        if (demonEye.velZ < 0 && demonEye.velZ > -2) {
                            demonEye.velZ = -2;
                        }
                    }

                    if (demonEye.onGround) {
                        demonEye.velY = demonEye.oldVelY * -0.5;
                        if (demonEye.velY > 0 && demonEye.velY < 1) {
                            demonEye.velY = 1;
                        }
                        if (demonEye.velY < 0 && demonEye.velY > -1) {
                            demonEye.velY = -1;
                        }
                    }

                }

                if (isDay && demonEye.level.canSeeSky(new BlockPos(demonEye.getEyePosition()))) {
                    if (!demonEye.doOnce) {
                        demonEye.velX = demonEye.random.nextInt(-2, 3);
                        demonEye.velZ = demonEye.random.nextInt(-2, 3);
                        demonEye.velY = demonEye.random.nextInt(-2, 5);
                        demonEye.doOnce = true;
                    }
                } else if (target != null) {
                    demonEye.doOnce = false;
                    if (demonEye.velX > -4 && demonEye.position().x > target.position().x + target.getBbWidth()) {
                        demonEye.velX -= 0.08;
                        if (demonEye.velX > 4) {
                            demonEye.velX -= 0.04;
                        }
                        else if (demonEye.velX > 0) {
                            demonEye.velX -= 0.2;
                        }
                        if (demonEye.velX < -4) {
                            demonEye.velX = -4;
                        }
                    } else if (demonEye.velX < 4 && demonEye.position().x + 1 < target.position().x) {
                        demonEye.velX += 0.08;
                        if (demonEye.velX < -4) {
                            demonEye.velX += 0.04;
                        }
                        else if (demonEye.velX < 0) {
                            demonEye.velX += 0.2;
                        }
                        if (demonEye.velX > 4) {
                            demonEye.velX = 4;
                        }
                    }

                    if (demonEye.velZ > -4 && demonEye.position().z > target.position().z + target.getBbWidth()) {
                        demonEye.velZ -= 0.08;
                        if (demonEye.velZ > 4) {
                            demonEye.velZ -= 0.04;
                        }
                        else if (demonEye.velZ > 0f) {
                            demonEye.velZ -= 0.2;
                        }
                        if (demonEye.velZ < -4) {
                            demonEye.velZ = -4;
                        }
                    } else if (demonEye.velZ < 4f && demonEye.position().z + 1 < target.position().z) {
                        demonEye.velZ += 0.08f;
                        if (demonEye.velZ < -4) {
                            demonEye.velZ += 0.04;
                        }
                        else if (demonEye.velZ < 0f) {
                            demonEye.velZ += 0.2;
                        }
                        if (demonEye.velZ > 4) {
                            demonEye.velZ = 4;
                        }
                    }

                    if (demonEye.velY > -2.5 && demonEye.position().y > target.position().y + target.getBbHeight()) {
                        demonEye.velY -= 0.1f;
                        if (demonEye.velY > 2.5) {
                            demonEye.velY -= 0.05;
                        } else if (demonEye.velY > 0f) {
                            demonEye.velY -= 0.15;
                        }
                        if (demonEye.velY < -2.5) {
                            demonEye.velY = -2.5;
                        }
                    } else if (demonEye.velY < 2.5 && demonEye.position().y + 1 < target.position().y) {
                        demonEye.velY += 0.1f;
                        if (demonEye.velY < -2.5) {
                            demonEye.velY += 0.05;
                        }
                        else if (demonEye.velY < 0) {
                            demonEye.velY += 0.15;
                        }
                        if (demonEye.velY > 2.5) {
                            demonEye.velY = 2.5;
                        }
                    }
                } else {
                    lookRandomly();
                }

                demonEye.bounce = false;
                demonEye.oldVelX = demonEye.velX;
                demonEye.oldVelY = demonEye.velY;
                demonEye.oldVelZ = demonEye.velZ;
                motionX = demonEye.velX * 0.075f * demonEye.getAttribute(Attributes.MOVEMENT_SPEED).getValue();
                motionY = demonEye.velY * 0.075f * demonEye.getAttribute(Attributes.MOVEMENT_SPEED).getValue();
                motionZ = demonEye.velZ * 0.075f * demonEye.getAttribute(Attributes.MOVEMENT_SPEED).getValue();

                demonEye.setYRot(rotlerp(demonEye.getYRot(), (float)Math.toDegrees(Math.atan2(demonEye.velZ, demonEye.velX)) - 90, 360));
                demonEye.setXRot(rotlerp(demonEye.getXRot(), (float)Math.toDegrees(demonEye.velY), 360));

                demonEye.setDeltaMovement(motionX, motionY, motionZ);
            } else {
                if (!demonEye.spawnedBlood) {
                    for (int i = 0; i <= 100; i++) {
                        demonEye.level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.RED_CONCRETE.defaultBlockState()), demonEye.position().x, demonEye.position().y, demonEye.position().z, 0, 0, 0);
                    }
                    demonEye.spawnedBlood = true;
                }
                demonEye.setDeltaMovement(0, -0.5f, 0);
            }
        }

        public void lookRandomly() {
            if (!demonEye.doOnce) {
                demonEye.setYRot(rotlerp(demonEye.getYRot(), demonEye.random.nextInt(3), 360));
                demonEye.velX = demonEye.random.nextInt(-2, 3);
                demonEye.velZ = demonEye.random.nextInt(-2, 3);
                demonEye.velY = demonEye.random.nextInt(-1, 2);
                demonEye.doOnce = true;
            }
        }
    }

    @Override
    public boolean checkSpawnRules(@NotNull LevelAccessor world, @NotNull MobSpawnType spawnReason) {
        if (isDarkEnoughToSpawn((ServerLevelAccessor) world, this.blockPosition(), random)) {
            return this.blockPosition().getY() <= 60 || !this.level.isDay();
        }

        return false;
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 15)
                .add(Attributes.ARMOR, 2)
                .add(Attributes.FOLLOW_RANGE, 24)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.2)
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.ATTACK_DAMAGE, 1.5);
    }

    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    @Override
    public void playerTouch(@NotNull Player player) {
        super.playerTouch(player);

        if (this.isAlive()) {
            player.hurt(DamageSource.mobAttack(this), (float) this.getAttribute(Attributes.ATTACK_DAMAGE).getValue());
        }
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        playSound(ModSoundEvents.DEMON_EYE_HURT, getSoundVolume(), getVoicePitch());
        return super.hurt(source, amount);
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
        return ModLootTables.DEMON_EYE;
    }

    @Override
    public ItemStack getItemBySlot(@NotNull EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlotAndDropWhenKilled(@NotNull EquipmentSlot slot, @NotNull ItemStack stack) {
    }

    static class DemonEyeRotationControl extends BodyRotationControl {
        private final DemonEyeEntity demonEye;

        public DemonEyeRotationControl(DemonEyeEntity demonEye) {
            super(demonEye);
            this.demonEye = demonEye;
        }

        public void clientTick() {
            demonEye.yHeadRot = demonEye.yBodyRot;
            demonEye.yBodyRot = demonEye.getYRot();
        }
    }
}
