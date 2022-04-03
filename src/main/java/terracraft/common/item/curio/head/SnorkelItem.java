package terracraft.common.item.curio.head;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import terracraft.common.item.curio.TrinketTerrariaItem;

public class SnorkelItem extends TrinketTerrariaItem {

    @Override
	public MobEffectInstance getPermanentEffect() {
		return new MobEffectInstance(MobEffects.WATER_BREATHING, 20, 0, true, false);
	}
}
