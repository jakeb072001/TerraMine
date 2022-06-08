package terramine.common.network;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import terramine.TerraMine;
import terramine.common.network.packet.UpdateInputPacket;

public class UpdateInputNetworkHandler {
    public static final ResourceLocation PACKET_ID = TerraMine.id("controls_packet");

    @Environment(EnvType.CLIENT)
    public static void sendToServer(UpdateInputPacket packet) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        UpdateInputPacket.write(packet, buf);
        ClientPlayNetworking.send(PACKET_ID, buf);
    }
}
