package terramine.extensions;

import net.minecraft.world.SimpleContainer;
import terramine.client.render.gui.menu.TerrariaInventoryContainerMenu;

public interface PlayerStorages {

	SimpleContainer getTerrariaInventory();

	SimpleContainer getPiggyBankInventory();

	SimpleContainer getSafeInventory();

	TerrariaInventoryContainerMenu getTerrariaMenu();

	void setTerrariaInventory(SimpleContainer terrariaInventory);

	void setPiggyBankInventory(SimpleContainer piggyBankInventory);

	void setSafeInventory(SimpleContainer safeInventory);

	void setTerrariaMenu(TerrariaInventoryContainerMenu terrariaMenu);
}
