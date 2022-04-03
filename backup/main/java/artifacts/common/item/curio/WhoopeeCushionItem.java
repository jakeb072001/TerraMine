package terracraft.common.item.curio;

import terracraft.common.init.SoundEvents;

public class WhoopeeCushionItem extends TrinketArtifactItem {

	@Override
	protected SoundInfo getEquipSoundInfo() {
		return new SoundInfo(SoundEvents.FART);
	}
}
