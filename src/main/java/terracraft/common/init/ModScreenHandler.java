package terracraft.common.init;

import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import terracraft.client.render.ChestBlockScreenHandler;
import terracraft.client.render.ChestScreenHandler;

public class ModScreenHandler {
    public static void register() {
        ScreenRegistry.<ChestScreenHandler, ChestBlockScreenHandler>register(ModScreenHandlerType.GOLD_CHEST, (description, inventory, title) -> new ChestBlockScreenHandler(description, inventory.player, title));
        ScreenRegistry.<ChestScreenHandler, ChestBlockScreenHandler>register(ModScreenHandlerType.FROZEN_CHEST, (description, inventory, title) -> new ChestBlockScreenHandler(description, inventory.player, title));
        ScreenRegistry.<ChestScreenHandler, ChestBlockScreenHandler>register(ModScreenHandlerType.IVY_CHEST, (description, inventory, title) -> new ChestBlockScreenHandler(description, inventory.player, title));
        ScreenRegistry.<ChestScreenHandler, ChestBlockScreenHandler>register(ModScreenHandlerType.SANDSTONE_CHEST, (description, inventory, title) -> new ChestBlockScreenHandler(description, inventory.player, title));
        ScreenRegistry.<ChestScreenHandler, ChestBlockScreenHandler>register(ModScreenHandlerType.WATER_CHEST, (description, inventory, title) -> new ChestBlockScreenHandler(description, inventory.player, title));
        ScreenRegistry.<ChestScreenHandler, ChestBlockScreenHandler>register(ModScreenHandlerType.SKYWARE_CHEST, (description, inventory, title) -> new ChestBlockScreenHandler(description, inventory.player, title));
        ScreenRegistry.<ChestScreenHandler, ChestBlockScreenHandler>register(ModScreenHandlerType.SHADOW_CHEST, (description, inventory, title) -> new ChestBlockScreenHandler(description, inventory.player, title));
    }
}
