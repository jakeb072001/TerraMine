package terracraft.mixin.item.magiccuffs;

import terracraft.common.init.Components;
import terracraft.common.init.Items;
import terracraft.common.trinkets.TrinketsHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin {

	@Inject(method = "hurt", at = @At("TAIL"))
	private void onHurt(DamageSource damageSource, float f, CallbackInfoReturnable info) {
		Player player = (Player) (Object) this;
		if (!TrinketsHelper.isEquipped(Items.MAGIC_CUFFS, player)) {
			return;
		}
		if (!damageSource.equals(DamageSource.DROWN)) {
			if (!damageSource.equals(DamageSource.MAGIC)) {
				Components.MANA_HANDLER.get(player).addCurrentMana(((int) f) * 5);
			}
		}
	}
}
