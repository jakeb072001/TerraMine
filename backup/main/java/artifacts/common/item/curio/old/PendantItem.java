package terracraft.common.item.curio.old;

import terracraft.common.events.LivingEntityAttackedCallback;
import terracraft.common.item.curio.TrinketArtifactItem;
import net.minecraft.sounds.SoundEvents;

public abstract class PendantItem extends TrinketArtifactItem {

	public PendantItem(LivingEntityAttackedCallback callback) {
        LivingEntityAttackedCallback.EVENT.register(callback);
	}

	@Override
	protected SoundInfo getEquipSoundInfo() {
		return new SoundInfo(SoundEvents.ARMOR_EQUIP_DIAMOND);
	}
}
