package terracraft.mixin.statuseffect;

import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import terracraft.extensions.MobEffectInstanceExtensions;

@Mixin(MobEffectInstance.class)
public abstract class MobEffectInstanceMixin implements MobEffectInstanceExtensions {

	@Shadow
	private int duration;
	@Shadow
	private MobEffectInstance hiddenEffect;

	@Unique
	@Override
	public void terracraft$setDuration(int duration) {
		// Recursively set duration for hidden effects
		if (this.hiddenEffect != null) {
			((MobEffectInstanceExtensions) this.hiddenEffect).terracraft$setDuration(duration);
		}

		this.duration = duration;
	}
}
