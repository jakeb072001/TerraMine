package terracraft.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import terracraft.TerraCraft;
import terracraft.client.render.entity.model.DemonEyeModel;
import terracraft.common.entity.DemonEyeEntity;
import terracraft.common.init.ModModelLayers;

@Environment(value=EnvType.CLIENT)
public class DemonEyeRenderer extends MobRenderer<DemonEyeEntity, DemonEyeModel> {

    private static final ResourceLocation[] TEXTURE = {
            TerraCraft.id("textures/entity/demoneyes/demon_eye.png"),
            TerraCraft.id("textures/entity/demoneyes/demon_eye_cataract.png"),
            TerraCraft.id("textures/entity/demoneyes/demon_eye_dilated.png"),
            TerraCraft.id("textures/entity/demoneyes/demon_eye_green.png"),
            TerraCraft.id("textures/entity/demoneyes/demon_eye_purple.png"),
            TerraCraft.id("textures/entity/demoneyes/demon_eye_sleepy.png")
    };

    public DemonEyeRenderer(EntityRendererProvider.Context context) {
        super(context, new DemonEyeModel(context.bakeLayer(ModModelLayers.DEMON_EYE)), 0.45F);
    }

    @Override
    public void render(DemonEyeEntity eye, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        super.render(eye, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(DemonEyeEntity entity) {
        int type = entity.getEntityData().get(DemonEyeEntity.typed_data);
        return TEXTURE[type];
    }
}
