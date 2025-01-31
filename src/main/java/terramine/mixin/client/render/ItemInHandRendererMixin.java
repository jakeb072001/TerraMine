package terramine.mixin.client.render;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import terramine.common.item.accessories.ShieldAccessoryLikeItem;
import terramine.common.item.dye.BasicDye;
import terramine.common.item.equipment.UmbrellaItem;
import terramine.common.utility.Utilities;
import terramine.extensions.EntityRenderStateExtensions;
import terramine.extensions.PlayerStorages;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {

    @WrapOperation(
            method = "renderHandsWithItems",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderArmWithItem(Lnet/minecraft/client/player/AbstractClientPlayer;FFLnet/minecraft/world/InteractionHand;FLnet/minecraft/world/item/ItemStack;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V")
    )
    private void vanityShield(ItemInHandRenderer instance, AbstractClientPlayer abstractClientPlayer, float f, float g, InteractionHand interactionHand, float h, ItemStack itemStack, float i, PoseStack poseStack, MultiBufferSource multiBufferSource, int j, Operation<Void> original) {
        ItemStack itemStack2 = itemStack;

        if (interactionHand == InteractionHand.OFF_HAND) {
            Item checkItem = itemStack.getItem();
            if (checkItem instanceof ShieldItem || checkItem instanceof ShieldAccessoryLikeItem) {
                if (((PlayerStorages) abstractClientPlayer).getTerrariaInventory().getItem(21) != ItemStack.EMPTY) {
                    itemStack2 = ((PlayerStorages) abstractClientPlayer).getTerrariaInventory().getItem(21);
                }
            }
        }
        if (abstractClientPlayer.getUsedItemHand() == InteractionHand.OFF_HAND) {
            Item checkItem = abstractClientPlayer.getUseItem().getItem();
            if (checkItem instanceof ShieldItem || checkItem instanceof ShieldAccessoryLikeItem) {
                if (((PlayerStorages) abstractClientPlayer).getTerrariaInventory().getItem(21) != ItemStack.EMPTY) {
                    abstractClientPlayer.useItem = ((PlayerStorages) abstractClientPlayer).getTerrariaInventory().getItem(21);
                }
            }
        }

        original.call(instance, abstractClientPlayer, f, g, interactionHand, h, itemStack2, i, poseStack, multiBufferSource, j);
    }

    @WrapMethod(method = "renderItem")
    private void shieldDye(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext itemDisplayContext, boolean bl, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, Operation<Void> original) {
        HumanoidArm humanoidArm = livingEntity.getMainArm().getOpposite();
        boolean bl3 = humanoidArm == HumanoidArm.RIGHT;
        if (livingEntity instanceof Player player && itemDisplayContext.equals(bl3 ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND)) {
            if (!itemStack.isEmpty()) {
                if (itemStack.getItem() instanceof ShieldItem || itemStack.getItem() instanceof ShieldAccessoryLikeItem) {
                    if (((PlayerStorages) player).getTerrariaInventory().getItem(22).getItem() instanceof BasicDye dye) {
                        ItemStackRenderState scratchItemStackRenderState = new ItemStackRenderState();
                        new ItemModelResolver(Minecraft.getInstance().getModelManager()).updateForTopItem(scratchItemStackRenderState, itemStack, itemDisplayContext, bl, livingEntity.level(), livingEntity, livingEntity.getId() + itemDisplayContext.ordinal());
                        Utilities.renderItemCustomDye(scratchItemStackRenderState, poseStack, multiBufferSource, i, OverlayTexture.NO_OVERLAY, dye.getColourInt());
                        return;
                    }
                }
            }
        }

        original.call(livingEntity, itemStack, itemDisplayContext, bl, poseStack, multiBufferSource, i);
    }

    // Copied from FabricShieldLib, credits to Starexify (Nova on disc) for the fix (I was going to change the json rotation which would probably be better but that would have taken forever)
    @WrapOperation(
            method = "renderArmWithItem",
            constant = @Constant(classValue = ShieldItem.class)
    )
    private boolean wrapInstanceCheck(Object instance, Operation<Boolean> original) {
        return original.call(instance) || instance instanceof ShieldAccessoryLikeItem || instance instanceof UmbrellaItem;
    }
}
