package terramine.extensions;

import net.minecraft.world.SimpleContainer;

public interface PlayerStorages {

	SimpleContainer getPiggyBankInventory();

	SimpleContainer getSafeInventory();

	void setPiggyBankInventory(SimpleContainer piggyBankInventory);

	void setSafeInventory(SimpleContainer safeInventory);
}
