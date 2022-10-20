package terramine.client.render.entity.renderer.projectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import terramine.TerraMine;
import terramine.common.entity.MagicMissileEntity;

public class MagicMissileRenderer extends EntityRenderer<MagicMissileEntity> {

    private static final ResourceLocation TEXTURE = TerraMine.id("textures/entity/falling_star.png");

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
