package terramine.common.network.types;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record InputNetworkType(List<Boolean> booleanList, Type<? extends CustomPacketPayload> type) implements CustomPacketPayload {
    private static final Map<ResourceLocation, Type<InputNetworkType>> TYPE_MAP = new HashMap<>();

    public static StreamCodec<RegistryFriendlyByteBuf, InputNetworkType> createCodec(ResourceLocation type) {
        return StreamCodec.composite(
                ByteBufCodecs.BOOL.apply(ByteBufCodecs.list()), InputNetworkType::booleanList,
                booleanList -> new InputNetworkType(booleanList, InputNetworkType.registerType(type))
        );
    }

    public static Type<InputNetworkType> registerType(ResourceLocation id) {
        return TYPE_MAP.computeIfAbsent(id, Type::new);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return type;
    }
}