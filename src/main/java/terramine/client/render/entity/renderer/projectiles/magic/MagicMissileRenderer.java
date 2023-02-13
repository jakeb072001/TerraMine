package terramine.client.render.entity.renderer.projectiles.magic;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import terramine.TerraMine;
import terramine.client.render.entity.renderer.BillboardEntityRenderer;
import terramine.common.entity.projectiles.MagicMissileEntity;

public class MagicMissileRenderer extends BillboardEntityRenderer<MagicMissileEntity> {
    private static final ResourceLocation TEXTURE = TerraMine.id("textures/entity/projectiles/magic/magic_missile.png");

    public MagicMissileRenderer(EntityRendererProvider.Context context) {
        super(context, TEXTURE);
    }
}
