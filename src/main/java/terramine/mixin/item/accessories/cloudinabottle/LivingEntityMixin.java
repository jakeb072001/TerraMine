package terramine.mixin.item.accessories.cloudinabottle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.sounds.SoundEvent;
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
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terramine.common.init.ModComponents;
import terramine.common.init.ModItems;
import terramine.common.init.ModSoundEvents;
import terramine.common.item.accessories.belt.CloudInABottleItem;
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

	@Unique
	private int quadJumped = 0;

	public LivingEntityMixin(EntityType<?> type, Level world) {
		super(type, world);
	}

	@Shadow
	protected abstract void jumpFromGround();

	@Shadow
	public abstract boolean onClimbable();

	@Unique
	@Override
	public void terramine$doubleJump() {
		LivingEntity self = (LivingEntity) (Object) this;
		if (self instanceof Player player) {
			if (WingsEquippedCheck.isEquipped(player) && !ModComponents.MOVEMENT_ORDER.get(player).getWingsFinished()) {
				return;
			}
			// Call the vanilla jump method
			// We modify the behaviour of this method in multiple places if terramine$isDoubleJumping is true
			this.isDoubleJumping = true;
			this.jumpFromGround();

			// Play jump sound
			SoundEvent jumpSound = TrinketsHelper.isEquipped(ModItems.WHOOPEE_CUSHION, self) ?
					ModSoundEvents.FART : ModSoundEvents.DOUBLE_JUMP;
			this.playSound(jumpSound, 1, 0.9F + self.getRandom().nextFloat() * 0.2F);

			// Reset fall distance for fall damage
			this.resetFallDistance();

			// Send double jump packet to server if we're on the client
			if (this.level.isClientSide) {
				sendDoubleJumpPacket();
			}

			this.isDoubleJumping = false;

			// Controls order of movement accessories, allows rocket boots after double jump
			if (TrinketsHelper.isEquipped(ModItems.BUNDLE_OF_BALLOONS, player)) {
				quadJumped++;
				if (quadJumped == 3) {
					ModComponents.MOVEMENT_ORDER.get(player).setCloudFinished(true);
					quadJumped = 0;
				}
			} else {
				ModComponents.MOVEMENT_ORDER.get(player).setCloudFinished(true);
			}
		}
	}

	// This code is extracted because the mixin fails to apply with the usage of client-side only classes
	@Unique
	@Environment(EnvType.CLIENT)
	private static void sendDoubleJumpPacket() {
		ClientPlayNetworking.send(CloudInABottleItem.C2S_DOUBLE_JUMPED_ID, PacketByteBufs.empty());
	}

	@ModifyVariable(method = "causeFallDamage", ordinal = 0, at = @At("HEAD"), argsOnly = true)
	private float reduceFallDistance(float fallDistance) {
		if (TrinketsHelper.isEquipped(ModItems.CLOUD_IN_A_BOTTLE, (LivingEntity) (Object) this)) {
			fallDistance = Math.max(0, fallDistance - 3);
		}

		return fallDistance;
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
				&& !this.hasDoubleJumped && !flying && TrinketsHelper.isEquipped(ModItems.CLOUD_IN_A_BOTTLE, player)
				&& !TrinketsHelper.isEquipped(ModItems.BUNDLE_OF_BALLOONS, player)) {
			this.terramine$doubleJump();
			this.hasDoubleJumped = true;
		}
	}

	@Unique
	private void resetJumpStatus(Player player) {
		if ((this.isOnGround() || this.onClimbable() || ModComponents.MOVEMENT_ORDER.get(player).getWallJumped()) && !this.isInWater()) {
			quadJumped = 0;
			this.hasDoubleJumped = false;
			this.jumpWasReleased = false;
			ModComponents.MOVEMENT_ORDER.get(player).setCloudFinished(false);
		}
	}

	@Inject(method = "jumpFromGround", at = @At("RETURN"))
	private void setJumpReleased(CallbackInfo info) {
		this.jumpWasReleased = false;
	}

	@Inject(method = "getJumpPower", cancellable = true, at = @At("HEAD"))
	private void increaseBaseDoubleJumpVelocity(CallbackInfoReturnable<Float> info) {
		if (this.isDoubleJumping) {
			info.setReturnValue(0.5f);
		}
	}
}
