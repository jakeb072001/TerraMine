package terracraft.common.item.curio.old;

import terracraft.common.item.curio.TrinketArtifactItem;
import net.minecraft.sounds.SoundEvents;

public class PocketPistonItem extends TrinketArtifactItem {

	@Override
	protected SoundInfo getEquipSoundInfo() {
		return new SoundInfo(SoundEvents.PISTON_EXTEND);
	}
}
