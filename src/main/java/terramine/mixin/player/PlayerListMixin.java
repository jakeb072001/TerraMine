package terramine.mixin.player;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.common.init.ModComponents;
import terramine.common.network.ServerPacketHandler;

@Mixin(PlayerList.class)
public class PlayerListMixin {

    // todo: was working, now not :/
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;initInventoryMenu()V"), method = "placeNewPlayer")
    private void onPlaceNewPlayer(Connection connection, ServerPlayer serverPlayer, CallbackInfo ci) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        FriendlyByteBuf buf2;
        buf.writeUUID(serverPlayer.getUUID());
        for (int i = 0; i < 7; i++) {
            buf.writeBoolean(ModComponents.ACCESSORY_VISIBILITY.get(serverPlayer).getSlotVisibility(i));
        }
        for (ServerPlayer otherPlayer : serverPlayer.serverLevel().players()) {
            buf2 = PacketByteBufs.create();
            buf2.writeUUID(otherPlayer.getUUID());
            for (int i = 0; i < 7; i++) {
                buf2.writeBoolean(ModComponents.ACCESSORY_VISIBILITY.get(otherPlayer).getSlotVisibility(i));
            }
            ServerPlayNetworking.send(serverPlayer, ServerPacketHandler.SETUP_ACCESSORY_VISIBILITY_PACKET_ID, buf2);
            ServerPlayNetworking.send(otherPlayer, ServerPacketHandler.SETUP_ACCESSORY_VISIBILITY_PACKET_ID, buf);
        }
    }
}
