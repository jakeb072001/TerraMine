package terracraft.common.item.curio;

import terracraft.common.init.ModSoundEvents;

public class WhoopeeCushionItem extends TrinketTerrariaItem {

	@Override
	protected SoundInfo getEquipSoundInfo() {
		return new SoundInfo(ModSoundEvents.FART);
	}
}
