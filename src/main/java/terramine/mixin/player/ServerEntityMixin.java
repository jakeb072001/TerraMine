package terramine.mixin.player;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.common.network.ServerPacketHandler;

@Mixin(ServerEntity.class)
public class ServerEntityMixin {
    @Shadow
    @Final
    @Mutable
    private final Entity entity;

    public ServerEntityMixin(Entity entity) {
        this.entity = entity;
    }

    @Inject(method = "addPairing", at = @At(value = "TAIL"))
    public void startTrackingMixin(ServerPlayer serverPlayer, CallbackInfo info) {
        if (entity instanceof Player player) {
            for (int i = 56; i < 61; i++) {
                if (!serverPlayer.getInventory().getItem(i).isEmpty()) {
                    FriendlyByteBuf data = new FriendlyByteBuf(Unpooled.buffer());
                    data.writeVarIntArray(new int[] { serverPlayer.getId(), i });
                    data.writeItem(serverPlayer.getInventory().getItem(i));
                    ServerPlayNetworking.send((ServerPlayer) player, ServerPacketHandler.UPDATE_ACCESSORY_VISIBILITY_PACKET_ID, data);
                }
                if (!player.getInventory().getItem(i).isEmpty()) {
                    FriendlyByteBuf data = new FriendlyByteBuf(Unpooled.buffer());
                    data.writeVarIntArray(new int[] { player.getId(), i });
                    data.writeItem(player.getInventory().getItem(i));
                    ServerPlayNetworking.send(serverPlayer, ServerPacketHandler.UPDATE_ACCESSORY_VISIBILITY_PACKET_ID, data);
                }
            }
        }
    }
}
