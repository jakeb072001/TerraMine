package terramine.client.render.entity.renderer.projectiles.magic;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import terramine.TerraMine;
import terramine.client.render.entity.renderer.BillboardEntityRenderer;
import terramine.common.entity.projectiles.FlamelashMissileEntity;

public class FlamelashMissileRenderer extends BillboardEntityRenderer<FlamelashMissileEntity> {
    private static final ResourceLocation TEXTURE = TerraMine.id("textures/entity/projectiles/magic/flamelash.png");

    public FlamelashMissileRenderer(EntityRendererProvider.Context context) {
        super(context, TEXTURE);
    }
}
