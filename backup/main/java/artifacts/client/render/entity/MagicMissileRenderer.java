package terracraft.client.render.entity;

import terracraft.Artifacts;
import terracraft.common.entity.MagicMissileEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class MagicMissileRenderer extends EntityRenderer<MagicMissileEntity> {

    private static final ResourceLocation TEXTURE = Artifacts.id("textures/entity/falling_star.png");

    public MagicMissileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(MagicMissileEntity missile, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        super.render(missile, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(MagicMissileEntity entity) {
        return TEXTURE;
    }
}
