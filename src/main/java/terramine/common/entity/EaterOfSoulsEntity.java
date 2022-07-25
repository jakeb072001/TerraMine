package terramine.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import terramine.common.init.ModLootTables;
import terramine.common.init.ModSoundEvents;

public class EaterOfSoulsEntity extends Monster implements Enemy {
    public boolean spawnedBlood = false;
    public boolean bounce;
    public boolean doOnce = false;
    public double velX, velY, velZ;
    public double oldVelX, oldVelY, oldVelZ;

    public EaterOfSoulsEntity(EntityType<? extends EaterOfSoulsEntity> entityType, Level worldIn) {
        super(entityType, worldIn);
        moveControl = new EaterOfSoulsMovementController(this);
    }

    protected BodyRotationControl createBodyControl() {
        return new EaterOfSoulsRotationControl(this);
    }

    @SuppressWarnings("ConstantConditions")
    protected static class EaterOfSoulsMovementController extends MoveControl {
        private final EaterOfSoulsEntity eaterOfSouls;

        public EaterOfSoulsMovementController(EaterOfSoulsEntity eaterOfSouls) {
            super(eaterOfSouls);
            this.eaterOfSouls = eaterOfSouls;
        }

        @Override
        public void tick() {
            if (eaterOfSouls.isAlive()) {
                if (eaterOfSouls.horizontalCollision) {
                    eaterOfSouls.bounce = true;
                }

                double motionY;
                double motionX;
                double motionZ;
                eaterOfSouls.setNoGravity(true);
                eaterOfSouls.fallDistance = 0;
                Level world = eaterOfSouls.level;
                Player target = null;

                for(int i = 0; i < eaterOfSouls.level.players().size(); ++i) {
                    double dist = eaterOfSouls.level.players().get(i).position().distanceTo(eaterOfSouls.position());
                    if (dist < eaterOfSouls.getAttribute(Attributes.FOLLOW_RANGE).getValue()) {
                        if (!eaterOfSouls.level.players().get(i).isCreative()) {
                            if (!eaterOfSouls.level.players().get(i).isSpectator()) {
                                target = eaterOfSouls.level.players().get(i);
                            }
                        }
                    }
                }

                if (world.getBlockState(new BlockPos(eaterOfSouls.position().x(), eaterOfSouls.position().y() - 1, eaterOfSouls.position().z())).getBlock().defaultBlockState() == Blocks.WATER.defaultBlockState()) {
                    if (eaterOfSouls.velY < 0) {
                        eaterOfSouls.velY = 3;
                    }
                }

                if (!eaterOfSouls.isSpectator()) {
                    if (world.getBlockState(new BlockPos(eaterOfSouls.position().x, eaterOfSouls.position().y - 0.5f, eaterOfSouls.position().z)).getMaterial().isSolid()) {
                        eaterOfSouls.velY = 2.0f;
                    }
                }

                if (eaterOfSouls.bounce) {
                    if (!eaterOfSouls.onGround) {
                        eaterOfSouls.velX = eaterOfSouls.oldVelX * -0.5;
                        if (eaterOfSouls.velX > 0 && eaterOfSouls.velX < 4) {
                            eaterOfSouls.velX = 4;
                        }
                        if (eaterOfSouls.velX < 0 && eaterOfSouls.velX > -4) {
                            eaterOfSouls.velX = -4;
                        }
                        eaterOfSouls.velZ = eaterOfSouls.oldVelZ * -0.5;
                        if (eaterOfSouls.velZ > 0 && eaterOfSouls.velZ < 4) {
                            eaterOfSouls.velZ = 4;
                        }
                        if (eaterOfSouls.velZ < 0 && eaterOfSouls.velZ > -4) {
                            eaterOfSouls.velZ = -4;
                        }
                    }

                    if (eaterOfSouls.onGround) {
                        eaterOfSouls.velY = eaterOfSouls.oldVelY * -0.5;
                        if (eaterOfSouls.velY > 0 && eaterOfSouls.velY < 2) {
                            eaterOfSouls.velY = 2;
                        }
                        if (eaterOfSouls.velY < 0 && eaterOfSouls.velY > -2) {
                            eaterOfSouls.velY = -2;
                        }
                    }
                }

                if (target != null) {
                    eaterOfSouls.doOnce = false;
                    if (eaterOfSouls.velX > -4 && eaterOfSouls.position().x > target.position().x + target.getBbWidth()) {
                        eaterOfSouls.velX -= 0.08;
                        if (eaterOfSouls.velX > 4) {
                            eaterOfSouls.velX -= 0.04;
                        }
                        else if (eaterOfSouls.velX > 0) {
                            eaterOfSouls.velX -= 0.2;
                        }
                        if (eaterOfSouls.velX < -4) {
                            eaterOfSouls.velX = -4;
                        }
                    } else if (eaterOfSouls.velX < 4 && eaterOfSouls.position().x + 1 < target.position().x) {
                        eaterOfSouls.velX += 0.08;
                        if (eaterOfSouls.velX < -4) {
                            eaterOfSouls.velX += 0.04;
                        }
                        else if (eaterOfSouls.velX < 0) {
                            eaterOfSouls.velX += 0.2;
                        }
                        if (eaterOfSouls.velX > 4) {
                            eaterOfSouls.velX = 4;
                        }
                    }

                    if (eaterOfSouls.velZ > -4 && eaterOfSouls.position().z > target.position().z + target.getBbWidth()) {
                        eaterOfSouls.velZ -= 0.08;
                        if (eaterOfSouls.velZ > 4) {
                            eaterOfSouls.velZ -= 0.04;
                        }
                        else if (eaterOfSouls.velZ > 0f) {
                            eaterOfSouls.velZ -= 0.2;
                        }
                        if (eaterOfSouls.velZ < -4) {
                            eaterOfSouls.velZ = -4;
                        }
                    } else if (eaterOfSouls.velZ < 4f && eaterOfSouls.position().z + 1 < target.position().z) {
                        eaterOfSouls.velZ += 0.08f;
                        if (eaterOfSouls.velZ < -4) {
                            eaterOfSouls.velZ += 0.04;
                        }
                        else if (eaterOfSouls.velZ < 0f) {
                            eaterOfSouls.velZ += 0.2;
                        }
                        if (eaterOfSouls.velZ > 4) {
                            eaterOfSouls.velZ = 4;
                        }
                    }

                    if (eaterOfSouls.velY > -2.5 && eaterOfSouls.position().y > target.position().y + target.getBbHeight()) {
                        eaterOfSouls.velY -= 0.1f;
                        if (eaterOfSouls.velY > 2.5) {
                            eaterOfSouls.velY -= 0.05;
                        } else if (eaterOfSouls.velY > 0f) {
                            eaterOfSouls.velY -= 0.15;
                        }
                        if (eaterOfSouls.velY < -2.5) {
                            eaterOfSouls.velY = -2.5;
                        }
                    } else if (eaterOfSouls.velY < 2.5 && eaterOfSouls.position().y + 1 < target.position().y) {
                        eaterOfSouls.velY += 0.1f;
                        if (eaterOfSouls.velY < -2.5) {
                            eaterOfSouls.velY += 0.05;
                        }
                        else if (eaterOfSouls.velY < 0) {
                            eaterOfSouls.velY += 0.15;
                        }
                        if (eaterOfSouls.velY > 2.5) {
                            eaterOfSouls.velY = 2.5;
                        }
                    }
                } else {
                    lookRandomly();
                }

                eaterOfSouls.bounce = false;
                eaterOfSouls.oldVelX = eaterOfSouls.velX;
                eaterOfSouls.oldVelY = eaterOfSouls.velY;
                eaterOfSouls.oldVelZ = eaterOfSouls.velZ;
                motionX = eaterOfSouls.velX * 0.075f * eaterOfSouls.getAttribute(Attributes.MOVEMENT_SPEED).getValue();
                motionY = eaterOfSouls.velY * 0.075f * eaterOfSouls.getAttribute(Attributes.MOVEMENT_SPEED).getValue();
                motionZ = eaterOfSouls.velZ * 0.075f * eaterOfSouls.getAttribute(Attributes.MOVEMENT_SPEED).getValue();

                eaterOfSouls.setYRot(rotlerp(eaterOfSouls.getYRot(), (float)Math.toDegrees(Math.atan2(eaterOfSouls.velZ, eaterOfSouls.velX)) - 90, 360));
                eaterOfSouls.setXRot(rotlerp(eaterOfSouls.getXRot(), (float)Math.toDegrees(eaterOfSouls.velY), 360));

                eaterOfSouls.setDeltaMovement(motionX, motionY, motionZ);
            } else {
                if (!eaterOfSouls.spawnedBlood) {
                    for (int i = 0; i <= 100; i++) {
                        eaterOfSouls.level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.RED_CONCRETE.defaultBlockState()), eaterOfSouls.position().x, eaterOfSouls.position().y, eaterOfSouls.position().z, 0, 0, 0);
                    }
                    eaterOfSouls.spawnedBlood = true;
                }
                eaterOfSouls.setDeltaMovement(0, -0.5f, 0);
            }
        }

        public void lookRandomly() {
            if (!eaterOfSouls.doOnce) {
                eaterOfSouls.setYRot(rotlerp(eaterOfSouls.getYRot(), eaterOfSouls.random.nextInt(3), 360));
                eaterOfSouls.velX = eaterOfSouls.random.nextInt(-2, 3);
                eaterOfSouls.velZ = eaterOfSouls.random.nextInt(-2, 3);
                eaterOfSouls.velY = eaterOfSouls.random.nextInt(-1, 2);
                eaterOfSouls.doOnce = true;
            }
        }
    }

    @Override
    public boolean checkSpawnRules(@NotNull LevelAccessor world, @NotNull MobSpawnType spawnReason) {
        return true;
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.ARMOR, 8)
                .add(Attributes.FOLLOW_RANGE, 24)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
                .add(Attributes.MOVEMENT_SPEED, 0.7)
                .add(Attributes.ATTACK_DAMAGE, 2);
    }

    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    @SuppressWarnings("ConstantConditions")
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
        return ModLootTables.EATER_OF_SOULS;
    }

    @Override
    public ItemStack getItemBySlot(@NotNull EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlotAndDropWhenKilled(@NotNull EquipmentSlot slot, @NotNull ItemStack stack) {
    }

    static class EaterOfSoulsRotationControl extends BodyRotationControl {
        private final EaterOfSoulsEntity eaterOfSouls;

        public EaterOfSoulsRotationControl(EaterOfSoulsEntity eaterOfSouls) {
            super(eaterOfSouls);
            this.eaterOfSouls = eaterOfSouls;
        }

        public void clientTick() {
            eaterOfSouls.yHeadRot = eaterOfSouls.yBodyRot;
            eaterOfSouls.yBodyRot = eaterOfSouls.getYRot();
        }
    }
}
