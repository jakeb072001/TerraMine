package terracraft.common.item.curio.old;

import net.minecraft.sounds.SoundEvents;
import terracraft.common.events.LivingEntityAttackedCallback;
import terracraft.common.item.curio.TrinketTerrariaItem;

public abstract class PendantItem extends TrinketTerrariaItem {

	public PendantItem(LivingEntityAttackedCallback callback) {
        LivingEntityAttackedCallback.EVENT.register(callback);
	}

	@Override
	protected SoundInfo getEquipSoundInfo() {
		return new SoundInfo(SoundEvents.ARMOR_EQUIP_DIAMOND);
	}
}
