package terramine.common.misc;

import org.joml.Vector3f;
import terramine.common.utility.Utilities;

public enum TeamColours {
    NONE(0, "none", 0x000000),
    WHITE(1, "blue", 0xFFFFFF),
    RED(2, "blue", 0xFF0000),
    GREEN(3, "blue", 0x008000),
    BLUE(4, "blue", 0x0000FF),
    YELLOW(5, "blue", 0xFFFF00),
    PURPLE(6, "blue", 0x800080);

    private final int index;
    private final String name;
    private final int colour;

    TeamColours(int index, String name, int colour) {
        this.index = index;
        this.name = name;
        this.colour = colour;
    }

    public static TeamColours getTeam(int i) {
        for (TeamColours l : TeamColours.values()) {
            if (l.index == i) return l;
        }
        return NONE;
    }

    public int getIndex() {
        return index;
    }

    public String getTeamName() {
        return name;
    }

    public Vector3f getTeamColour() {
        return Utilities.colorFromInt(colour);
    }
}
