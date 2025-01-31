package terramine.common.utility;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import terramine.common.network.ServerPacketHandler;
import terramine.common.network.packet.UpdateInputPacket;
import terramine.common.network.types.InputNetworkType;

import java.util.List;

@Environment(EnvType.CLIENT)
public class KeyBindingsHandler {
    private static boolean jump, attack, shift = false;
    private static boolean forwards, backwards, left, right = false;

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

            List<Boolean> booleanList = new java.util.ArrayList<>(List.of());
            booleanList.add(jumpNow);
            booleanList.add(attackNow);
            booleanList.add(shiftNow);
            booleanList.add(forwardsNow);
            booleanList.add(backwardsNow);
            booleanList.add(leftNow);
            booleanList.add(rightNow);
            sendToServer(new UpdateInputPacket(booleanList));
            InputHandler.update(client.player, jumpNow, attackNow, shiftNow, forwardsNow, backwardsNow, leftNow, rightNow);
        }
    }

    public static void sendToServer(UpdateInputPacket packet) {
        List<Boolean> booleanList = new java.util.ArrayList<>(List.of());
        UpdateInputPacket.write(packet, booleanList);
        ClientPlayNetworking.send(new InputNetworkType(booleanList, ServerPacketHandler.CONTROLS_PACKET_ID));
    }
}
