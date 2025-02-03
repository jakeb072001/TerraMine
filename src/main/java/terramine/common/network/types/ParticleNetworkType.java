package terramine.common.network.types;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public record ParticleNetworkType(ParticleOptions particleOptions, Type<? extends CustomPacketPayload> type) implements CustomPacketPayload {
    private static final Map<ResourceLocation, Type<ParticleNetworkType>> TYPE_MAP = new HashMap<>();

    public static StreamCodec<RegistryFriendlyByteBuf, ParticleNetworkType> createCodec(ResourceLocation type) {
        return StreamCodec.composite(
                ParticleTypes.STREAM_CODEC, ParticleNetworkType::particleOptions,
                particleOptions -> new ParticleNetworkType(particleOptions, ParticleNetworkType.registerType(type))
        );
    }

    public static Type<ParticleNetworkType> registerType(ResourceLocation id) {
        return TYPE_MAP.computeIfAbsent(id, Type::new);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return type;
    }
}