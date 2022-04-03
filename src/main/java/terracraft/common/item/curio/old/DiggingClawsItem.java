package terracraft.common.item.curio.old;

import net.minecraft.sounds.SoundEvents;
import terracraft.common.item.curio.TrinketTerrariaItem;

public class DiggingClawsItem extends TrinketTerrariaItem {

	public static final int NEW_BASE_MINING_LEVEL = 1;
	public static final float MINING_SPEED_INCREASE = 3.2f;

    @Override
	protected SoundInfo getEquipSoundInfo() {
		return new SoundInfo(SoundEvents.ARMOR_EQUIP_NETHERITE);
	}
}
