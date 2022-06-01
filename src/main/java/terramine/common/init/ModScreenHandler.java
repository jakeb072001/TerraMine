package terramine.common.init;

import net.minecraft.client.gui.screens.MenuScreens;
import terramine.client.render.ChestBlockScreenHandler;
import terramine.client.render.ChestScreenHandler;

public class ModScreenHandler {
    public static void register() {
        MenuScreens.<ChestScreenHandler, ChestBlockScreenHandler>register(ModScreenHandlerType.GOLD_CHEST, (description, inventory, title) -> new ChestBlockScreenHandler(description, inventory.player, title));
        MenuScreens.<ChestScreenHandler, ChestBlockScreenHandler>register(ModScreenHandlerType.FROZEN_CHEST, (description, inventory, title) -> new ChestBlockScreenHandler(description, inventory.player, title));
        MenuScreens.<ChestScreenHandler, ChestBlockScreenHandler>register(ModScreenHandlerType.IVY_CHEST, (description, inventory, title) -> new ChestBlockScreenHandler(description, inventory.player, title));
        MenuScreens.<ChestScreenHandler, ChestBlockScreenHandler>register(ModScreenHandlerType.SANDSTONE_CHEST, (description, inventory, title) -> new ChestBlockScreenHandler(description, inventory.player, title));
        MenuScreens.<ChestScreenHandler, ChestBlockScreenHandler>register(ModScreenHandlerType.WATER_CHEST, (description, inventory, title) -> new ChestBlockScreenHandler(description, inventory.player, title));
        MenuScreens.<ChestScreenHandler, ChestBlockScreenHandler>register(ModScreenHandlerType.SKYWARE_CHEST, (description, inventory, title) -> new ChestBlockScreenHandler(description, inventory.player, title));
        MenuScreens.<ChestScreenHandler, ChestBlockScreenHandler>register(ModScreenHandlerType.SHADOW_CHEST, (description, inventory, title) -> new ChestBlockScreenHandler(description, inventory.player, title));
    }
}
