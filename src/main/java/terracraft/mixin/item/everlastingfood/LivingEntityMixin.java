package terracraft.mixin.item.everlastingfood;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import terracraft.common.item.EverlastingFoodItem;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	@ModifyArgs(method = "eat", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V"))
	private void cancelDecrement(Args args, Level level, ItemStack stack) {
		if (stack.getItem() instanceof EverlastingFoodItem) {
			args.set(0, 0);
		}
	}
}
