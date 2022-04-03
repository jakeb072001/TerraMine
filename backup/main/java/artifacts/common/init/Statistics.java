package terracraft.common.init;

import terracraft.Artifacts;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class Statistics {
    public static final ResourceLocation MANA_USED = register("mana_used", Artifacts.id("mana_used"));

    public static ResourceLocation register(String name, ResourceLocation stat) {
        return Registry.register(Registry.CUSTOM_STAT, name, stat);
    }

    private Statistics() {
    }
}
