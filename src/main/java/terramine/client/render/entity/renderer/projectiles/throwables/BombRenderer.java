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
import terramine.client.render.entity.model.projectiles.throwables.BombModel;
import terramine.client.render.entity.states.TerrariaEntityRenderState;
import terramine.common.entity.projectiles.throwables.BombEntity;
import terramine.common.init.ModModelLayers;

public class BombRenderer extends EntityRenderer<BombEntity, TerrariaEntityRenderState> {

    private BombEntity bombEntity;
    private static final ResourceLocation TEXTURE = TerraMine.id("textures/item/weapons/throwables/bomb/bomb_lit.png");
    private static final ResourceLocation STICKY_TEXTURE = TerraMine.id("textures/item/weapons/throwables/bomb/sticky_bomb_lit.png");
    private static final ResourceLocation BOUNCY_TEXTURE = TerraMine.id("textures/item/weapons/throwables/bomb/bouncy_bomb_lit.png");
    protected final EntityModel<TerrariaEntityRenderState> model;

    public BombRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new BombModel(context.bakeLayer(ModModelLayers.BOMB));
    }

    @Override
    public void render(@NotNull TerrariaEntityRenderState renderState, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        super.render(renderState, poseStack, buffer, packedLight);
        poseStack.pushPose();
        this.model.setupAnim(renderState);
        poseStack.mulPose(Axis.YP.rotationDegrees(bombEntity.getYRot()));
        poseStack.mulPose(Axis.XN.rotationDegrees(bombEntity.getXRot() - 90));
        VertexConsumer vertexConsumer = buffer.getBuffer(this.model.renderType(this.getTextureLocation(renderState)));
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }

    @Override
    public @NotNull TerrariaEntityRenderState createRenderState() {
        return new TerrariaEntityRenderState();
    }

    @Override
    public void extractRenderState(BombEntity bombEntity, TerrariaEntityRenderState terrariaEntityRenderState, float f) {
        super.extractRenderState(bombEntity, terrariaEntityRenderState, f);
        this.bombEntity = bombEntity;
    }

    public ResourceLocation getTextureLocation(@NotNull TerrariaEntityRenderState renderState) {
        if (bombEntity.isSticky()) {
            return STICKY_TEXTURE;
        }
        if (bombEntity.isBouncy()) {
            return BOUNCY_TEXTURE;
        }
        return TEXTURE;
    }
}
