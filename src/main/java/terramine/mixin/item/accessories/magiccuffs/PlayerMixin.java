package terramine.mixin.item.accessories.magiccuffs;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terramine.common.init.ModComponents;
import terramine.common.init.ModItems;
import terramine.common.misc.AccessoriesHelper;

@Mixin(Player.class)
public abstract class PlayerMixin extends Entity {

	public PlayerMixin(EntityType<?> entityType, Level level) {
		super(entityType, level);
	}

	@Inject(method = "hurt", at = @At("TAIL"))
	private void onHurt(DamageSource damageSource, float f, CallbackInfoReturnable<Boolean> cir) {
		Player player = (Player) (Object) this;
		if (!AccessoriesHelper.isEquipped(ModItems.MAGIC_CUFFS, player)) {
			return;
		}
		if (!damageSource.equals(damageSources().drown())) {
			if (!damageSource.equals(damageSources().magic())) {
				ModComponents.MANA_HANDLER.get(player).addCurrentMana(((int) f) * 5);
			}
		}
	}
}
