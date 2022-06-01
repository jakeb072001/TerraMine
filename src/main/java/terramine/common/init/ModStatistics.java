package terramine.common.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import terramine.TerraMine;

public class ModStatistics {
    public static final ResourceLocation MANA_USED = register("mana_used", TerraMine.id("mana_used"));

    public static ResourceLocation register(String name, ResourceLocation stat) {
        return Registry.register(Registry.CUSTOM_STAT, name, stat);
    }

    private ModStatistics() {
    }
}
