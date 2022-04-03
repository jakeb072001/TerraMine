package terracraft.common.item.curio.necklace;

import terracraft.common.item.curio.TrinketArtifactItem;
import net.minecraft.sounds.SoundEvents;

public class CrossNecklaceItem extends TrinketArtifactItem {

	public static final double HURT_RESISTANCE_MULTIPLIER = 3; // Hurt invuln is multiplied by this factor

	@Override
	public SoundInfo getEquipSoundInfo() {
		return new SoundInfo(SoundEvents.ARMOR_EQUIP_DIAMOND);
	}
}
