package terracraft.common.item.curio.hands;

import terracraft.common.item.curio.TrinketArtifactItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class BandOfRegenerationItem extends TrinketArtifactItem {

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
