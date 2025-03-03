package terramine.common.utility.dps;

import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DPSManager {
    private static final Map<UUID, DPSTracker> dpsTrackers = new HashMap<>();

    public static DPSTracker getTracker(Player player) {
        return dpsTrackers.computeIfAbsent(player.getUUID(), uuid -> new DPSTracker());
    }

    public static void decayAll() {
        for (DPSTracker tracker : dpsTrackers.values()) {
            tracker.decayDps();
        }
    }
}