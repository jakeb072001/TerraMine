package terracraft.common.item.curio.belt;

import terracraft.common.item.curio.TrinketArtifactItem;
import net.minecraft.sounds.SoundEvents;

public class AncientChiselItem extends TrinketArtifactItem {

	public static final float MINING_SPEED_INCREASE = 1.25f;

    @Override
	protected SoundInfo getEquipSoundInfo() {
		return new SoundInfo(SoundEvents.ARMOR_EQUIP_NETHERITE);
	}
}
