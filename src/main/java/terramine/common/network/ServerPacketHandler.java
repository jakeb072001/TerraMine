package terramine.common.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.Vec3;
import terramine.TerraMine;
import terramine.client.render.gui.menu.TerrariaInventoryContainerMenu;
import terramine.common.init.ModComponents;
import terramine.common.misc.TeamColours;
import terramine.common.network.packet.BoneMealPacket;
import terramine.common.network.packet.UpdateInputPacket;
import terramine.common.network.types.*;
import terramine.extensions.PlayerStorages;

import java.lang.reflect.Method;
import java.util.*;

// todo: terraria inventory not syncing on player join or changing dimension (player join on dedicated server works but not change dimension, seems it's happening too early)
public class ServerPacketHandler {
    private static final Map<UUID, List<Runnable>> pendingUpdates = new HashMap<>();

    // Server
    public static final CustomPacketPayload.Type<LongNetworkType> BONE_MEAL_PACKET_ID = registerType("bone_meal", false, LongNetworkType.class);
    public static final CustomPacketPayload.Type<FloatSoundNetworkType> FALL_DISTANCE_PACKET_ID = registerType("fall_distance", false, FloatSoundNetworkType.class);
    public static final CustomPacketPayload.Type<IntBoolUUIDNetworkType> WALL_JUMP_PACKET_ID = registerType("wall_jump", false, IntBoolUUIDNetworkType.class);
    public static final CustomPacketPayload.Type<ItemNetworkType> DASH_PACKET_ID = registerType("dash", false, ItemNetworkType.class);
    public static final CustomPacketPayload.Type<InputNetworkType> CONTROLS_PACKET_ID = registerType("controls_packet", false, InputNetworkType.class);
    public static final CustomPacketPayload.Type<DoubleNetworkType> PLAYER_MOVEMENT_PACKET_ID = registerType("player_movement", false, DoubleNetworkType.class);
    public static final CustomPacketPayload.Type<FloatSoundNetworkType> ROCKET_BOOTS_SOUND_PACKET_ID = registerType("rocket_boots_sound", false, FloatSoundNetworkType.class);
    public static final CustomPacketPayload.Type<ParticleNetworkType> ROCKET_BOOTS_PARTICLE_PACKET_ID = registerType("rocket_boots_particles", false, ParticleNetworkType.class);
    public static final CustomPacketPayload.Type<LongNetworkType> OPEN_INVENTORY_PACKET_ID = registerType("open_inventory", false, LongNetworkType.class);
    public static final CustomPacketPayload.Type<IntBoolUUIDNetworkType> UPDATE_TEAM_PACKET_ID = registerType("update_team", false, IntBoolUUIDNetworkType.class);
    public static final CustomPacketPayload.Type<LongNetworkType> C2S_DOUBLE_JUMPED_ID = registerType("c2s_double_jumped", false, LongNetworkType.class);
    public static final CustomPacketPayload.Type<LongNetworkType> C2S_QUADRUPLE_JUMPED_ID = registerType("c2s_quadruple_jumped", false, LongNetworkType.class);

    // Client
    public static final CustomPacketPayload.Type<ItemNetworkType> UPDATE_INVENTORY_PACKET_ID = registerType("update_inventory", true, ItemNetworkType.class);
    public static final CustomPacketPayload.Type<IntBoolUUIDNetworkType> UPDATE_BIOME_PACKET_ID = registerType("update_biome", true, IntBoolUUIDNetworkType.class);

    // Both
    public static final CustomPacketPayload.Type<IntBoolUUIDNetworkType> UPDATE_ACCESSORY_VISIBILITY_PACKET_ID = registerType("update_accessory_visibility", IntBoolUUIDNetworkType.class);

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(BONE_MEAL_PACKET_ID, BoneMealPacket::receive);

        ServerPlayNetworking.registerGlobalReceiver(CONTROLS_PACKET_ID, (buf, context) ->
                UpdateInputPacket.onMessage(UpdateInputPacket.read(buf.booleanList()), context.player().server, context.player()));

        ServerPlayNetworking.registerGlobalReceiver(DASH_PACKET_ID, (buf, context) -> {
            ItemStack gear = buf.itemStack();
            int cooldown = 10;

            context.player().server.execute(() -> {
                ServerPlayer player = context.player();
                for (int i = 0; i < 20; ++i) {
                    double d0 = player.level().random.nextGaussian() * 0.02D;
                    double d1 = player.level().random.nextGaussian() * 0.02D;
                    double d2 = player.level().random.nextGaussian() * 0.02D;
                    float random = (player.getRandom().nextFloat() - 0.5F) * 0.1F;
                    player.serverLevel().sendParticles(ParticleTypes.POOF, player.getX() + (double) (player.level().random.nextFloat() * player.getBbWidth() * 2.0F) - (double) player.getBbWidth() - d0 * 10.0D, player.getY() + (double) (player.level().random.nextFloat() * player.getBbHeight()) - d1 * 10.0D, player.getZ() + (double) (player.level().random.nextFloat() * player.getBbWidth() * 2.0F) - (double) player.getBbWidth() - d2 * 10.0D, 1, 0, -0.2D, 0, random);
                }

                player.level().playSound(null, player.blockPosition(), SoundEvents.PHANTOM_FLAP, SoundSource.PLAYERS, 1.0F, 2.0F);
                player.getCooldowns().addCooldown(gear.getItem(), cooldown);
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(FALL_DISTANCE_PACKET_ID, (buf, context) -> {
            float fallDistance = buf.float1();
            context.player().server.execute(() -> {
                context.player().fallDistance = fallDistance;
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(WALL_JUMP_PACKET_ID, (buf, context) -> {
            boolean wallJumped = buf.bool();
            context.player().server.execute(() -> {
                ModComponents.MOVEMENT_ORDER.get(context.player()).setWallJumped(wallJumped);
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(PLAYER_MOVEMENT_PACKET_ID, (buf, context) -> {
            double x = buf.double1();
            double y = buf.double2();
            double z = buf.double3();
            context.player().server.execute(() -> {
                context.player().setDeltaMovement(x, y, z);
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(ROCKET_BOOTS_SOUND_PACKET_ID, (buf, context) -> {
            SoundEvent sound = buf.soundEvent();
            float soundVolume = buf.float1();
            float soundPitch = buf.float2();
            context.player().server.execute(() -> {
                if (sound != null) {
                    context.player().level().playSound(null, context.player().blockPosition(), sound, SoundSource.PLAYERS, soundVolume, soundPitch);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(ROCKET_BOOTS_PARTICLE_PACKET_ID, (buf, context) -> {
            ServerPlayer player = context.player();
            SimpleParticleType particle1 = (SimpleParticleType) buf.particleOptions().getType();
            //SimpleParticleType particle2 = (SimpleParticleType) buf.getParticleOption().getType();
            Vec3 vLeft = new Vec3(-0.15, -1.5, 0).xRot(0).yRot(player.yBodyRot * -0.017453292F);
            Vec3 vRight = new Vec3(0.15, -1.5, 0).xRot(0).yRot(player.yBodyRot * -0.017453292F);
            Vec3 playerPos = player.getPosition(0).add(0, 1.5, 0);
            float random = (player.getRandom().nextFloat() - 0.5F) * 0.1F;

            context.player().server.execute(() -> {
                Vec3 v = playerPos.add(vLeft);
                player.serverLevel().sendParticles(particle1, v.x, v.y, v.z, 1, 0, -0.2D, 0, random);
                //player.serverLevel().sendParticles(particle2, v.x, v.y, v.z, 1, 0, -0.2D, 0, random);
                v = playerPos.add(vRight);
                player.serverLevel().sendParticles(particle1, v.x, v.y, v.z, 1, 0, -0.2D, 0, random);
                //player.serverLevel().sendParticles(particle2, v.x, v.y, v.z, 1, 0, -0.2D, 0, random);
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(OPEN_INVENTORY_PACKET_ID, (buf, context) ->
                context.player().server.execute(() -> context.player().openMenu(new SimpleMenuProvider((id, inventory, player2) -> new TerrariaInventoryContainerMenu(context.player()), Component.empty()))));

        // maybe does nothing? I don't remember
        ServerPlayNetworking.registerGlobalReceiver(UPDATE_ACCESSORY_VISIBILITY_PACKET_ID, (buf, context) -> {
            int slot = buf.integer1();
            boolean isVisible = buf.bool();
            context.player().server.execute(() -> ((PlayerStorages) context.player()).setSlotVisibility(slot, isVisible));
        });

        ServerPlayNetworking.registerGlobalReceiver(UPDATE_TEAM_PACKET_ID, (buf, context) -> {
            int slot = buf.integer1();
            context.player().server.execute(() -> {
                ModComponents.TEAMS.get(context.player()).setTeamColour(TeamColours.getTeam(slot));
                ModComponents.TEAMS.sync(context.player());
            });
        });
    }

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        ClientPlayNetworking.registerGlobalReceiver(UPDATE_INVENTORY_PACKET_ID, (buf, context) -> {
            int slot = buf.integer();
            ItemStack itemStack = buf.itemStack();
            UUID uuid = buf.uuid();

            context.client().execute(() -> {
                Player player = context.client().level.getPlayerByUUID(uuid);

                if (player != null) {
                    ((PlayerStorages) player).getTerrariaInventory().setItem(slot, itemStack);
                } else {
                    pendingUpdates.computeIfAbsent(uuid, k -> new ArrayList<>()).add(() ->
                            ((PlayerStorages) Objects.requireNonNull(context.client().level.getPlayerByUUID(uuid))).getTerrariaInventory().setItem(slot, itemStack)
                    );
                }
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(UPDATE_ACCESSORY_VISIBILITY_PACKET_ID, (buf, context) -> {
            int slot = buf.integer1();
            boolean isVisible = buf.bool();
            UUID uuid = buf.uuid();

            context.client().execute(() -> {
                Player player = context.client().level.getPlayerByUUID(uuid);

                if (player != null) {
                    ((PlayerStorages) player).setSlotVisibility(slot, isVisible);
                } else {
                    pendingUpdates.computeIfAbsent(uuid, k -> new ArrayList<>()).add(() ->
                            ((PlayerStorages) Objects.requireNonNull(context.client().level.getPlayerByUUID(uuid))).setSlotVisibility(slot, isVisible)
                    );
                }
            });
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (pendingUpdates.isEmpty() || client.level == null) return;

            Iterator<Map.Entry<UUID, List<Runnable>>> iterator = pendingUpdates.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<UUID, List<Runnable>> entry = iterator.next();
                UUID uuid = entry.getKey();
                Player player = client.level.getPlayerByUUID(uuid);

                if (player != null) {
                    for (Runnable update : entry.getValue()) {
                        update.run();
                    }
                    iterator.remove();
                }
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(UPDATE_BIOME_PACKET_ID, (buf, context) -> {
            int chunkX = buf.integer1();
            int chunkZ = buf.integer2();
            if (context.player() != null) {
                ((ClientLevel) context.player().level()).onChunkLoaded(new ChunkPos(chunkX, chunkZ));
            }
        });
    }

    public static <T extends CustomPacketPayload> CustomPacketPayload.Type<T> registerType(String name, boolean S2C, Class<T> clazz) {
        return registerTypeInternal(name, S2C, !S2C, clazz);
    }

    public static <T extends CustomPacketPayload> CustomPacketPayload.Type<T> registerType(String name, Class<T> clazz) {
        return registerTypeInternal(name, true, true, clazz);
    }

    public static <T extends CustomPacketPayload> CustomPacketPayload.Type<T> registerTypeInternal(String name, boolean S2C, boolean C2S, Class<T> clazz) {
        ResourceLocation id = TerraMine.id(name);
        CustomPacketPayload.Type<T> type = new CustomPacketPayload.Type<>(id);

        try {
            Method method = clazz.getMethod("createCodec", ResourceLocation.class);
            @SuppressWarnings("unchecked")
            StreamCodec<RegistryFriendlyByteBuf, T> codec = (StreamCodec<RegistryFriendlyByteBuf, T>) method.invoke(null, id);

            if (S2C) {
                PayloadTypeRegistry.playS2C().register(type, codec);
            }
            if (C2S) {
                PayloadTypeRegistry.playC2S().register(type, codec);
            }

            return type;
        } catch (Exception e) {
            throw new RuntimeException("Failed to register packet type for " + clazz.getSimpleName(), e);
        }
    }
}