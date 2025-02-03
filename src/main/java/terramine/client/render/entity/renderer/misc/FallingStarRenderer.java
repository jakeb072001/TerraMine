package terramine.client.render.entity.renderer.misc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import terramine.TerraMine;
import terramine.client.render.entity.model.misc.FallingStarModel;
import terramine.client.render.entity.states.TerrariaEntityRenderState;
import terramine.common.entity.projectiles.FallingStarEntity;
import terramine.common.init.ModModelLayers;

public class FallingStarRenderer extends EntityRenderer<FallingStarEntity, TerrariaEntityRenderState> {

    private static final ResourceLocation TEXTURE = TerraMine.id("textures/entity/falling_star.png");
    protected final EntityModel<TerrariaEntityRenderState> model;
    private FallingStarEntity fallingStarEntity;

    public FallingStarRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new FallingStarModel<>(context.bakeLayer(ModModelLayers.FALLING_STAR));
    }

    @Override
    public void extractRenderState(FallingStarEntity fallingStarEntity, TerrariaEntityRenderState terrariaEntityRenderState, float f) {
        super.extractRenderState(fallingStarEntity, terrariaEntityRenderState, f);
        this.fallingStarEntity = fallingStarEntity;
    }

    @Override
    public void render(@NotNull TerrariaEntityRenderState renderState, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        super.render(renderState, poseStack, buffer, packedLight);
        poseStack.pushPose();
        this.model.setupAnim(renderState);
        poseStack.mulPose(Axis.YP.rotationDegrees(fallingStarEntity.getYRot() - 90));
        poseStack.mulPose(Axis.XN.rotationDegrees(fallingStarEntity.getXRot() - 125));
        VertexConsumer vertexConsumer = buffer.getBuffer(this.model.renderType(this.getTextureLocation(renderState)));
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }

    @Override
    public @NotNull TerrariaEntityRenderState createRenderState() {
        return new TerrariaEntityRenderState();
    }

    public ResourceLocation getTextureLocation(@NotNull TerrariaEntityRenderState entity) {
        return TEXTURE;
    }
}
