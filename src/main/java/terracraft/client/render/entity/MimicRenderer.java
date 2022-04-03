package terracraft.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import terracraft.TerraCraft;
import terracraft.client.render.entity.model.MimicModel;
import terracraft.common.entity.MimicEntity;
import terracraft.common.init.ModModelLayers;

public class MimicRenderer extends MobRenderer<MimicEntity, MimicModel> {

    private static final ResourceLocation TEXTURE = TerraCraft.id("textures/entity/mimic.png");

    public MimicRenderer(EntityRendererProvider.Context context) {
        super(context, new MimicModel(context.bakeLayer(ModModelLayers.MIMIC)), 0.45F);
        addLayer(new MimicChestLayer(this, context.getModelSet()));
    }

    @Override
    public void render(MimicEntity mimic, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        super.render(mimic, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(MimicEntity entity) {
        return TEXTURE;
    }
}
