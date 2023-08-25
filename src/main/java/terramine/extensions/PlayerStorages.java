package terramine.extensions;

import net.minecraft.world.SimpleContainer;
import terramine.client.render.gui.TerrariaInventoryCreator;

public interface PlayerStorages {

	SimpleContainer getTerrariaInventory();

	SimpleContainer getPiggyBankInventory();

	SimpleContainer getSafeInventory();

	TerrariaInventoryCreator getTerrariaMenu();

	void setTerrariaInventory(SimpleContainer terrariaInventory);

	void setPiggyBankInventory(SimpleContainer piggyBankInventory);

	void setSafeInventory(SimpleContainer safeInventory);
}
