package terramine.common.utility;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import terramine.common.init.ModComponents;
import terramine.common.init.ModItems;
import terramine.common.network.ServerPacketHandler;
import terramine.common.trinkets.TrinketsHelper;
import terramine.common.utility.equipmentchecks.CloudBottleEquippedCheck;

public class RocketBootHelper {
    private SimpleParticleType particle1;
    private SimpleParticleType particle2;
    private SoundEvent sound;
    private boolean stoppedHolding = false;
    private float soundVolume, soundPitch;
    private int timer, soundTimer, glideDelay, glideCloudDelay;
    private int rocketTime = 40;

    public void rocketFly(double speed, int priority, LivingEntity player) {
        if (player instanceof Player user && user.isLocalPlayer()) {
            if (!user.isInWater()) {
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
        if (player instanceof Player user && user.isLocalPlayer()) {
            if (!user.isInWater()) {
                rocketTime = flyTime;
                realFly(speed, glideSpeed, priority, true, user);
            }
        }
    }

    private void realFly(double speed, double glideSpeed, int priority, boolean wings, Player player) {
        if (player.isOnGround() || ModComponents.MOVEMENT_ORDER.get(player).getWallJumped())
        {
            if (timer > 0)
            {
                timer = 0;
                soundTimer = 0;
                glideDelay = 0;
                glideCloudDelay = 0;
            }
            if (wings) {
                ModComponents.MOVEMENT_ORDER.get(player).setWingsFinished(false);
            }
        }

        if (wings && timer >= rocketTime / 2 && !ModComponents.MOVEMENT_ORDER.get(player).getWallJumped()) {
            ModComponents.MOVEMENT_ORDER.get(player).setWingsFinished(true);
            if (InputHandler.isHoldingJump(player)) {
                boolean getCloudEquipped = CloudBottleEquippedCheck.isEquipped(player);
                boolean trueCloudFinished = false;

                if (getCloudEquipped && ModComponents.MOVEMENT_ORDER.get(player).getCloudFinished()) {
                    if (glideCloudDelay >= 10) {
                        trueCloudFinished = true;
                    } else {
                        glideCloudDelay++;
                    }
                }

                if (((trueCloudFinished || !getCloudEquipped) && glideDelay >= 3) || (!trueCloudFinished && glideDelay >= 10)) {
                    double currentAccel = speed * (player.getDeltaMovement().y() < 0.3D ? 2.5D : 1.0D);
                    double motionY = player.getDeltaMovement().y();
                    double glideFallSpeed = InputHandler.isHoldingShift(player) ? -0.28D : -0.14D;
                    fly(player, Math.min(motionY + currentAccel, glideFallSpeed));
                    player.resetFallDistance();

                    float speedSideways = (float) (player.isCrouching() ? glideSpeed * 0.5F : glideSpeed);
                    if (!player.isFallFlying()) {
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
                } else {
                    glideDelay++;
                }
            } else {
                glideDelay = 0;
            }
        }

        if (timer < rocketTime / 2 && priorityOrder(player, priority) && !ModComponents.MOVEMENT_ORDER.get(player).getWallJumped()) {
            if (InputHandler.isHoldingJump(player)) {
                double currentAccel = speed * (player.getDeltaMovement().y() < 0.3D ? 2.5D : 1.0D);
                double currentSpeedVertical = speed * (player.isUnderWater() ? 0.4D : 1.0D);
                stoppedHolding = true;
                timer++;
                soundTimer++;

                double motionY = player.getDeltaMovement().y();
                if (InputHandler.isHoldingJump(player)) {
                    fly(player, Math.abs(Math.min(motionY + currentAccel, currentSpeedVertical)));
                    if ((wings && soundTimer >= 6) || (!wings && soundTimer >= 4)) {
                        FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
                        passedData.writeId(Registry.SOUND_EVENT, sound);
                        passedData.writeFloat(soundVolume);
                        passedData.writeFloat(soundPitch);
                        ClientPlayNetworking.send(ServerPacketHandler.ROCKET_BOOTS_SOUND_PACKET_ID, passedData);
                        soundTimer = 0;
                    }

                    FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
                    if (particle1 != null) {
                        passedData.writeId(Registry.PARTICLE_TYPE, particle1);
                    } else {
                        passedData.writeId(Registry.PARTICLE_TYPE, ParticleTypes.DRIPPING_WATER);
                    }
                    if (particle2 != null) {
                        passedData.writeId(Registry.PARTICLE_TYPE, particle2);
                    } else {
                        passedData.writeId(Registry.PARTICLE_TYPE, ParticleTypes.DRIPPING_WATER);
                    }
                    ClientPlayNetworking.send(ServerPacketHandler.ROCKET_BOOTS_PARTICLE_PACKET_ID, passedData);
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

                player.resetFallDistance();
            } else if (stoppedHolding) {
                timer += 3;
                stoppedHolding = false;
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

    private void fly(Player player, double y) {
        Vec3 motion = player.getDeltaMovement();
        player.setDeltaMovement(motion.x(), y, motion.z());
    }

    private boolean priorityOrder(Player player, int priority) { // todo: change to a better system, don't know how and it's not super important but would be cleaner and easier to manage i would think
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
        if (TrinketsHelper.isEquipped(ModItems.ANGEL_WINGS, player)) {
            priorityOrder = 8;
        }
        if (TrinketsHelper.isEquipped(ModItems.DEMON_WINGS, player)) {
            priorityOrder = 9;
        }
        if (TrinketsHelper.isEquipped(ModItems.LEAF_WINGS, player)) {
            priorityOrder = 10;
        }

        return priority >= priorityOrder;
    }
}
