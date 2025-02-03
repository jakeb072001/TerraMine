package terramine.client.render.entity.renderer.projectiles.magic;

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
import terramine.client.render.entity.model.projectiles.magic.LaserModel;
import terramine.client.render.entity.states.TerrariaEntityRenderState;
import terramine.common.entity.projectiles.LaserEntity;
import terramine.common.init.ModModelLayers;

public class LaserRenderer extends EntityRenderer<LaserEntity, TerrariaEntityRenderState> {

    private LaserEntity laserEntity;
    private static final ResourceLocation TEXTURE = TerraMine.id("textures/entity/projectiles/magic/laser.png");
    protected final EntityModel<TerrariaEntityRenderState> model;

    public LaserRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new LaserModel<>(context.bakeLayer(ModModelLayers.LASER));
    }

    @Override
    public void render(@NotNull TerrariaEntityRenderState entity, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        super.render(entity, poseStack, buffer, packedLight);
        poseStack.pushPose();

        this.model.setupAnim(entity);
        poseStack.mulPose(Axis.YP.rotationDegrees(laserEntity.getYRot()));
        poseStack.mulPose(Axis.XN.rotationDegrees(laserEntity.getXRot()));
        VertexConsumer vertexConsumer = buffer.getBuffer(this.model.renderType(this.getTextureLocation(entity))).setColor(0xe0e0e0);
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();
    }

    @Override
    public @NotNull TerrariaEntityRenderState createRenderState() {
        return new TerrariaEntityRenderState();
    }

    @Override
    public void extractRenderState(LaserEntity laserEntity, TerrariaEntityRenderState terrariaEntityRenderState, float f) {
        super.extractRenderState(laserEntity, terrariaEntityRenderState, f);
        this.laserEntity = laserEntity;
    }

    public ResourceLocation getTextureLocation(@NotNull TerrariaEntityRenderState entity) {
        return TEXTURE;
    }
}
