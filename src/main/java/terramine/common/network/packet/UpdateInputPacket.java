package terramine.common.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import terramine.common.utility.InputHandler;

public class UpdateInputPacket {
    private final boolean jump, attack, shift, forwards, backwards, left, right;

    public UpdateInputPacket(boolean jump, boolean attack, boolean shift, boolean forwards, boolean backwards, boolean left, boolean right) {
        this.jump = jump;
        this.attack = attack;
        this.shift = shift;
        this.forwards = forwards;
        this.backwards = backwards;
        this.left = left;
        this.right = right;
    }

    public static UpdateInputPacket read(FriendlyByteBuf buffer) {
        return new UpdateInputPacket(buffer.readBoolean(), buffer.readBoolean(), buffer.readBoolean(), buffer.readBoolean(), buffer.readBoolean(), buffer.readBoolean(), buffer.readBoolean());
    }

    public static void write(UpdateInputPacket message, FriendlyByteBuf buffer) {
        buffer.writeBoolean(message.jump);
        buffer.writeBoolean(message.attack);
        buffer.writeBoolean(message.shift);
        buffer.writeBoolean(message.forwards);
        buffer.writeBoolean(message.backwards);
        buffer.writeBoolean(message.left);
        buffer.writeBoolean(message.right);
    }

    public static void onMessage(UpdateInputPacket message, MinecraftServer server, ServerPlayer player) {
        server.execute(() -> {
            if (player != null) {
                InputHandler.update(player, message.jump, message.attack, message.shift, message.forwards, message.backwards, message.left, message.right);
            }
        });
    }
}
