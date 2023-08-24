package terramine.extensions;

import net.minecraft.world.SimpleContainer;

public interface PlayerStorages {

	SimpleContainer getTerrariaInventory();

	SimpleContainer getPiggyBankInventory();

	SimpleContainer getSafeInventory();

	void setTerrariaInventory(SimpleContainer terrariaInventory);

	void setPiggyBankInventory(SimpleContainer piggyBankInventory);

	void setSafeInventory(SimpleContainer safeInventory);
}
