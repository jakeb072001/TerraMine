package terracraft.mixin.accessors;

import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(DamageSource.class)
public interface DamageSourceAccessor {

	// TODO: Temporary fix for https://github.com/SpongePowered/Mixin/issues/430
	@Invoker("setIsFire")
	DamageSource artifacts$callSetIsFire();
}
