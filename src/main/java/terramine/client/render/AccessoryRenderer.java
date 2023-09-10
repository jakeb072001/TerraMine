package terramine.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface AccessoryRenderer {
    /**
     * Renders the Accessory
     *
     * @param stack The {@link ItemStack} for the Accessory being rendered
     * @param contextModel The model this Accessory is being rendered on
     */
    void render(ItemStack stack, int dyeSlot, int realSlot, EntityModel<? extends LivingEntity> contextModel,
                PoseStack poseStack, MultiBufferSource vertexConsumers, int light, Player player,
                float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw,
                float headPitch);

    /**
     * Rotates the rendering for the models based on the entity's poses and movements. This will do
     * nothing if the entity render object does not implement {@link LivingEntityRenderer} or if the
     * model does not implement {@link HumanoidModel}).
     *
     * @param entity The wearer of the accessory
     * @param model The model to align to the body movement
     */
    @SuppressWarnings("unchecked")
    static void followBodyRotations(final LivingEntity entity, final HumanoidModel<LivingEntity> model) {

        EntityRenderer<? super LivingEntity> render = Minecraft.getInstance()
                .getEntityRenderDispatcher().getRenderer(entity);

        if (render instanceof LivingEntityRenderer) {
            //noinspection unchecked
            LivingEntityRenderer<LivingEntity, EntityModel<LivingEntity>> livingRenderer =
                    (LivingEntityRenderer<LivingEntity, EntityModel<LivingEntity>>) render;
            EntityModel<LivingEntity> entityModel = livingRenderer.getModel();

            if (entityModel instanceof HumanoidModel) {
                HumanoidModel<LivingEntity> bipedModel = (HumanoidModel<LivingEntity>) entityModel;
                bipedModel.copyPropertiesTo(model);
            }
        }
    }

    /**
     * Translates the rendering context to the center of the player's face
     */
    static void translateToFace(PoseStack poseStack, PlayerModel<AbstractClientPlayer> model,
                                AbstractClientPlayer player, float headYaw, float headPitch) {

        if (player.isVisuallySwimming() || player.isFallFlying()) {
            poseStack.mulPose(Axis.ZP.rotationDegrees(model.head.zRot));
            poseStack.mulPose(Axis.YP.rotationDegrees(headYaw));
            poseStack.mulPose(Axis.XP.rotationDegrees(-45.0F));
        } else {

            if (player.isCrouching() && !model.riding) {
                poseStack.translate(0.0F, 0.25F, 0.0F);
            }
            poseStack.mulPose(Axis.YP.rotationDegrees(headYaw));
            poseStack.mulPose(Axis.XP.rotationDegrees(headPitch));
        }
        poseStack.translate(0.0F, -0.25F, -0.3F);
    }

    /**
     * Translates the rendering context to the center of the player's chest/torso segment
     */
    static void translateToChest(PoseStack poseStack, PlayerModel<AbstractClientPlayer> model,
                                 AbstractClientPlayer player) {

        if (player.isCrouching() && !model.riding && !player.isSwimming()) {
            poseStack.translate(0.0F, 0.2F, 0.0F);
            poseStack.mulPose(Axis.XP.rotation(model.body.xRot));
        }
        poseStack.mulPose(Axis.YP.rotation(model.body.yRot));
        poseStack.translate(0.0F, 0.4F, -0.16F);
    }

    /**
     * Translates the rendering context to the center of the bottom of the player's right arm
     */
    static void translateToRightArm(PoseStack poseStack, PlayerModel<AbstractClientPlayer> model,
                                    AbstractClientPlayer player) {

        if (player.isCrouching() && !model.riding && !player.isSwimming()) {
            poseStack.translate(0.0F, 0.2F, 0.0F);
        }
        poseStack.mulPose(Axis.YP.rotation(model.body.yRot));
        poseStack.translate(-0.3125F, 0.15625F, 0.0F);
        poseStack.mulPose(Axis.ZP.rotation(model.rightArm.zRot));
        poseStack.mulPose(Axis.YP.rotation(model.rightArm.yRot));
        poseStack.mulPose(Axis.XP.rotation(model.rightArm.xRot));
        poseStack.translate(-0.0625F, 0.625F, 0.0F);
    }

    /**
     * Translates the rendering context to the center of the bottom of the player's left arm
     */
    static void translateToLeftArm(PoseStack poseStack, PlayerModel<AbstractClientPlayer> model,
                                   AbstractClientPlayer player) {

        if (player.isCrouching() && !model.riding && !player.isSwimming()) {
            poseStack.translate(0.0F, 0.2F, 0.0F);
        }
        poseStack.mulPose(Axis.YP.rotation(model.body.yRot));
        poseStack.translate(0.3125F, 0.15625F, 0.0F);
        poseStack.mulPose(Axis.ZP.rotation(model.leftArm.zRot));
        poseStack.mulPose(Axis.YP.rotation(model.leftArm.yRot));
        poseStack.mulPose(Axis.XP.rotation(model.leftArm.xRot));
        poseStack.translate(0.0625F, 0.625F, 0.0F);
    }

    /**
     * Translates the rendering context to the center of the bottom of the player's right leg
     */
    static void translateToRightLeg(PoseStack poseStack, PlayerModel<AbstractClientPlayer> model,
                                    AbstractClientPlayer player) {

        if (player.isCrouching() && !model.riding && !player.isSwimming()) {
            poseStack.translate(0.0F, 0.0F, 0.25F);
        }
        poseStack.translate(-0.125F, 0.75F, 0.0F);
        poseStack.mulPose(Axis.ZP.rotation(model.rightLeg.zRot));
        poseStack.mulPose(Axis.YP.rotation(model.rightLeg.yRot));
        poseStack.mulPose(Axis.XP.rotation(model.rightLeg.xRot));
        poseStack.translate(0.0F, 0.75F, 0.0F);
    }

    /**
     * Translates the rendering context to the center of the bottom of the player's left leg
     */
    static void translateToLeftLeg(PoseStack poseStack, PlayerModel<AbstractClientPlayer> model,
                                   AbstractClientPlayer player) {

        if (player.isCrouching() && !model.riding && !player.isSwimming()) {
            poseStack.translate(0.0F, 0.0F, 0.25F);
        }
        poseStack.translate(0.125F, 0.75F, 0.0F);
        poseStack.mulPose(Axis.ZP.rotation(model.leftLeg.zRot));
        poseStack.mulPose(Axis.YP.rotation(model.leftLeg.yRot));
        poseStack.mulPose(Axis.XP.rotation(model.leftLeg.xRot));
        poseStack.translate(0.0F, 0.75F, 0.0F);
    }
}
