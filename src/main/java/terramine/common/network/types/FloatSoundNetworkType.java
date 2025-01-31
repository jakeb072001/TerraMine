package terramine.common.network.types;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public record FloatSoundNetworkType(float float1, float float2, SoundEvent soundEvent, Type<? extends CustomPacketPayload> type) implements CustomPacketPayload {
    private static final Map<ResourceLocation, Type<FloatSoundNetworkType>> TYPE_MAP = new HashMap<>();

    public static StreamCodec<RegistryFriendlyByteBuf, FloatSoundNetworkType> createCodec(ResourceLocation type) {
        return StreamCodec.composite(
                ByteBufCodecs.FLOAT, FloatSoundNetworkType::float1,
                ByteBufCodecs.FLOAT, FloatSoundNetworkType::float2,
                SoundEvent.DIRECT_STREAM_CODEC, FloatSoundNetworkType::soundEvent,
                (float1, float2, soundEvent) -> new FloatSoundNetworkType(float1, float2, soundEvent, FloatSoundNetworkType.registerType(type))
        );
    }

    public static Type<FloatSoundNetworkType> registerType(ResourceLocation id) {
        return TYPE_MAP.computeIfAbsent(id, Type::new);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return type;
    }
}