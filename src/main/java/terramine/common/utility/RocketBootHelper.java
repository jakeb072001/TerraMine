package terramine.common.utility;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import terramine.common.init.ModComponents;
import terramine.common.init.ModItems;
import terramine.common.trinkets.TrinketsHelper;

import java.util.Random;

public class RocketBootHelper {
    private static final Random RANDOM = new Random();
    private SimpleParticleType particle1;
    private SimpleParticleType particle2;
    private SoundEvent sound;
    private float soundVolume;
    private float soundPitch;
    private int timer;
    private int soundTimer;
    private double speedSide = 0.03D;
    private double sprintSpeed = 1.0D;
    private int rocketTime = 40;

    public void rocketFly(double speed, int priority, LivingEntity player) {
        if (player instanceof Player user) {
            if (!user.isCreative() && !user.isInWater()) {
                if (CloudBottleEquippedCheck.isEquipped(user)) {
                    if (ModComponents.MOVEMENT_ORDER.get(user).getCloudFinished()) {
                        realFly(speed, priority, user);
                    }
                } else {
                    realFly(speed, priority, user);
                }
            }
        }
    }

    private void realFly(double speed, int priority, Player player) {
        Options settings = Minecraft.getInstance().options;
        if (player.isOnGround())
        {
            if (timer > 0)
            {
                timer = 0;
                soundTimer = 0;
            }
        }
        if (settings != null && timer < rocketTime && priorityOrder(player, priority)) {
            if (settings.keyJump.isDown()) {
                double currentAccel = speed * (player.getDeltaMovement().y() < 0.3D ? 2.5D : 1.0D);
                double currentSpeedVertical = speed * (player.isUnderWater() ? 0.4D : 1.0D);
                float random = (RANDOM.nextFloat() - 0.5F) * 0.1F;
                timer += 1;
                soundTimer += 1;

                double motionY = player.getDeltaMovement().y();
                if (settings.keyJump.isDown()) {
                    this.fly(player, Math.min(motionY + currentAccel, currentSpeedVertical));
                    if (soundTimer >= 3) {
                        player.level.playSound(null, player.blockPosition(), sound, SoundSource.PLAYERS, soundVolume, soundPitch);
                        soundTimer = 0;
                    }
                    Vec3 vLeft = new Vec3(-0.15, -1.5, 0).xRot(0).yRot(player.yBodyRot * -0.017453292F);
                    Vec3 vRight = new Vec3(0.15, -1.5, 0).xRot(0).yRot(player.yBodyRot * -0.017453292F);
                    Vec3 playerPos = player.getPosition(0).add(0, 1.5, 0);
                    if (!player.isLocalPlayer()) {
                        Vec3 v = playerPos.add(vLeft);
                        ((ServerPlayer) player).getLevel().sendParticles(particle1, v.x, v.y, v.z, 1, 0, -0.2D, 0, random);
                        if (particle2 != null) {
                            ((ServerPlayer) player).getLevel().sendParticles(particle2, v.x, v.y, v.z, 1, 0, -0.2D, 0, random);
                        }
                        v = playerPos.add(vRight);
                        ((ServerPlayer) player).getLevel().sendParticles(particle1, v.x, v.y, v.z, 1, 0, -0.2D, 0, random);
                        if (particle2 != null) {
                            ((ServerPlayer) player).getLevel().sendParticles(particle2, v.x, v.y, v.z, 1, 0, -0.2D, 0, random);
                        }
                    }
                }

                float speedSideways = (float) (player.isCrouching() ? this.speedSide * 0.5F : this.speedSide);
                float speedForward = player.isSprinting() ? (float) (speedSideways * this.sprintSpeed) : speedSideways;

                if (settings.keyUp.isDown()) {
                    player.moveRelative(1, new Vec3(0, 0, speedForward));
                }
                if (settings.keyDown.isDown()) {
                    player.moveRelative(1, new Vec3(0, 0, -speedSideways * 0.8F));
                }
                if (settings.keyLeft.isDown()) {
                    player.moveRelative(1, new Vec3(speedSideways, 0, 0));
                }
                if (settings.keyRight.isDown()) {
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
        if (motion.y() >= 0)
        {
            player.moveRelative(1, new Vec3(0, y, 0));
        } else {
            player.moveRelative(1, new Vec3(0, -y + 1, 0));
        }
        //player.moveRelative(1, new Vec3(motion.x(), y, motion.z()));
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

        return priority >= priorityOrder;
    }
}
