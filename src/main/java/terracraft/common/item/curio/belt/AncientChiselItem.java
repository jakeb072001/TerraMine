package terracraft.common.item.curio.belt;

import net.minecraft.sounds.SoundEvents;
import terracraft.common.item.curio.TrinketTerrariaItem;

public class AncientChiselItem extends TrinketTerrariaItem {

    @Override
	protected SoundInfo getEquipSoundInfo() {
		return new SoundInfo(SoundEvents.ARMOR_EQUIP_NETHERITE);
	}
}
