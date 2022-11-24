package terramine.client.render.entity.renderer.projectiles.magic;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import terramine.TerraMine;
import terramine.client.render.entity.model.projectiles.magic.LaserModel;
import terramine.common.entity.projectiles.LaserEntity;
import terramine.common.init.ModModelLayers;

public class LaserRenderer extends EntityRenderer<LaserEntity> {

    private static final ResourceLocation TEXTURE = TerraMine.id("textures/entity/projectiles/magic/laser.png");
    protected final EntityModel<LaserEntity> model;

    public LaserRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new LaserModel<>(context.bakeLayer(ModModelLayers.LASER));
    }

    @Override
    public void render(@NotNull LaserEntity entity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        poseStack.pushPose();

        this.model.setupAnim(entity, 0.0f, 0.0f, partialTicks, entity.getYRot(), entity.getXRot());
        poseStack.mulPose(Vector3f.YP.rotationDegrees(entity.getYRot()));
        poseStack.mulPose(Vector3f.XN.rotationDegrees(entity.getXRot()));
        VertexConsumer vertexConsumer = buffer.getBuffer(this.model.renderType(this.getTextureLocation(entity))).color(0xe0e0e0);
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 0.9f, 0.9f, 0.9f, 1.0f);

        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(@NotNull LaserEntity entity) {
        return TEXTURE;
    }
}
