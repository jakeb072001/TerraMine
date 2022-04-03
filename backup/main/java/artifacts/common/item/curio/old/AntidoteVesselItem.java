package terracraft.common.item.curio.old;

import terracraft.common.item.curio.TrinketArtifactItem;
import terracraft.extensions.MobEffectInstanceExtensions;
import terracraft.mixin.accessors.MobEffectAccessor;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class AntidoteVesselItem extends TrinketArtifactItem {

    @Override
	protected void curioTick(LivingEntity livingEntity, ItemStack stack) {
		// Reduce duration of all negative status effects to 80
		livingEntity.getActiveEffectsMap().forEach((effect, instance) -> {
			if (!effect.isInstantenous() && ((MobEffectAccessor) effect).getCategory() != MobEffectCategory.BENEFICIAL && instance.getDuration() > 80) {
				((MobEffectInstanceExtensions) instance).artifacts$setDuration(80);
			}
		});
	}

	@Override
	public SoundInfo getEquipSoundInfo() {
		return new SoundInfo(SoundEvents.BOTTLE_FILL);
	}
}
