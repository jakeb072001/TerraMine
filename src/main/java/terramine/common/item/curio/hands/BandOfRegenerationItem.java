package terramine.common.item.curio.hands;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import terramine.common.init.ModItems;
import terramine.common.item.curio.TrinketTerrariaItem;
import terramine.common.trinkets.TrinketsHelper;

public class BandOfRegenerationItem extends TrinketTerrariaItem {

	/**
    @Override
	public MobEffectInstance getPermanentEffect() {
		return new MobEffectInstance(MobEffects.REGENERATION, 20, 0, true, false);
	}
	*/

	private int timer;

	@Override
	public void curioTick(LivingEntity player, ItemStack stack) {
		if (player != null && !TrinketsHelper.isEquipped(ModItems.CHARM_OF_MYTHS, player)) {
			timer += 1;
			if (timer >= 50) {
				player.heal(0.5f);
				timer = 0;
			}
		}
	}
}
