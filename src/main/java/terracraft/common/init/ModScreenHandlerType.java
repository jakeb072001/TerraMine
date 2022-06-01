package terracraft.common.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import terracraft.TerraCraft;
import terracraft.client.render.ChestScreenHandler;

public class ModScreenHandlerType {
    public static MenuType<ChestScreenHandler> GOLD_CHEST;
    public static MenuType<ChestScreenHandler> FROZEN_CHEST;
    public static MenuType<ChestScreenHandler> IVY_CHEST;
    public static MenuType<ChestScreenHandler> SANDSTONE_CHEST;
    public static MenuType<ChestScreenHandler> WATER_CHEST;
    public static MenuType<ChestScreenHandler> SKYWARE_CHEST;
    public static MenuType<ChestScreenHandler> SHADOW_CHEST;

    public static void register() {
        GOLD_CHEST = registerSimple(TerraCraft.id("gold_chest"), (syncId, inventory) -> new ChestScreenHandler(40, GOLD_CHEST, syncId, inventory, ContainerLevelAccess.NULL));
        FROZEN_CHEST = registerSimple(TerraCraft.id("frozen_chest"), (syncId, inventory) -> new ChestScreenHandler(40, FROZEN_CHEST, syncId, inventory, ContainerLevelAccess.NULL));
        IVY_CHEST = registerSimple(TerraCraft.id("ivy_chest"), (syncId, inventory) -> new ChestScreenHandler(40, IVY_CHEST, syncId, inventory, ContainerLevelAccess.NULL));
        SANDSTONE_CHEST = registerSimple(TerraCraft.id("sandstone_chest"), (syncId, inventory) -> new ChestScreenHandler(40, SANDSTONE_CHEST, syncId, inventory, ContainerLevelAccess.NULL));
        WATER_CHEST = registerSimple(TerraCraft.id("water_chest"), (syncId, inventory) -> new ChestScreenHandler(40, WATER_CHEST, syncId, inventory, ContainerLevelAccess.NULL));
        SKYWARE_CHEST = registerSimple(TerraCraft.id("skyware_chest"), (syncId, inventory) -> new ChestScreenHandler(40, SKYWARE_CHEST, syncId, inventory, ContainerLevelAccess.NULL));
        SHADOW_CHEST = registerSimple(TerraCraft.id("shadow_chest"), (syncId, inventory) -> new ChestScreenHandler(40, SHADOW_CHEST, syncId, inventory, ContainerLevelAccess.NULL));
    }

    public static <T extends AbstractContainerMenu> MenuType<T> registerSimple(ResourceLocation id, MenuType.MenuSupplier<T> factory) {
        MenuType<T> type = new MenuType<>(factory);
        return Registry.register(Registry.MENU, id, type);
    }
}
