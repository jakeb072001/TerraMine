package terramine.common.network;

import dev.architectury.networking.NetworkManager;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.Vec3;
import terramine.TerraMine;
import terramine.common.init.ModComponents;
import terramine.common.init.ModItems;
import terramine.common.network.packet.BoneMealPacket;
import terramine.common.network.packet.UpdateInputPacket;

public class ServerPacketHandler {
    public static final ResourceLocation BONE_MEAL_PACKET_ID = TerraMine.id("bone_meal");
    public static final ResourceLocation FALL_DISTANCE_PACKET_ID = TerraMine.id("fall_distance");
    public static final ResourceLocation WALL_JUMP_PACKET_ID = TerraMine.id("wall_jump");
    public static final ResourceLocation DASH_PACKET_ID = TerraMine.id("dash");
    public static final ResourceLocation CONTROLS_PACKET_ID = TerraMine.id("controls_packet");
    public static final ResourceLocation PLAYER_MOVEMENT_PACKET_ID = TerraMine.id("player_movement");
    public static final ResourceLocation ROCKET_BOOTS_SOUND_PACKET_ID = TerraMine.id("rocket_boots_sound");
    public static final ResourceLocation ROCKET_BOOTS_PARTICLE_PACKET_ID = TerraMine.id("rocket_boots_particles");
    public static final ResourceLocation UPDATE_BIOME_PACKET_ID = TerraMine.id("update_biome");

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(BONE_MEAL_PACKET_ID, BoneMealPacket::receive);

        ServerPlayNetworking.registerGlobalReceiver(CONTROLS_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            UpdateInputPacket.onMessage(UpdateInputPacket.read(buf), server, player);
        });

        ServerPlayNetworking.registerGlobalReceiver(DASH_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            boolean isGear = buf.readBoolean();
            int cooldown = 10;

            server.execute(() -> {
                for (int i = 0; i < 20; ++i) {
                    double d0 = player.level.random.nextGaussian() * 0.02D;
                    double d1 = player.level.random.nextGaussian() * 0.02D;
                    double d2 = player.level.random.nextGaussian() * 0.02D;
                    float random = (player.getRandom().nextFloat() - 0.5F) * 0.1F;
                    player.getLevel().sendParticles(ParticleTypes.POOF, player.getX() + (double) (player.level.random.nextFloat() * player.getBbWidth() * 2.0F) - (double) player.getBbWidth() - d0 * 10.0D, player.getY() + (double) (player.level.random.nextFloat() * player.getBbHeight()) - d1 * 10.0D, player.getZ() + (double) (player.level.random.nextFloat() * player.getBbWidth() * 2.0F) - (double) player.getBbWidth() - d2 * 10.0D, 1, 0, -0.2D, 0, random);
                }

                player.level.playSound(null, player.blockPosition(), SoundEvents.PHANTOM_FLAP, SoundSource.PLAYERS, 1.0F, 2.0F);
                if (isGear) {
                    player.getCooldowns().addCooldown(ModItems.MASTER_NINJA_GEAR, cooldown);
                } else {
                    player.getCooldowns().addCooldown(ModItems.TABI, cooldown);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(FALL_DISTANCE_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            float fallDistance = buf.readFloat();
            server.execute(() -> {
                player.fallDistance = fallDistance;
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(WALL_JUMP_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            boolean wallJumped = buf.readBoolean();
            server.execute(() -> {
                ModComponents.MOVEMENT_ORDER.get(player).setWallJumped(wallJumped);
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(PLAYER_MOVEMENT_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            double x = buf.readDouble();
            double y = buf.readDouble();
            double z = buf.readDouble();
            server.execute(() -> {
                player.setDeltaMovement(x, y, z);
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(ROCKET_BOOTS_SOUND_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            SoundEvent sound = buf.readById(Registry.SOUND_EVENT);
            float soundVolume = buf.readFloat();
            float soundPitch = buf.readFloat();
            server.execute(() -> {
                if (sound != null) {
                    player.level.playSound(null, player.blockPosition(), sound, SoundSource.PLAYERS, soundVolume, soundPitch);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(ROCKET_BOOTS_PARTICLE_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            SimpleParticleType particle1 = (SimpleParticleType) buf.readById(Registry.PARTICLE_TYPE);
            SimpleParticleType particle2 = (SimpleParticleType) buf.readById(Registry.PARTICLE_TYPE);
            Vec3 vLeft = new Vec3(-0.15, -1.5, 0).xRot(0).yRot(player.yBodyRot * -0.017453292F);
            Vec3 vRight = new Vec3(0.15, -1.5, 0).xRot(0).yRot(player.yBodyRot * -0.017453292F);
            Vec3 playerPos = player.getPosition(0).add(0, 1.5, 0);
            float random = (player.getRandom().nextFloat() - 0.5F) * 0.1F;

            server.execute(() -> {
                Vec3 v = playerPos.add(vLeft);
                if (particle1 != null && particle1 != ParticleTypes.DRIPPING_WATER) {
                    player.getLevel().sendParticles(particle1, v.x, v.y, v.z, 1, 0, -0.2D, 0, random);
                }
                if (particle2 != null && particle2 != ParticleTypes.DRIPPING_WATER) {
                    player.getLevel().sendParticles(particle2, v.x, v.y, v.z, 1, 0, -0.2D, 0, random);
                }
                v = playerPos.add(vRight);
                if (particle1 != null && particle1 != ParticleTypes.DRIPPING_WATER) {
                    player.getLevel().sendParticles(particle1, v.x, v.y, v.z, 1, 0, -0.2D, 0, random);
                }
                if (particle2 != null && particle2 != ParticleTypes.DRIPPING_WATER) {
                    player.getLevel().sendParticles(particle2, v.x, v.y, v.z, 1, 0, -0.2D, 0, random);
                }
            });
        });

        NetworkManager.registerReceiver(NetworkManager.s2c(), UPDATE_BIOME_PACKET_ID, (buf, context) -> {
            int chunkX = buf.readInt();
            int chunkZ = buf.readInt();
            if (context.getPlayer() != null) {
                ((ClientLevel) context.getPlayer().level).onChunkLoaded(new ChunkPos(chunkX, chunkZ));
            }
        });
    }
}
