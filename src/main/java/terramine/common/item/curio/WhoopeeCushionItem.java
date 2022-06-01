package terramine.common.item.curio;

import terramine.common.init.ModSoundEvents;

public class WhoopeeCushionItem extends TrinketTerrariaItem {

	@Override
	protected SoundInfo getEquipSoundInfo() {
		return new SoundInfo(ModSoundEvents.FART);
	}
}
