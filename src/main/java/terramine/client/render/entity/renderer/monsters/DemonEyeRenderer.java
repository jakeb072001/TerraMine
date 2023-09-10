package terramine.client.render.entity.renderer.monsters;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import terramine.TerraMine;
import terramine.client.render.entity.model.monsters.DemonEyeModel;
import terramine.common.entity.mobs.DemonEyeEntity;
import terramine.common.init.ModModelLayers;

@Environment(value=EnvType.CLIENT)
public class DemonEyeRenderer extends MobRenderer<DemonEyeEntity, DemonEyeModel<DemonEyeEntity>> {

    private static final ResourceLocation[] TEXTURE = {
            TerraMine.id("textures/entity/monsters/pre-hardmode/demon_eyes/demon_eye.png"),
            TerraMine.id("textures/entity/monsters/pre-hardmode/demon_eyes/demon_eye_cataract.png"),
            TerraMine.id("textures/entity/monsters/pre-hardmode/demon_eyes/demon_eye_dilated.png"),
            TerraMine.id("textures/entity/monsters/pre-hardmode/demon_eyes/demon_eye_green.png"),
            TerraMine.id("textures/entity/monsters/pre-hardmode/demon_eyes/demon_eye_purple.png"),
            TerraMine.id("textures/entity/monsters/pre-hardmode/demon_eyes/demon_eye_sleepy.png")
    };

    public DemonEyeRenderer(EntityRendererProvider.Context context) {
        super(context, new DemonEyeModel<>(context.bakeLayer(ModModelLayers.DEMON_EYE)), 0.45F);
    }

    @Override
    public void render(@NotNull DemonEyeEntity eye, float entityYaw, float partialTicks, @NotNull PoseStack matrixStack, @NotNull MultiBufferSource buffer, int packedLight) {
        super.render(eye, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(DemonEyeEntity entity) {
        int type = entity.getEntityData().get(DemonEyeEntity.typed_data);
        return TEXTURE[type];
    }

    @Override
    protected void setupRotations(@NotNull DemonEyeEntity entity, @NotNull PoseStack poseStack, float f, float g, float h) {
        super.setupRotations(entity, poseStack, f, g, h);
        poseStack.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));
    }
}
