package terramine.client.render.accessory.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.joml.Vector3f;
import terramine.TerraMine;
import terramine.client.render.RenderTypes;
import terramine.common.item.dye.BasicDye;
import terramine.extensions.PlayerStorages;

public class GlowingAccessoryRenderer extends BaseAccessoryRenderer {

    private final ResourceLocation glowTexture;

    public GlowingAccessoryRenderer(String name, HumanoidModel<LivingEntity> model) {
        super(String.format("%s/%s", name, name), model);
        this.glowTexture = TerraMine.id(String.format("textures/entity/accessory/%s/%s_glow.png", name, name));
    }

    private ResourceLocation getGlowTexture() {
        return glowTexture;
    }

    @Override
    protected void render(PoseStack poseStack, MultiBufferSource multiBufferSource, Player player, int slot, int light, boolean hasFoil) {
        super.render(poseStack, multiBufferSource, player, slot, light, hasFoil);
        RenderType renderType = RenderTypes.unlit(getGlowTexture());
        VertexConsumer builder = ItemRenderer.getFoilBuffer(multiBufferSource, renderType, false, hasFoil);
        if (((PlayerStorages)player).getTerrariaInventory().getItem(slot + 14).getItem() instanceof BasicDye dye) {
            Vector3f color = dye.getColour();
            getModel().renderToBuffer(poseStack, builder, LightTexture.pack(15, 15), OverlayTexture.NO_OVERLAY, color.x(), color.y(), color.z(), 1);
            return;
        }
        getModel().renderToBuffer(poseStack, builder, LightTexture.pack(15, 15), OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }
}
