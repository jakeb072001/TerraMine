package terracraft.common.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import terracraft.TerraCraft;

public class ModStatistics {
    public static final ResourceLocation MANA_USED = register("mana_used", TerraCraft.id("mana_used"));

    public static ResourceLocation register(String name, ResourceLocation stat) {
        return Registry.register(Registry.CUSTOM_STAT, name, stat);
    }

    private ModStatistics() {
    }
}
