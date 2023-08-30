package terramine.mixin.item.accessories.crossnecklace;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import terramine.common.init.ModItems;
import terramine.common.item.accessories.necklace.CrossNecklaceItem;
import terramine.common.misc.AccessoriesHelper;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	// todo: make work alongside artifacts instead of disabling when installed using TerraMineMixinPlugin
	// todo: use @WrapOperation to get working with Artifacts (hopefully)

	/**
	 * Extends the amount of ticks of vulnerability
	 */
	@ModifyConstant(method = "hurt", constant = @Constant(intValue = 20, ordinal = 0))
	private int longerInvulnerability(int original) {
		if (AccessoriesHelper.isEquipped(ModItems.CROSS_NECKLACE, (LivingEntity) (Object) this)) {
			// Invulnerability is determined by timeUntilRegen > 10, so we subtract this amount before applying our multiplier
			return (int) ((original - 10) * CrossNecklaceItem.HURT_RESISTANCE_MULTIPLIER + 10);
		}

		return original;
	}
}