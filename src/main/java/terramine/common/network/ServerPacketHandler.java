package terramine.common.network;

import com.google.common.collect.Lists;
import dev.architectury.networking.NetworkManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.Vec3;
import terramine.TerraMine;
import terramine.client.render.gui.menu.TerrariaInventoryContainerMenu;
import terramine.common.init.ModComponents;
import terramine.common.init.ModItems;
import terramine.common.network.packet.BoneMealPacket;
import terramine.common.network.packet.UpdateInputPacket;
import terramine.extensions.PlayerStorages;

import java.util.ArrayList;
import java.util.UUID;

public class ServerPacketHandler {
    // Server
    public static final ResourceLocation BONE_MEAL_PACKET_ID = TerraMine.id("bone_meal");
    public static final ResourceLocation FALL_DISTANCE_PACKET_ID = TerraMine.id("fall_distance");
    public static final ResourceLocation WALL_JUMP_PACKET_ID = TerraMine.id("wall_jump");
    public static final ResourceLocation DASH_PACKET_ID = TerraMine.id("dash");
    public static final ResourceLocation CONTROLS_PACKET_ID = TerraMine.id("controls_packet");
    public static final ResourceLocation PLAYER_MOVEMENT_PACKET_ID = TerraMine.id("player_movement");
    public static final ResourceLocation ROCKET_BOOTS_SOUND_PACKET_ID = TerraMine.id("rocket_boots_sound");
    public static final ResourceLocation ROCKET_BOOTS_PARTICLE_PACKET_ID = TerraMine.id("rocket_boots_particles");
    public static final ResourceLocation UPDATE_BIOME_PACKET_ID = TerraMine.id("update_biome");
    public static final ResourceLocation OPEN_INVENTORY_PACKET_ID = TerraMine.id("open_inventory");
    public static final ResourceLocation UPDATE_ACCESSORY_VISIBILITY_PACKET_ID = TerraMine.id("update_accessory_visibility");

    // Client
    public static final ResourceLocation SETUP_INVENTORY_PACKET_ID = TerraMine.id("setup_inventory");
    public static final ResourceLocation UPDATE_INVENTORY_PACKET_ID = TerraMine.id("update_inventory");

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(BONE_MEAL_PACKET_ID, BoneMealPacket::receive);

        ServerPlayNetworking.registerGlobalReceiver(CONTROLS_PACKET_ID, (server, player, handler, buf, responseSender) ->
                UpdateInputPacket.onMessage(UpdateInputPacket.read(buf), server, player));

        ServerPlayNetworking.registerGlobalReceiver(DASH_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            boolean isGear = buf.readBoolean();
            int cooldown = 10;

            server.execute(() -> {
                for (int i = 0; i < 20; ++i) {
                    double d0 = player.level().random.nextGaussian() * 0.02D;
                    double d1 = player.level().random.nextGaussian() * 0.02D;
                    double d2 = player.level().random.nextGaussian() * 0.02D;
                    float random = (player.getRandom().nextFloat() - 0.5F) * 0.1F;
                    player.serverLevel().sendParticles(ParticleTypes.POOF, player.getX() + (double) (player.level().random.nextFloat() * player.getBbWidth() * 2.0F) - (double) player.getBbWidth() - d0 * 10.0D, player.getY() + (double) (player.level().random.nextFloat() * player.getBbHeight()) - d1 * 10.0D, player.getZ() + (double) (player.level().random.nextFloat() * player.getBbWidth() * 2.0F) - (double) player.getBbWidth() - d2 * 10.0D, 1, 0, -0.2D, 0, random);
                }

                player.level().playSound(null, player.blockPosition(), SoundEvents.PHANTOM_FLAP, SoundSource.PLAYERS, 1.0F, 2.0F);
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
            SoundEvent sound = buf.readById(BuiltInRegistries.SOUND_EVENT);
            float soundVolume = buf.readFloat();
            float soundPitch = buf.readFloat();
            server.execute(() -> {
                if (sound != null) {
                    player.level().playSound(null, player.blockPosition(), sound, SoundSource.PLAYERS, soundVolume, soundPitch);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(ROCKET_BOOTS_PARTICLE_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            SimpleParticleType particle1 = (SimpleParticleType) buf.readById(BuiltInRegistries.PARTICLE_TYPE);
            SimpleParticleType particle2 = (SimpleParticleType) buf.readById(BuiltInRegistries.PARTICLE_TYPE);
            Vec3 vLeft = new Vec3(-0.15, -1.5, 0).xRot(0).yRot(player.yBodyRot * -0.017453292F);
            Vec3 vRight = new Vec3(0.15, -1.5, 0).xRot(0).yRot(player.yBodyRot * -0.017453292F);
            Vec3 playerPos = player.getPosition(0).add(0, 1.5, 0);
            float random = (player.getRandom().nextFloat() - 0.5F) * 0.1F;

            server.execute(() -> {
                Vec3 v = playerPos.add(vLeft);
                if (particle1 != null && particle1 != ParticleTypes.DRIPPING_WATER) {
                    player.serverLevel().sendParticles(particle1, v.x, v.y, v.z, 1, 0, -0.2D, 0, random);
                }
                if (particle2 != null && particle2 != ParticleTypes.DRIPPING_WATER) {
                    player.serverLevel().sendParticles(particle2, v.x, v.y, v.z, 1, 0, -0.2D, 0, random);
                }
                v = playerPos.add(vRight);
                if (particle1 != null && particle1 != ParticleTypes.DRIPPING_WATER) {
                    player.serverLevel().sendParticles(particle1, v.x, v.y, v.z, 1, 0, -0.2D, 0, random);
                }
                if (particle2 != null && particle2 != ParticleTypes.DRIPPING_WATER) {
                    player.serverLevel().sendParticles(particle2, v.x, v.y, v.z, 1, 0, -0.2D, 0, random);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(OPEN_INVENTORY_PACKET_ID, (server, player, handler, buf, responseSender) ->
                server.execute(() -> player.openMenu(new SimpleMenuProvider((id, inventory, player2) -> new TerrariaInventoryContainerMenu(player), Component.empty()))));

        ServerPlayNetworking.registerGlobalReceiver(UPDATE_ACCESSORY_VISIBILITY_PACKET_ID, (server, player, handler, buf, responseSender) -> {
                int slot = buf.readInt();
                boolean isVisible = buf.readBoolean();
                server.execute(() -> {
                    ModComponents.ACCESSORY_VISIBILITY.get(player).setSlotVisibility(slot, isVisible);
                    ModComponents.ACCESSORY_VISIBILITY.sync(player);
                });
        });
    }

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        ClientPlayNetworking.registerGlobalReceiver(SETUP_INVENTORY_PACKET_ID, (client, handler, buffer, responseSender) -> {
            UUID uuid = buffer.readUUID();
            ArrayList<ItemStack> items = Lists.newArrayList();
            for (int i = 0; i < 35; i++) {
                items.add(buffer.readItem());
            }
            client.execute(() -> {
                for (int i = 0; i < items.size(); i++) {
                    ((PlayerStorages) client.level.getPlayerByUUID(uuid)).getTerrariaInventory().setItem(i, items.get(i));
                }
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(UPDATE_INVENTORY_PACKET_ID, (client, handler, buffer, responseSender) -> {
            int slot = buffer.readInt();
            ItemStack itemStack = buffer.readItem();
            UUID uuid = buffer.readUUID();
            client.execute(() -> {
                ((PlayerStorages) client.level.getPlayerByUUID(uuid)).getTerrariaInventory().setItem(slot, itemStack);
            });
        });

        NetworkManager.registerReceiver(NetworkManager.s2c(), UPDATE_BIOME_PACKET_ID, (buf, context) -> {
            int chunkX = buf.readInt();
            int chunkZ = buf.readInt();
            if (context.getPlayer() != null) {
                ((ClientLevel) context.getPlayer().level()).onChunkLoaded(new ChunkPos(chunkX, chunkZ));
            }
        });
    }
}
