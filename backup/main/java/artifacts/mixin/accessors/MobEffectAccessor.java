package terracraft.mixin.accessors;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MobEffect.class)
public interface MobEffectAccessor {

	@Accessor("category")
	MobEffectCategory getCategory();
}
