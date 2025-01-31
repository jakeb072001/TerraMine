package terramine.mixin.client.render;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import terramine.common.item.accessories.ShieldAccessoryLikeItem;
import terramine.common.item.dye.BasicDye;
import terramine.common.utility.Utilities;
import terramine.extensions.EntityRenderStateExtensions;
import terramine.extensions.PlayerStorages;

@Mixin(PlayerItemInHandLayer.class)
public class PlayerItemInHandLayerMixin<S extends PlayerRenderState, M extends EntityModel<S> & ArmedModel & HeadedModel> extends ItemInHandLayer<S, M> {

    public PlayerItemInHandLayerMixin(RenderLayerParent<S, M> renderLayerParent) {
        super(renderLayerParent);
    }

    @WrapMethod(method = "renderArmWithItem(Lnet/minecraft/client/renderer/entity/state/ArmedEntityRenderState;Lnet/minecraft/client/renderer/item/ItemStackRenderState;Lnet/minecraft/world/entity/HumanoidArm;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V")
    private void shieldDye(ArmedEntityRenderState armedEntityRenderState, ItemStackRenderState itemStackRenderState, HumanoidArm humanoidArm, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, Operation<Void> original) {
        if (((EntityRenderStateExtensions) armedEntityRenderState).terrariaCraft$getLivingEntity() instanceof Player player && humanoidArm.equals(armedEntityRenderState.mainArm.getOpposite())) {
            if (!itemStackRenderState.isEmpty()) {
                vanityShield(player, itemStackRenderState);

                Item checkItem = player.getOffhandItem().getItem();
                if (checkItem instanceof ShieldItem || checkItem instanceof ShieldAccessoryLikeItem) {
                    if (((PlayerStorages) player).getTerrariaInventory().getItem(22).getItem() instanceof BasicDye dye) {
                        poseStack.pushPose();
                        this.getParentModel().translateToHand(humanoidArm, poseStack);
                        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
                        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
                        boolean bl = humanoidArm == HumanoidArm.LEFT;
                        poseStack.translate((float)(bl ? -1 : 1) / 16.0F, 0.125F, -0.625F);
                        Utilities.renderItemCustomDye(itemStackRenderState, poseStack, multiBufferSource, i, OverlayTexture.NO_OVERLAY, dye.getColourInt());
                        poseStack.popPose();
                        return;
                    }
                }
            }
        }

        original.call(armedEntityRenderState, itemStackRenderState, humanoidArm, poseStack, multiBufferSource, i);
    }

    @Unique
    private void vanityShield(Player player, ItemStackRenderState itemStackRenderState) {
        Item checkItem = player.getUseItem().getItem();
        if (checkItem == player.getOffhandItem().getItem()) {
            if (checkItem instanceof ShieldItem || checkItem instanceof ShieldAccessoryLikeItem) {
                if (((PlayerStorages) player).getTerrariaInventory().getItem(21) != ItemStack.EMPTY) {
                    player.useItem = ((PlayerStorages) player).getTerrariaInventory().getItem(21);
                }
            }
        }
        checkItem = player.getOffhandItem().getItem();
        if (checkItem instanceof ShieldItem || checkItem instanceof ShieldAccessoryLikeItem) {
            if (((PlayerStorages) player).getTerrariaInventory().getItem(21) != ItemStack.EMPTY) {
                new ItemModelResolver(Minecraft.getInstance().getModelManager()).updateForLiving(itemStackRenderState, ((PlayerStorages) player).getTerrariaInventory().getItem(21), ItemDisplayContext.THIRD_PERSON_LEFT_HAND, true, player);
            }
        }
    }
}
