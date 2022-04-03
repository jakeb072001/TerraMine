package terracraft.common.item.curio.hands;

import dev.emi.trinkets.api.SlotReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import terracraft.common.init.ModComponents;
import terracraft.common.item.curio.TrinketTerrariaItem;

public class BandOfStarpowerItem extends TrinketTerrariaItem {

	@Override
	public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
		Player player = (Player)entity;
		ModComponents.MANA_HANDLER.get(player).addMaxMana(20);
	}

    @Override
	public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
		Player player = (Player)entity;
		ModComponents.MANA_HANDLER.get(player).addMaxMana(-20);
		ModComponents.MANA_HANDLER.get(player).addCurrentMana(-20);
	}
}
