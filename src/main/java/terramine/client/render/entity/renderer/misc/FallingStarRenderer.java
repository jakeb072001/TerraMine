package terramine.client.render.entity.renderer.misc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import terramine.TerraMine;
import terramine.client.render.entity.model.misc.FallingStarModel;
import terramine.common.entity.FallingStarEntity;
import terramine.common.init.ModModelLayers;

public class FallingStarRenderer extends EntityRenderer<FallingStarEntity> {

    private static final ResourceLocation TEXTURE = TerraMine.id("textures/entity/falling_star.png");
    protected final EntityModel<FallingStarEntity> model;

    public FallingStarRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new FallingStarModel<>(context.bakeLayer(ModModelLayers.FALLING_STAR));
    }

    @Override
    public void render(@NotNull FallingStarEntity entity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        poseStack.pushPose();
        this.model.setupAnim(entity, 0.0f, 0.0f, partialTicks, entity.getYRot(), entity.getXRot());
        VertexConsumer vertexConsumer = buffer.getBuffer(this.model.renderType(this.getTextureLocation(entity)));
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(@NotNull FallingStarEntity entity) {
        return TEXTURE;
    }
}
