package terramine.common.network.types;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public record DoubleNetworkType(double double1, double double2, double double3, Type<? extends CustomPacketPayload> type) implements CustomPacketPayload {
    private static final Map<ResourceLocation, Type<DoubleNetworkType>> TYPE_MAP = new HashMap<>();

    public static StreamCodec<RegistryFriendlyByteBuf, DoubleNetworkType> createCodec(ResourceLocation type) {
        return StreamCodec.composite(
                ByteBufCodecs.DOUBLE, DoubleNetworkType::double1,
                ByteBufCodecs.DOUBLE, DoubleNetworkType::double2,
                ByteBufCodecs.DOUBLE, DoubleNetworkType::double3,
                (double1, double2, double3) -> new DoubleNetworkType(double1, double2, double3, DoubleNetworkType.registerType(type))
        );
    }

    public static Type<DoubleNetworkType> registerType(ResourceLocation id) {
        return TYPE_MAP.computeIfAbsent(id, Type::new);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return type;
    }
}