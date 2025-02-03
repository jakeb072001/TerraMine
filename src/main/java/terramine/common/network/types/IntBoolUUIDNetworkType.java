package terramine.common.network.types;

import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public record IntBoolUUIDNetworkType(int integer1, int integer2, boolean bool, UUID uuid, Type<? extends CustomPacketPayload> type) implements CustomPacketPayload {
    private static final Map<ResourceLocation, Type<IntBoolUUIDNetworkType>> TYPE_MAP = new HashMap<>();

    public static StreamCodec<RegistryFriendlyByteBuf, IntBoolUUIDNetworkType> createCodec(ResourceLocation type) {
        return StreamCodec.composite(
                ByteBufCodecs.INT, IntBoolUUIDNetworkType::integer1,
                ByteBufCodecs.INT, IntBoolUUIDNetworkType::integer2,
                ByteBufCodecs.BOOL, IntBoolUUIDNetworkType::bool,
                UUIDUtil.STREAM_CODEC, IntBoolUUIDNetworkType::uuid,
                (int1, int2, bool, uuid) -> new IntBoolUUIDNetworkType(int1, int2, bool, uuid, IntBoolUUIDNetworkType.registerType(type))
        );
    }

    public static Type<IntBoolUUIDNetworkType> registerType(ResourceLocation id) {
        return TYPE_MAP.computeIfAbsent(id, Type::new);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return type;
    }
}