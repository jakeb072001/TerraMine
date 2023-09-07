package terramine.common.item.accessories.hands;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import terramine.common.init.ModItems;
import terramine.common.item.accessories.AccessoryTerrariaItem;
import terramine.common.misc.AccessoriesHelper;

public class BandOfRegenerationItem extends AccessoryTerrariaItem {

	/**
    @Override
	public MobEffectInstance getPermanentEffect() {
		return new MobEffectInstance(MobEffects.REGENERATION, 20, 0, true, false);
	}
	*/

	private int timer;

	@Override
	public void curioTick(Player player, ItemStack stack) {
		if (player != null && !AccessoriesHelper.isEquipped(ModItems.CHARM_OF_MYTHS, player)) {
			timer += 1;
			if (timer >= 50) {
				player.heal(0.5f);
				timer = 0;
			}
		}
	}

	@Override
	public boolean isGlove() {
		return true;
	}
}
