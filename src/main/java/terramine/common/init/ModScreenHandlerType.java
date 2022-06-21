package terramine.common.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import terramine.TerraMine;
import terramine.client.render.ChestScreenHandler;

public class ModScreenHandlerType {
    public static MenuType<ChestScreenHandler> GOLD_CHEST;
    public static MenuType<ChestScreenHandler> FROZEN_CHEST;
    public static MenuType<ChestScreenHandler> IVY_CHEST;
    public static MenuType<ChestScreenHandler> SANDSTONE_CHEST;
    public static MenuType<ChestScreenHandler> WATER_CHEST;
    public static MenuType<ChestScreenHandler> SKYWARE_CHEST;
    public static MenuType<ChestScreenHandler> SHADOW_CHEST;
    public static MenuType<ChestScreenHandler> PIGGY_BANK;
    public static MenuType<ChestScreenHandler> SAFE;

    public static void register() {
        GOLD_CHEST = registerSimple(TerraMine.id("gold_chest"), (syncId, inventory) -> new ChestScreenHandler(40, GOLD_CHEST, syncId, inventory, ContainerLevelAccess.NULL));
        FROZEN_CHEST = registerSimple(TerraMine.id("frozen_chest"), (syncId, inventory) -> new ChestScreenHandler(40, FROZEN_CHEST, syncId, inventory, ContainerLevelAccess.NULL));
        IVY_CHEST = registerSimple(TerraMine.id("ivy_chest"), (syncId, inventory) -> new ChestScreenHandler(40, IVY_CHEST, syncId, inventory, ContainerLevelAccess.NULL));
        SANDSTONE_CHEST = registerSimple(TerraMine.id("sandstone_chest"), (syncId, inventory) -> new ChestScreenHandler(40, SANDSTONE_CHEST, syncId, inventory, ContainerLevelAccess.NULL));
        WATER_CHEST = registerSimple(TerraMine.id("water_chest"), (syncId, inventory) -> new ChestScreenHandler(40, WATER_CHEST, syncId, inventory, ContainerLevelAccess.NULL));
        SKYWARE_CHEST = registerSimple(TerraMine.id("skyware_chest"), (syncId, inventory) -> new ChestScreenHandler(40, SKYWARE_CHEST, syncId, inventory, ContainerLevelAccess.NULL));
        SHADOW_CHEST = registerSimple(TerraMine.id("shadow_chest"), (syncId, inventory) -> new ChestScreenHandler(40, SHADOW_CHEST, syncId, inventory, ContainerLevelAccess.NULL));
        PIGGY_BANK = registerSimple(TerraMine.id("piggy_bank"), (syncId, inventory) -> new ChestScreenHandler(40, PIGGY_BANK, syncId, inventory, ContainerLevelAccess.NULL));
        SAFE = registerSimple(TerraMine.id("safe"), (syncId, inventory) -> new ChestScreenHandler(40, SAFE, syncId, inventory, ContainerLevelAccess.NULL));
    }

    public static <T extends AbstractContainerMenu> MenuType<T> registerSimple(ResourceLocation id, MenuType.MenuSupplier<T> factory) {
        MenuType<T> type = new MenuType<>(factory);
        return Registry.register(Registry.MENU, id, type);
    }
}
