package terramine.common.utility;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import terramine.common.init.ModComponents;
import terramine.common.init.ModItems;
import terramine.common.trinkets.TrinketsHelper;
import terramine.common.utility.equipmentchecks.CloudBottleEquippedCheck;

public class RocketBootHelper {
    private static final RandomSource RANDOM = RandomSource.create();
    private SimpleParticleType particle1;
    private SimpleParticleType particle2;
    private SoundEvent sound;
    private float soundVolume;
    private float soundPitch;
    private int timer;
    private int soundTimer;
    private int rocketTime = 40;

    public void rocketFly(double speed, int priority, LivingEntity player) {
        if (player instanceof Player user) {
            if (!user.isCreative() && !player.isSpectator() && !user.isInWater()) {
                if (CloudBottleEquippedCheck.isEquipped(user)) {
                    if (ModComponents.MOVEMENT_ORDER.get(user).getCloudFinished()) {
                        realFly(speed, 0.05D, priority, false, user);
                    }
                } else {
                    realFly(speed, 0.05D, priority, false, user);
                }
            }
        }
    }

    public void wingFly(double speed, double glideSpeed, int priority, int flyTime, LivingEntity player) {
        this.rocketTime = flyTime;
        if (player instanceof Player user) {
            if (!user.isCreative() && !user.isSpectator() && !user.isInWater()) {
                realFly(speed, glideSpeed, priority, true, user);
            }
        }
    }

    private void realFly(double speed, double glideSpeed, int priority, boolean wings, Player player) { // todo: move to a packet so it only runs on server
        if (player.isOnGround() || ModComponents.MOVEMENT_ORDER.get(player).getWallJumped())
        {
            if (timer > 0)
            {
                timer = 0;
                soundTimer = 0;
            }
            if (wings) {
                ModComponents.MOVEMENT_ORDER.get(player).setWingsFinished(false);
            }
        }

        if (player.level.isClientSide()) {
            if (wings && timer >= rocketTime && !ModComponents.MOVEMENT_ORDER.get(player).getWallJumped()) {
                ModComponents.MOVEMENT_ORDER.get(player).setWingsFinished(true);
                if (InputHandler.isHoldingJump(player)) { // todo: add a small delay to help smooth movement slightly and allow double jump to work with glide
                    double currentAccel = speed * (player.getDeltaMovement().y() < 0.3D ? 2.5D : 1.0D);
                    double motionY = player.getDeltaMovement().y();
                    double glideFallSpeed = InputHandler.isHoldingShift(player) ? -0.28D : -0.14D;
                    this.fly(player, Math.min(motionY + currentAccel, glideFallSpeed));
                    player.resetFallDistance();

                    float speedSideways = (float) (player.isCrouching() ? glideSpeed * 0.5F : glideSpeed);
                    if (InputHandler.isHoldingForwards(player)) {
                        player.moveRelative(1, new Vec3(0, 0, speedSideways));
                    }
                    if (InputHandler.isHoldingBackwards(player)) {
                        player.moveRelative(1, new Vec3(0, 0, -speedSideways * 0.8F));
                    }
                    if (InputHandler.isHoldingLeft(player)) {
                        player.moveRelative(1, new Vec3(speedSideways, 0, 0));
                    }
                    if (InputHandler.isHoldingRight(player)) {
                        player.moveRelative(1, new Vec3(-speedSideways, 0, 0));
                    }
                }
            }
        }

        if (timer < rocketTime && priorityOrder(player, priority) && !ModComponents.MOVEMENT_ORDER.get(player).getWallJumped()) {
            if (InputHandler.isHoldingJump(player)) {
                double currentAccel = speed * (player.getDeltaMovement().y() < 0.3D ? 2.5D : 1.0D);
                double currentSpeedVertical = speed * (player.isUnderWater() ? 0.4D : 1.0D);
                float random = (RANDOM.nextFloat() - 0.5F) * 0.1F;
                timer += 1;
                soundTimer += 1;

                double motionY = player.getDeltaMovement().y();
                if (InputHandler.isHoldingJump(player)) {
                    this.fly(player, Math.abs(Math.min(motionY + currentAccel, currentSpeedVertical)));
                    //if ((wings && soundTimer >= 6) || (!wings && soundTimer >= 3)) { // todo: doesn't work right now, should do when ran from packet
                    //    player.level.playSound(null, player.blockPosition(), sound, SoundSource.PLAYERS, soundVolume, soundPitch);
                    //    soundTimer = 0;
                    //}
                    if (soundTimer >= 3) { // remove after above code is working
                        player.level.playSound(null, player.blockPosition(), sound, SoundSource.PLAYERS, soundVolume, soundPitch);
                        soundTimer = 0;
                    }
                    Vec3 vLeft = new Vec3(-0.15, -1.5, 0).xRot(0).yRot(player.yBodyRot * -0.017453292F);
                    Vec3 vRight = new Vec3(0.15, -1.5, 0).xRot(0).yRot(player.yBodyRot * -0.017453292F);
                    Vec3 playerPos = player.getPosition(0).add(0, 1.5, 0);
                    if (!player.isLocalPlayer()) {
                        Vec3 v = playerPos.add(vLeft);
                        if (particle1 != null) {
                        ((ServerPlayer) player).getLevel().sendParticles(particle1, v.x, v.y, v.z, 1, 0, -0.2D, 0, random);
                        }
                        if (particle2 != null) {
                            ((ServerPlayer) player).getLevel().sendParticles(particle2, v.x, v.y, v.z, 1, 0, -0.2D, 0, random);
                        }
                        v = playerPos.add(vRight);
                        if (particle1 != null) {
                            ((ServerPlayer) player).getLevel().sendParticles(particle1, v.x, v.y, v.z, 1, 0, -0.2D, 0, random);
                        }
                        if (particle2 != null) {
                            ((ServerPlayer) player).getLevel().sendParticles(particle2, v.x, v.y, v.z, 1, 0, -0.2D, 0, random);
                        }
                    }
                }

                float speedSideways = (float) (player.isCrouching() ? glideSpeed * 0.5F : glideSpeed);
                if (InputHandler.isHoldingForwards(player)) {
                    player.moveRelative(1, new Vec3(0, 0, speedSideways));
                }
                if (InputHandler.isHoldingBackwards(player)) {
                    player.moveRelative(1, new Vec3(0, 0, -speedSideways * 0.8F));
                }
                if (InputHandler.isHoldingLeft(player)) {
                    player.moveRelative(1, new Vec3(speedSideways, 0, 0));
                }
                if (InputHandler.isHoldingRight(player)) {
                    player.moveRelative(1, new Vec3(-speedSideways, 0, 0));
                }
                if (!player.level.isClientSide()) {
                    player.fallDistance = 0.0F;
                }
            }
        }
    }

    public void setSoundSettings(SoundEvent sound, float soundVolume, float soundPitch) {
        this.sound = sound;
        this.soundVolume = soundVolume;
        this.soundPitch = soundPitch;
    }

    public void setParticleSettings(SimpleParticleType particle1) {
        this.particle1 = particle1;
    }

    public void setParticleSettings(SimpleParticleType particle1, SimpleParticleType particle2) {
        this.particle1 = particle1;
        this.particle2 = particle2;
    }

    private void fly(LivingEntity player, double y) {
        Vec3 motion = player.getDeltaMovement();
        player.setDeltaMovement(motion.x(), y, motion.z());
    }

    private boolean priorityOrder(Player player, int priority) {
        int priorityOrder = 0;
        if (TrinketsHelper.isEquipped(ModItems.ROCKET_BOOTS, player)) {
            priorityOrder = 1;
        }
        if (TrinketsHelper.isEquipped(ModItems.SPECTRE_BOOTS, player)) {
            priorityOrder = 2;
        }
        if (TrinketsHelper.isEquipped(ModItems.FAIRY_BOOTS, player)) {
            priorityOrder = 3;
        }
        if (TrinketsHelper.isEquipped(ModItems.LIGHTNING_BOOTS, player)) {
            priorityOrder = 4;
        }
        if (TrinketsHelper.isEquipped(ModItems.FROSTSPARK_BOOTS, player)) {
            priorityOrder = 5;
        }
        if (TrinketsHelper.isEquipped(ModItems.TERRASPARK_BOOTS, player)) {
            priorityOrder = 6;
        }
        if (TrinketsHelper.isEquipped(ModItems.FLEDGLING_WINGS, player)) {
            priorityOrder = 7;
        }

        return priority >= priorityOrder;
    }
}
