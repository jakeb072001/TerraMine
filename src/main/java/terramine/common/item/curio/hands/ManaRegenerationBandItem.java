package terramine.common.item.curio.hands;

import dev.emi.trinkets.api.SlotReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import terramine.common.init.ModComponents;
import terramine.common.item.curio.TrinketTerrariaItem;

public class ManaRegenerationBandItem extends TrinketTerrariaItem {

	@Override
	public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
		Player player = (Player)entity;
		ModComponents.MANA_HANDLER.get(player).addMaxMana(20);
		ModComponents.MANA_HANDLER.get(player).setManaBonus(25, true);
	}

    @Override
	public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
		Player player = (Player)entity;
		ModComponents.MANA_HANDLER.get(player).addMaxMana(-20);
		ModComponents.MANA_HANDLER.get(player).addCurrentMana(-20);
		ModComponents.MANA_HANDLER.get(player).setManaBonus(0, false);
	}
}
