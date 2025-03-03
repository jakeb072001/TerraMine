package terramine.common.utility.dps;


import com.mojang.datafixers.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class DPSTracker {
    private final List<Pair<Long, Float>> damageLog = new ArrayList<>();
    private static final long TIME_WINDOW_MS = 5000; // 5 seconds
    private float currentDps = 0;

    public void recordDamage(float damage) {
        long now = System.currentTimeMillis();
        damageLog.add(new Pair<>(now, damage));
        cleanUpOldEntries(now);
        updateDps();
    }

    private void cleanUpOldEntries(long now) {
        damageLog.removeIf(entry -> now - entry.getFirst() > TIME_WINDOW_MS);
    }

    private void updateDps() {
        long now = System.currentTimeMillis();
        cleanUpOldEntries(now);
        float totalDamage = 0;
        for (Pair<Long, Float> entry : damageLog) {
            totalDamage += entry.getSecond();
        }
        currentDps = totalDamage / (TIME_WINDOW_MS / 1000.0f);
    }

    public float getDps() {
        return currentDps;
    }

    public void decayDps() {
        currentDps *= 0.95f; // Reduce DPS by 5% per update when idle
    }
}