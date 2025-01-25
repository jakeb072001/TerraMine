package terramine.mixin.item.accessories.umbrella.client;

import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.common.item.equipment.UmbrellaItem;
import terramine.extensions.EntityRenderStateExtensions;

@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin<T extends HumanoidRenderState> extends EntityModel<T> implements ArmedModel, HeadedModel {

	@Final
	@Shadow
	public ModelPart rightArm;
	@Final
	@Shadow
	public ModelPart leftArm;

	@Shadow public abstract void poseLeftArm(T humanoidRenderState, HumanoidModel.ArmPose armPose);

	@Shadow public abstract void poseRightArm(T humanoidRenderState, HumanoidModel.ArmPose armPose);

	protected HumanoidModelMixin(ModelPart modelPart) {
		super(modelPart);
	}

	@Inject(
			method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V",
			at = @At(value = "FIELD",
					target = "Lnet/minecraft/client/model/HumanoidModel;rightLeg:Lnet/minecraft/client/model/geom/ModelPart;",
					ordinal = 0)
	)
	private void reduceHandSwing(T humanoidRenderState, CallbackInfo ci) {
		if (((EntityRenderStateExtensions)humanoidRenderState).terrariaCraft$getLivingEntity() instanceof LivingEntity livingEntity) {
			boolean mainHandHeldUp = UmbrellaItem.getHeldStatusForHand(livingEntity, InteractionHand.MAIN_HAND) == UmbrellaItem.HeldStatus.HELD_UP;
			boolean offHandHeldUp = UmbrellaItem.getHeldStatusForHand(livingEntity, InteractionHand.OFF_HAND) == UmbrellaItem.HeldStatus.HELD_UP;
			boolean rightHanded = humanoidRenderState.mainArm == HumanoidArm.RIGHT;

			if ((mainHandHeldUp && rightHanded) || (offHandHeldUp && !rightHanded)) {
				this.rightArm.xRot /= 8;
			}

			if ((mainHandHeldUp && !rightHanded) || (offHandHeldUp && rightHanded)) {
				this.leftArm.xRot /= 8;
			}
		}
	}

	@Inject(method = "setupAttackAnimation", at = @At(value = "TAIL"))
	private void fixAttackAnim(T humanoidRenderState, float f, CallbackInfo ci) {
		if (((EntityRenderStateExtensions)humanoidRenderState).terrariaCraft$getLivingEntity() instanceof LivingEntity livingEntity) {
			boolean mainHandHeldUp = UmbrellaItem.getHeldStatusForHand(livingEntity, InteractionHand.MAIN_HAND) == UmbrellaItem.HeldStatus.HELD_UP;
			boolean offHandHeldUp = UmbrellaItem.getHeldStatusForHand(livingEntity, InteractionHand.OFF_HAND) == UmbrellaItem.HeldStatus.HELD_UP;
			boolean rightHanded = humanoidRenderState.mainArm == HumanoidArm.RIGHT;

			if ((mainHandHeldUp && rightHanded) || (offHandHeldUp && !rightHanded)) {
				this.rightArm.xRot = -this.rightArm.xRot;
			}

			if ((mainHandHeldUp && !rightHanded) || (offHandHeldUp && rightHanded)) {
				this.leftArm.xRot = -this.leftArm.xRot;
			}
		}
	}

	@Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/HumanoidModel;poseRightArm(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;Lnet/minecraft/client/model/HumanoidModel$ArmPose;)V", ordinal = 0))
	private void fixMainHandOverride(T humanoidRenderState, CallbackInfo ci) {
		if (((EntityRenderStateExtensions)humanoidRenderState).terrariaCraft$getLivingEntity() instanceof LivingEntity livingEntity) {
			boolean offHandHeldUp = UmbrellaItem.getHeldStatusForHand(livingEntity, InteractionHand.OFF_HAND) == UmbrellaItem.HeldStatus.HELD_UP;
			if (offHandHeldUp) {
				poseLeftArm(humanoidRenderState, HumanoidModel.ArmPose.THROW_SPEAR);
			}
		}
	}

	@Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/HumanoidModel;poseLeftArm(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;Lnet/minecraft/client/model/HumanoidModel$ArmPose;)V", ordinal = 0))
	private void fixOffHandOverride(T humanoidRenderState, CallbackInfo ci) {
		if (((EntityRenderStateExtensions)humanoidRenderState).terrariaCraft$getLivingEntity() instanceof LivingEntity livingEntity) {
			boolean mainHandHeldUp = UmbrellaItem.getHeldStatusForHand(livingEntity, InteractionHand.MAIN_HAND) == UmbrellaItem.HeldStatus.HELD_UP;
			if (mainHandHeldUp) {
				poseRightArm(humanoidRenderState, HumanoidModel.ArmPose.THROW_SPEAR);
			}
		}
	}

	@ModifyVariable(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V", at = @At("STORE"), ordinal = 0)
	private HumanoidModel.ArmPose renderUmbrellaLeftArm(HumanoidModel.ArmPose armPose, T humanoidRenderState) {
		return replaceArmPose(armPose, humanoidRenderState, false);
	}

	@ModifyVariable(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V", at = @At("STORE"), ordinal = 1)
	private HumanoidModel.ArmPose renderUmbrellaRightArm(HumanoidModel.ArmPose armPose, T humanoidRenderState) {
		return replaceArmPose(armPose, humanoidRenderState, true);
	}

	@Unique
	private HumanoidModel.ArmPose replaceArmPose(HumanoidModel.ArmPose armPose, T humanoidRenderState, boolean isRightArm) {
		if (((EntityRenderStateExtensions)humanoidRenderState).terrariaCraft$getLivingEntity() instanceof LivingEntity livingEntity) {
			boolean mainHandHeldUp = UmbrellaItem.getHeldStatusForHand(livingEntity, InteractionHand.MAIN_HAND) == UmbrellaItem.HeldStatus.HELD_UP;
			boolean offHandHeldUp = UmbrellaItem.getHeldStatusForHand(livingEntity, InteractionHand.OFF_HAND) == UmbrellaItem.HeldStatus.HELD_UP;
			boolean isRightHanded = humanoidRenderState.mainArm == HumanoidArm.RIGHT;

			boolean umbrellaMatchesRight = (mainHandHeldUp && isRightHanded) || (offHandHeldUp && !isRightHanded);
			boolean umbrellaMatchesLeft = (mainHandHeldUp && !isRightHanded) || (offHandHeldUp && isRightHanded);

			if ((isRightArm && umbrellaMatchesRight) || (!isRightArm && umbrellaMatchesLeft)) {
				return HumanoidModel.ArmPose.THROW_SPEAR;
			}
		}

		return armPose;
	}
}
