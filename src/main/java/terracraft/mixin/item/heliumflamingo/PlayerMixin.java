package terracraft.mixin.item.heliumflamingo;

import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terracraft.common.init.ModComponents;
import terracraft.common.init.ModItems;
import terracraft.common.init.ModSoundEvents;
import terracraft.common.item.curio.old.HeliumFlamingoItem;
import terracraft.common.trinkets.TrinketsHelper;

@Mixin(Player.class)
public abstract class PlayerMixin {

	@Inject(method = "tick", at = @At("TAIL"))
	private void stopAirSwimming(CallbackInfo info) {
		Player self = (Player) (Object) this;
		int maxFlightTime = HeliumFlamingoItem.MAX_FLIGHT_TIME;
		int rechargeTime = HeliumFlamingoItem.RECHARGE_TIME;

		ModComponents.SWIM_ABILITIES.maybeGet(self).ifPresent(swimAbilities -> {
			if (swimAbilities.isSwimming()) {
				if (!TrinketsHelper.isEquipped(ModItems.HELIUM_FLAMINGO, self)
						|| swimAbilities.getSwimTime() > maxFlightTime
						|| self.isInWater() && !self.isSwimming() && !swimAbilities.isSinking()
						|| (!self.isInWater() || swimAbilities.isSinking()) && self.isOnGround()) {
					swimAbilities.setSwimming(false);
					if (!self.isOnGround() && !self.isInWater()) {
						self.playSound(ModSoundEvents.POP, 0.5F, 0.75F);
					}
				}

				if (TrinketsHelper.isEquipped(ModItems.HELIUM_FLAMINGO, self) && !self.isEyeInFluid(FluidTags.WATER)) {
					// TODO: durability
					/*if (self.age % 20 == 0) {
						damageEquippedStacks(self);
					}*/

					//noinspection ConstantConditions TODO: config
					if (!self.getAbilities().invulnerable && maxFlightTime > 0) {
						swimAbilities.setSwimTime(swimAbilities.getSwimTime() + 1);
					}
				}
			} else if (swimAbilities.getSwimTime() < 0) {
				swimAbilities.setSwimTime(swimAbilities.getSwimTime() < -rechargeTime ? -rechargeTime : swimAbilities.getSwimTime() + 1);
			}
		});
	}
}
