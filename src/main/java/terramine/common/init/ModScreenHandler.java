package terramine.common.init;

import net.minecraft.client.gui.screens.MenuScreens;
import terramine.client.render.gui.menu.ChestBlockContainerMenu;
import terramine.client.render.gui.menu.TerrariaInventoryContainerMenu;
import terramine.client.render.gui.screen.ChestBlockScreen;
import terramine.client.render.gui.screen.TerrariaInventoryScreen;

public class ModScreenHandler {
    public static void register() {
        MenuScreens.<TerrariaInventoryContainerMenu, TerrariaInventoryScreen>register(ModScreenHandlerType.TERRARIA_CONTAINER, (description, inventory, title) -> new TerrariaInventoryScreen(inventory.player));
        MenuScreens.<ChestBlockContainerMenu, ChestBlockScreen>register(ModScreenHandlerType.GOLD_CHEST, (description, inventory, title) -> new ChestBlockScreen(description, inventory.player, title));
        MenuScreens.<ChestBlockContainerMenu, ChestBlockScreen>register(ModScreenHandlerType.FROZEN_CHEST, (description, inventory, title) -> new ChestBlockScreen(description, inventory.player, title));
        MenuScreens.<ChestBlockContainerMenu, ChestBlockScreen>register(ModScreenHandlerType.IVY_CHEST, (description, inventory, title) -> new ChestBlockScreen(description, inventory.player, title));
        MenuScreens.<ChestBlockContainerMenu, ChestBlockScreen>register(ModScreenHandlerType.SANDSTONE_CHEST, (description, inventory, title) -> new ChestBlockScreen(description, inventory.player, title));
        MenuScreens.<ChestBlockContainerMenu, ChestBlockScreen>register(ModScreenHandlerType.WATER_CHEST, (description, inventory, title) -> new ChestBlockScreen(description, inventory.player, title));
        MenuScreens.<ChestBlockContainerMenu, ChestBlockScreen>register(ModScreenHandlerType.SKYWARE_CHEST, (description, inventory, title) -> new ChestBlockScreen(description, inventory.player, title));
        MenuScreens.<ChestBlockContainerMenu, ChestBlockScreen>register(ModScreenHandlerType.SHADOW_CHEST, (description, inventory, title) -> new ChestBlockScreen(description, inventory.player, title));
        MenuScreens.<ChestBlockContainerMenu, ChestBlockScreen>register(ModScreenHandlerType.PIGGY_BANK, (description, inventory, title) -> new ChestBlockScreen(description, inventory.player, title));
        MenuScreens.<ChestBlockContainerMenu, ChestBlockScreen>register(ModScreenHandlerType.SAFE, (description, inventory, title) -> new ChestBlockScreen(description, inventory.player, title));
    }
}
