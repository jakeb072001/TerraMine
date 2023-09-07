package terramine.mixin.item.accessories.bundleofballoons;

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
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.common.init.ModComponents;
import terramine.common.init.ModItems;
import terramine.common.item.accessories.hands.BundleOfBalloonsItem;
import terramine.common.misc.AccessoriesHelper;
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
	private boolean hasTripleJumped = false;
	@Unique
	private boolean hasQuadrupleJumped = false;

	public LivingEntityMixin(EntityType<?> type, Level world) {
		super(type, world);
	}

	@Shadow
	protected abstract void jumpFromGround();

	@Shadow
	public abstract boolean onClimbable();

	// This code is extracted because the mixin fails to apply with the usage of client-side only classes
	@Unique
	@Environment(EnvType.CLIENT)
	private static void sendDoubleJumpPacket() {
		ClientPlayNetworking.send(BundleOfBalloonsItem.C2S_QUADRUPLE_JUMPED_ID, PacketByteBufs.empty());
	}

	@ModifyVariable(method = "causeFallDamage", ordinal = 0, at = @At("HEAD"), argsOnly = true)
	private float reduceFallDistance(float fallDistance) {
		if (AccessoriesHelper.isEquipped(ModItems.BUNDLE_OF_BALLOONS, (LivingEntity) (Object) this)) {
			fallDistance = Math.max(0, fallDistance - 3);
		}

		return fallDistance;
	}

	@SuppressWarnings("ConstantConditions")
	@Inject(method = "aiStep", at = @At("HEAD"))
	private void invokeQuadrupleJump(CallbackInfo info) {
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
				&& !flying && AccessoriesHelper.isEquipped(ModItems.BUNDLE_OF_BALLOONS, player)) {
			if (!this.hasDoubleJumped || !this.hasTripleJumped || !this.hasQuadrupleJumped) {
				this.terramine$doubleJump();
				if (this.hasDoubleJumped) {
					if (this.hasTripleJumped) {
						this.hasQuadrupleJumped = true;
					}
					this.hasTripleJumped = true;
				}
				this.hasDoubleJumped = true;
			}
		}
	}

	@Unique
	private void resetJumpStatus(Player player) {
		if ((this.isOnGround() || this.onClimbable() || ModComponents.MOVEMENT_ORDER.get(player).getWallJumped()) && !this.isInWater()) {
			this.hasDoubleJumped = false;
			this.hasTripleJumped = false;
			this.hasQuadrupleJumped = false;
			this.jumpWasReleased = false;
			ModComponents.MOVEMENT_ORDER.get(player).setCloudFinished(false);
		}
	}

	@Inject(method = "jumpFromGround", at = @At("RETURN"))
	private void setJumpReleased(CallbackInfo info) {
		this.jumpWasReleased = false;
	}
}
