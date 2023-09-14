package terramine.extensions;

import net.minecraft.world.SimpleContainer;
import terramine.common.misc.TerrariaInventory;

public interface PlayerStorages {

	TerrariaInventory getTerrariaInventory();

	SimpleContainer getPiggyBankInventory();

	SimpleContainer getSafeInventory();

	void setTerrariaInventory(TerrariaInventory terrariaInventory);

	void setPiggyBankInventory(SimpleContainer piggyBankInventory);

	void setSafeInventory(SimpleContainer safeInventory);
}
