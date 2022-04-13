package terracraft.common.init;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import terracraft.TerraCraft;
import terracraft.client.render.ChestScreenHandler;

public class ModScreenHandlerType {
    public static MenuType<ChestScreenHandler> GOLD_CHEST;
    public static MenuType<ChestScreenHandler> FROZEN_CHEST;
    public static MenuType<ChestScreenHandler> IVY_CHEST;
    public static MenuType<ChestScreenHandler> WATER_CHEST;
    public static MenuType<ChestScreenHandler> SKYWARE_CHEST;

    public static void register() {
        GOLD_CHEST = ScreenHandlerRegistry.registerSimple(TerraCraft.id("gold_chest"), (syncId, inventory) -> new ChestScreenHandler(40, GOLD_CHEST, syncId, inventory, ContainerLevelAccess.NULL));
        FROZEN_CHEST = ScreenHandlerRegistry.registerSimple(TerraCraft.id("frozen_chest"), (syncId, inventory) -> new ChestScreenHandler(40, FROZEN_CHEST, syncId, inventory, ContainerLevelAccess.NULL));
        IVY_CHEST = ScreenHandlerRegistry.registerSimple(TerraCraft.id("ivy_chest"), (syncId, inventory) -> new ChestScreenHandler(40, IVY_CHEST, syncId, inventory, ContainerLevelAccess.NULL));
        WATER_CHEST = ScreenHandlerRegistry.registerSimple(TerraCraft.id("water_chest"), (syncId, inventory) -> new ChestScreenHandler(40, WATER_CHEST, syncId, inventory, ContainerLevelAccess.NULL));
        SKYWARE_CHEST = ScreenHandlerRegistry.registerSimple(TerraCraft.id("skyware_chest"), (syncId, inventory) -> new ChestScreenHandler(40, SKYWARE_CHEST, syncId, inventory, ContainerLevelAccess.NULL));
    }
}
