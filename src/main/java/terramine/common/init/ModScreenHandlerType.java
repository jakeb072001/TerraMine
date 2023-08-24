package terramine.common.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import terramine.TerraMine;
import terramine.client.render.gui.ChestScreenCreator;
import terramine.client.render.gui.TerrariaInventoryCreator;

public class ModScreenHandlerType {
    public static MenuType<TerrariaInventoryCreator> TERRARIA_CONTAINER;
    public static MenuType<ChestScreenCreator> GOLD_CHEST;
    public static MenuType<ChestScreenCreator> FROZEN_CHEST;
    public static MenuType<ChestScreenCreator> IVY_CHEST;
    public static MenuType<ChestScreenCreator> SANDSTONE_CHEST;
    public static MenuType<ChestScreenCreator> WATER_CHEST;
    public static MenuType<ChestScreenCreator> SKYWARE_CHEST;
    public static MenuType<ChestScreenCreator> SHADOW_CHEST;
    public static MenuType<ChestScreenCreator> PIGGY_BANK;
    public static MenuType<ChestScreenCreator> SAFE;

    public static void register() {
        TERRARIA_CONTAINER = registerSimple(TerraMine.id("terraria_container"), (syncId, inventory) -> new TerrariaInventoryCreator(inventory.player));
        GOLD_CHEST = registerSimple(TerraMine.id("gold_chest"), (syncId, inventory) -> new ChestScreenCreator(40, GOLD_CHEST, syncId, inventory, ContainerLevelAccess.NULL));
        FROZEN_CHEST = registerSimple(TerraMine.id("frozen_chest"), (syncId, inventory) -> new ChestScreenCreator(40, FROZEN_CHEST, syncId, inventory, ContainerLevelAccess.NULL));
        IVY_CHEST = registerSimple(TerraMine.id("ivy_chest"), (syncId, inventory) -> new ChestScreenCreator(40, IVY_CHEST, syncId, inventory, ContainerLevelAccess.NULL));
        SANDSTONE_CHEST = registerSimple(TerraMine.id("sandstone_chest"), (syncId, inventory) -> new ChestScreenCreator(40, SANDSTONE_CHEST, syncId, inventory, ContainerLevelAccess.NULL));
        WATER_CHEST = registerSimple(TerraMine.id("water_chest"), (syncId, inventory) -> new ChestScreenCreator(40, WATER_CHEST, syncId, inventory, ContainerLevelAccess.NULL));
        SKYWARE_CHEST = registerSimple(TerraMine.id("skyware_chest"), (syncId, inventory) -> new ChestScreenCreator(40, SKYWARE_CHEST, syncId, inventory, ContainerLevelAccess.NULL));
        SHADOW_CHEST = registerSimple(TerraMine.id("shadow_chest"), (syncId, inventory) -> new ChestScreenCreator(40, SHADOW_CHEST, syncId, inventory, ContainerLevelAccess.NULL));
        PIGGY_BANK = registerSimple(TerraMine.id("piggy_bank"), (syncId, inventory) -> new ChestScreenCreator(40, PIGGY_BANK, syncId, inventory, ContainerLevelAccess.NULL));
        SAFE = registerSimple(TerraMine.id("safe"), (syncId, inventory) -> new ChestScreenCreator(40, SAFE, syncId, inventory, ContainerLevelAccess.NULL));
    }

    public static <T extends AbstractContainerMenu> MenuType<T> registerSimple(ResourceLocation id, MenuType.MenuSupplier<T> factory) {
        MenuType<T> type = new MenuType<>(factory);
        return Registry.register(Registry.MENU, id, type);
    }
}
