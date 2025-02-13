package terramine.common.init;

import net.minecraft.world.item.Items;
import terramine.common.utility.ShimmerConversionRegistry;

public class ShimmerConversions {

    // todo: fill with more conversions (https://terraria.fandom.com/wiki/Shimmer)
    public static void register() {
        // One-Way

        // Two-Way
        ShimmerConversionRegistry.registerTwoWay(ModItems.SHADOW_ARMOR.HELMET, ModItems.ANCIENT_SHADOW_ARMOR.HELMET);
        ShimmerConversionRegistry.registerTwoWay(ModItems.SHADOW_ARMOR.CHESTPLATE, ModItems.ANCIENT_SHADOW_ARMOR.CHESTPLATE);
        ShimmerConversionRegistry.registerTwoWay(ModItems.SHADOW_ARMOR.LEGGINGS, ModItems.ANCIENT_SHADOW_ARMOR.LEGGINGS);
        ShimmerConversionRegistry.registerTwoWay(ModItems.SHADOW_ARMOR.BOOTS, ModItems.ANCIENT_SHADOW_ARMOR.BOOTS);
        ShimmerConversionRegistry.registerTwoWay(ModItems.MAGMA_STONE, ModItems.LAVA_CHARM);
        ShimmerConversionRegistry.registerTwoWay(ModItems.TIN_INGOT, Items.COPPER_INGOT);
        ShimmerConversionRegistry.registerTwoWay(ModItems.LEAD_INGOT, Items.IRON_INGOT);
        ShimmerConversionRegistry.registerTwoWay(ModItems.SILVER_INGOT, ModItems.TUNGSTEN_INGOT);
        ShimmerConversionRegistry.registerTwoWay(ModItems.PLATINUM_INGOT, Items.GOLD_INGOT);
    }
}
