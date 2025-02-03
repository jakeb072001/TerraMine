package terramine.client.render.entity.renderer.mobs.prehardmode;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import terramine.TerraMine;
import terramine.client.render.entity.model.mobs.prehardmode.DemonEyeModel;
import terramine.client.render.entity.states.TerrariaLivingEntityRenderState;
import terramine.common.entity.mobs.prehardmode.DemonEyeEntity;
import terramine.common.init.ModModelLayers;

@Environment(value=EnvType.CLIENT)
public class DemonEyeRenderer extends MobRenderer<DemonEyeEntity, TerrariaLivingEntityRenderState, DemonEyeModel<TerrariaLivingEntityRenderState>> {

    private DemonEyeEntity demonEyeEntity;
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
    public @NotNull TerrariaLivingEntityRenderState createRenderState() {
        return new TerrariaLivingEntityRenderState();
    }

    @Override
    public void extractRenderState(DemonEyeEntity demonEyeEntity, TerrariaLivingEntityRenderState terrariaLivingEntityRenderState, float f) {
        super.extractRenderState(demonEyeEntity, terrariaLivingEntityRenderState, f);
        this.demonEyeEntity = demonEyeEntity;
    }

    @Override
    public void render(@NotNull TerrariaLivingEntityRenderState eye, @NotNull PoseStack matrixStack, @NotNull MultiBufferSource buffer, int packedLight) {
        super.render(eye, matrixStack, buffer, packedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(TerrariaLivingEntityRenderState entity) {
        int type = demonEyeEntity.getEntityData().get(DemonEyeEntity.typed_data);
        return TEXTURE[type];
    }

    @Override
    protected void scale(TerrariaLivingEntityRenderState terrariaLivingEntityRenderState, PoseStack poseStack) {
        float f = 1.5f;
        poseStack.scale(f, f, f);
        poseStack.translate(0f, -0.15f, -0.2f);
    }
}
