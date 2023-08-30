package terramine.client.render.accessory.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import terramine.TerraMine;
import terramine.client.render.AccessoryRenderer;
import terramine.client.render.accessory.model.ArmsModel;
import terramine.common.item.accessories.AccessoryTerrariaItem;
import terramine.common.misc.AccessoriesHelper;


public class GloveCurioRenderer implements AccessoryRenderer {

    private final ResourceLocation defaultTexture;
    private final ResourceLocation slimTexture;
    private final ArmsModel defaultModel;
    private final ArmsModel slimModel;

    public GloveCurioRenderer(String name, ArmsModel defaultModel, ArmsModel slimModel) {
        this(String.format("glove/%s/%s_default", name, name), String.format("glove/%s/%s_slim", name, name), defaultModel, slimModel);
    }

    public GloveCurioRenderer(String defaultTexturePath, String slimTexturePath, ArmsModel defaultModel, ArmsModel slimModel) {
        this.defaultTexture = TerraMine.id(String.format("textures/entity/accessory/%s.png", defaultTexturePath));
        this.slimTexture = TerraMine.id(String.format("textures/entity/accessory/%s.png", slimTexturePath));
        this.defaultModel = defaultModel;
        this.slimModel = slimModel;
    }

    protected ResourceLocation getTexture(boolean hasSlimArms) {
        return hasSlimArms ? slimTexture : defaultTexture;
    }

    protected ArmsModel getModel(boolean hasSlimArms) {
        return hasSlimArms ? slimModel : defaultModel;
    }

    protected static boolean hasSlimArms(Entity entity) {
        return entity instanceof AbstractClientPlayer player && player.getModelName().equals("slim");
    }

    @Override
    public final void render(ItemStack stack, EntityModel<? extends LivingEntity> contextModel, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, Player player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!AccessoriesHelper.areCosmeticsEnabled(stack)) {
            return;
        }

        boolean hasSlimArms = hasSlimArms(player);
        ArmsModel model = getModel(hasSlimArms);

        model.setupAnim(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        model.prepareMobModel(player, limbSwing, limbSwingAmount, partialTicks);
        AccessoryRenderer.followBodyRotations(player, model);

        if (stack.getItem() instanceof AccessoryTerrariaItem item) {
            if (item.isBothHands()) {
                renderArm(model, poseStack, multiBufferSource, player.getMainArm(), light, hasSlimArms, stack.hasFoil());
                renderArm(model, poseStack, multiBufferSource, player.getMainArm().getOpposite(), light, hasSlimArms, stack.hasFoil());
            }
        }
        renderArm(model, poseStack, multiBufferSource, player.getMainArm(), light, hasSlimArms, stack.hasFoil());
    }

    protected void renderArm(ArmsModel model, PoseStack matrixStack, MultiBufferSource buffer, HumanoidArm handSide, int light, boolean hasSlimArms, boolean hasFoil) {
        RenderType renderType = model.renderType(getTexture(hasSlimArms));
        VertexConsumer vertexBuilder = ItemRenderer.getFoilBuffer(buffer, renderType, false, hasFoil);
        model.renderArm(handSide, matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }

    public final void renderFirstPersonArm(PoseStack matrixStack, MultiBufferSource buffer, int light, AbstractClientPlayer player, HumanoidArm side, boolean hasFoil) {
        if (!player.isSpectator()) {
            boolean hasSlimArms = hasSlimArms(player);
            ArmsModel model = getModel(hasSlimArms);

            ModelPart arm = side == HumanoidArm.LEFT ? model.leftArm : model.rightArm;

            model.setAllVisible(false);
            arm.visible = true;

            model.crouching = false;
            model.attackTime = model.swimAmount = 0;
            model.setupAnim(player, 0, 0, 0, 0, 0);
            arm.xRot = 0;

            renderFirstPersonArm(model, arm, matrixStack, buffer, light, hasSlimArms, hasFoil);
        }
    }

    protected void renderFirstPersonArm(ArmsModel model, ModelPart arm, PoseStack matrixStack, MultiBufferSource buffer, int light, boolean hasSlimArms, boolean hasFoil) {
        RenderType renderType = model.renderType(getTexture(hasSlimArms));
        VertexConsumer builder = ItemRenderer.getFoilBuffer(buffer, renderType, false, hasFoil);
        arm.render(matrixStack, builder, light, OverlayTexture.NO_OVERLAY);
    }
}
