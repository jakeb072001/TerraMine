package terracraft.mixin.statuseffect;

import terracraft.extensions.MobEffectInstanceExtensions;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(MobEffectInstance.class)
public abstract class MobEffectInstanceMixin implements MobEffectInstanceExtensions {

	@Shadow
	private int duration;
	@Shadow
	private MobEffectInstance hiddenEffect;

	@Unique
	@Override
	public void artifacts$setDuration(int duration) {
		// Recursively set duration for hidden effects
		if (this.hiddenEffect != null) {
			((MobEffectInstanceExtensions) this.hiddenEffect).artifacts$setDuration(duration);
		}

		this.duration = duration;
	}
}
