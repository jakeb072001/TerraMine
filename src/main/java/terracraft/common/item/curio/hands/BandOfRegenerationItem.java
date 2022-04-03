package terracraft.common.item.curio.hands;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import terracraft.common.item.curio.TrinketTerrariaItem;

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
		if (player != null) {
			timer += 1;
			if (timer >= 50) {
				player.heal(0.5f);
				timer = 0;
			}
		}
	}
}
