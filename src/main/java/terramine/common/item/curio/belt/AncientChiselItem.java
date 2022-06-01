package terramine.common.item.curio.belt;

import net.minecraft.sounds.SoundEvents;
import terramine.common.item.curio.TrinketTerrariaItem;

public class AncientChiselItem extends TrinketTerrariaItem {

    @Override
	protected SoundInfo getEquipSoundInfo() {
		return new SoundInfo(SoundEvents.ARMOR_EQUIP_NETHERITE);
	}
}
