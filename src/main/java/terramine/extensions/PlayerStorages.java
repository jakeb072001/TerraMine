package terramine.extensions;

import net.minecraft.world.SimpleContainer;
import terramine.client.render.gui.menu.TerrariaInventoryContainerMenu;
import terramine.common.misc.TerrariaInventory;

public interface PlayerStorages {

	TerrariaInventory getTerrariaInventory();

	SimpleContainer getPiggyBankInventory();

	SimpleContainer getSafeInventory();

	TerrariaInventoryContainerMenu getTerrariaMenu();

	void setTerrariaInventory(TerrariaInventory terrariaInventory);

	void setPiggyBankInventory(SimpleContainer piggyBankInventory);

	void setSafeInventory(SimpleContainer safeInventory);

	void setTerrariaMenu(TerrariaInventoryContainerMenu terrariaMenu);
}
