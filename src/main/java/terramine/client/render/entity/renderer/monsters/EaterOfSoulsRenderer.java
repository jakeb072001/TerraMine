package terramine.client.render.entity.renderer.monsters;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import terramine.TerraMine;
import terramine.client.render.entity.model.monsters.EaterOfSoulsModel;
import terramine.common.entity.EaterOfSoulsEntity;
import terramine.common.init.ModModelLayers;

@Environment(value=EnvType.CLIENT)
public class EaterOfSoulsRenderer extends MobRenderer<EaterOfSoulsEntity, EaterOfSoulsModel<EaterOfSoulsEntity>> {

    private static final ResourceLocation TEXTURE = TerraMine.id("textures/entity/monsters/pre-hardmode/eater_of_souls/default.png");

    public EaterOfSoulsRenderer(EntityRendererProvider.Context context) {
        super(context, new EaterOfSoulsModel<>(context.bakeLayer(ModModelLayers.EATER_OF_SOULS)), 0.80F);
    }

    @Override
    public void render(@NotNull EaterOfSoulsEntity entity, float entityYaw, float partialTicks, @NotNull PoseStack matrixStack, @NotNull MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(@NotNull EaterOfSoulsEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void setupRotations(@NotNull EaterOfSoulsEntity entity, @NotNull PoseStack poseStack, float f, float g, float h) {
        super.setupRotations(entity, poseStack, f, g, h);
        poseStack.mulPose(Vector3f.XP.rotationDegrees(entity.getXRot()));
    }
}
