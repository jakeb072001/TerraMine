package terramine.common.item.accessories.hands;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import terramine.common.item.accessories.AccessoryTerrariaItem;

public class ShinyRedBalloonItem extends AccessoryTerrariaItem {

    @Override
	public MobEffectInstance getPermanentEffect() {
		return new MobEffectInstance(MobEffects.JUMP, 20, 1, true, false);
	}
}
