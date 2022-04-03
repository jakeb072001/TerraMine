package terracraft.mixin.item.cloudinabottle;

import terracraft.common.init.Items;
import terracraft.common.init.SoundEvents;
import terracraft.common.item.curio.belt.CloudInABottleItem;
import terracraft.extensions.LivingEntityExtensions;
import terracraft.common.trinkets.TrinketsHelper;
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
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
	protected abstract void jumpFromGround();

	@Shadow
	public abstract boolean onClimbable();

	@Unique
	@Override
	public void artifacts$doubleJump() {
		// Call the vanilla jump method
		// We modify the behaviour of this method in multiple places if artifacts$isDoubleJumping is true
		this.isDoubleJumping = true;
		this.jumpFromGround();

		// Play jump sound
		LivingEntity self = (LivingEntity) (Object) this;
		SoundEvent jumpSound = TrinketsHelper.isEquipped(Items.WHOOPEE_CUSHION, self) ?
				terracraft.common.init.SoundEvents.FART : SoundEvents.DOUBLE_JUMP;
		this.playSound(jumpSound, 1, 0.9F + self.getRandom().nextFloat() * 0.2F);

		// Reset fall distance for fall damage
		this.fallDistance = 0;

		// Send double jump packet to server if we're on the client
		if (this.level.isClientSide) {
			sendDoubleJumpPacket();
		}

		this.isDoubleJumping = false;
	}

	// This code is extracted because the mixin fails to apply with the usage of client-side only classes
	@Unique
	@Environment(EnvType.CLIENT)
	private static void sendDoubleJumpPacket() {
		ClientPlayNetworking.send(CloudInABottleItem.C2S_DOUBLE_JUMPED_ID, PacketByteBufs.empty());
	}

	@ModifyVariable(method = "causeFallDamage", ordinal = 0, at = @At("HEAD"))
	private float reduceFallDistance(float fallDistance) {
		// FIXME: this probably also works if we didn't double jump, intended?
		if (TrinketsHelper.isEquipped(Items.CLOUD_IN_A_BOTTLE, (LivingEntity) (Object) this)) {
			fallDistance = Math.max(0, fallDistance - 3);
		}

		return fallDistance;
	}

	@SuppressWarnings("ConstantConditions")
	@Inject(method = "aiStep", at = @At("HEAD"))
	private void invokeDoubleJump(CallbackInfo info) {
		LivingEntity self = (LivingEntity) (Object) this;
		jumpWasReleased |= !this.jumping;

		if ((this.isOnGround() || this.onClimbable()) && !this.isInWater()) {
			this.hasDoubleJumped = false;
		}

		boolean flying = self instanceof Player player && player.getAbilities().flying;
		if (this.jumping && this.jumpWasReleased && !this.isInWater() && !this.isOnGround() && !this.isPassenger()
				&& !this.hasDoubleJumped && !flying && TrinketsHelper.isEquipped(Items.CLOUD_IN_A_BOTTLE, self)) {
			this.artifacts$doubleJump();
			this.hasDoubleJumped = true;
		}
	}

	@Inject(method = "jumpFromGround", at = @At("RETURN"))
	private void setJumpReleased(CallbackInfo info) {
		this.jumpWasReleased = false;
	}

	// The next three injectors modify the behaviour of the vanilla jump
	// method if the artifacts$isDoubleJumping field is set to true

	@Inject(method = "getJumpPower", cancellable = true, at = @At("HEAD"))
	private void increaseBaseDoubleJumpVelocity(CallbackInfoReturnable<Float> info) {
		if (this.isDoubleJumping) {
			info.setReturnValue(0.5f);
		}
	}

	@ModifyArg(method = "jumpFromGround", index = 1, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setDeltaMovement(DDD)V"))
	private double sprintingDoubleJumpUpwardVelocityMultiplier(double y) {
		return this.isDoubleJumping && this.isSprinting() ? y * 1.5 : y;
	}

	@ModifyConstant(method = "jumpFromGround", constant = @Constant(floatValue = 0.2f))
	private float sprintingDoubleJumpHorizontalVelocityMultiplier(float multiplier) {
		return this.isDoubleJumping ? 0.5f : multiplier;
	}
}
