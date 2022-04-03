package terracraft.common.item.curio.old;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import terracraft.common.item.curio.TrinketTerrariaItem;

public class NightVisionGogglesItem extends TrinketTerrariaItem {

    @Override
	public MobEffectInstance getPermanentEffect() {
		return new MobEffectInstance(MobEffects.NIGHT_VISION, 20, 0, true, false);
	}
}
