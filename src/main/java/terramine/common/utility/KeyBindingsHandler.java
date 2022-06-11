package terramine.common.utility;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.network.FriendlyByteBuf;
import terramine.TerraMine;
import terramine.common.network.ServerPacketHandler;
import terramine.common.network.packet.UpdateInputPacket;

@Environment(EnvType.CLIENT)
public class KeyBindingsHandler {
    private static boolean jump = false;
    private static boolean forwards = false;
    private static boolean backwards = false;
    private static boolean left = false;
    private static boolean right = false;
    private static boolean attack = false;
    private static boolean shift = false;

    public static void onClientTick(Minecraft client) {
        updateInputs(client);
    }

    /*
     * Keyboard handling borrowed from Simply Jetpacks
     * https://github.com/Tomson124/SimplyJetpacks-2/blob/1.12/src/main/java/tonius/simplyjetpacks/client/handler/KeyTracker.java
     */
    public static void updateInputs(Minecraft client) {
        Options settings = client.options;

        if (client.getConnection() == null)
            return;

        boolean jumpNow = settings.keyJump.isDown();
        boolean attackNow = settings.keyAttack.isDown();
        boolean shiftNow = settings.keyShift.isDown();
        boolean forwardsNow = settings.keyUp.isDown();
        boolean backwardsNow = settings.keyDown.isDown();
        boolean leftNow = settings.keyLeft.isDown();
        boolean rightNow = settings.keyRight.isDown();

        if (jumpNow != jump || attackNow != attack || shiftNow != shift || forwardsNow != forwards || backwardsNow != backwards || leftNow != left || rightNow != right) {
            jump = jumpNow;
            attack = attackNow;
            shift = shiftNow;
            forwards = forwardsNow;
            backwards = backwardsNow;
            left = leftNow;
            right = rightNow;

            sendToServer(new UpdateInputPacket(jumpNow, attackNow, shiftNow, forwardsNow, backwardsNow, leftNow, rightNow));
            InputHandler.update(client.player, jumpNow, attackNow, shiftNow, forwardsNow, backwardsNow, leftNow, rightNow);
        }
    }

    public static void sendToServer(UpdateInputPacket packet) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        UpdateInputPacket.write(packet, buf);
        ClientPlayNetworking.send(ServerPacketHandler.CONTROLS_PACKET_ID, buf);
    }
}
