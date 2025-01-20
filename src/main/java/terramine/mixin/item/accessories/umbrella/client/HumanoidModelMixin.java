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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
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

	// todo: pose doesn't seem to be working for zombie, i think i need to inject into AnimationUtils animateZombieArms and manually change xRot after its set
	@Inject(method = "getArmPose", at = @At("RETURN"), cancellable = true)
	private void renderUmbrella(T humanoidRenderState, HumanoidArm humanoidArm, CallbackInfoReturnable<HumanoidModel.ArmPose> cir) {
		if (((EntityRenderStateExtensions)humanoidRenderState).terrariaCraft$getLivingEntity() instanceof LivingEntity livingEntity) {
			boolean mainHandHeldUp = UmbrellaItem.getHeldStatusForHand(livingEntity, InteractionHand.MAIN_HAND) == UmbrellaItem.HeldStatus.HELD_UP;
			boolean offHandHeldUp = UmbrellaItem.getHeldStatusForHand(livingEntity, InteractionHand.OFF_HAND) == UmbrellaItem.HeldStatus.HELD_UP;
			boolean isRightHanded = humanoidRenderState.mainArm == HumanoidArm.RIGHT;

			boolean isRightArm = humanoidArm == HumanoidArm.RIGHT;
			boolean umbrellaMatchesRight = (mainHandHeldUp && isRightHanded) || (offHandHeldUp && !isRightHanded);
			boolean umbrellaMatchesLeft = (mainHandHeldUp && !isRightHanded) || (offHandHeldUp && isRightHanded);

			if ((isRightArm && umbrellaMatchesRight) || (!isRightArm && umbrellaMatchesLeft)) {
				cir.setReturnValue(HumanoidModel.ArmPose.THROW_SPEAR);
			}
		}
	}
}
