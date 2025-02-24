package terramine.mixin.item.accessories.wormscarf;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import terramine.common.init.ModItems;
import terramine.common.misc.AccessoriesHelper;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	@SuppressWarnings("all")
	@ModifyVariable(method = "hurtServer", at = @At("HEAD"), argsOnly = true)
	private float reduceDamage(float originalDamage) {
		if (AccessoriesHelper.isEquipped(ModItems.WORM_SCARF, (LivingEntity) (Object) this)) {
			return originalDamage - (originalDamage * 0.17F); // Reduce by 17%
		}

		return originalDamage;
	}
}
