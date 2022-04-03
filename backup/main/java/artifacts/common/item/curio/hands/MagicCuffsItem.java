package terracraft.common.item.curio.hands;

import terracraft.common.init.Components;
import terracraft.common.item.curio.TrinketArtifactItem;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class MagicCuffsItem extends TrinketArtifactItem {

	@Override
	public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
		Player player = (Player)entity;
		Components.MANA_HANDLER.get(player).addMaxMana(20);
	}

    @Override
	public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
		Player player = (Player)entity;
		Components.MANA_HANDLER.get(player).addMaxMana(-20);
		Components.MANA_HANDLER.get(player).addCurrentMana(-20);
	}
}
