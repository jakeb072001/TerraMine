package terracraft.common.item.curio.old;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import terracraft.common.item.curio.TrinketTerrariaItem;

public class KittySlippersItem extends TrinketTerrariaItem {

    @Override
	protected SoundEvent getExtraHurtSound() {
		return SoundEvents.CAT_HURT;
	}

	@Override
	protected SoundInfo getEquipSoundInfo() {
		return new SoundInfo(SoundEvents.CAT_AMBIENT);
	}
}
