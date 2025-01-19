package terramine.mixin.item.accessories.umbrella.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.common.item.equipment.UmbrellaItem;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> extends EntityRenderer<T, S> implements RenderLayerParent<S, M> {

	@Shadow
	protected M model;

	@Unique
	private T livingEntity;

	protected LivingEntityRendererMixin(EntityRendererProvider.Context context) {
		super(context);
	}

	@Inject(method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V", at = @At("HEAD"))
	private void renderUmbrella(T livingEntity, LivingEntityRenderState livingEntityRenderState, float f, CallbackInfo ci) {
		this.livingEntity = livingEntity;
	}

	// todo: not working, unsure why
	@Inject(method = "render*", at = @At("HEAD"))
	private void renderUmbrella(S renderState, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo info) {
		boolean heldMainHand = UmbrellaItem.getHeldStatusForHand(livingEntity, InteractionHand.MAIN_HAND) == UmbrellaItem.HeldStatus.HELD_UP;
		boolean heldOffHand = UmbrellaItem.getHeldStatusForHand(livingEntity, InteractionHand.OFF_HAND) == UmbrellaItem.HeldStatus.HELD_UP;
		boolean rightHanded = renderState.mainArm == HumanoidArm.RIGHT;

		if (renderState instanceof HumanoidRenderState humanoidRenderState) {
			if (model instanceof HumanoidModel<?>) {
				HumanoidModel<HumanoidRenderState> humanoidModel = (HumanoidModel<HumanoidRenderState>) model;

				if ((heldMainHand && rightHanded) || (heldOffHand && !rightHanded)) {
					humanoidModel.poseRightArm(humanoidRenderState, HumanoidModel.ArmPose.THROW_SPEAR);
				}

				if ((heldMainHand && !rightHanded) || (heldOffHand && rightHanded)) {
					humanoidModel.poseLeftArm(humanoidRenderState, HumanoidModel.ArmPose.THROW_SPEAR);
				}
			}
		}
	}
}
