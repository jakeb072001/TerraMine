package terramine.client.render.accessory.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3f;
import terramine.TerraMine;
import terramine.client.render.AccessoryRenderer;
import terramine.common.init.ModComponents;
import terramine.common.item.dye.BasicDye;
import terramine.extensions.PlayerStorages;

public class BaseAccessoryRenderer implements AccessoryRenderer {

    private final ResourceLocation texture;
    private final HumanoidModel<LivingEntity> model;

    public BaseAccessoryRenderer(String texturePath, HumanoidModel<LivingEntity> model) {
        this(TerraMine.id(String.format("textures/entity/accessory/%s.png", texturePath)), model);
    }

    public BaseAccessoryRenderer(ResourceLocation texture, HumanoidModel<LivingEntity> model) {
        this.texture = texture;
        this.model = model;
    }

    protected ResourceLocation getTexture() {
        return texture;
    }

    protected HumanoidModel<LivingEntity> getModel() {
        return model;
    }

    @Override
    public final void render(ItemStack itemStack, int dyeSlot, int realSlot, EntityModel<? extends LivingEntity> contextModel, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, Player player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!ModComponents.ACCESSORY_VISIBILITY.get(player).getSlotVisibility(realSlot)) {
            return;
        }
        HumanoidModel<LivingEntity> model = getModel();

        model.setupAnim(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        model.prepareMobModel(player, limbSwing, limbSwingAmount, partialTicks);
        AccessoryRenderer.followBodyRotations(player, model);
        render(poseStack, multiBufferSource, player, dyeSlot, light, itemStack.hasFoil());
    }

    protected void render(PoseStack matrixStack, MultiBufferSource buffer, Player player, int slot, int light, boolean hasFoil) {
        RenderType renderType = model.renderType(getTexture());
        VertexConsumer vertexBuilder = ItemRenderer.getFoilBuffer(buffer, renderType, false, hasFoil);
        if (((PlayerStorages)player).getTerrariaInventory().getItem(slot + 14).getItem() instanceof BasicDye dye) {
            Vector3f color = dye.getColour();
            model.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, color.x(), color.y(), color.z(), 1);
            return;
        }
        model.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }
}
