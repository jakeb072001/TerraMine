package terracraft.mixin.item.heliumflamingo.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terracraft.common.init.ModComponents;
import terracraft.common.init.ModItems;
import terracraft.common.trinkets.TrinketsHelper;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin {

	@Shadow @Final protected Minecraft minecraft;
	@Unique private boolean wasSprintKeyDown;
	@Unique private boolean wasSprintingOnGround;
	@Unique private boolean hasTouchedGround;

	@Inject(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/Input;tick(Z)V", shift = At.Shift.AFTER))
	private void handleAirSwimmingInput(CallbackInfo info) {
		LocalPlayer self = (LocalPlayer) (Object) this;
		boolean isSprintKeyDown = this.minecraft.options.keySprint.isDown();

		ModComponents.SWIM_ABILITIES.maybeGet(self).ifPresent(swimAbilities -> {
			if (!swimAbilities.isSwimming()) {
				if (self.isOnGround()) {
					hasTouchedGround = true;
				} else if (!swimAbilities.isSwimming()
						&& swimAbilities.getSwimTime() >= 0
						&& TrinketsHelper.isEquipped(ModItems.HELIUM_FLAMINGO, self)
						&& (self.isSwimming()
						|| isSprintKeyDown
						&& !wasSprintKeyDown
						&& !wasSprintingOnGround
						&& hasTouchedGround
						&& !self.isOnGround()
						&& (!self.isInWater() || swimAbilities.isSinking())
						&& !self.isFallFlying()
						&& !self.getAbilities().flying
						&& !self.isPassenger())) {
					swimAbilities.setSwimming(true);
					swimAbilities.syncSwimming();
					hasTouchedGround = false;
				}
			} else if (self.getAbilities().flying) {
				swimAbilities.setSwimming(false);
				swimAbilities.syncSwimming();
				hasTouchedGround = true;
			}
		});

		wasSprintKeyDown = isSprintKeyDown;
		if (!isSprintKeyDown) {
			wasSprintingOnGround = false;
		} else if (self.isOnGround()) {
			wasSprintingOnGround = true;
		}
	}
}
