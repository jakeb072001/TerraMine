package terracraft.common.item.curio.belt;

import terracraft.common.item.curio.TrinketArtifactItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class ShinyRedBalloonItem extends TrinketArtifactItem {

    @Override
	public MobEffectInstance getPermanentEffect() {
		return new MobEffectInstance(MobEffects.JUMP, 20, 1, true, false);
	}
}
