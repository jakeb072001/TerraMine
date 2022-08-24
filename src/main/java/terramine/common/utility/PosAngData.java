package terramine.common.utility;

import net.minecraft.core.Position;

// From Sculk Worm
public class PosAngData {
    private final Position pos;
    private final float yaw;
    private final float pitch;

    public PosAngData(Position pos, float yaw, float pitch) {
        this.pos = pos;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Position getPos() {
        return this.pos;
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }
}
