package terracraft.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import terracraft.TerraCraft;
import terracraft.client.render.entity.model.FallingStarModel;
import terracraft.common.entity.FallingStarEntity;
import terracraft.common.init.ModModelLayers;

public class FallingStarRenderer extends MobRenderer<FallingStarEntity, FallingStarModel> {

    private static final ResourceLocation TEXTURE = TerraCraft.id("textures/entity/falling_star.png");

    public FallingStarRenderer(EntityRendererProvider.Context context) {
        super(context, new FallingStarModel(context.bakeLayer(ModModelLayers.FALLING_STAR)), 0.45F);
    }

    @Override
    public void render(FallingStarEntity star, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        super.render(star, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(FallingStarEntity entity) {
        return TEXTURE;
    }
}
