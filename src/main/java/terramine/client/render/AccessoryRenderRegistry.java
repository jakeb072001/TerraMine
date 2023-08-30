package terramine.client.render;

import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AccessoryRenderRegistry {
    private static final Map<Item, AccessoryRenderer> RENDERERS = new HashMap<>();

    /**
     * Registers an accessory renderer for the provided item
     */
    public static void registerRenderer(Item item, AccessoryRenderer accessoryRenderer) {
        RENDERERS.put(item, accessoryRenderer);
    }

    public static Optional<AccessoryRenderer> getRenderer(Item item) {
        return Optional.ofNullable(RENDERERS.get(item));
    }
}
