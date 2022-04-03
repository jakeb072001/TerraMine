package terracraft.mixin.item.universalattractor;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terracraft.common.init.ModComponents;

@Mixin(Player.class)
public abstract class PlayerMixin {

	@Inject(method = "drop(Lnet/minecraft/world/item/ItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity;", at = @At("TAIL"))
	private void setDroppedFlag(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> info) {
		ModComponents.DROPPED_ITEM_ENTITY.maybeGet(info.getReturnValue()).ifPresent(component -> component.set(true));
	}
}
