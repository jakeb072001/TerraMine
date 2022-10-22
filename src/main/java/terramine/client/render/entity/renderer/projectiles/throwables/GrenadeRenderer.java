package terramine.client.render.entity.renderer.projectiles.throwables;

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
import terramine.client.render.entity.model.throwables.GrenadeModel;
import terramine.common.entity.throwables.GrenadeEntity;
import terramine.common.init.ModModelLayers;

public class GrenadeRenderer extends EntityRenderer<GrenadeEntity> {

    private static final ResourceLocation TEXTURE = TerraMine.id("textures/item/weapons/throwables/grenade/grenade.png");
    private static final ResourceLocation STICKY_TEXTURE = TerraMine.id("textures/item/weapons/throwables/grenade/sticky_grenade.png");
    private static final ResourceLocation BOUNCY_TEXTURE = TerraMine.id("textures/item/weapons/throwables/grenade/bouncy_grenade.png");
    protected final EntityModel<GrenadeEntity> model;

    public GrenadeRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new GrenadeModel(context.bakeLayer(ModModelLayers.GRENADE));
    }

    @Override
    public void render(@NotNull GrenadeEntity entity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        poseStack.pushPose();
        this.model.setupAnim(entity, 0.0f, 0.0f, 0.0f, entity.getYRot(), entity.getXRot());
        VertexConsumer vertexConsumer = buffer.getBuffer(this.model.renderType(this.getTextureLocation(entity)));
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(@NotNull GrenadeEntity entity) {
        if (entity.isSticky()) {
            return STICKY_TEXTURE;
        }
        if (entity.isBouncy()) {
            return BOUNCY_TEXTURE;
        }
        return TEXTURE;
    }
}
