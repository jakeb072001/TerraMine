package terramine.common.init;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import terramine.common.utility.Utilities;

public class ModColourProviders {

    public static void registerProviders() {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 0 ? Utilities.getDyeColour(stack) : -1, ModItems.RED_DYE, ModItems.GREEN_DYE, ModItems.BLUE_DYE, ModItems.YELLOW_DYE, ModItems.ORANGE_DYE, ModItems.PURPLE_DYE, ModItems.PINK_DYE,
                ModItems.BROWN_DYE, ModItems.GRAY_DYE, ModItems.BLACK_DYE);
    }
}