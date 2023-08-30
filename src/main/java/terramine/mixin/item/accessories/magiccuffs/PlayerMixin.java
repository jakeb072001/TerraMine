package terramine.mixin.item.accessories.magiccuffs;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terramine.common.init.ModComponents;
import terramine.common.init.ModItems;
import terramine.common.misc.AccessoriesHelper;

@Mixin(Player.class)
public abstract class PlayerMixin {

	@Inject(method = "hurt", at = @At("TAIL"))
	private void onHurt(DamageSource damageSource, float f, CallbackInfoReturnable<Boolean> cir) {
		Player player = (Player) (Object) this;
		if (!AccessoriesHelper.isEquipped(ModItems.MAGIC_CUFFS, player)) {
			return;
		}
		if (!damageSource.equals(DamageSource.DROWN)) {
			if (!damageSource.equals(DamageSource.MAGIC)) {
				ModComponents.MANA_HANDLER.get(player).addCurrentMana(((int) f) * 5);
			}
		}
	}
}
