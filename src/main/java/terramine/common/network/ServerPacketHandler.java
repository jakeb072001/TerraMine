package terramine.common.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import terramine.TerraMine;
import terramine.common.init.ModItems;
import terramine.common.network.packet.BoneMealPacket;
import terramine.common.network.packet.UpdateInputPacket;

public class ServerPacketHandler {
    public static final ResourceLocation BONE_MEAL_PACKET_ID = TerraMine.id("bone_meal");
    public static final ResourceLocation FALL_DISTANCE_PACKET_ID = TerraMine.id("falldistance");
    public static final ResourceLocation DASH_PACKET_ID = TerraMine.id("dash");
    public static final ResourceLocation CONTROLS_PACKET_ID = TerraMine.id("controls_packet");

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
    }
}
