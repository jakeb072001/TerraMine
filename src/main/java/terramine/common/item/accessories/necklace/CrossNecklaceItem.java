package terramine.common.item.accessories.necklace;

import net.minecraft.sounds.SoundEvents;
import terramine.common.item.accessories.AccessoryTerrariaItem;

public class CrossNecklaceItem extends AccessoryTerrariaItem {

	public static final double HURT_RESISTANCE_MULTIPLIER = 3; // Hurt invuln is multiplied by this factor

	@Override
	public SoundInfo getEquipSoundInfo() {
		return new SoundInfo(SoundEvents.ARMOR_EQUIP_DIAMOND);
	}
}
