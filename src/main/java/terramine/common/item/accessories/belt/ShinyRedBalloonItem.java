package terramine.common.item.accessories.belt;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import terramine.common.item.accessories.TrinketTerrariaItem;

public class ShinyRedBalloonItem extends TrinketTerrariaItem {

    @Override
	public MobEffectInstance getPermanentEffect() {
		return new MobEffectInstance(MobEffects.JUMP, 20, 1, true, false);
	}
}
