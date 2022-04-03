package terracraft.common.item.curio.head;

import terracraft.common.item.curio.TrinketArtifactItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class SnorkelItem extends TrinketArtifactItem {

    @Override
	public MobEffectInstance getPermanentEffect() {
		return new MobEffectInstance(MobEffects.WATER_BREATHING, 20, 0, true, false);
	}
}
