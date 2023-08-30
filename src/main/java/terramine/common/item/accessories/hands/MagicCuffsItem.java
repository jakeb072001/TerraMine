package terramine.common.item.accessories.hands;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import terramine.common.init.ModComponents;
import terramine.common.item.accessories.AccessoryTerrariaItem;

public class MagicCuffsItem extends AccessoryTerrariaItem {

	@Override
	public void onEquip(ItemStack stack, Player player) {
		ModComponents.MANA_HANDLER.get(player).addMaxMana(20);
	}

    @Override
	public void onUnequip(ItemStack stack, Player player) {
		ModComponents.MANA_HANDLER.get(player).addMaxMana(-20);
		ModComponents.MANA_HANDLER.get(player).addCurrentMana(-20);
	}

	@Override
	public boolean isBothHands() {
		return true;
	}

	@Override
	public boolean isGlove() {
		return true;
	}
}
