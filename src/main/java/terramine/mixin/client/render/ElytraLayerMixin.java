package terramine.mixin.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.ElytraModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import terramine.common.item.armor.vanity.FamiliarVanity;
import terramine.common.item.dye.BasicDye;
import terramine.extensions.PlayerStorages;

@Mixin(ElytraLayer.class)
public abstract class ElytraLayerMixin<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    @Shadow @Final private ElytraModel<T> elytraModel;
    @Shadow @Final private static ResourceLocation WINGS_LOCATION;

    public ElytraLayerMixin(RenderLayerParent<T, M> renderLayerParent) {
        super(renderLayerParent);
    }

    @Redirect(method = "render*", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
    private boolean hideElytra(ItemStack itemStack, Item item, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, LivingEntity livingEntity, float f, float g, float h, float j, float k, float l) {
        if (livingEntity instanceof Player player) {
            if ((((PlayerStorages) player).getTerrariaInventory().getItem(25).getItem() instanceof FamiliarVanity)) {
                return false;
            }

            if (((PlayerStorages) player).getTerrariaInventory().getItem(29).getItem() instanceof BasicDye dye && itemStack.is(Items.ELYTRA)) {
                ResourceLocation resourceLocation;
                AbstractClientPlayer abstractClientPlayer = (AbstractClientPlayer)player;
                if (abstractClientPlayer.isElytraLoaded() && abstractClientPlayer.getElytraTextureLocation() != null) {
                    resourceLocation = abstractClientPlayer.getElytraTextureLocation();
                } else if (abstractClientPlayer.isCapeLoaded() && abstractClientPlayer.getCloakTextureLocation() != null && abstractClientPlayer.isModelPartShown(PlayerModelPart.CAPE)) {
                    resourceLocation = abstractClientPlayer.getCloakTextureLocation();
                } else {
                    resourceLocation = WINGS_LOCATION;
                }
                poseStack.pushPose();
                poseStack.translate(0.0, 0.0, 0.125);
                Vector3f colour = dye.getColour();
                this.getParentModel().copyPropertiesTo(this.elytraModel);
                this.elytraModel.setupAnim((T) player, f, g, j, k, l);
                VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(multiBufferSource, RenderType.armorCutoutNoCull(resourceLocation), false, itemStack.hasFoil());
                this.elytraModel.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, colour.x(), colour.y(), colour.z(), 1.0F);
                poseStack.popPose();
                return false;
            }
        }

        return itemStack.is(item);
    }
}