package terramine.common.init;

import net.minecraft.client.gui.screens.MenuScreens;
import terramine.client.render.gui.ChestBlockScreenHandler;
import terramine.client.render.gui.ChestScreenCreator;
import terramine.client.render.gui.TerrariaInventoryCreator;
import terramine.client.render.gui.TerrariaInventoryHandler;

public class ModScreenHandler {
    public static void register() {
        MenuScreens.<TerrariaInventoryCreator, TerrariaInventoryHandler>register(ModScreenHandlerType.TERRARIA_CONTAINER, (description, inventory, title) -> new TerrariaInventoryHandler(inventory.player));
        MenuScreens.<ChestScreenCreator, ChestBlockScreenHandler>register(ModScreenHandlerType.GOLD_CHEST, (description, inventory, title) -> new ChestBlockScreenHandler(description, inventory.player, title));
        MenuScreens.<ChestScreenCreator, ChestBlockScreenHandler>register(ModScreenHandlerType.FROZEN_CHEST, (description, inventory, title) -> new ChestBlockScreenHandler(description, inventory.player, title));
        MenuScreens.<ChestScreenCreator, ChestBlockScreenHandler>register(ModScreenHandlerType.IVY_CHEST, (description, inventory, title) -> new ChestBlockScreenHandler(description, inventory.player, title));
        MenuScreens.<ChestScreenCreator, ChestBlockScreenHandler>register(ModScreenHandlerType.SANDSTONE_CHEST, (description, inventory, title) -> new ChestBlockScreenHandler(description, inventory.player, title));
        MenuScreens.<ChestScreenCreator, ChestBlockScreenHandler>register(ModScreenHandlerType.WATER_CHEST, (description, inventory, title) -> new ChestBlockScreenHandler(description, inventory.player, title));
        MenuScreens.<ChestScreenCreator, ChestBlockScreenHandler>register(ModScreenHandlerType.SKYWARE_CHEST, (description, inventory, title) -> new ChestBlockScreenHandler(description, inventory.player, title));
        MenuScreens.<ChestScreenCreator, ChestBlockScreenHandler>register(ModScreenHandlerType.SHADOW_CHEST, (description, inventory, title) -> new ChestBlockScreenHandler(description, inventory.player, title));
        MenuScreens.<ChestScreenCreator, ChestBlockScreenHandler>register(ModScreenHandlerType.PIGGY_BANK, (description, inventory, title) -> new ChestBlockScreenHandler(description, inventory.player, title));
        MenuScreens.<ChestScreenCreator, ChestBlockScreenHandler>register(ModScreenHandlerType.SAFE, (description, inventory, title) -> new ChestBlockScreenHandler(description, inventory.player, title));
    }
}
