package terramine.client.render.entity.renderer.projectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import terramine.TerraMine;
import terramine.common.entity.RainbowMissileEntity;

public class RainbowMissileRenderer extends EntityRenderer<RainbowMissileEntity> {

    private static final ResourceLocation TEXTURE = TerraMine.id("textures/entity/falling_star.png");

    public RainbowMissileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(RainbowMissileEntity missile, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        super.render(missile, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(RainbowMissileEntity entity) {
        return TEXTURE;
    }
}
