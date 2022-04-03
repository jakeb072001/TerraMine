package terracraft.common.item.curio.old;

import terracraft.common.item.curio.TrinketArtifactItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class ScarfOfInvisibilityItem extends TrinketArtifactItem {

    @Override
	public MobEffectInstance getPermanentEffect() {
		return new MobEffectInstance(MobEffects.INVISIBILITY, 20, 0, true, false);
	}
}
