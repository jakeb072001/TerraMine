package terramine.client.render.entity.renderer.projectiles.magic;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import terramine.TerraMine;
import terramine.client.render.entity.renderer.BillboardEntityRenderer;
import terramine.common.entity.projectiles.RainbowMissileEntity;

public class RainbowMissileRenderer extends BillboardEntityRenderer<RainbowMissileEntity> {
    private static final ResourceLocation TEXTURE = TerraMine.id("textures/entity/projectiles/magic/rainbow_rod.png");

    public RainbowMissileRenderer(EntityRendererProvider.Context context) {
        super(context, TEXTURE);
    }
}
