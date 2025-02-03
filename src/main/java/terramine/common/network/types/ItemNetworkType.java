package terramine.common.network.types;

import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public record ItemNetworkType(ItemStack itemStack, int integer, UUID uuid, Type<? extends CustomPacketPayload> type) implements CustomPacketPayload {
    private static final Map<ResourceLocation, Type<ItemNetworkType>> TYPE_MAP = new HashMap<>();

    public static StreamCodec<RegistryFriendlyByteBuf, ItemNetworkType> createCodec(ResourceLocation type) {
        return StreamCodec.composite(
                ItemStack.OPTIONAL_STREAM_CODEC, ItemNetworkType::itemStack,
                ByteBufCodecs.INT, ItemNetworkType::integer,
                UUIDUtil.STREAM_CODEC, ItemNetworkType::uuid,
                (itemStacks, integer, uuid) -> new ItemNetworkType(itemStacks, integer, uuid, ItemNetworkType.registerType(type))
        );
    }

    public static Type<ItemNetworkType> registerType(ResourceLocation id) {
        return TYPE_MAP.computeIfAbsent(id, Type::new);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return type;
    }
}