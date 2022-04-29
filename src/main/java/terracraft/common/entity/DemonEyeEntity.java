package terracraft.common.entity;

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
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import terracraft.common.init.ModComponents;
import terracraft.common.init.ModLootTables;
import terracraft.common.init.ModSoundEvents;

public class DemonEyeEntity extends Monster implements Enemy {
    public static final EntityDataAccessor<Integer> typed_data = SynchedEntityData.defineId(DemonEyeEntity.class, EntityDataSerializers.INT);
    public boolean spawnedBlood = false;
    public boolean bounce;
    public boolean doOnce = false;
    public double velX, velY, velZ;
    public double oldVelX, oldVelY, oldVelZ;
    private int eyeType;

    public DemonEyeEntity(EntityType<? extends DemonEyeEntity> entityType, Level worldIn) {
        super(entityType, worldIn);
        moveControl = new DemonEyeMovementController(this);
        eyeType = random.nextInt(6);
        this.getEntityData().define(DemonEyeEntity.typed_data, 1);
        this.getEntityData().set(DemonEyeEntity.typed_data, eyeType);
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
                boolean night = demonEye.level.getDayTime() <= 23999 && demonEye.level.getDayTime() >= 13000;

                double motionY;
                double motionX;
                double motionZ;
                demonEye.setNoGravity(true);
                demonEye.fallDistance = 0;
                demonEye.setYRot(0);
                demonEye.setXRot(0);
                demonEye.yHeadRot = 0;
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

                if (!night && demonEye.level.canSeeSky(demonEye.eyeBlockPosition())) {
                    if (!demonEye.doOnce) {
                        demonEye.velX = demonEye.random.nextInt(-2, 3);
                        demonEye.velZ = demonEye.random.nextInt(-2, 3);
                        demonEye.velY = demonEye.random.nextInt(-2, 5);
                        demonEye.doOnce = true;
                    }
                    if (demonEye.isSunBurnTick()) {
                        //demonEye.setSecondsOnFire(8);
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
                demonEye.oldVelX = demonEye.velX + 0;
                demonEye.oldVelY = demonEye.velY + 0;
                demonEye.oldVelZ = demonEye.velZ + 0;
                motionX = demonEye.velX * 0.075f * demonEye.getAttribute(Attributes.MOVEMENT_SPEED).getValue();
                motionY = demonEye.velY * 0.075f * demonEye.getAttribute(Attributes.MOVEMENT_SPEED).getValue();
                motionZ = demonEye.velZ * 0.075f * demonEye.getAttribute(Attributes.MOVEMENT_SPEED).getValue();

                demonEye.setYRot(rotlerp(demonEye.getYRot(), (float)Math.toDegrees(Math.atan2(demonEye.velZ, demonEye.velX)) - 90, 360));
                demonEye.setXRot(rotlerp(demonEye.getXRot(), (float) demonEye.velY, 360));
                demonEye.yHeadRot = demonEye.yBodyRot = demonEye.getYRot();

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

    /*
    public static AttributeSupplier.Builder createMobAttributes() {
        return switch (eyeType) {
            case 0 -> Mob.createMobAttributes()
                    .add(Attributes.MAX_HEALTH, 15)
                    .add(Attributes.ARMOR, 2)
                    .add(Attributes.FOLLOW_RANGE, 24)
                    .add(Attributes.KNOCKBACK_RESISTANCE, 0.2)
                    .add(Attributes.MOVEMENT_SPEED, 1)
                    .add(Attributes.ATTACK_DAMAGE, 3.5);
            case 1 -> Mob.createMobAttributes()
                    .add(Attributes.MAX_HEALTH, 20)
                    .add(Attributes.ARMOR, 4)
                    .add(Attributes.FOLLOW_RANGE, 24)
                    .add(Attributes.KNOCKBACK_RESISTANCE, 0.3)
                    .add(Attributes.MOVEMENT_SPEED, 1)
                    .add(Attributes.ATTACK_DAMAGE, 3.5);
            case 2 -> Mob.createMobAttributes()
                    .add(Attributes.MAX_HEALTH, 25)
                    .add(Attributes.ARMOR, 2)
                    .add(Attributes.FOLLOW_RANGE, 24)
                    .add(Attributes.KNOCKBACK_RESISTANCE, 0.2)
                    .add(Attributes.MOVEMENT_SPEED, 1)
                    .add(Attributes.ATTACK_DAMAGE, 3.5);
            case 3 -> Mob.createMobAttributes()
                    .add(Attributes.MAX_HEALTH, 20)
                    .add(Attributes.ARMOR, 0)
                    .add(Attributes.FOLLOW_RANGE, 24)
                    .add(Attributes.KNOCKBACK_RESISTANCE, 0.2)
                    .add(Attributes.MOVEMENT_SPEED, 1)
                    .add(Attributes.ATTACK_DAMAGE, 4);
            case 4 -> Mob.createMobAttributes()
                    .add(Attributes.MAX_HEALTH, 20)
                    .add(Attributes.ARMOR, 4)
                    .add(Attributes.FOLLOW_RANGE, 24)
                    .add(Attributes.KNOCKBACK_RESISTANCE, 0.2)
                    .add(Attributes.MOVEMENT_SPEED, 1)
                    .add(Attributes.ATTACK_DAMAGE, 3);
            case 5 -> Mob.createMobAttributes()
                    .add(Attributes.MAX_HEALTH, 20)
                    .add(Attributes.ARMOR, 2)
                    .add(Attributes.FOLLOW_RANGE, 24)
                    .add(Attributes.KNOCKBACK_RESISTANCE, 0.15)
                    .add(Attributes.MOVEMENT_SPEED, 1)
                    .add(Attributes.ATTACK_DAMAGE, 3);
            default -> Mob.createMobAttributes();
        };
    }
     */

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 25)
                .add(Attributes.ARMOR, 2)
                .add(Attributes.FOLLOW_RANGE, 24)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.2)
                .add(Attributes.MOVEMENT_SPEED, 1)
                .add(Attributes.ATTACK_DAMAGE, 3.5);
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

    @Override
    public boolean removeWhenFarAway(double distance) {
        return true;
    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.LEFT;
    }
}
