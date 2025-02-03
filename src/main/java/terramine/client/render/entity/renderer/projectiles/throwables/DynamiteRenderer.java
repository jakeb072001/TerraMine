package terramine.client.render.entity.renderer.projectiles.throwables;

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
import terramine.client.render.entity.model.projectiles.throwables.DynamiteModel;
import terramine.client.render.entity.states.TerrariaEntityRenderState;
import terramine.common.entity.projectiles.throwables.DynamiteEntity;
import terramine.common.init.ModModelLayers;

public class DynamiteRenderer extends EntityRenderer<DynamiteEntity, TerrariaEntityRenderState> {

    private DynamiteEntity dynamiteEntity;
    private static final ResourceLocation TEXTURE = TerraMine.id("textures/item/weapons/throwables/dynamite/dynamite_lit.png");
    private static final ResourceLocation STICKY_TEXTURE = TerraMine.id("textures/item/weapons/throwables/dynamite/sticky_dynamite_lit.png");
    private static final ResourceLocation BOUNCY_TEXTURE = TerraMine.id("textures/item/weapons/throwables/dynamite/bouncy_dynamite_lit.png");
    protected final EntityModel<TerrariaEntityRenderState> model;

    public DynamiteRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new DynamiteModel(context.bakeLayer(ModModelLayers.DYNAMITE));
    }

    @Override
    public void render(@NotNull TerrariaEntityRenderState renderState, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        super.render(renderState, poseStack, buffer, packedLight);
        poseStack.pushPose();
        this.model.setupAnim(renderState);
        poseStack.mulPose(Axis.YP.rotationDegrees(dynamiteEntity.getYRot()));
        poseStack.mulPose(Axis.XN.rotationDegrees(dynamiteEntity.getXRot() - 180));
        VertexConsumer vertexConsumer = buffer.getBuffer(this.model.renderType(this.getTextureLocation(renderState)));
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }

    @Override
    public @NotNull TerrariaEntityRenderState createRenderState() {
        return new TerrariaEntityRenderState();
    }

    @Override
    public void extractRenderState(DynamiteEntity dynamiteEntity, TerrariaEntityRenderState terrariaEntityRenderState, float f) {
        super.extractRenderState(dynamiteEntity, terrariaEntityRenderState, f);
        this.dynamiteEntity = dynamiteEntity;
    }

    public ResourceLocation getTextureLocation(@NotNull TerrariaEntityRenderState renderState) {
        if (dynamiteEntity.isSticky()) {
            return STICKY_TEXTURE;
        }
        if (dynamiteEntity.isBouncy()) {
            return BOUNCY_TEXTURE;
        }
        return TEXTURE;
    }
}
