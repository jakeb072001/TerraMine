package terramine.common.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import terramine.common.utility.InputHandler;

public class UpdateInputPacket {
    private final boolean up;
    private final boolean down;
    private final boolean forwards;
    private final boolean backwards;
    private final boolean left;
    private final boolean right;

    public UpdateInputPacket(boolean up, boolean down, boolean forwards, boolean backwards, boolean left, boolean right) {
        this.up = up;
        this.down = down;
        this.forwards = forwards;
        this.backwards = backwards;
        this.left = left;
        this.right = right;
    }

    public static UpdateInputPacket read(FriendlyByteBuf buffer) {
        return new UpdateInputPacket(buffer.readBoolean(), buffer.readBoolean(), buffer.readBoolean(), buffer.readBoolean(), buffer.readBoolean(), buffer.readBoolean());
    }

    public static void write(UpdateInputPacket message, FriendlyByteBuf buffer) {
        buffer.writeBoolean(message.up);
        buffer.writeBoolean(message.down);
        buffer.writeBoolean(message.forwards);
        buffer.writeBoolean(message.backwards);
        buffer.writeBoolean(message.left);
        buffer.writeBoolean(message.right);
    }

    public static void onMessage(UpdateInputPacket message, MinecraftServer server, ServerPlayer player) {
        server.execute(() -> {
            if (player != null) {
                InputHandler.update(player, message.up, message.down, message.forwards, message.backwards, message.left, message.right);
            }
        });
    }
}
