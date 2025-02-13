package terramine.common.utility;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ShimmerConversionRegistry {
    private static final Map<Item, Item> CONVERSION_MAP = new HashMap<>();

    public static void register(Item input, Item output) {
        CONVERSION_MAP.put(input, output);
    }
    public static void registerTwoWay(Item input, Item output) {
        CONVERSION_MAP.put(input, output);
        CONVERSION_MAP.put(output, input);
    }

    public static Item getConvertedItem(ItemStack stack) {
        return CONVERSION_MAP.get(stack.getItem());
    }

    public static boolean hasConversion(ItemStack stack) {
        return CONVERSION_MAP.containsKey(stack.getItem());
    }
}
