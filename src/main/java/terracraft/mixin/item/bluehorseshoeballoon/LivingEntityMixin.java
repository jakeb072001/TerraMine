package terracraft.mixin.item.bluehorseshoeballoon;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.world.damagesource.DamageSource;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terracraft.common.init.ModItems;
import terracraft.common.item.curio.belt.BlueHorseshoeBalloonItem;
import terracraft.common.trinkets.TrinketsHelper;
import terracraft.extensions.LivingEntityExtensions;

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

	@Inject(method = "causeFallDamage", cancellable = true, at = @At("HEAD"))
	private void cancelFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> info) {
		if (TrinketsHelper.isEquipped(ModItems.BLUE_HORSESHOE_BALLOON, (LivingEntity) (Object) this)) {
			info.setReturnValue(false);
		}
	}

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
		jumpWasReleased |= !this.jumping;

		if ((this.isOnGround() || this.onClimbable()) && !this.isInWater()) {
			this.hasDoubleJumped = false;
		}

		boolean flying = self instanceof Player player && player.getAbilities().flying;
		if (this.jumping && this.jumpWasReleased && !this.isInWater() && !this.isOnGround() && !this.isPassenger()
				&& !this.hasDoubleJumped && !flying && TrinketsHelper.isEquipped(ModItems.BLUE_HORSESHOE_BALLOON, self)) {
			this.terracraft$doubleJump();
			this.hasDoubleJumped = true;
		}
	}

	@Inject(method = "jumpFromGround", at = @At("RETURN"))
	private void setJumpReleased(CallbackInfo info) {
		this.jumpWasReleased = false;
	}
}
