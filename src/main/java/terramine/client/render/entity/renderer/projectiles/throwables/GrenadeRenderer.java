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
import terramine.client.render.entity.model.projectiles.throwables.GrenadeModel;
import terramine.client.render.entity.states.TerrariaEntityRenderState;
import terramine.common.entity.projectiles.throwables.GrenadeEntity;
import terramine.common.init.ModModelLayers;

public class GrenadeRenderer extends EntityRenderer<GrenadeEntity, TerrariaEntityRenderState> {

    private GrenadeEntity grenadeEntity;
    private static final ResourceLocation TEXTURE = TerraMine.id("textures/item/weapons/throwables/grenade/grenade.png");
    private static final ResourceLocation STICKY_TEXTURE = TerraMine.id("textures/item/weapons/throwables/grenade/sticky_grenade.png");
    private static final ResourceLocation BOUNCY_TEXTURE = TerraMine.id("textures/item/weapons/throwables/grenade/bouncy_grenade.png");
    protected final EntityModel<TerrariaEntityRenderState> model;

    public GrenadeRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new GrenadeModel(context.bakeLayer(ModModelLayers.GRENADE));
    }

    @Override
    public void render(@NotNull TerrariaEntityRenderState renderState, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        super.render(renderState, poseStack, buffer, packedLight);
        poseStack.pushPose();
        this.model.setupAnim(renderState);
        poseStack.mulPose(Axis.YP.rotationDegrees(grenadeEntity.getYRot()));
        poseStack.mulPose(Axis.XN.rotationDegrees(grenadeEntity.getXRot() - 180));
        VertexConsumer vertexConsumer = buffer.getBuffer(this.model.renderType(this.getTextureLocation(renderState)));
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }

    @Override
    public @NotNull TerrariaEntityRenderState createRenderState() {
        return new TerrariaEntityRenderState();
    }

    @Override
    public void extractRenderState(GrenadeEntity grenadeEntity, TerrariaEntityRenderState terrariaEntityRenderState, float f) {
        super.extractRenderState(grenadeEntity, terrariaEntityRenderState, f);
        this.grenadeEntity = grenadeEntity;
    }

    public ResourceLocation getTextureLocation(@NotNull TerrariaEntityRenderState renderState) {
        if (grenadeEntity.isSticky()) {
            return STICKY_TEXTURE;
        }
        if (grenadeEntity.isBouncy()) {
            return BOUNCY_TEXTURE;
        }
        return TEXTURE;
    }
}
