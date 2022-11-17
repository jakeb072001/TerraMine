package terramine.mixin.item.accessories.bluehorseshoeballoon;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.common.init.ModComponents;
import terramine.common.init.ModItems;
import terramine.common.item.accessories.belt.BlueHorseshoeBalloonItem;
import terramine.common.trinkets.TrinketsHelper;
import terramine.common.utility.equipmentchecks.WingsEquippedCheck;
import terramine.extensions.LivingEntityExtensions;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements LivingEntityExtensions {

	@Shadow
	protected boolean jumping;
	// Is entity double jumping in this tick
	@Unique
	private boolean isDoubleJumping = false;
	// Has entity released jump key since last jump
	@Unique
	private boolean jumpWasReleased = false;
	// Has entity double jumped during current airtime
	@Unique
	private boolean hasDoubleJumped = false;

	public LivingEntityMixin(EntityType<?> type, Level world) {
		super(type, world);
	}

	@Shadow
	public abstract boolean onClimbable();

	// This code is extracted because the mixin fails to apply with the usage of client-side only classes
	@Unique
	@Environment(EnvType.CLIENT)
	private static void sendDoubleJumpPacket() {
		ClientPlayNetworking.send(BlueHorseshoeBalloonItem.C2S_DOUBLE_JUMPED_ID, PacketByteBufs.empty());
	}

	@SuppressWarnings("ConstantConditions")
	@Inject(method = "aiStep", at = @At("HEAD"))
	private void invokeDoubleJump(CallbackInfo info) {
		LivingEntity self = (LivingEntity) (Object) this;
		if (self instanceof Player player) {
			if (WingsEquippedCheck.isEquipped(player)) {
				if (ModComponents.MOVEMENT_ORDER.get(player).getWingsFinished()) {
					doDoubleJump(player);
				}
			} else {
				doDoubleJump(player);
			}
			resetJumpStatus(player);
		}
	}

	@Unique
	private void doDoubleJump(Player player) {
		jumpWasReleased |= !this.jumping;

		boolean flying = player.getAbilities().flying;
		if (this.jumping && this.jumpWasReleased && !this.isInWater() && !this.isOnGround() && !this.isPassenger()
				&& !this.hasDoubleJumped && !flying && TrinketsHelper.isEquipped(ModItems.BLUE_HORSESHOE_BALLOON, player)
				&& !TrinketsHelper.isEquipped(ModItems.BUNDLE_OF_BALLOONS, player) && !TrinketsHelper.isEquipped(ModItems.CLOUD_IN_A_BOTTLE, player)
				&& !TrinketsHelper.isEquipped(ModItems.CLOUD_IN_A_BALLOON, player)) {
			this.terramine$doubleJump();
			this.hasDoubleJumped = true;
		}
	}

	@Unique
	private void resetJumpStatus(Player player) {
		if ((this.isOnGround() || this.onClimbable() || ModComponents.MOVEMENT_ORDER.get(player).getWallJumped()) && !this.isInWater()) {
			this.hasDoubleJumped = false;
			this.jumpWasReleased = false;
			ModComponents.MOVEMENT_ORDER.get(player).setCloudFinished(false);
		}
	}

	@Inject(method = "jumpFromGround", at = @At("RETURN"))
	private void setJumpReleased(CallbackInfo info) {
		this.jumpWasReleased = false;
	}
}
