package terramine.mixin.item.accessories.umbrella.client;

import net.minecraft.client.Minecraft;
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
import terramine.common.item.equipment.UmbrellaItem;

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

	// Target is unresolved because method owner is a generic T
	// Seems to work fine, but has failed to apply once or twice in dev (in a fresh runtime)
	@Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/HumanoidModel;setupAttackAnimation(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;F)V"))
	private void reduceHandSwing(T humanoidRenderState, CallbackInfo ci) {
		boolean heldMainHand = UmbrellaItem.getHeldStatusForHand(humanoidRenderState.getMainHandItem(), humanoidRenderState.isUsingItem, humanoidRenderState.useItemHand, InteractionHand.MAIN_HAND) == UmbrellaItem.HeldStatus.HELD_UP;
		// can't get the offhand item for some reason, so just use left hand for now
		boolean heldOffHand = UmbrellaItem.getHeldStatusForHand(humanoidRenderState.leftHandItem, humanoidRenderState.isUsingItem, humanoidRenderState.useItemHand, InteractionHand.OFF_HAND) == UmbrellaItem.HeldStatus.HELD_UP;
		boolean rightHanded = humanoidRenderState.mainArm == HumanoidArm.RIGHT;

		if ((heldMainHand && rightHanded) || (heldOffHand && !rightHanded)) {
			this.rightArm.xRot /= 8;
		}

		if ((heldMainHand && !rightHanded) || (heldOffHand && rightHanded)) {
			this.leftArm.xRot /= 8;
		}
	}
}
