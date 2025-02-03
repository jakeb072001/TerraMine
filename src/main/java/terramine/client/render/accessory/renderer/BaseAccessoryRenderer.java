package terramine.client.render.accessory.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import terramine.TerraMine;
import terramine.client.render.AccessoryRenderer;
import terramine.client.render.accessory.model.NecklaceModel;
import terramine.common.item.dye.BasicDye;
import terramine.extensions.PlayerStorages;

public class BaseAccessoryRenderer implements AccessoryRenderer {

    private final ResourceLocation texture;
    private final HumanoidModel<HumanoidRenderState> model;

    public BaseAccessoryRenderer(String texturePath, HumanoidModel<HumanoidRenderState> model) {
        this(TerraMine.id(String.format("textures/entity/accessory/%s.png", texturePath)), model);
    }

    public BaseAccessoryRenderer(ResourceLocation texture, HumanoidModel<HumanoidRenderState> model) {
        this.texture = texture;
        this.model = model;
    }

    protected ResourceLocation getTexture() {
        return texture;
    }

    protected HumanoidModel<HumanoidRenderState> getModel() {
        return model;
    }

    @Override
    public final void render(ItemStack itemStack, int dyeSlot, int realSlot, EntityModel<? extends HumanoidRenderState> contextModel, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, Player player, float f, float g) {
        if (!((PlayerStorages) player).getSlotVisibility(realSlot)) {
            return;
        }
        HumanoidModel<HumanoidRenderState> model = getModel();

        model.setupAnim(new PlayerRenderState());
        AccessoryRenderer.followBodyRotations(player, model);
        render(poseStack, multiBufferSource, player, dyeSlot, light, itemStack.hasFoil());
    }

    protected void render(PoseStack poseStack, MultiBufferSource buffer, Player player, int slot, int light, boolean hasFoil) {
        RenderType renderType = model.renderType(getTexture());
        VertexConsumer vertexBuilder = ItemRenderer.getFoilBuffer(buffer, renderType, false, hasFoil);
        if (model instanceof NecklaceModel) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
        if (((PlayerStorages)player).getTerrariaInventory().getItem(slot + 14).getItem() instanceof BasicDye dye) {
            model.renderToBuffer(poseStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, dye.getColourInt());
            return;
        }
        model.renderToBuffer(poseStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, -1);
    }
}
