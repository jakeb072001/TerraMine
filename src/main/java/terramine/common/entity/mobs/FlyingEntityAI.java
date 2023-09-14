package terramine.common.entity.mobs;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import terramine.common.init.ModSoundEvents;

public class FlyingEntityAI extends Monster implements Enemy {
    public boolean dayEscape, doOnce, bounce = false;
    public double velX, velY, velZ;
    public double oldVelX, oldVelY, oldVelZ;

    public FlyingEntityAI(EntityType<? extends FlyingEntityAI> entityType, Level worldIn) {
        super(entityType, worldIn);
        this.moveControl = new FlyingEntityMovementController(this);
        this.lookControl = new FlyingEntityLookControl(this);
    }

    protected BodyRotationControl createBodyControl() {
        return new FlyingEntityRotationControl(this);
    }

    protected static class FlyingEntityLookControl extends LookControl {
        public FlyingEntityLookControl(FlyingEntityAI flyingEntity) {
            super(flyingEntity);
        }

        @Override
        public void tick() {
        }
    }

    @SuppressWarnings("ConstantConditions")
    protected static class FlyingEntityMovementController extends MoveControl {
        private final FlyingEntityAI flyingEntity;

        public FlyingEntityMovementController(FlyingEntityAI flyingEntity) {
            super(flyingEntity);
            this.flyingEntity = flyingEntity;
        }

        @Override
        public void tick() {
            if (flyingEntity.isAlive()) {
                if (flyingEntity.horizontalCollision) {
                    flyingEntity.bounce = true;
                }

                boolean isDay = flyingEntity.level().isDay();

                double motionY;
                double motionX;
                double motionZ;
                flyingEntity.setNoGravity(true);
                flyingEntity.fallDistance = 0;
                Level world = flyingEntity.level();
                Player target;

                /*
                for(int i = 0; i < flyingEntity.level.players().size(); ++i) {
                    double dist = flyingEntity.level.players().get(i).position().distanceTo(flyingEntity.position());
                    if (dist < flyingEntity.getAttribute(Attributes.FOLLOW_RANGE).getValue()) {
                        if (!flyingEntity.level.players().get(i).isCreative()) {
                            if (!flyingEntity.level.players().get(i).isSpectator()) {
                                target = flyingEntity.level.players().get(i);
                            }
                        }
                    }
                }
                 */

                target = flyingEntity.level().getNearestPlayer(flyingEntity.getX(), flyingEntity.getY(), flyingEntity.getZ(), flyingEntity.getAttribute(Attributes.FOLLOW_RANGE).getValue(), true);

                if (world.getBlockState(new BlockPos((int) flyingEntity.position().x(), (int) (flyingEntity.position().y() - 1), (int) flyingEntity.position().z())).getBlock().defaultBlockState() == Blocks.WATER.defaultBlockState()) {
                    if (flyingEntity.velY < 0) {
                        flyingEntity.velY = 3;
                    }
                }

                if (!flyingEntity.isSpectator()) {
                    if (world.getBlockState(new BlockPos((int) flyingEntity.position().x, (int) (flyingEntity.position().y - 0.5f), (int) flyingEntity.position().z)).isSolid()) {
                        flyingEntity.velY = 2.0f;
                    }
                }

                if (flyingEntity.bounce) {
                    if (!flyingEntity.onGround()) {
                        flyingEntity.velX = flyingEntity.oldVelX * -0.5;
                        if (flyingEntity.velX > 0 && flyingEntity.velX < 4) {
                            flyingEntity.velX = 4;
                        }
                        if (flyingEntity.velX < 0 && flyingEntity.velX > -4) {
                            flyingEntity.velX = -4;
                        }
                        flyingEntity.velZ = flyingEntity.oldVelZ * -0.5;
                        if (flyingEntity.velZ > 0 && flyingEntity.velZ < 4) {
                            flyingEntity.velZ = 4;
                        }
                        if (flyingEntity.velZ < 0 && flyingEntity.velZ > -4) {
                            flyingEntity.velZ = -4;
                        }
                    }

                    if (flyingEntity.onGround()) {
                        flyingEntity.velY = flyingEntity.oldVelY * -0.5;
                        if (flyingEntity.velY > 0 && flyingEntity.velY < 2) {
                            flyingEntity.velY = 2;
                        }
                        if (flyingEntity.velY < 0 && flyingEntity.velY > -2) {
                            flyingEntity.velY = -2;
                        }
                    }
                }

                if (flyingEntity.dayEscape && isDay && flyingEntity.level().canSeeSky(new BlockPos(new Vec3i((int) flyingEntity.getEyePosition().x, (int) flyingEntity.getEyePosition().y, (int) flyingEntity.getEyePosition().z)))) {
                    if (!flyingEntity.doOnce) {
                        flyingEntity.velX = flyingEntity.random.nextInt(-2, 3);
                        flyingEntity.velZ = flyingEntity.random.nextInt(-2, 3);
                        flyingEntity.velY = flyingEntity.random.nextInt(-2, 5);
                        flyingEntity.doOnce = true;
                    }
                } else if (target != null && !target.isInvisible()) {
                    flyingEntity.doOnce = false;
                    if (flyingEntity.velX > -4 && flyingEntity.position().x > target.position().x + target.getBbWidth()) {
                        flyingEntity.velX -= 0.08;
                        if (flyingEntity.velX > 4) {
                            flyingEntity.velX -= 0.04;
                        }
                        else if (flyingEntity.velX > 0) {
                            flyingEntity.velX -= 0.2;
                        }
                        if (flyingEntity.velX < -4) {
                            flyingEntity.velX = -4;
                        }
                    } else if (flyingEntity.velX < 4 && flyingEntity.position().x + 1 < target.position().x) {
                        flyingEntity.velX += 0.08;
                        if (flyingEntity.velX < -4) {
                            flyingEntity.velX += 0.04;
                        }
                        else if (flyingEntity.velX < 0) {
                            flyingEntity.velX += 0.2;
                        }
                        if (flyingEntity.velX > 4) {
                            flyingEntity.velX = 4;
                        }
                    }

                    if (flyingEntity.velZ > -4 && flyingEntity.position().z > target.position().z + target.getBbWidth()) {
                        flyingEntity.velZ -= 0.08;
                        if (flyingEntity.velZ > 4) {
                            flyingEntity.velZ -= 0.04;
                        }
                        else if (flyingEntity.velZ > 0f) {
                            flyingEntity.velZ -= 0.2;
                        }
                        if (flyingEntity.velZ < -4) {
                            flyingEntity.velZ = -4;
                        }
                    } else if (flyingEntity.velZ < 4f && flyingEntity.position().z + 1 < target.position().z) {
                        flyingEntity.velZ += 0.08f;
                        if (flyingEntity.velZ < -4) {
                            flyingEntity.velZ += 0.04;
                        }
                        else if (flyingEntity.velZ < 0f) {
                            flyingEntity.velZ += 0.2;
                        }
                        if (flyingEntity.velZ > 4) {
                            flyingEntity.velZ = 4;
                        }
                    }

                    if (flyingEntity.velY > -2.5 && flyingEntity.position().y > target.position().y + target.getBbHeight()) {
                        flyingEntity.velY -= 0.1f;
                        if (flyingEntity.velY > 2.5) {
                            flyingEntity.velY -= 0.05;
                        } else if (flyingEntity.velY > 0f) {
                            flyingEntity.velY -= 0.15;
                        }
                        if (flyingEntity.velY < -2.5) {
                            flyingEntity.velY = -2.5;
                        }
                    } else if (flyingEntity.velY < 2.5 && flyingEntity.position().y + 1 < target.position().y) {
                        flyingEntity.velY += 0.1f;
                        if (flyingEntity.velY < -2.5) {
                            flyingEntity.velY += 0.05;
                        }
                        else if (flyingEntity.velY < 0) {
                            flyingEntity.velY += 0.15;
                        }
                        if (flyingEntity.velY > 2.5) {
                            flyingEntity.velY = 2.5;
                        }
                    }
                } else {
                    lookRandomly();
                }

                flyingEntity.bounce = false;
                flyingEntity.oldVelX = flyingEntity.velX;
                flyingEntity.oldVelY = flyingEntity.velY;
                flyingEntity.oldVelZ = flyingEntity.velZ;
                motionX = flyingEntity.velX * 0.075f * flyingEntity.getAttribute(Attributes.MOVEMENT_SPEED).getValue();
                motionY = flyingEntity.velY * 0.075f * flyingEntity.getAttribute(Attributes.MOVEMENT_SPEED).getValue();
                motionZ = flyingEntity.velZ * 0.075f * flyingEntity.getAttribute(Attributes.MOVEMENT_SPEED).getValue();

                flyingEntity.setYRot(rotlerp(flyingEntity.getYRot(), (float)Math.toDegrees(Math.atan2(flyingEntity.velZ, flyingEntity.velX)) - 90, 360));
                flyingEntity.setXRot((float)(-(Mth.atan2(-flyingEntity.velY, Math.sqrt(flyingEntity.velX * flyingEntity.velX + flyingEntity.velZ * flyingEntity.velZ)) * 180.0F / (float)Math.PI)));

                flyingEntity.setDeltaMovement(motionX, motionY, motionZ);
            } else {
                flyingEntity.setDeltaMovement(0, -0.5f, 0);
            }
        }

        public void lookRandomly() {
            if (!flyingEntity.doOnce) {
                flyingEntity.velX = flyingEntity.random.nextInt(-2, 3);
                flyingEntity.velZ = flyingEntity.random.nextInt(-2, 3);
                flyingEntity.velY = flyingEntity.random.nextInt(-1, 2);
                flyingEntity.doOnce = true;
            }
        }
    }

    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void playerTouch(@NotNull Player player) {
        super.playerTouch(player);

        if (this.isAlive()) {
            player.hurt(damageSources().mobAttack(this), (float) this.getAttribute(Attributes.ATTACK_DAMAGE).getValue());
        }
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (source.getEntity() instanceof Player) {
            bounce = true;
        }
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
    public ItemStack getItemBySlot(@NotNull EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlotAndDropWhenKilled(@NotNull EquipmentSlot slot, @NotNull ItemStack stack) {
    }

    static class FlyingEntityRotationControl extends BodyRotationControl {
        private final FlyingEntityAI flyingEntity;

        public FlyingEntityRotationControl(FlyingEntityAI flyingEntity) {
            super(flyingEntity);
            this.flyingEntity = flyingEntity;
        }

        public void clientTick() {
            flyingEntity.yHeadRot = flyingEntity.yBodyRot;
            flyingEntity.yBodyRot = flyingEntity.getYRot();
        }
    }
}
