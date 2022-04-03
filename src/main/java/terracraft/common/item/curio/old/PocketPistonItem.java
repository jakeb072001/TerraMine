package terracraft.common.item.curio.old;

import net.minecraft.sounds.SoundEvents;
import terracraft.common.item.curio.TrinketTerrariaItem;

public class PocketPistonItem extends TrinketTerrariaItem {

	@Override
	protected SoundInfo getEquipSoundInfo() {
		return new SoundInfo(SoundEvents.PISTON_EXTEND);
	}
}
